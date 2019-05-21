package entidades;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(
        name = "Posteo"
)
public class PosteoEntity {
    @EmbeddedId
    PosteoIdentity posteoIdentity;
    @Column
    private int vecesEnDoc;
    @ManyToOne
    @MapsId("palabra")
    @JoinColumn(
            name = "palabra"
    )
    VocabularioEntity vocabulario;
    @ManyToOne
    @MapsId("idDocumento")
    @JoinColumn(
            name = "idDocumento"
    )
    DocumentoEntity documento;

    public PosteoEntity(String palabra, int idDocumento) {
        this.posteoIdentity = new PosteoIdentity(palabra, idDocumento);
    }

    public PosteoIdentity getPosteoIdentity() {
        return this.posteoIdentity;
    }

    public void setPosteoIdentity(PosteoIdentity posteoIdentity) {
        this.posteoIdentity = posteoIdentity;
    }

    public int getVecesEnDoc() {
        return this.vecesEnDoc;
    }

    public void setVecesEnDoc(int vecesEnDoc) {
        this.vecesEnDoc = vecesEnDoc;
    }

    public String getPalabra() {
        return this.posteoIdentity.getPalabra();
    }

    public void setVocabulario(VocabularioEntity vocabulario) {
        this.vocabulario = vocabulario;
    }

    public DocumentoEntity getDocumento() {
        return this.documento;
    }

    public void setLibro(DocumentoEntity documento) {
        this.documento = documento;
    }
}
