package gui;

import domain.Participante;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;

public class VentanaConcurso extends JFrame {  
	
	private static final long serialVersionUID = 1L;
	
	private List<Participante> participantes;  // Declara una lista privada de objetos 'Participante' que almacena los participantes del concurso

    // Constructor de la clase VentanaConcurso
    public VentanaConcurso(List<Participante> participantes) {

        this.participantes = participantes;  // Asigna la lista de participantes pasada como argumento al atributo 'participantes'
        
        setTitle("Concurso de Dibujo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        setTitle("Concurso de Dibujo");  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        setSize(800, 600);  
        setLocationRelativeTo(null); 

        ConcursoPanel concursoPanel = new ConcursoPanel(participantes); 
        add(concursoPanel);  

        // Crear un panel para mostrar los participantes
        ConcursoPanel ConcursoPanel = new ConcursoPanel(participantes);
        concursoPanel.setBackground(Color.WHITE);
        add(concursoPanel, BorderLayout.CENTER);

        // Botón para subir una nueva imagen
        JButton btnSubirImagen = new JButton("Sube tu foto");
        btnSubirImagen.addActionListener(e -> {
            VentanaSubirConcurso ventanaSubir = new VentanaSubirConcurso();
            ventanaSubir.setVisible(true);
        });

        // Panel para colocar el botón en la parte superior
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnSubirImagen);
        add(bottomPanel, BorderLayout.NORTH);
        
        setVisible(true);
        
        // Crear un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(concursoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir el JScrollPane a la ventana principal
        add(scrollPane);
    }
}
   