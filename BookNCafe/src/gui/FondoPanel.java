package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Image fondo;

    public FondoPanel(String rutaImagen) {
        ImageIcon icono = new ImageIcon(rutaImagen);
        this.fondo = icono.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen redimensionada para que cubra todo el panel
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
