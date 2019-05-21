package entidades;

import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class PosteoIdentity implements Serializable {
    @NotNull
    private String palabra;
    @NotNull
    private int idLibro;

    public String getPalabra() {
        return this.palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getIdLibro() {
        return this.idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public PosteoIdentity(String palabra, int idLibro) {
        this.palabra = palabra;
        this.idLibro = idLibro;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            PosteoIdentity that = (PosteoIdentity)o;
            return !this.palabra.equals(that.palabra) ? false : this.palabra.equals(that.palabra);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.palabra.hashCode();
        result = 31 * result + this.palabra.hashCode();
        return result;
    }
}
