package entidades;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Posteo")
@IdClass(PosteoID.class)
public class PosteoEntity {

    @Id
    private String palabra;

    @Id
    private int idDocumento;

    private int vecesEnDoc;

    @ManyToOne
    @JoinColumn(name = "palabra", referencedColumnName = "palabra", insertable = false, updatable = false)
    private VocabularioEntity vocabulario;

    @ManyToOne
    @JoinColumn(name = "idDocumento", referencedColumnName = "idDocumento", insertable = false, updatable = false)
    private DocumentoEntity documento;

    public PosteoEntity() {

    }

    public PosteoEntity(String palabra, int idDocumento, int vecesEnDoc) {
        this.palabra = palabra;
        this.idDocumento = idDocumento;
        this.vecesEnDoc = vecesEnDoc;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public int getVecesEnDoc() {
        return vecesEnDoc;
    }

    public void setVecesEnDoc(int vecesEnDoc) {
        this.vecesEnDoc = vecesEnDoc;
    }

    public VocabularioEntity getVocabulario() {
        return vocabulario;
    }

    public void setVocabulario(VocabularioEntity vocabulario) {
        this.vocabulario = vocabulario;
    }

    public DocumentoEntity getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoEntity documento) {
        this.documento = documento;
    }

    @Override
    public String toString() {
        return "Posteo [palabra=" + palabra + ", documento=" + idDocumento + ", veces=" + vecesEnDoc
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PosteoEntity that = (PosteoEntity) o;
        return Objects.equals(vocabulario, that.vocabulario) &&
                Objects.equals(documento, that.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vocabulario, documento);
    }

}
