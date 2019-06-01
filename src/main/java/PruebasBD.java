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
        final EntityManager em = persistencia.crearPersistencia();
        persistencia.abrirPersistencia(em);

        final JFrame interfaz = new JFrame();

        final JLabel labelResultado = new JLabel();
        //logo.setIcon(new ImageIcon("imagenes/Logo Buscador.png"));
        labelResultado.setBounds(400,40+125+20+30+20,600,600);

        final JTextField textoBusqueda = new JTextField();
        textoBusqueda.setBounds(445,40+125+20,400,30);
        JButton buttonBusqueda = new JButton();
        buttonBusqueda.setIcon(new ImageIcon("imagenes/Boton.png"));
        buttonBusqueda.setBounds(445+400+10,40+125+20,100,30);
        buttonBusqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consulta = textoBusqueda.getText();
                consulta = consulta.toLowerCase();
                Buscador buscador = new Buscador();
                String resultado = buscador.busqueda(consulta, vocabulario, em);
                labelResultado.setText(resultado);
                interfaz.add(labelResultado);
            }
        });

        interfaz.add(buttonBusqueda);
        interfaz.add(textoBusqueda);

        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setTitle("BOKE Buscador");
        interfaz.setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/favicon.png"));

        interfaz.setSize(1400,800);
        interfaz.setMinimumSize(new Dimension(1400,800));

        interfaz.setLayout(null);
        interfaz.setVisible(true);


//        interfaz.setMinimumSize(new Dimension(800,600));

        //Buscador buscador = new Buscador();
        //String consulta = "CeRn";
        //consulta = consulta.toLowerCase();
        //buscador.busqueda(consulta, hT1);

        //persistencia.cerrarPersistencia(em);



    }

}
