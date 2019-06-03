import entidades.DocumentoEntity;
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
            em.merge(voc);
        }
        em.getTransaction().commit();
        System.out.println("Persistido el vocabulario.");
    }

    public void persistirPosteo (EntityManager em, File carpeta,int docCargados) throws IOException {

        int idDocumento = obtenerMaxIdDoc(em,true) - docCargados;

        for (final File ficheroEntrada : carpeta.listFiles())
        {
            abrirPersistencia(em);
            if (ficheroEntrada.isDirectory())
            {
                persistirPosteo(em, ficheroEntrada, docCargados);
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

    static int obtenerMaxIdDoc(EntityManager em, Boolean flag) {
        int idDocumento = 0;

        if (flag){

            Query consulta = em.createNativeQuery("SELECT Documento.idDocumento, titulo, url FROM Documento ORDER BY Documento.idDocumento DESC", DocumentoEntity.class);
            List lista = consulta.setMaxResults(1).getResultList();
            DocumentoEntity doc = (DocumentoEntity)lista.get(0);
            idDocumento = doc.getIdDocumento();

        }
        return idDocumento;
    }

    public Hashtable<String,VocabularioEntity>  cargarTabla(EntityManager em) {

        Hashtable<String,VocabularioEntity> hashTable = new Hashtable<>();

        Query consulta = em.createNativeQuery("SELECT Vocabulario.palabra, cantDoc, maxVecesEnDoc FROM Vocabulario", VocabularioEntity.class);
        List lista = consulta.getResultList();
        Iterator iterator = lista.iterator();
        while (iterator.hasNext())
        {
            VocabularioEntity voc = (VocabularioEntity)iterator.next();
            hashTable.put(voc.getPalabra(),voc);
        }
        return hashTable;
    }

    public Hashtable<String,VocabularioEntity>  leerTabla (String nombreArchivo) throws IOException, ClassNotFoundException {
        try{
            ObjectInputStream leerArchivo =  new ObjectInputStream(new FileInputStream(nombreArchivo));
            Hashtable<String,VocabularioEntity> hashTable = (Hashtable<String,VocabularioEntity>)leerArchivo.readObject();
            leerArchivo.close();
            return hashTable;
        }
        catch (Exception ex){
            throw ex;
        }
    }

}
