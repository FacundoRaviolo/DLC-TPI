import entidades.VocabularioEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

    // ESTO ESTÁ MAL!!!! SI YO ARMO UNA HashMap (o HashTable) Y DOS PALABRAS APARECEN EN LA MISMA CANTIDAD DE DOCUMENTOS,
    // AL SER cantDoc LA CLAVE, NO SE PODRÁN GUARDAR AMBOS!!! CORREGIR. BUSCAR OTRA MANERA.

public class Buscador {
    public void busqueda(String consulta, Hashtable<String, VocabularioEntity> tablaHash)
    {
        HashMap<Integer,VocabularioEntity> hashMap = new HashMap<>();
        for (String word : consulta.split(" "))
        {
            VocabularioEntity termino = tablaHash.get(word);
            int cantDoc = termino.getCantDoc();
            hashMap.put(cantDoc,termino);
        }

        obtencionCandidatos(hashMap);
    }

    public void obtencionCandidatos(HashMap<Integer,VocabularioEntity> hashMap)
    {
        Iterator iterator = hashMap.entrySet().iterator();
        Persistencia persistencia = new Persistencia();
        EntityManager em = persistencia.crearPersistencia();
        while (iterator.hasNext())
        {
            Map.Entry termino = (Map.Entry) iterator.next();
            VocabularioEntity voc = (VocabularioEntity)termino.getValue();
            String palabra = voc.getPalabra();
            int cantDoc = (int) termino.getKey();
            Query query = em.createQuery("SELECT (posteo.vecesEnDoc) FROM PosteoEntity posteo WHERE posteo.palabra = '" + palabra + "' ORDER BY posteo.vecesEnDoc DESC");
            List lista = query.setMaxResults(5).getResultList();
            System.out.println("Lista de " + palabra + ": " + lista.toString());
        }
        persistencia.cerrarPersistencia(em);
        System.out.println(hashMap.toString());
    }


}
