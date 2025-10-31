package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.*;

import java.awt.*;
import java.util.Set;

public class ProductoPanel extends JPanel {

    public ProductoPanel(Producto producto) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Obtener el nombre, personaje, descripción, precio y alérgenos del objeto Producto
        String nombre = ((Producto) producto).getNombre();
        String personaje = ((Producto) producto).getPersonaje();
        String descripcion = ((Producto) producto).getDescripcion();
        double precio = ((Producto) producto).getPrecio();
        Set<Alergeno> alergenos = ((Producto) producto).getAlergeno();
        
        // Inicializamos tieneAlcohol a false por defecto
        boolean tieneAlcohol = false;

        // Cargar imagen del producto dependiendo del tipo
        ImageIcon imagenProducto = null;

        if (producto instanceof Bebida) {
            tieneAlcohol = ((Bebida) producto).isAlcohol();
            imagenProducto = cargarImagenProducto(nombre, "Bebida");
        } else if (producto instanceof Dulce) {
            imagenProducto = cargarImagenProducto(nombre, "Dulce");
        } else if (producto instanceof Salado) {
            imagenProducto = cargarImagenProducto(nombre, "Salado");
        }

        // Cargar la imagen
        imagenProducto = redimensionarImagen(imagenProducto, 300, 300);
        JLabel imagenLabel = new JLabel(imagenProducto);
        imagenLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        imagenLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(imagenLabel);

        // Precio
        JLabel precioLabel = new JLabel(precio + "€");
        precioLabel.setAlignmentX(CENTER_ALIGNMENT);
        precioLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        add(precioLabel);

        // Nombre
        JLabel nombreLabel = new JLabel(nombre);
        nombreLabel.setAlignmentX(CENTER_ALIGNMENT);
        nombreLabel.setFont(new Font("Papyrus", Font.BOLD, 14));
        add(nombreLabel);
        
        // Personaje
        JLabel personajeLabel = new JLabel(personaje);
        personajeLabel.setAlignmentX(CENTER_ALIGNMENT); 
        personajeLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        add(personajeLabel);

        // Descripción
        JLabel descripcionLabel = new JLabel(descripcion);
        descripcionLabel.setAlignmentX(CENTER_ALIGNMENT);
        descripcionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        add(descripcionLabel);

        // Panel de alérgenos y símbolo de alcohol
        JPanel panelAlergenos = new JPanel();
        panelAlergenos.setBorder(new EmptyBorder(10, 20, 0, 20));
        panelAlergenos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Añadir iconos de alérgenos presentes en el Set
        for (Alergeno alergeno : alergenos) {
            ImageIcon iconoAlergeno = cargarIconoAlergeno(alergeno);
            iconoAlergeno = redimensionarImagen(iconoAlergeno, 50, 50);
            JLabel alergenoLabel = new JLabel();

            if (iconoAlergeno != null) {
                alergenoLabel.setIcon(iconoAlergeno);
            } else {
                System.err.println("Icono no encontrado para el alérgeno: " + alergeno);
            }

            panelAlergenos.add(alergenoLabel);
        }

        // Icono de alcohol si aplica
        if (producto instanceof Bebida) {
            JLabel alcoholLabel;
            ImageIcon iconoAlcohol;
            
            // Cargar la imagen según si tiene alcohol o no
            if (((Bebida) producto).isAlcohol()) {
                iconoAlcohol = new ImageIcon("resources/images/Alcohol/ALCOHOL_SI.jpg");
            } else {
                iconoAlcohol = new ImageIcon("resources/images/Alcohol/ALCOHOL_NO.jpg");
            }
            
            // Redimensionar la imagen de alcohol
            iconoAlcohol = redimensionarImagen(iconoAlcohol, 50, 50);
            alcoholLabel = new JLabel(iconoAlcohol); // Crear el JLabel con la imagen redimensionada
            panelAlergenos.add(alcoholLabel); // Añadir solo si es bebida
        }
        
        panelAlergenos.setBackground(Color.WHITE);
        add(panelAlergenos);
    }
    
    // Método para redimensionar las imágenes
    public static ImageIcon redimensionarImagen(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage(); // Obtener la imagen del ImageIcon
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH); // Redimensionar la imagen
        return new ImageIcon(imagenRedimensionada); // Devolver la imagen redimensionada como ImageIcon
    }

    // Método para cargar la imagen del producto según el nombre
    private ImageIcon cargarImagenProducto(String nombre, String tipoProducto) {
        String rutaImagen = "resources/images/" + tipoProducto + "/" + nombre + ".jpeg";
        return new ImageIcon(rutaImagen);
    }

    // Método para cargar el icono de un alérgeno según su nombre
    private ImageIcon cargarIconoAlergeno(Alergeno alergeno) {
    	String nombreAlergeno = alergeno.name();
        String rutaIcono = "resources/images/Símbolos alérgenos/" + nombreAlergeno + ".png";
        return new ImageIcon(rutaIcono);
    }
}
