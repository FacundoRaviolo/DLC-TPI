import entidades.DocumentoEntity;
import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.*;
import java.io.*;
import java.util.*;

public class Persistencia {


    /**
     * Este método crea el EntityManagerFactory y el EntityManager.
     * @return el EntityManager creado.
     */
    public EntityManager crearPersistencia()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocumentosPU");
        EntityManager em = emf.createEntityManager();
        return em;
    }


    /**
     * Este método inicia una transacción.
     * @param em el EntityManager anteriormente creado.
     */
    public void abrirPersistencia(EntityManager em)
    {
        em.getTransaction().begin();
    }

    /**
     * Este método cierra el EntityManager.
     * @param em el EntityManager anteriormente creado.
     */
    public void cerrarPersistencia(EntityManager em)
    {
        em.close();
    }


    /**
     * Este método recibe una tablaHash con el Vocabulario cargado y lo persiste en la Base de Datos.
     * @param em el EntityManager anteriormente creado.
     * @param tablaHash tablaHash que contiene objetos VocabularioEntity.
     */
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


    /**
     * Este método recibe una carpeta con documentos y arma la lista de posteo la cual la persiste en la Base de Datos
     * @param em el EntityManager anteriormente creado.
     * @param carpeta carpeta que es recorrida recursivamente para obtener sus documentos
     * @param docCargados cantidad de documentos cargados con anterioridad.
     * @throws IOException
     */
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


    /**
     * Este método obtiene el número de id del último documento cargado en la Base de Datos.
     * @param em el EntityManager creado anteriormente.
     * @param flag es True si la Base de Datos ya contiene Documentos, False en el caso contrario.
     * @return el número de id del último documento cargado en la Base de Datos.
     */
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


    /**
     * Este método recupera los Vocabularios cargados en la Base de Datos y los coloca en una HashTable para aumentar
     * la velocidad de las consultas.
     * @param em el EntityManager creado anteriormente.
     * @return la HashTable con el Vocabulario.
     */
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


    /**
     * Este método recupera los Vocabularios cargados en un archivo binario y los coloca en una HashTable para aumentar
     * la velocidad de las consultas.
     * @param nombreArchivo archivo a recuperar.
     * @return la HashTable con el Vocabulario.
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    public void  serializarTabla (Hashtable hashtable) throws IOException {
        try{
            ObjectOutputStream grabarArchivo = new ObjectOutputStream(new FileOutputStream("tabla.dat"));
            grabarArchivo.writeObject(hashtable);
            grabarArchivo.close();
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
