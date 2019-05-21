package entidades;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(
        name = "Vocabulario"
)
public class VocabularioEntity {
    @Id
    private String palabra;
    @Column
    private int cantDoc;
    @OneToMany(
            mappedBy = "vocabulario"
    )
    private List<PosteoEntity> posteos;
    @Column
    private int maxVecesEnDoc;

    public VocabularioEntity() {
    }

    public List<PosteoEntity> getPosteos() {
        return this.posteos;
    }

    public String getPalabra() {
        return this.palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getCantDoc() {
        return this.cantDoc;
    }

    public void setCantDoc(int nr) {
        this.cantDoc = this.cantDoc;
    }

    public int getMaxVecesEnDoc() {
        return this.maxVecesEnDoc;
    }

    public void setMaxVecesEnDoc(int maxVecesEnDoc) {
        this.maxVecesEnDoc = maxVecesEnDoc;
    }

    public String toString() {
        return "VocabularioEntity{palabra='" + this.palabra + '\'' + ", nr=" + this.cantDoc + ", maxtf=" + this.maxVecesEnDoc + '}';
    }
}
