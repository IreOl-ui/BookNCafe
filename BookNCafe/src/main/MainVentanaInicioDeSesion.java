package main;

import javax.swing.SwingUtilities;

import domain.Cliente;
import gui.VentanaInicioDeSesion;
import gui.VentanaPrincipal;
import io.GestionClientes;

public class MainVentanaInicioDeSesion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SwingUtilities.invokeLater(() -> {
        	// Inicio de sesión
            VentanaInicioDeSesion ventanaInicioDeSesion = new VentanaInicioDeSesion();
            String usuarioLogueado = ventanaInicioDeSesion.mostrar();

            // Si usuario existe, busca su cliente asociado
            if (usuarioLogueado != null) {
            	GestionClientes gestionClientes = new GestionClientes();
                Cliente cliente = gestionClientes.obtenerClientePorNombre(usuarioLogueado);

                // Y después abre ventana principal
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(cliente);
                ventanaPrincipal.setVisible(true);
            }
        });
	}

}
