package gui;

import domain.Participante;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;

public class VentanaConcurso extends JFrame {  
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

        
        // Crear un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(concursoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

   
        add(scrollPane);
    }
}
