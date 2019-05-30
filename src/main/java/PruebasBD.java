import entidades.DocumentoEntity;
import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

public class PruebasBD {


    public static void main(String[] args) throws Exception {

        Persistencia persistencia = new Persistencia();
        EntityManager em = persistencia.crearPersistencia();

        Hashtable<String,VocabularioEntity> tablaHash = new Hashtable<>();

        Parseo parseo = new Parseo();
        File carpeta = new File("DocumentosTP");
        parseo.ObtenerDatosDocumento(em,carpeta,tablaHash);

        persistencia.persistirVocabulario(em,tablaHash);

        persistencia.persistirPosteo(em,carpeta);
        persistencia.cerrarPersistencia(em);


/*      HashMap<String,Integer> listaPosteo = new HashMap<String,Integer>();

        //File file1 = new File("DocumentosTP/0ddc809a.txt");
        //parseo.parseador(file1,tablaHash);

        //System.out.println(tablaHash.get("and"));
        // System.out.println(tablaHash.size());
*/
    }
}
