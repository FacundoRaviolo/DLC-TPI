package entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class PosteoID implements Serializable {

    @Column(name = "palabra")
    private String palabra;

    @Column(name = "idDocumento")
    private int idDocumento;

    public PosteoID() {

    }

    public PosteoID(String palabra, int idDocumento) {
        this.palabra = palabra;
        this.idDocumento = idDocumento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PosteoID that = (PosteoID) o;
        return Objects.equals(palabra, that.palabra) &&
                Objects.equals(idDocumento, that.idDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(palabra, idDocumento);
    }

    @Override
    public String toString() {
        return "ProfesormoduloId [palabra=" + palabra + ", idDoc=" + idDocumento + "]";
    }

}
