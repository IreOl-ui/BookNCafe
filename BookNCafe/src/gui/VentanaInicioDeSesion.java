package gui; 

import java.awt.GridBagConstraints; // definir restricciones de diseño en el GridBagLayout
import java.awt.GridBagLayout; // administrador de diseño GridBagLayout
import java.awt.Insets; // definir los márgenes de componentes

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

    public String mostrar() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.pack(); // Ajustar el tamaño de la ventana a sus componentes
        loginFrame.setVisible(true); // Mostrar la ventana
        return usuarioLogueado;
    }

    //clase interna que representa la ventana de inicio de sesión
    public class LoginFrame extends JFrame {
    	
		private static final long serialVersionUID = 1L;
		
		// campo de texto para el nombre de usuario
        private JTextField usernameField; 
        // campo de texto para la contraseña
        private JPasswordField passwordField; 

        // constructor
        public LoginFrame() {
            
            // VENTANA
            
            //título de la ventana
            setTitle("Inicio de Sesión"); 
            // tamaño de la ventana (ancho, alto)
            setSize(400, 300); 
             // cierra la aplicación al cerrar esta ventana
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // centra la ventana en la pantalla
            setLocationRelativeTo(null); 

            // ------------------------------------------------------------------------------------------------------------------------------------
            
            // DISEÑO (layout)
            
            // configura el diseño como GridBagLayout
            setLayout(new GridBagLayout()); 
            // restricciones del layout
            GridBagConstraints gbc = new GridBagConstraints();
            // margen alrededor de cada componente
            gbc.insets = new Insets(5, 5, 5, 5); 
            // expande horizontalmente
            gbc.fill = GridBagConstraints.HORIZONTAL; 

            // ------------------------------------------------------------------------------------------------------------------------------------
            
            // ETIQUETAS
            
            // etiqueta y campo de Usuario
            gbc.gridx = 0; // columna 0
            gbc.gridy = 0; // fila 0
            add(new JLabel("Usuario:"), gbc); // etiqueta en (0, 0)
            gbc.gridx = 1; // cambia a columna 1 para el próximo componente
            usernameField = new JTextField(15); // crea un campo de texto para el usuario con ancho de 15 columnas
            add(usernameField, gbc); // añade el campo de texto en la posición (1, 0)

            // etiqueta y campo de Contraseña
            gbc.gridy++; // incrementa la fila
            gbc.gridx = 0; // reinicia la columna
            add(new JLabel("Contraseña:"), gbc); // añade la etiqueta en la posición (0, 1)
            gbc.gridx = 1; // cambia a columna 1
            passwordField = new JPasswordField(15); // campo de texto para la contraseña con ancho de 15 columnas
            add(passwordField, gbc); // añade el campo en (1, 1)

// ------------------------------------------------------------------------------------------------------------------------------------
            
            // BOTONES
            
            // botón de inicio de sesión
            gbc.gridy++; // incrementa la fila
            gbc.gridx = 1; // coloca en columna 1
            JButton loginButton = new JButton("Iniciar Sesión"); // crea el botón 
            loginButton.addActionListener(new LoginButtonListener()); // añade el listener de acción para el botón
            add(loginButton, gbc); // añade el botón

            // botón de registro
            gbc.gridy++; // incrementa la fila
            JButton registerButton = new JButton("Registrarse"); // crea el botón 
            registerButton.addActionListener(new RegisterButtonListener()); // añade el listener de acción para el botón
            add(registerButton, gbc); // Añade el botón

            // Botón de usuario invitado
            gbc.gridy++; // incrementa la fila
            JButton guestButton = new JButton("Continuar como Invitado"); // crea el botón
            guestButton.addActionListener(new GuestButtonListener()); // añade el listener de acción para el botón
            add(guestButton, gbc); // añade el botón
        }

        // ------------------------------------------------------------------------------------------------------------------------------------
            
        // AUTENTIFICACIÓN USUARIO
        
        // clase interna para manejar el botón de inicio de sesión
        private class LoginButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                 // obtiene el nombre de usuario ingresado
                String username = usernameField.getText();
                // obtiene la contraseña ingresada
                String password = new String(passwordField.getPassword()); 
                // llama a iniciarSesion para verificar las credenciales
                iniciarSesion(); 
            }
        }

        // método para autenticar al usuario leyendo el archivo CSV
        private boolean autenticar(String username, String password) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader("resources/data/contraClientes.csv"))) {
                // Instancia temporal para acceder al método encriptarContraseña
            	ContraCliente tempCliente = new ContraCliente("", password);
                String passwordEncriptada = tempCliente.getContraseñaEncriptada(); // Encripta la contraseña ingresada

                while ((line = br.readLine()) != null) { // lee cada línea del archivo
                    String[] datos = line.split(";"); // separa la línea por punto y coma
                    if (datos.length >= 2) { // verifica que haya al menos dos elementos (usuario y contraseña)
                        String csvUsername = datos[0].trim(); // extrae el nombre de usuario
                        String csvPassword = datos[1].trim(); // extrae la contraseña encriptada

                        // Compara las credenciales del usuario con las del archivo
                        if (username.equals(csvUsername) && passwordEncriptada.equals(csvPassword)) {
                            return true; // autentica exitosamente si las credenciales coinciden
                        }
                    }
                }
            } catch (IOException e) { // Maneja errores al abrir o leer el archivo
                e.printStackTrace();
                JOptionPane.showMessageDialog(LoginFrame.this, "Error al leer el archivo de usuarios", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return false; // devuelve falso si no encuentra una coincidencia
        }

        // método para iniciar sesión
        private void iniciarSesion() {
        	// Asignar el usuario logueado
        	usuarioLogueado = usernameField.getText(); // campo de texto del usuario
            String password = new String(passwordField.getPassword()); // campo de contraseña
            

            // Si la autenticación es correcta, abre la ventana principal
            if (autenticar(usuarioLogueado, password)) {
                // Cerrar la ventana de inicio de sesión
                this.dispose();  // Cierra la ventana de login (Inicio de sesión)

                // Crear y mostrar la ventana principal
                GestionClientes gestionClientes = new GestionClientes();
                Cliente cliente = gestionClientes.obtenerClientePorNombre(usuarioLogueado);
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(cliente);
                ventanaPrincipal.setVisible(true);  // Muestra la ventana principal
            } else {
                // Si las credenciales no son correctas, mostrar un mensaje
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }
        }

        // clase interna para manejar el botón de registro
        private class RegisterButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // abre la ventana de registro
                new RegisterFrame().setVisible(true); 
            }
        }

        // clase interna para manejar el botón de invitado
        private class GuestButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un cliente genérico para el usuario invitado
                Cliente clienteInvitado = new Cliente("Invitado", "", "", "");

                // Cerrar la ventana de inicio de sesión
                LoginFrame.this.dispose();

                // Abrir la ventana principal con el cliente invitado -- TODAVIA NO ESTÁ TERMINADA
                
            }
        }
    }
    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    
    // VENTANA DE REGISTRO DE NUEVO CLIENTE/USUARIO

    // clase interna para la ventana de registro
    class RegisterFrame extends JFrame {

		private static final long serialVersionUID = 1L;
		
		private JTextField nameField; // campo de texto para el nombre
        private JTextField emailField; // campo de texto para el email
        private JTextField dniField; // campo de texto para el DNI
        private JTextField phoneField; // campo de texto para el teléfono
        private JPasswordField passwordField; // campo de texto para la contraseña

        // constructor
        public RegisterFrame() {

            // ------------------------------------------------------------------------------------------------------------------------------------
    
            // VENTANA
            
            setTitle("Registro Nuevo Cliente"); // título de la ventana
            setSize(400, 400); // tamaño de la ventana
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra solo la ventana de registro al cerrar
            setLocationRelativeTo(null); // centra la ventana en la pantalla

            // ------------------------------------------------------------------------------------------------------------------------------------
    
            // DISEÑO, LAYOUT
            
            setLayout(new GridBagLayout()); // configura el diseño como GridBagLayout
            GridBagConstraints gbc = new GridBagConstraints(); //  onfiguración de restricciones del layout
            gbc.insets = new Insets(5, 5, 5, 5); // margen 
            gbc.fill = GridBagConstraints.HORIZONTAL; // expande los componentes horizontalmente

            // ------------------------------------------------------------------------------------------------------------------------------------
    
            // FIELDS

            // etiqueta y campo de Nombre
            gbc.gridx = 0; // fila
            gbc.gridy = 0; // columna
            add(new JLabel("Nombre:"), gbc); // añade la etiqueta "Nombre:"
            gbc.gridx = 1; // cambia la fila a 1
            nameField = new JTextField(15); //  campo de texto para el nombre
            add(nameField, gbc); // añade el campo de texto

            // etiqueta y campo de Email
            gbc.gridy++; // fila (suma 1)
            gbc.gridx = 0; // columna (se restablece)
            add(new JLabel("DNI:"), gbc); // añade la etiqueta "Email:"
            gbc.gridx = 1; // cambia la fila a 1
            emailField = new JTextField(15); // campo de texto para el email
            add(emailField, gbc); // añade el campo de texto

            // etiqueta y campo de DNI
            gbc.gridy++; // fila (suma 1)
            gbc.gridx = 0; // columna (se restablece)
            add(new JLabel("Email:"), gbc); // añade la etiqueta "DNI:"
            gbc.gridx = 1; // cambia la fila a 1
            dniField = new JTextField(15); // campo de texto para el DNI
            add(dniField, gbc); // añade el campo de texto

            // etiqueta y campo de Teléfono
            gbc.gridy++; // fila (suma 1)
            gbc.gridx = 0; // columna (se restablece)
            add(new JLabel("Teléfono:"), gbc); // Añade la etiqueta "Teléfono:"
            gbc.gridx = 1; // cambia la fila a 1
            phoneField = new JTextField(15); // Campo de texto para el teléfono
            add(phoneField, gbc); // Añade el campo de texto

            // etiqueta y campo de Contraseña
            gbc.gridy++; // fila (suma 1)
            gbc.gridx = 0; // columna (se restablece)
            add(new JLabel("Contraseña:"), gbc); // Añade la etiqueta "Contraseña:"
            gbc.gridx = 1; // cambia la fila a 1
            passwordField = new JPasswordField(15); // Campo de texto para la contraseña
            add(passwordField, gbc); // Añade el campo de contraseña


            // ------------------------------------------------------------------------------------------------------------------------------------
    
            // BOTONES
            
            // botón de registro
            gbc.gridy++; // fila (suma 1)
            gbc.gridx = 1; // establece columna a 1
            JButton registerButton = new JButton("Registrar"); // crea el botón
            registerButton.addActionListener(new RegisterButtonListener()); // añade el listener 
            add(registerButton, gbc); // añade el botón
        }

        // clase interna para manejar el lo que debe hacer el botón de registro
        private class RegisterButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obtiene los valores introducidos por el usuario
                String nombre = nameField.getText();
                String dni = dniField.getText();
                String email = emailField.getText();
                String tlf = phoneField.getText();
                String contraseña = new String(passwordField.getPassword());

                // guardar datos cliente (DNI;NOMBRE;EMAIL;TELEFONO)
                // crea una instancia de Cliente con los datos ingresados
                Cliente cliente = new Cliente(nombre, dni, email, tlf);

                // Ruta de la imagen predeterminada
                String rutaImagenPredeterminada = "resources/images/Perfiles/Invitado.jpg"; 
                // Ruta de la nueva imagen con el nombre del usuario
                String rutaNuevaImagen = "resources/images/Perfiles/" + nombre + ".jpg";
                
                // Crear la ruta de la nueva imagen
                File archivoImagenPredeterminada = new File(rutaImagenPredeterminada);
                File archivoNuevaImagen = new File(rutaNuevaImagen);

                // Si no existe una imagen con el nombre del usuario, copiar la imagen predeterminada
                if (!archivoNuevaImagen.exists()) {
                    try {
                        // Crear directorios si no existen
                        archivoNuevaImagen.getParentFile().mkdirs();
                        // Copiar la imagen predeterminada a la nueva ruta
                        Files.copy(archivoImagenPredeterminada.toPath(), archivoNuevaImagen.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        System.err.println("Error al copiar la imagen predeterminada: " + ex.getMessage());
                    }
                }

                // crear una instancia de GestionClientes y guardar el cliente
                GestionClientes gestionClientes = new GestionClientes();
                gestionClientes.guardarClientesCSV(cliente);

                // guardar contraseña cliente (NOMBRE;CONTRASEÑA)
                // crea una instancia de ContraseñaCliente con los datos ingresados
                ContraCliente contraseñaCliente = new ContraCliente(nombre, contraseña);

                // crea una instancia de GestionContraseñasClientes y guardar la contraseña
                GestionContraClientes gestionContraseñasClientes = new GestionContraClientes();
                gestionContraseñasClientes.guardarContraCliente(contraseñaCliente);

                // mensaje de confirmación
                JOptionPane.showMessageDialog(RegisterFrame.this, "Registro exitoso para " + nombre);

                // Cerrar la ventana de registro
                dispose();

                // Abrir la ventana principal con el perfil del cliente registrado
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(cliente);  // Pasar el cliente y perfil registrado
                ventanaPrincipal.setVisible(true);  // Mostrar la ventana principal
            }
        }
    }
}

