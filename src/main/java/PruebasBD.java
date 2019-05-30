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

        // Las siguientes instrucciones realizan la creación de la HashTable con sus datos, y almacenan en la BD los Documentos, el Vocabulario y los Posteos.
        //long startTime = System.nanoTime();
        Persistencia persistencia = new Persistencia();
      //  EntityManager em = persistencia.crearPersistencia();
        //Hashtable<String,VocabularioEntity> tablaHash = new Hashtable<>();
       // Parseo parseo = new Parseo();
       // File carpeta = new File("DocumentosTP");
      //  parseo.ObtenerDatosDocumento(em,carpeta,tablaHash,false);
       // persistencia.persistirVocabulario(em,tablaHash);
       // persistencia.persistirPosteo(em,carpeta);
      //  persistencia.cerrarPersistencia(em);
      //  long endTime = System.nanoTime() - startTime;
       // System.out.println("Tiempo final: " + endTime);
       // persistencia.serializarTabla(tablaHash);
/*      HashMap<String,Integer> listaPosteo = new HashMap<String,Integer>();

        //File file1 = new File("DocumentosTP/0ddc809a.txt");
        //parseo.parseador(file1,tablaHash);

        //System.out.println(tablaHash.get("and"));
        // System.out.println(tablaHash.size());

        */
        Hashtable<String,VocabularioEntity> hT1 = persistencia.leerTabla("tabla.dat");
        System.out.println(hT1.get("aber"));

    }
}
