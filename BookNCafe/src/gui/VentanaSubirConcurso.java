package gui;

import domain.Participante;
import io.GestionParticipantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;

public class VentanaSubirConcurso extends JFrame {

    public VentanaSubirConcurso() {
        setTitle("Subir tu Foto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(380, 400);  // Ajusté el tamaño de la ventana
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Espaciado reducido

        // Instrucciones en la parte superior
        JLabel instruccionesLabel = new JLabel("<html><center>Ingrese su nombre, apellido y teléfono.<br>Luego seleccione una imagen para participar.<br>La imagen debe ser en formato jpeg y ser nombrada con su nombre y apellido, así: NombreApellido.<br>El ganador será obsequiado con un cheque de 50€ para gastar en la cafetería y un vaso con su dibujo.</center></html>", SwingConstants.CENTER);
        instruccionesLabel.setFont(new Font("Arial", Font.BOLD, 13));  // Tamaño de fuente para visibilidad
        instruccionesLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear los componentes para ingresar nombre, apellido y teléfono
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Usar FlowLayout con espaciado entre componentes
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Reducción de márgenes

        // Campos de texto ajustados para ser más pequeños
        JTextField nombreField = new JTextField();
        nombreField.setFont(new Font("Arial", Font.PLAIN, 13));  // Tamaño de fuente ajustado
        nombreField.setBorder(new TitledBorder("Nombre"));
        nombreField.setPreferredSize(new Dimension(325, 50));  // Ajustar el tamaño del campo

        JTextField apellidoField = new JTextField();
        apellidoField.setFont(new Font("Arial", Font.PLAIN, 13));
        apellidoField.setBorder(new TitledBorder("Apellido"));
        apellidoField.setPreferredSize(new Dimension(325, 50));  // Ajustar el tamaño del campo

        JTextField telefonoField = new JTextField();
        telefonoField.setFont(new Font("Arial", Font.PLAIN, 13));
        telefonoField.setBorder(new TitledBorder("Teléfono"));
        telefonoField.setPreferredSize(new Dimension(325, 50));  // Ajustar el tamaño del campo

        // Añadir los campos al panel del formulario
        panelFormulario.add(nombreField);
        panelFormulario.add(apellidoField);
        panelFormulario.add(telefonoField);

        // Botón de subir imagen
        JButton btnSubirImagen = new JButton("Subir Imagen");
        btnSubirImagen.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubirImagen.setBackground(new Color(70, 130, 180));
        btnSubirImagen.setForeground(Color.WHITE);
        btnSubirImagen.setFocusPainted(false);
        btnSubirImagen.setPreferredSize(new Dimension(150, 30)); // Reducir tamaño del botón
        btnSubirImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccione una imagen para subir");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg", "gif"));

            int seleccion = fileChooser.showOpenDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                String nombreCompleto = nombreField.getText().replace(" ", "_") + apellidoField.getText().replace(" ", "_");
                String rutaDestino = "resources/images/Concurso/" + nombreCompleto + ".jpeg";

                try {
                    Files.copy(archivoSeleccionado.toPath(), Paths.get(rutaDestino), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Imagen guardada exitosamente en: " + rutaDestino);

                    // Crear y guardar el participante
                    Participante participante = new Participante(nombreField.getText(), apellidoField.getText(), telefonoField.getText());
                    GestionParticipantes.guardarParticipante(participante);

                    JOptionPane.showMessageDialog(this, "¡Imagen subida correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    System.err.println("Error al guardar la imagen: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Error al subir la imagen. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir el botón al panel inferior
        JPanel panelBoton = new JPanel();  // Panel para el botón
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Alineación centrada
        panelBoton.add(btnSubirImagen);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.add(instruccionesLabel, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);  // Agregar el panel del botón al sur

        add(panelPrincipal, BorderLayout.CENTER);

        setVisible(true);
    }
}