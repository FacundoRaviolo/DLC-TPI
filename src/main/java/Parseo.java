import entidades.VocabularioEntity;

import java.io.*;
import java.util.*;

public class Parseo {


    public void ObtenerDatosDocumento (File carpeta,Hashtable<String,VocabularioEntity> tablaHash) {
        for (final File ficheroEntrada : carpeta.listFiles()) {
            if (ficheroEntrada.isDirectory()) {
                ObtenerDatosDocumento(ficheroEntrada,tablaHash);
            } else {
                String nomDoc = (ficheroEntrada.getName());
                String url = carpeta + "/" + nomDoc;
                parseador(ficheroEntrada,tablaHash);
            }

        }
    }





    public void parseador(File file,Hashtable<String,VocabularioEntity> tablaHash)
    {
        //Hashtable<String,VocabularioEntity> tablaHash = new Hashtable<String, VocabularioEntity>();

        HashMap<String,Integer> listaPosteo = new HashMap<String,Integer>();
        LectorPalabras lector = new LectorPalabras();
        lector.readFile(file,listaPosteo);

        Iterator iterator = listaPosteo.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry posteo = (Map.Entry) iterator.next();
            String palabra = (String)posteo.getKey();
            int cantVeces = (int)posteo.getValue();

            if (tablaHash.containsKey(palabra)){
                VocabularioEntity voc = tablaHash.get(palabra);
                if (cantVeces > voc.getMaxVecesEnDoc()){
                    VocabularioEntity vocabulario = new VocabularioEntity(palabra,voc.getCantDoc() + 1,cantVeces);
                    tablaHash.put(palabra,vocabulario);
                }
                else{
                    VocabularioEntity vocabulario = new VocabularioEntity(palabra,voc.getCantDoc() + 1,voc.getMaxVecesEnDoc());
                    tablaHash.put(palabra,vocabulario);
                }
            }
            else{
                VocabularioEntity vocabulario = new VocabularioEntity(palabra,1,cantVeces);
                tablaHash.put(palabra,vocabulario);
            }
        }
    }
}
