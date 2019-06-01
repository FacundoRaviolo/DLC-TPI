import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.*;
import java.io.*;
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

    public void persistirVocabulario(EntityManager em, Hashtable<String, VocabularioEntity> tablaHash)
    {
        abrirPersistencia(em);
        Iterator it = tablaHash.keySet().iterator();
        while (it.hasNext())
        {
            String clave = (String) it.next();
            VocabularioEntity voc =  tablaHash.get(clave);
            em.persist(voc);
        }
        em.getTransaction().commit();
        System.out.println("Persistido el vocabulario.");
    }

    /*public void persistirVocabulario(EntityManager em, Hashtable<String, VocabularioEntity> tablaHash){
        abrirPersistencia(em);
        Set<Map.Entry<String,VocabularioEntity>> se = tablaHash.entrySet();
        Iterator<Map.Entry<String,VocabularioEntity>> it = se.iterator();
        while(it.hasNext())
        {
            Map.Entry<String,VocabularioEntity> entry = it.next();
            //em.persist(entry.getValue());
            String palabra = (String)entry.getKey();
            int cantDoc = (int)entry.getValue().getCantDoc();
            int maxVeces = (int)entry.getValue().getMaxVecesEnDoc();
            VocabularioEntity voc = new VocabularioEntity(palabra,cantDoc,maxVeces);
            em.persist(voc);
        }
        em.getTransaction().commit();
        System.out.println("Persistido el vocabulario");
    }*/

    public void persistirPosteo (EntityManager em, File carpeta) throws IOException {

        int idDocumento = 0;
        for (final File ficheroEntrada : carpeta.listFiles())
        {
            abrirPersistencia(em);
            if (ficheroEntrada.isDirectory())
            {
                persistirPosteo(em, ficheroEntrada);
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
            }
            System.out.println("Guardado el documento número " + idDocumento);
            em.getTransaction().commit();
            em.clear();
        }


    }

    public void  serializarTabla (Hashtable hashtable) throws IOException {
        try{
            ObjectOutputStream grabarArchivo = new ObjectOutputStream(new FileOutputStream("tabla.dat"));
            grabarArchivo.writeObject(hashtable);
            grabarArchivo.close();
            System.out.println("****Tabla grabada en el archivo****");
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public Hashtable<String,VocabularioEntity>  leerTabla (String nombreArchivo) throws IOException, ClassNotFoundException {
        try{
            ObjectInputStream leerArchivo =  new ObjectInputStream(new FileInputStream(nombreArchivo));
            Hashtable<String,VocabularioEntity> hashTable = (Hashtable<String,VocabularioEntity>)leerArchivo.readObject();
            leerArchivo.close();
            System.out.println("****Tabla leida del archivo****");
            return hashTable;
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
