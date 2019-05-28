package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Vocabulario")
public class VocabularioEntity implements Serializable {
    @Id
    private String palabra;
    @Column
    private int cantDoc;
    @Column
    private int maxVecesEnDoc;

    @OneToMany(mappedBy = "vocabulario")
    private List<PosteoEntity> posteo = new ArrayList<>();


    public VocabularioEntity() {
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

    public void setCantDoc(int cantDoc) {
        this.cantDoc = cantDoc;
    }

    public int getMaxVecesEnDoc() {
        return this.maxVecesEnDoc;
    }

    public void setMaxVecesEnDoc(int maxVecesEnDoc) {
        this.maxVecesEnDoc = maxVecesEnDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        VocabularioEntity post = (VocabularioEntity) o;
        return Objects.equals(palabra, post.palabra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(palabra);
    }

    public String toString() {
        return "VocabularioEntity{palabra='" + this.palabra + '\'' + ", nr=" + this.cantDoc + ", maxtf=" + this.maxVecesEnDoc + '}';
    }
}
