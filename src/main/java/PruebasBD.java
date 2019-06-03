import entidades.VocabularioEntity;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

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

        final JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("imagenes/Logo Buscador.png"));
        logo.setBounds(400,40,600,125);

        final JTextField textoBusqueda = new JTextField();
        textoBusqueda.setBounds(445,40+125+20,400,30);

        final JLabel autores = new JLabel("Realizado por Levián Lastra, Facundo Raviolo y Naim Saadi para DLC 2019",SwingConstants.CENTER);
        autores.setBounds(300,40+125+20+30+20+90+90+90+90+90,800,70);

        final JButton buttonBusqueda = new JButton();
        buttonBusqueda.setIcon(new ImageIcon("imagenes/Boton.png"));
        buttonBusqueda.setBounds(445+400+10,40+125+20,100,30);
        buttonBusqueda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                interfaz.getContentPane().removeAll();
                interfaz.repaint();
                interfaz.add(logo);
                interfaz.add(textoBusqueda);
                interfaz.add(buttonBusqueda);
                interfaz.add(autores);
                String consulta = textoBusqueda.getText();
                consulta = consulta.toLowerCase();
                Buscador buscador = new Buscador();
                ArrayList<String> listaResultados = buscador.busqueda(consulta, vocabulario, em);
              /*
                if (listaResultados.size() == 1)
                {
                    if (listaResultados.get(0) == "No se encontraron resultados para la búsqueda realizada.")
                    {
                        JTextField textoBusquedaVacia = new JTextField("porq eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        textoBusquedaVacia.setEditable(false);
                        textoBusquedaVacia.setBackground(new java.awt.Color(219,219,219));
                        textoBusquedaVacia.setBounds(300,40+125+20+30+20,800,110);
                        textoBusquedaVacia.setBorder(new LineBorder(new java.awt.Color(219,219,219),2));
                        interfaz.add(textoBusquedaVacia);
                    }
                }

               */

                int altura = 40+125+20+30+20;
                int numero = 0;
                for (String cadena : listaResultados)
                {
                    JTextPane texto = new JTextPane();
                    final String cad = listaResultados.get(numero);
                    texto.setText(cad);
                    texto.setEditable(false);
                    texto.setBackground(new java.awt.Color(219,219,219));
                    texto.setBounds(300,altura,800,70);
                    texto.setBorder(new LineBorder(new java.awt.Color(219,219,219),2));
                    interfaz.add(texto);
                    JButton link = new JButton();
                    link.setIcon(new ImageIcon("imagenes/Ir.png"));
                    link.addActionListener( new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            String result = StringUtils.substringBetween(cad, "DocumentosTP/", "\n");
                            Buscador buscaLink = new Buscador();
                            String url = "F:/Documents/Facultad/DLC/TP-Unico/DLC-TPI/DocumentosTP/" + result;
                            buscaLink.openWebPage(url);
                        }
                    });
                    link.setBounds(300+800+10,altura,45,70);
                    interfaz.add(link);
                    numero++;
                    String imagen = "imagenes/" + numero + ".png";
                    JLabel img = new JLabel();
                    img.setIcon(new ImageIcon(imagen));
                    img.setBounds(245,altura,45,70);
                    interfaz.add(img);
                    altura = altura + 70+20;

                }
            }
        });

        interfaz.add(logo);
        interfaz.add(textoBusqueda);
        interfaz.add(buttonBusqueda);
        interfaz.add(autores);

        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setTitle("BOKE Search");
        interfaz.setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/favicon.png"));

        interfaz.setSize(1400,800);
        interfaz.setMinimumSize(new Dimension(1400,800));
        //interfaz.setMaximumSize(new Dimension(1400,800));
        interfaz.setLayout(null);
        interfaz.setVisible(true);
        interfaz.setLocationRelativeTo(null);


        //Buscador buscador = new Buscador();
        //String consulta = "CeRn";
        //consulta = consulta.toLowerCase();
        //buscador.busqueda(consulta, hT1);

        //persistencia.cerrarPersistencia(em);



    }





}
