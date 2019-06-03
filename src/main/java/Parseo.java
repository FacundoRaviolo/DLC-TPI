import entidades.DocumentoEntity;
import entidades.VocabularioEntity;
import javax.persistence.*;
import java.io.*;
import java.util.*;

public class Parseo {


   /* public void ObtenerDatosDocumento (EntityManager em, File carpeta,Hashtable<String,VocabularioEntity> tablaHash,Boolean flag) throws IOException {

        Persistencia persistencia = new Persistencia();
        persistencia.abrirPersistencia(em);
        int idDocumento = 0;
        for (final File ficheroEntrada : carpeta.listFiles())
        {

            if (ficheroEntrada.isDirectory())
            {
                ObtenerDatosDocumento(em, ficheroEntrada,tablaHash,flag);
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
                if (flag){em.persist(documento);}
                System.out.println("Guardado en BD el documento número " + idDocumento);
                parseador(ficheroEntrada,tablaHash);
            }
        }
        em.getTransaction().commit();
    }

*/
    public void ObtenerDatosDocumento (EntityManager em, File carpeta,Hashtable<String,VocabularioEntity> tablaHash,Boolean flag) throws IOException {

        int idDocumento = 0;

        if (flag){

            Query consulta = em.createNativeQuery("SELECT Documento.idDocumento FROM Documento ORDER BY idDocumento DESC",DocumentoEntity.class);
            idDocumento = (int)consulta.getFirstResult();
            System.out.println(idDocumento);

        }

        for (final File ficheroEntrada : carpeta.listFiles())
        {

            if (ficheroEntrada.isDirectory())
            {
                ObtenerDatosDocumento(em, ficheroEntrada,tablaHash,flag);
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
        em.getTransaction().commit();
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
