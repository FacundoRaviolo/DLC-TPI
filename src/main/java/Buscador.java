import entidades.VocabularioEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

public class Buscador implements Comparator{
    public void busqueda(String consulta, Hashtable<String, VocabularioEntity> tablaHash)
    {
        ArrayList<VocabularioEntity> listaVoc = new ArrayList<VocabularioEntity>();
        for (String word : consulta.split(" "))
        {
            VocabularioEntity termino = tablaHash.get(word);
            listaVoc.add(termino);

        }
        Buscador buscador = new Buscador();
        Collections.sort(listaVoc,buscador);

        obtencionCandidatos(listaVoc);
    }

    public void obtencionCandidatos(ArrayList<VocabularioEntity> arrayList)
    {
        Iterator iterator = arrayList.iterator();
        Persistencia persistencia = new Persistencia();
        EntityManager em = persistencia.crearPersistencia();
        while (iterator.hasNext())
        {
            //Map.Entry termino = (Map.Entry) iterator.next();
            VocabularioEntity voc = (VocabularioEntity)iterator.next();
            String palabra = voc.getPalabra();
            //int cantDoc = (int) voc.getCantDoc();
            Query query = em.createQuery("SELECT (posteo.idDocumento) FROM PosteoEntity posteo WHERE posteo.palabra = '" + palabra + "' ORDER BY posteo.vecesEnDoc DESC");
            List lista = query.setMaxResults(5).getResultList();
            System.out.println("Lista de " + palabra + ": " + lista.toString());
        }
        persistencia.cerrarPersistencia(em);
        //System.out.println(arrayList.toString());
    }


    @Override
    public int compare(Object o1, Object o2) {
        VocabularioEntity v1 =(VocabularioEntity)o1;
        VocabularioEntity v2 =(VocabularioEntity)o2;
        return new Integer(v1.getCantDoc()).compareTo(new Integer(v2.getCantDoc()));
    }
}
