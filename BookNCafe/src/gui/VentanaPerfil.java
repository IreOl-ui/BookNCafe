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
        fotoPerfil = new ImageIcon(createCircleImage(perfil.getFotoPerfil(), 100, perfil.getColorBorde()));
        fotoLabel = new JLabel(fotoPerfil);
        fotoLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1));
        infoPanel.setBorder(new EmptyBorder(0, 15, 5, 15));
        
        // JLabel
        infoPanel.add(new JLabel("Nombre: " + cliente.getNombre()));
        infoPanel.add(new JLabel("Email: " + cliente.getEmail()));
        
        infoPanel.add(new JLabel("Puntos Acumulados: " + perfil.calcularPuntos() + " puntos"));

        // JButton
        // -- Cambiar color
        JButton changeColorButton = new JButton("Cambiar color de círculo");
        changeColorButton.addActionListener(new ChangeColorListener());
        infoPanel.add(changeColorButton);
        // -- Cambiar imagen
        JButton changeImageButton = new JButton("Cambiar imagen");
        changeImageButton.addActionListener(new ChangeImageListener());
        infoPanel.add(changeImageButton);
        
        add(fotoLabel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private BufferedImage createCircleImage(String imagePath, int diameter, Color borderColor) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            
            Image scaledImage = originalImage.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = circularImage.createGraphics();
            
            g.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
            g.drawImage(scaledImage, 0, 0, null);
            g.setClip(null);
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
            Color nuevoColor = JColorChooser.showDialog(VentanaPerfil.this, "Selecciona un color", perfil.getColorBorde());
            if (nuevoColor != null) {
                perfil.setColorBorde(nuevoColor);
                
                fotoPerfil = new ImageIcon(createCircleImage(perfil.getFotoPerfil(), 100, nuevoColor));
                fotoLabel.setIcon(fotoPerfil);
                
                fotoLabel.revalidate();
                fotoLabel.repaint();
            }
        }
    }

    private class ChangeImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una nueva imagen de perfil");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

            int resultado = fileChooser.showOpenDialog(VentanaPerfil.this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                if (archivoSeleccionado != null) {
                    try {
                        BufferedImage nuevaImagen = ImageIO.read(archivoSeleccionado);
                        if (nuevaImagen != null) {
                            String imagePath = "resources/images/Perfiles/" + cliente.getNombre() + ".jpg";
                            File destino = new File(imagePath);
                            
                            destino.getParentFile().mkdirs();
                            
                            ImageIO.write(nuevaImagen, "jpg", destino);
                            
                            fotoPerfil = new ImageIcon(createCircleImage(imagePath, 100, perfil.getColorBorde()));
                            fotoLabel.setIcon(fotoPerfil);
                            
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
