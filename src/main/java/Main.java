import entidades.VocabularioEntity;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class Main {


    public static void main(String[] args) throws IOException {
        Persistencia persistencia = new Persistencia();
        EntityManager em = persistencia.crearPersistencia();
        persistencia.abrirPersistencia(em);
        Hashtable<String, VocabularioEntity> tablaHash = new Hashtable<>();
        Parseo parseo = new Parseo();
        File carpeta = new File("DocumentosAdd");
        parseo.ObtenerDatosDocumento(em,carpeta,tablaHash,true);

    }
}