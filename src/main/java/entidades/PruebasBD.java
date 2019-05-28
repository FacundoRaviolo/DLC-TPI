package entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PruebasBD {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocumentosPU");
        EntityManager em = emf.createEntityManager();

        VocabularioEntity vocabulario = new VocabularioEntity();
        vocabulario.setPalabra("RosadoLoL5o");
        vocabulario.setCantDoc(5);
        vocabulario.setMaxVecesEnDoc(150);

        DocumentoEntity documento = new DocumentoEntity();
        documento.setTitulo("Harry Potter 3");
        documento.setUrl("linkazo");

        PosteoEntity posteo = new PosteoEntity("Hola",1);

        em.getTransaction().begin();
        //em.persist(vocabulario);
        //em.persist(documento);
        em.persist(posteo);
        em.getTransaction().commit();

        em.close();

    }
}
