package gui;

import domain.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConcursoPanel extends JPanel {
    private List<Participante> participantes;

    public ConcursoPanel(List<Participante> participantes) {
        this.participantes = participantes;
        setLayout(new GridLayout(4, 3)); 
        setLayout(new GridLayout(0, 3));  // 3 columnas para organizar las imágenes
        mostrarParticipantes();
    }

    private void mostrarParticipantes() {
        for (Participante participante : participantes) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setLayout(new BorderLayout());

            // Cargar la imagen del dibujo
            ImageIcon imagen = new ImageIcon("resources/images/Concurso/" + participante.getNombre() + participante.getApellido() + ".jpeg");
            imagen = ProductoPanel.redimensionarImagen(imagen, 400, 400);
            JLabel imagenLabel = new JLabel(imagen);
            panel.add(imagenLabel, BorderLayout.CENTER);

         // Crear un JLabel para el nombre y apellido del participante
            JLabel nombreApellidoLabel = new JLabel(participante.getNombre() + " " + participante.getApellido(), SwingConstants.CENTER);

            // Cambiar la fuente y el tamaño de la letra
            nombreApellidoLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nombreApellidoLabel.setForeground(Color.DARK_GRAY);

            // Añadir un borde vacío para dejar espacio entre la imagen y el nombre
            nombreApellidoLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

            panel.add(nombreApellidoLabel, BorderLayout.SOUTH);

            add(panel);
        }
    }
}
