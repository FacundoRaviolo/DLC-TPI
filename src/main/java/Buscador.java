import entidades.DocumentoEntity;
import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

public class Buscador implements Comparator{

    /**
     * Este método busca las palabras de la consulta en la hashtable de Vocabulario y añade los objetos a una nueva lista.
     * Luego llama al método "obtencionCandidatos".
     * @param consulta la cadena con la consulta ingresada.
     * @param tablaHash la hashtable del Vocabulario.
     * @param em el EntityManager que ha sido abierto anteriormente.
     * @return devuelve una lista con el resultado de la búsqueda (información de los documentos más relacionados).
     */
    public ArrayList<String> busqueda(String consulta, Hashtable<String, VocabularioEntity> tablaHash,EntityManager em)
    {
        ArrayList<VocabularioEntity> listaVoc = new ArrayList<VocabularioEntity>();
        for (String word : consulta.split(" "))
        {
            if (tablaHash.containsKey(word))
            {
                VocabularioEntity termino = tablaHash.get(word);
                listaVoc.add(termino);
            }
        }
        Buscador buscador = new Buscador();
        Collections.sort(listaVoc,buscador);

        ArrayList<String> listaResultados = obtencionCandidatos(listaVoc,em);
        return listaResultados;
    }

    /**
     * Este método recibe una lista de palabras, para cada una de ellas busca sus 5 mejores documentos y los añade a una lista.
     * Calcula el peso del documento para cada término y los suma. Luego llama al método "ordenamientoHashMap". Si la lista
     * que se genera está vacía, lo informa. Sino, llama al método "mostrarDatosDocumentos" y luego retorna la lista de resultados.
     * @param arrayList lista con los objetos de Vocabulario de las palabras de la consulta.
     * @param em el EntityManager que ha sido abierto anteriormente.
     * @return devuelve una lista con el resultado de la búsqueda (información de los documentos más relacionados).
     */
    public ArrayList<String> obtencionCandidatos(ArrayList<VocabularioEntity> arrayList,EntityManager em)
    {
        Iterator iterator = arrayList.iterator();
        HashMap<Integer,Double> listaDocumentosPeso = new HashMap<>();
        ArrayList<String> listaResultados = new ArrayList<>();

        Query consulta = em.createQuery("SELECT COUNT(documento.idDocumento) FROM DocumentoEntity documento");
        long cantDocTotal = (long)consulta.getSingleResult();
        while (iterator.hasNext())
        {
            VocabularioEntity voc = (VocabularioEntity)iterator.next();
            String palabra = voc.getPalabra();
            double cantDoc = (double) voc.getCantDoc();
            Query query = em.createNativeQuery("SELECT Posteo.idDocumento, palabra, vecesEnDoc FROM Posteo WHERE palabra = ?1 ORDER BY vecesEnDoc DESC", PosteoEntity.class);
            List lista = query.setParameter(1,palabra).setMaxResults(5).getResultList();
            Iterator iteratorLista = lista.iterator();
            while (iteratorLista.hasNext())
            {
                PosteoEntity posteo = (PosteoEntity)iteratorLista.next();
                int idDoc = (int) posteo.getIdDocumento();
                int cantVeces = (int) posteo.getVecesEnDoc();
                Double peso = (cantVeces * Math.log(cantDocTotal/cantDoc));
                if (listaDocumentosPeso.containsKey(idDoc))
                {
                    Double pesoAnt = (Double)listaDocumentosPeso.get(idDoc);
                    listaDocumentosPeso.put(idDoc,pesoAnt+peso);
                }
                else
                {
                    listaDocumentosPeso.put(idDoc,peso);
                }
            }

        }
        LinkedHashMap listaOrden = ordenamientoHashMap(listaDocumentosPeso);
        if (listaOrden.isEmpty())
        {
            listaResultados.add("No se encontraron resultados para la búsqueda realizada.");
        }
        else
        {
            listaResultados = mostrarDatosDocumentos(listaOrden,em);
        }
        return listaResultados;
    }

    /**
     * Este método recibe una HashMap con el ID del documento y su peso, y la devuelve ordenada según su peso (de mayor a menor).
     * @param hashMap una HashMap con el peso de cada documento.
     * @return devuelve una LinkedHashMap ordenada por peso de mayor a menor.
     */
    public LinkedHashMap<Integer, Double> ordenamientoHashMap(HashMap<Integer, Double> hashMap) {
        List<Integer> listadoId = new ArrayList<>(hashMap.keySet());
        List<Double> listadoPeso = new ArrayList<>(hashMap.values());
        Collections.sort(listadoId, Collections.<Integer>reverseOrder());
        Collections.sort(listadoPeso,Collections.<Double>reverseOrder());

        LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<>();

        Iterator<Double> valueIt = listadoPeso.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<Integer> keyIt = listadoId.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Double comp1 = hashMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    /**
     * Este método recibe la lista de documentos con sus pesos, y genera una ArrayList con las cadenas correspondientes a
     * la información de cada documento.
     * @param listaFinal recibe la lista de todos los documentos candidatos con sus pesos.
     * @param em el EntityManager que ha sido abierto anteriormente.
     * @return devuelve una ArrayList con la información de cada documento a mostrar.
     */
    public ArrayList<String> mostrarDatosDocumentos(HashMap<Integer, Double> listaFinal, EntityManager em)
    {
        int numero = 0;
        ArrayList<String> listaResultados = new ArrayList<>();

        Iterator iterator = listaFinal.entrySet().iterator();
        while (iterator.hasNext())
        {
            if (numero < 5)
            {
                numero++;
                Map.Entry documento = (Map.Entry) iterator.next();
                int id = (int)documento.getKey();
                double peso = (double)documento.getValue();
                Query query = em.createNativeQuery("SELECT Documento.idDocumento, titulo, url FROM Documento WHERE idDocumento = ?1", DocumentoEntity.class);
                Object obj = query.setParameter(1,id).getSingleResult();
                DocumentoEntity doc = (DocumentoEntity) obj;
                String cadena = "<< " + doc.getTitulo().toUpperCase() + " >>\n• ID: " + doc.getIdDocumento() + "\n• URL: " + doc.getUrl() + "\n• Peso del documento: " + peso;
                listaResultados.add(cadena);
            }
            else
            {
                break;
            }
        }
        return listaResultados;
    }

    /**
     * Este método recibe una URL, convierte en URI y abre.
     * @param url recibe una URL del documento que se desea abrir.
     */
    public void openWebPage(String url){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Implementa el método "compare" de la clase Comparator para convertir los objetos en VocabularioEntity. Este método
     * se utilizará luego para comparar y ordenar.
     * @param o1 objeto 1
     * @param o2 objeto 2
     * @return devuelve un valor negativo si la cantDoc de v1 es menor que la cantDoc de v2. Al revés devuelve positivo.
     */
    @Override
    public int compare(Object o1, Object o2) {
        VocabularioEntity v1 =(VocabularioEntity)o1;
        VocabularioEntity v2 =(VocabularioEntity)o2;
        return new Integer(v1.getCantDoc()).compareTo(new Integer(v2.getCantDoc()));
    }
}
