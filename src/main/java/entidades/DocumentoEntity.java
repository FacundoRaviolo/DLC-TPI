package entidades;

@javax.persistence.Entity
@javax.persistence.Table(name = "Documento")
public class DocumentoEntity {
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private int idDocumento;
    @javax.persistence.Column
    private java.lang.String titulo;
    @javax.persistence.OneToMany(mappedBy = "documento")
    private java.util.List<entidades.PosteoEntity> posteos;

    public java.util.List<entidades.PosteoEntity> getPosteos() { /* compiled code */ }

    public DocumentoEntity() { /* compiled code */ }

        public int getIdDocumento() { /* compiled code */ }

    public void setIdDocumento(int idDocumento) { /* compiled code */ }

    public java.lang.String getTitulo() { /* compiled code */ }

    public void setTitulo(java.lang.String titulo) { /* compiled code */ }

    public java.lang.String toString() { /* compiled code */ }
}