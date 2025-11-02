package gui;

import domain.Cliente;
import domain.Perfil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VentanaPerfil extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Perfil perfil;
    private JLabel fotoLabel;
    private ImageIcon fotoPerfil;
    private Cliente cliente;

    public VentanaPerfil(Cliente cliente, Perfil perfil) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        this.cliente = cliente;
        this.perfil = perfil;

        setTitle("Perfil de Usuario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear una imagen con el color de borde almacenado en el perfil
        fotoPerfil = new ImageIcon(createCircleImage(perfil.getFotoPerfilPath(), 100, perfil.getColorBorde()));
        fotoLabel = new JLabel(fotoPerfil);
        fotoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1));
        infoPanel.setBorder(new EmptyBorder(0, 15, 5, 15));

        // Mostrar la información del cliente
        infoPanel.add(new JLabel("Nombre: " + cliente.getNombre()));
        infoPanel.add(new JLabel("Email: " + cliente.getEmail()));

        // Mostrar puntos calculados por perfil
        infoPanel.add(new JLabel("Puntos Acumulados: " + perfil.calcularPuntos() + " puntos"));

        // Botón para cambiar el color del círculo
        JButton changeColorButton = new JButton("Cambiar color de círculo");
        changeColorButton.addActionListener(new ChangeColorListener());
        infoPanel.add(changeColorButton);

        // Botón para cambiar la foto de perfil
        JButton changeImageButton = new JButton("Cambiar imagen");
        changeImageButton.addActionListener(new ChangeImageListener());
        infoPanel.add(changeImageButton);

        // Añadir componentes al marco
        add(fotoLabel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    // Método para crear una imagen circular a partir de una imagen existente
    private BufferedImage createCircleImage(String imagePath, int diameter, Color borderColor) {
        try {
            // Cargar la imagen desde el path
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Redimensionar la imagen para que encaje en el círculo
            Image scaledImage = originalImage.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = circularImage.createGraphics();

            // Crear un círculo y establecer la región de recorte
            g.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));

            // Dibujar la imagen escalada dentro del círculo
            g.drawImage(scaledImage, 0, 0, null);

            // Dibujar el borde circular
            g.setClip(null); // Desactivar el clip para el borde
            g.setColor(borderColor);
            g.setStroke(new BasicStroke(3));
            g.drawOval(0, 0, diameter - 1, diameter - 1);

            g.dispose();

            return circularImage;
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
            return null;
        }
    }

    private class ChangeColorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mostrar el selector de color con el color actual del borde como predeterminado
            Color nuevoColor = JColorChooser.showDialog(VentanaPerfil.this, "Selecciona un color", perfil.getColorBorde());
            if (nuevoColor != null) {
                // Guardar el color seleccionado en el perfil
                perfil.setColorBorde(nuevoColor);

                // Actualizar la imagen de perfil con el nuevo color del borde
                fotoPerfil = new ImageIcon(createCircleImage(perfil.getFotoPerfilPath(), 100, nuevoColor));
                fotoLabel.setIcon(fotoPerfil);

                // Forzar actualización de la interfaz
                fotoLabel.revalidate();
                fotoLabel.repaint();
            }
        }
    }

    private class ChangeImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Crear un JFileChooser para que el usuario seleccione una imagen
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una nueva imagen de perfil");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

            int resultado = fileChooser.showOpenDialog(VentanaPerfil.this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();

                // Verificar si la imagen fue seleccionada y si se encuentra en el formato correcto
                if (archivoSeleccionado != null) {
                    try {
                        // Leer la imagen seleccionada
                        BufferedImage nuevaImagen = ImageIO.read(archivoSeleccionado);

                        if (nuevaImagen != null) {
                            // Guardar la nueva imagen en el directorio de destino, reemplazando la anterior
                            String imagePath = "resources/images/Perfiles/" + cliente.getNombre() + ".jpg";
                            File destino = new File(imagePath);

                            // Crear el directorio si no existe
                            destino.getParentFile().mkdirs();

                            // Guardar la imagen en formato JPG
                            ImageIO.write(nuevaImagen, "jpg", destino);

                            // Actualizar el JLabel con la nueva imagen en forma de círculo
                            fotoPerfil = new ImageIcon(createCircleImage(imagePath, 100, perfil.getColorBorde()));
                            fotoLabel.setIcon(fotoPerfil);

                            // Forzar la actualización de la interfaz
                            fotoLabel.revalidate();
                            fotoLabel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(VentanaPerfil.this, "La imagen seleccionada no es válida.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
