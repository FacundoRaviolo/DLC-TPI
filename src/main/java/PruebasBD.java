import entidades.VocabularioEntity;

import java.io.File;
import java.util.Hashtable;

public class PruebasBD {


    public static void main(String[] args) {

        Hashtable<String,VocabularioEntity> tablaHash = new Hashtable<>();

        Parseo parseo = new Parseo();
        File file1 = new File("DocumentosTP/0ddc809a.txt");
       // parseo.parseador(file1,tablaHash);
        File carpeta = new File("DocumentosTP");
        parseo.ObtenerDatosDocumento(carpeta,tablaHash);
        System.out.println(tablaHash.get("and"));
        System.out.println(tablaHash.size());


        //File file2 = new File("DocumentosTP/ttnic10.txt");
        //parseo.parseador(file2,tablaHash);

        /*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocumentosPU");
        EntityManager em = emf.createEntityManager();

        VocabularioEntity vocabulario = new VocabularioEntity();
        vocabulario.setPalabra("CABocaJuniors");
        vocabulario.setCantDoc(59);
        vocabulario.setMaxVecesEnDoc(150);

        DocumentoEntity documento = new DocumentoEntity();
        documento.setTitulo("Harry Potter 99999");
        documento.setUrl("linkaASDzo");

        PosteoEntity posteo = new PosteoEntity("mojarrita",3,9);
        posteo.setVocabulario(vocabulario);
        posteo.setDocumento(documento);
        posteo.setVecesEnDoc(9);

        em.getTransaction().begin();
        em.persist(vocabulario);
        em.persist(documento);
        em.persist(posteo);
        em.getTransaction().commit();

        em.close();
        */
    }
}
