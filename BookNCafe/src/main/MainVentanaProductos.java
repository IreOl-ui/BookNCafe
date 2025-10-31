package main;

import domain.Producto;
import gui.VentanaProductos;
import io.GestionProductos;

import javax.swing.SwingUtilities;
import java.util.Set;

public class MainVentanaProductos {
    public static void main(String[] args) {
        // Cargar los productos desde el archivo CSV
        Set<Producto> productos = cargarProductos();
        
        System.out.println("Total productos cargados: " + productos.size());
        for (Producto p : productos) {
            System.out.println("- " + p.getNombre() + " (" + p.getClass().getSimpleName() + ")");
        }
        
        // Crear y mostrar la ventana
        SwingUtilities.invokeLater(() -> {
            VentanaProductos ventana = new VentanaProductos(productos);
            ventana.setVisible(true);
        });
    }
    
    private static Set<Producto> cargarProductos() {
        return GestionProductos.cargarProductosCSV();
    }
}