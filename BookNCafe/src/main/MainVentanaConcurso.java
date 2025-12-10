package main;

import domain.Participante;
import gui.VentanaConcurso;
import io.GestionParticipantes;
import javax.swing.SwingUtilities;
import java.util.List;

public class MainVentanaConcurso {
    public static void main(String[] args) {
        // Cargar los participantes desde el archivo CSV
        List<Participante> participantes = cargarParticipantes();
        
        System.out.println("Total participantes cargados: " + participantes.size());
        for (Participante p : participantes) {
            System.out.println("- " + p.getNombre() + " " + p.getApellido() + 
                             " (Tel: " + p.getTelefono() + ")");
        }
        
        // Crear y mostrar la ventana del concurso
        SwingUtilities.invokeLater(() -> {
            VentanaConcurso ventana = new VentanaConcurso(participantes);
            ventana.setVisible(true);
        });
    }
    
    private static List<Participante> cargarParticipantes() {
        return GestionParticipantes.cargarParticipantesCSV();
    }
}
