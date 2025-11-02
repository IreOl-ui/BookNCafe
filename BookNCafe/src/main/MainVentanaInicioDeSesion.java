package main;

import javax.swing.SwingUtilities;

import domain.Cliente;
import gui.VentanaInicioDeSesion;
import io.GestionClientes;

public class MainVentanaInicioDeSesion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SwingUtilities.invokeLater(() -> {
            // Crear y mostrar la ventana de inicio de sesión
            VentanaInicioDeSesion ventanaInicioDeSesion = new VentanaInicioDeSesion();
            String usuarioLogueado = ventanaInicioDeSesion.mostrar(); // Método que bloquea hasta obtener un usuario

            if (usuarioLogueado != null) { // Si hay usuario, buscar su cliente asociado
            	GestionClientes gestionClientes = new GestionClientes();
                Cliente cliente = gestionClientes.obtenerClientePorNombre(usuarioLogueado); // Aquí se obtiene el cliente

                // Y después crear y mostrar la ventana principal
            }
        });
	}

}
