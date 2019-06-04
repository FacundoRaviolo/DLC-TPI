import java.io.File;
        import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LectorPalabras {
    public LectorPalabras() {
    }


    /**
     * Este método recibe un archivo de texto y en base a sus palabras las carga en una lista de posteo.
     * @param f archivo del cual se obtienen las palabras.
     * @param listaPosteo HashMap que contiene como clave la palabra y la cantidad de veces que aparece en el documento.
     */
    public void readFile(File f, HashMap listaPosteo)
    {
        try
        {
            Scanner sc = new Scanner(f,"ISO-8859-1");
            sc.useDelimiter("'*[^\\p{IsAlphabetic}']+'*");
            String words;

            while (sc.hasNext())
            {
                words = sc.next();
                words = words.toLowerCase();
                if (words.length() >= 1)
                {
                    if (listaPosteo.containsKey(words))
                    {
                        int num = (int) listaPosteo.get(words);
                        listaPosteo.put(words, num + 1);
                    }
                    else {
                        listaPosteo.put(words, 1);
                    }
                }
            }

            sc.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}