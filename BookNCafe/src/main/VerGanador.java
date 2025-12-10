package main;

import domain.Concurso;
import domain.Participante;
import io.GestionParticipantes;

public class VerGanador {
    public static void main(String[] args) {
        Concurso concurso = new Concurso();
        
        // Cargar participantes CON calificaciones ya guardadas
        concurso.cargarCalificacionesDesdeArchivo();
        
        // Mostrar todos los participantes y sus promedios
        System.out.println("=== PARTICIPANTES Y PROMEDIOS ===");
        for (Participante p : concurso.getParticipantes()) {
            System.out.printf("%s %s - Promedio: %.2f%n", 
                p.getNombre(), 
                p.getApellido(), 
                p.obtenerPromedioGeneral());
        }
        
        // Mostrar el ganador
        System.out.println("\n=== GANADOR ===");
        System.out.println(concurso.obtenerPromedioMasAlto());
    }
}