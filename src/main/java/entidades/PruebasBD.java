package entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PruebasBD {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocumentosPU");
        EntityManager em = emf.createEntityManager();

        VocabularioEntity vocabulario = new VocabularioEntity();
        vocabulario.setPalabra("Hola");
        vocabulario.setCantDoc(5);
        vocabulario.setMaxVecesEnDoc(100);

        DocumentoEntity documento = new DocumentoEntity();
        documento.setIdDocumento(5);
        documento.setTitulo("Harry Potter");
        documento.setUrl("link");

        PosteoEntity posteo = new PosteoEntity("Hola",5);

        em.getTransaction().begin();
        em.persist(vocabulario);
        em.persist(documento);
        em.persist(posteo);
        em.getTransaction().commit();

        em.close();

    }
}
