package entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PruebasBD {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocumentosPU");
        EntityManager em = emf.createEntityManager();

        /*VocabularioEntity vocabulario = new VocabularioEntity();
        vocabulario.setPalabra("CABocaJuniors");
        vocabulario.setCantDoc(59);
        vocabulario.setMaxVecesEnDoc(150);

        DocumentoEntity documento = new DocumentoEntity();
        documento.setTitulo("Harry Potter 99999");
        documento.setUrl("linkaASDzo");
*/
        PosteoEntity posteo = new PosteoEntity("mojarrita",2,9);
        //posteo.setVocabulario(vocabulario);
        //posteo.setDocumento(documento);
        //posteo.setVecesEnDoc(9);

        em.getTransaction().begin();
//        em.persist(vocabulario);
//        em.persist(documento);
        em.persist(posteo);
        em.getTransaction().commit();

        em.close();

    }
}
