import java.io.*;
import java.util.*;

public class Parseo {

    /*private void armadorHT() {
        Hashtable<String, VocabularioEntity> tablaNueva=new Hashtable<String, VocabularioEntity>();
    }*/

    public void parseador()
    {
        File file = new File("0ddcc10.txt");

        HashMap<String,Integer> listaPosteo = new HashMap<String,Integer>();
        LectorPalabras lector = new LectorPalabras();
        lector.readFile(file,listaPosteo);
        System.out.println(listaPosteo);
        System.out.println(listaPosteo.size());
        System.out.println(listaPosteo.get("cary"));
    }

}
