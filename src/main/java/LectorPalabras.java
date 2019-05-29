import java.io.File;
        import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LectorPalabras {
    public LectorPalabras() {
    }

    public void readFile(File f, HashMap listaPosteo)
    {
        try
        {
            Scanner sc = new Scanner(f,"ISO-8859-1");
            //sc.useDelimiter("[- \n\r/_,¡!@¿?.:«»;*\"º\\[\\]()=°ª%$#0123456789`}]+");
            sc.useDelimiter("[^\\p{IsAlphabetic}']+");
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