import entidades.DocumentoEntity;
import entidades.VocabularioEntity;
import javax.persistence.*;
import java.io.*;
import java.util.*;

public class Parseo {

    public int ObtenerDatosDocumento (EntityManager em, File carpeta, Hashtable<String,VocabularioEntity> tablaHash, Boolean huboCargaAnterior) throws IOException {

        int idDocumento = Persistencia.obtenerMaxIdDoc(em, huboCargaAnterior);
        int docIniciales = idDocumento;

        for (final File ficheroEntrada : carpeta.listFiles())
        {

            if (ficheroEntrada.isDirectory())
            {
                ObtenerDatosDocumento(em, ficheroEntrada,tablaHash,huboCargaAnterior);
            }

            else
            {
                idDocumento++;
                String nomDoc = (ficheroEntrada.getName());
                String url = carpeta + "/" + nomDoc;
                BufferedReader brTest = new BufferedReader(new FileReader(ficheroEntrada));
                String titulo = brTest.readLine();
                if (titulo.length() < 1)
                {
                    titulo = brTest.readLine();
                }
                DocumentoEntity documento = new DocumentoEntity(idDocumento,titulo,url);
                em.persist(documento);
                System.out.println("Guardado en BD el documento número " + idDocumento);
                parseador(ficheroEntrada,tablaHash);
            }
        }
        int docFinales = idDocumento;
        int docCargados = docFinales - docIniciales;
        em.getTransaction().commit();
        return docCargados;
    }

    public void parseador(File file,Hashtable<String,VocabularioEntity> tablaVocabulario)
    {
        //Hashtable<String,VocabularioEntity> tablaHash = new Hashtable<String, VocabularioEntity>();
        HashMap<String,Integer> listaPosteo = new HashMap<>();
        LectorPalabras lector = new LectorPalabras();
        lector.readFile(file,listaPosteo);

        Iterator iterator = listaPosteo.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry posteo = (Map.Entry) iterator.next();
            String palabra = (String)posteo.getKey();
            int cantVeces = (int)posteo.getValue();

            if (tablaVocabulario.containsKey(palabra)){
                VocabularioEntity voc = tablaVocabulario.get(palabra);
                if (cantVeces > voc.getMaxVecesEnDoc()){
                    VocabularioEntity vocabulario = new VocabularioEntity(palabra,voc.getCantDoc() + 1,cantVeces);
                    tablaVocabulario.put(palabra,vocabulario);
                }
                else{
                    VocabularioEntity vocabulario = new VocabularioEntity(palabra,voc.getCantDoc() + 1,voc.getMaxVecesEnDoc());
                    tablaVocabulario.put(palabra,vocabulario);
                }
            }
            else{
                VocabularioEntity vocabulario = new VocabularioEntity(palabra,1,cantVeces);
                tablaVocabulario.put(palabra,vocabulario);
            }
        }
    }

}
