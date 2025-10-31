package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.*;
import io.*;
import java.awt.*;
import java.util.Set;

public class VentanaProductos extends JFrame {
    public VentanaProductos(Set<Producto> productos) { // Recibe los productos como parámetro
        setTitle("Catálogo de Productos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(9, 3, 0, 0)); // 9 filas, 3 columnas
        panelProductos.setBackground(Color.WHITE);
        
        // Añadir borde vacío para el margen de la ventana
        panelProductos.setBorder(new EmptyBorder(30, 10, 10, 10));

        // Iterar sobre los productos cargados
        for (Producto producto : productos) {
            String nombre = producto.getNombre(); // Obtiene el nombre del producto
            String descripcion = producto.getDescripcion(); // Obtiene la descripción
            double precio = producto.getPrecio(); // Obtiene el precio
            Set<Alergeno> alergenos = producto.getAlergeno(); // Obtiene los alérgenos

            // Verificar si el producto es una bebida para determinar si tiene alcohol
            boolean tieneAlcohol = false;
            if (producto instanceof Bebida) {
                tieneAlcohol = ((Bebida) producto).isAlcohol();
            }

            // Crear el panel del producto utilizando el constructor de ProductoPanel
            ProductoPanel productoPanel = new ProductoPanel(producto);
            panelProductos.add(productoPanel);
            productoPanel.setBackground(Color.WHITE);
        }

        // Crear un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(panelProductos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir el JScrollPane a la ventana principal
        add(scrollPane);
    }
}
