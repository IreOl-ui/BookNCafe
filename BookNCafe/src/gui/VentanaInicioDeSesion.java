package gui; 

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPasswordField; 
import javax.swing.JTextField; 

import domain.Cliente; 
import io.GestionClientes;
import io.GestionContraClientes;
import domain.ContraCliente; 


public class VentanaInicioDeSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String usuarioLogueado = null;

	// Mostrar la ventana del inicio de sesión
    public String mostrar() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.pack();
        loginFrame.setVisible(true);
        return usuarioLogueado;
    }

    // Creamos otra clase interna de inicio de sesión
    public class LoginFrame extends JFrame {
    	
		private static final long serialVersionUID = 1L;
		
        private JTextField usernameField; 
        private JPasswordField passwordField; 

        public LoginFrame() {
            setTitle("Inicio de Sesión"); 
            setSize(400, 300); 
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null); 
            
            // Layouts
            setLayout(new GridBagLayout()); 
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); 
            gbc.fill = GridBagConstraints.HORIZONTAL; 
            
            // De ahí crearemos la etiqueta y campos de usuario y contraseña
            // -- Usuario
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new JLabel("Usuario:"), gbc);
            gbc.gridx = 1;
            usernameField = new JTextField(15);
            add(usernameField, gbc);
            // -- Contraseña
            gbc.gridy++;
            gbc.gridx = 0;
            add(new JLabel("Contraseña:"), gbc);
            gbc.gridx = 1;
            passwordField = new JPasswordField(15);
            add(passwordField, gbc);
            
            // Botones de inicio de sesión, registro y usuario invitado
            // -- Inicio de sesión
            gbc.gridy++;
            gbc.gridx = 1;
            JButton loginButton = new JButton("Iniciar Sesión");
            loginButton.addActionListener(new LoginButtonListener());
            add(loginButton, gbc);
            // -- Registro
            gbc.gridy++;
            JButton registerButton = new JButton("Registrarse");
            registerButton.addActionListener(new RegisterButtonListener());
            add(registerButton, gbc);
            // -- Usuario invitado
            gbc.gridy++;
            JButton guestButton = new JButton("Continuar como Invitado");
            guestButton.addActionListener(new GuestButtonListener());
            add(guestButton, gbc);
        }
        
        // ActionListener al pulsar el botón de iniciar sesión
        private class LoginButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword()); 
                iniciarSesion(); 
            }
        }

        // Método para autenticar al usuario leyendo el archivo CSV
        private boolean autenticar(String username, String password) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader("resources/data/contraClientes.csv"))) {
            	ContraCliente tempCliente = new ContraCliente("", password);
                String passwordEncriptada = tempCliente.getContraseñaEncriptada(); // Encripta la contraseña ingresada
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(";");
                    if (datos.length >= 2) {
                        String csvUsername = datos[0].trim();
                        String csvPassword = datos[1].trim();
                        // Verifica si el username se encuentra en el archivo csv o no, incluido la contraseña
                        if (username.equals(csvUsername) && passwordEncriptada.equals(csvPassword)) {
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(LoginFrame.this, "Error al leer el archivo de usuarios", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }

        // Método para iniciar sesión
        private void iniciarSesion() {
        	usuarioLogueado = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Si la autenticación es correcta, abre la ventana principal
            if (usuarioLogueado.isEmpty() && password.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "No se puede iniciar la sesión.", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            } else if (autenticar(usuarioLogueado, password)) {
                this.dispose();

                // Crear y mostrar la ventana principal
                GestionClientes gestionClientes = new GestionClientes();
                Cliente cliente = gestionClientes.obtenerClientePorNombre(usuarioLogueado);
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(cliente);
                ventanaPrincipal.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }
        }

        // ActionListener al pulsar el botón Registro
        private class RegisterButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true); 
            }
        }

        // ActionListener al pulsar el botón Usuario invitado
        private class GuestButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Cliente generañ
                Cliente clienteInvitado = new Cliente("Invitado", "", "", "");
                
                LoginFrame.this.dispose();

                // Y de ahí, Abre la ventana principal con el cliente invitado
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(clienteInvitado);
                ventanaPrincipal.setVisible(true);
            }
        }
    }
    
    // Creamos otra clase interna de registro
    class RegisterFrame extends JFrame {

		private static final long serialVersionUID = 1L;
		
		private JTextField nameField;
        private JTextField emailField;
        private JTextField dniField;
        private JTextField phoneField;
        private JPasswordField passwordField;

        public RegisterFrame() {
            setTitle("Registro Nuevo Cliente"); // título de la ventana
            setSize(400, 400); // tamaño de la ventana
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra solo la ventana de registro al cerrar
            setLocationRelativeTo(null); // centra la ventana en la pantalla
            
            // Layouts
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Fields
            // -- Nombre
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            nameField = new JTextField(15);
            add(nameField, gbc);
            // -- Email
            gbc.gridy++;
            gbc.gridx = 0;
            add(new JLabel("DNI:"), gbc);
            gbc.gridx = 1;
            emailField = new JTextField(15);
            add(emailField, gbc);
            // -- DNI
            gbc.gridy++;
            gbc.gridx = 0;
            add(new JLabel("Email:"), gbc);
            gbc.gridx = 1;
            dniField = new JTextField(15);
            add(dniField, gbc);
            // -- Teléfono
            gbc.gridy++;
            gbc.gridx = 0;
            add(new JLabel("Teléfono:"), gbc);
            gbc.gridx = 1;
            phoneField = new JTextField(15);
            add(phoneField, gbc);
            // -- Contraseña
            gbc.gridy++;
            gbc.gridx = 0;
            add(new JLabel("Contraseña:"), gbc);
            gbc.gridx = 1;
            passwordField = new JPasswordField(15);
            add(passwordField, gbc);
            
            // Botones de registrar
            gbc.gridy++;
            gbc.gridx = 1;
            JButton registerButton = new JButton("Registrar");
            registerButton.addActionListener(new RegisterButtonListener());
            add(registerButton, gbc);
        }

        // ActionListener una vez pulsado el botón de registrar
        private class RegisterButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nameField.getText();
                String dni = dniField.getText();
                String email = emailField.getText();
                String tlf = phoneField.getText();
                String contraseña = new String(passwordField.getPassword());
                
                Cliente cliente = new Cliente(nombre, dni, email, tlf);
                
                String rutaImagenPredeterminada = "resources/images/Perfiles/Invitado.jpg"; 
                String rutaNuevaImagen = "resources/images/Perfiles/" + nombre + ".jpg";
                
                File archivoImagenPredeterminada = new File(rutaImagenPredeterminada);
                File archivoNuevaImagen = new File(rutaNuevaImagen);

                // Si no existe una imagen con el nombre del usuario, copiar la imagen predeterminada
                if (!archivoNuevaImagen.exists()) {
                    try {
                        archivoNuevaImagen.getParentFile().mkdirs();
                        Files.copy(archivoImagenPredeterminada.toPath(), archivoNuevaImagen.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        System.err.println("Error al copiar la imagen predeterminada: " + ex.getMessage());
                    }
                }
                
                GestionClientes gestionClientes = new GestionClientes();
                gestionClientes.guardarClientesCSV(cliente);
                
                ContraCliente contraseñaCliente = new ContraCliente(nombre, contraseña);
                
                GestionContraClientes gestionContraseñasClientes = new GestionContraClientes();
                gestionContraseñasClientes.guardarContraCliente(contraseñaCliente);
                
                JOptionPane.showMessageDialog(RegisterFrame.this, "Registro exitoso para " + nombre);
                
                dispose();

                // Una vez registrado correctamente, se abre la ventana principal
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(cliente);
                ventanaPrincipal.setVisible(true);
            }
        }
    }
}

