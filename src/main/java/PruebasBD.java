import entidades.DocumentoEntity;
import entidades.PosteoEntity;
import entidades.VocabularioEntity;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        System.out.println("Armando la hashtable de vocabulario...");
        final Hashtable<String,VocabularioEntity> vocabulario = persistencia.leerTabla("tabla.dat");
        System.out.println("Hashtable armada con éxito.");

        JFrame interfaz = new JFrame();

        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setTitle("BOKE Buscador");

        final ImageIcon imagen = new ImageIcon("C:/Users/levia/OneDrive/Documentos/Facultad/DLC/DLC-TPI/Boton.png");
        JLabel labelBusqueda = new JLabel(){
            public void fondo(Graphics g){
                g.drawImage(imagen.getImage(),0,0,null);
                fondo(g);
            }
        };
        labelBusqueda.setBounds(10,50,100,30);
        labelBusqueda.setOpaque(false);
        interfaz.getContentPane().add(labelBusqueda);
        labelBusqueda.setText("Caca");
        interfaz.add(labelBusqueda);

        final JTextField textoBusqueda = new JTextField();
        textoBusqueda.setBounds(155,50,140,20);

        JButton buttonBusqueda = new JButton("Buscar");
        buttonBusqueda.setBounds(300,50,80,20);
        buttonBusqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textoBusqueda.getText();
                consulta = consulta.toLowerCase();
                Buscador buscador = new Buscador();
                buscador.busqueda(consulta, vocabulario);
            }
        });

        interfaz.add(buttonBusqueda);
        interfaz.add(textoBusqueda);

        interfaz.setSize(700,700);
        interfaz.setLayout(null);
        interfaz.setVisible(true);

        //Buscador buscador = new Buscador();
        //String consulta = "CeRn";
        //consulta = consulta.toLowerCase();
        //buscador.busqueda(consulta, hT1);
    }
}
