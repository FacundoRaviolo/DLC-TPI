import entidades.DocumentoEntity;
import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class PruebasBD {


    public static void main(String[] args) throws Exception {

        // Las siguientes instrucciones realizan la creación de la HashTable con sus datos, y almacenan en la BD los Documentos, el Vocabulario y los Posteos.
        /*
        long startTime = System.nanoTime();
        Persistencia persistencia = new Persistencia();
        EntityManager em = persistencia.crearPersistencia();
        Hashtable<String,VocabularioEntity> tablaHash = new Hashtable<>();
        Parseo parseo = new Parseo();
        File carpeta = new File("DocumentosTP");
        parseo.ObtenerDatosDocumento(em,carpeta,tablaHash,true);
        persistencia.persistirVocabulario(em,tablaHash);
        persistencia.persistirPosteo(em,carpeta);
        persistencia.cerrarPersistencia(em);
        long endTime = System.nanoTime() - startTime;
        System.out.println("Tiempo final: " + endTime);

        persistencia.serializarTabla(tablaHash);
        */
        Persistencia persistencia = new Persistencia();
        Hashtable<String,VocabularioEntity> hT1 = persistencia.leerTabla("tabla.dat");
        System.out.println("Hashtable armada");

        Buscador buscador = new Buscador();
        String consulta = "CeRn";
        consulta = consulta.toLowerCase();
        buscador.busqueda(consulta, hT1);
    }
}
