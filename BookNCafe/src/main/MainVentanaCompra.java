
package main;

import domain.Producto;
import gui.VentanaCompra;
import io.GestionProductos;
import javax.swing.SwingUtilities;
import java.util.Set;

public class MainVentanaCompra {
    public static void main(String[] args) {
        // Cargar los productos desde el archivo CSV
        Set<Producto> productos = cargarProductos();
        
        // Mostrar información de los productos cargados en consola
        System.out.println("Total productos cargados: " + productos.size());
        for (Producto p : productos) {
            System.out.println("- " + p.getNombre() + " (" + p.getClass().getSimpleName() + ")");
        }
        
        // Crear y mostrar la ventana de compra en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new VentanaCompra(productos);
        });
    }
    
    /**
     * Método auxiliar para cargar los productos desde el CSV
     */
    private static Set<Producto> cargarProductos() {
        return GestionProductos.cargarProductosCSV();
    }
}