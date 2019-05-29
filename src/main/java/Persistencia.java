import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Persistencia {

    public EntityManager crearPersistencia()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocumentosPU");
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public void abrirPersistencia(EntityManager em)
    {
        em.getTransaction().begin();
    }

    public void cerrarPersistencia(EntityManager em)
    {
        em.close();
    }

    public void persistirVocabulario(EntityManager em, Hashtable<String, VocabularioEntity> tablaHash) throws Exception {
        int i = 0;
        abrirPersistencia(em);
        Iterator it = tablaHash.keySet().iterator();
        while (it.hasNext())
        {
            String clave = (String) it.next();
            VocabularioEntity voc =  tablaHash.get(clave);

            if (!voc.getPalabra().equals(clave)){
                System.out.println(clave +"-"+ voc.getPalabra());
                throw new Exception("Entry y clave inconsistentes");
            }
            //VocabularioEntity voc = tablaHash.get(it.next());
            em.persist(voc);
            i++;
            if (i%10000 == 0){
                em.getTransaction().commit();
                abrirPersistencia(em);
            }
        }
        em.getTransaction().commit();
        System.out.println("Persistido el vocabulario");



        /*Set<Map.Entry<String,VocabularioEntity>> se = tablaHash.entrySet();
        Iterator<Map.Entry<String,VocabularioEntity>> it = se.iterator();
        while(it.hasNext())
        {
            Map.Entry<String,VocabularioEntity> entry = it.next();
            em.persist(entry.getValue());
            System.out.println("PALABRA " + entry.getKey());
        }
        em.getTransaction().commit();
        System.out.println("Persistido el vocabulario");*/
    }

    public void persistirPosteo (EntityManager em, File carpeta, Hashtable<String,VocabularioEntity> tablaHash) throws IOException {

        Persistencia persistencia = new Persistencia();
        int idDocumento = 0;
        for (final File ficheroEntrada : carpeta.listFiles())
        {
            persistencia.abrirPersistencia(em);
            if (ficheroEntrada.isDirectory())
            {
                persistirPosteo(em, ficheroEntrada,tablaHash);
            }
            else
            {
                idDocumento++;
                HashMap<String,Integer> listaPosteo = new HashMap<String,Integer>();
                LectorPalabras lector = new LectorPalabras();
                lector.readFile(ficheroEntrada,listaPosteo);
                Iterator iterator = listaPosteo.entrySet().iterator();
                while (iterator.hasNext())
                {
                    Map.Entry posteo = (Map.Entry) iterator.next();
                    String palabra = (String) posteo.getKey();
                    int cantVeces = (int) posteo.getValue();
                    PosteoEntity post = new PosteoEntity(palabra,idDocumento,cantVeces);
                    em.persist(post);
                }
                em.getTransaction().commit();

            }
        }

    }

}
