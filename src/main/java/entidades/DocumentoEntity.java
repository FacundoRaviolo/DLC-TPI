package entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Documento")
public class DocumentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDocumento;

    @Column
    private String titulo;

    @Column
    private String url;

    @OneToMany(mappedBy = "documento")
    private List<PosteoEntity> posteos;

    public List<PosteoEntity> getPosteos(){
        return posteos;
    }


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

    @Override
    public String toString() {
        return "DocumentoEntity{" +
                "id=" + idDocumento +
                ", titulo='" + titulo + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
