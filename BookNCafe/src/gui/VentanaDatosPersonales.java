package gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaDatosPersonales extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Abriendo la ventana principal una vez iniciado la sesion correctamente
	public VentanaDatosPersonales() {
		setTitle("Panel principal");
		setSize(450, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// E aqui creo un nuevo panel con todos los detalles importantes del usuario
		JPanel panelPrincipal = new JPanel(new GridLayout(10, 1, 8, 8));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		
		panelPrincipal.add(new JLabel("Nombre: "));
		panelPrincipal.add(new JLabel("Apellidos: "));
		panelPrincipal.add(new JLabel("DNI: "));
		panelPrincipal.add(new JLabel("Género: "));
		panelPrincipal.add(new JLabel("Edad: "));
		panelPrincipal.add(new JLabel("Fecha de nacimiento: "));
		panelPrincipal.add(new JLabel("Email: "));
		panelPrincipal.add(new JLabel("Teléfono: "));
		
		// Creo un nuevo botón de cerrar sesión
		JButton botonCerrarSesion = new JButton("Cerrar sesión");
		botonCerrarSesion.addActionListener(e -> {
			dispose();
			new VentanaInicioDeSesion().setVisible(true);
		});
		
		panelPrincipal.add(new JLabel()); // Para tener espacio
		panelPrincipal.add(botonCerrarSesion);
		
		add(panelPrincipal);
		
		setVisible(true);
	}

}
