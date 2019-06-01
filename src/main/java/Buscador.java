import entidades.DocumentoEntity;
import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.*;

public class Buscador implements Comparator{
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
                double division = (cantDocTotal/cantDoc);
                double log = Math.log(cantDocTotal/cantDoc);
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
            //System.out.println(cadena);
        }
        return listaResultados;
    }

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

    public void openWebPage(String url){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public int compare(Object o1, Object o2) {
        VocabularioEntity v1 =(VocabularioEntity)o1;
        VocabularioEntity v2 =(VocabularioEntity)o2;
        return new Integer(v1.getCantDoc()).compareTo(new Integer(v2.getCantDoc()));
    }
}
