package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Documento")
public class DocumentoEntity implements Serializable {
    @Id
    private int idDocumento;

    @Column
    private String titulo;

    @Column
    private String url;

    @OneToMany(mappedBy = "documento")
    private List<PosteoEntity> posteo = new ArrayList<>();

    public DocumentoEntity() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdDocumento() {
        return idDocumento;
    }


    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public DocumentoEntity(int idDocumento,String titulo, String url) {
        this.idDocumento = idDocumento;
        this.titulo = titulo;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoEntity documento = (DocumentoEntity) o;
        return Objects.equals(idDocumento, documento.idDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocumento);
    }

    @Override
    public String toString() {
        return "DocumentoEntity{" +
                "id=" + idDocumento +
                ", titulo='" + titulo + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
