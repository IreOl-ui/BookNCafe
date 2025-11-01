package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaInicioDeSesion extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField campoUsuario;
	private JTextField campoContra;

	public VentanaInicioDeSesion() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Iniciar sesión");
		setSize(350, 300);
		setLocationRelativeTo(null);
		
		// Creando un nuevo panel
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		
		// Texto de usuario
		panel.add(new JLabel("Usuario:"));
		campoUsuario = new JTextField();
		panel.add(campoUsuario);
		
		// Texto de contraseña en modo password
		panel.add(new JLabel("Password:"));
		campoContra = new JPasswordField();
		panel.add(campoContra);
		
		add(panel, BorderLayout.CENTER);
		
		// Creando otro panel nuevo con botones
		JPanel panelBotones = new JPanel();
		JButton botonInicio = new JButton("Iniciar sesión");
		JButton botonCancelar = new JButton("Cancelar");
		
		panelBotones.add(botonInicio);
		panelBotones.add(botonCancelar);
		add(panelBotones, BorderLayout.SOUTH);
		
		// Listeners
		// -- Queremos iniciar sesión
		botonInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String user = campoUsuario.getText();
				String contra = new String(campoContra.getText());
				
				if (user.equals("admin") && contra.equals("1234")) {
					JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
				}
			}
		});
		
		// -- Queremos cancelar para salir
		botonCancelar.addActionListener(e -> System.exit(0));
		
		setVisible(true);
	}

}
