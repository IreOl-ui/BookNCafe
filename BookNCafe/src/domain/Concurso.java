package domain;

import io.GestionParticipantes;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Concurso {
    private List<Participante> participantes;

    public Concurso() {
        this.participantes = new ArrayList<>();
    }

    public void agregarParticipante(Participante participante) {
        participantes.add(participante);
    }

    // Método para cargar los participantes con calificaciones desde el archivo CSV
    public void cargarCalificacionesDesdeArchivo() {
        this.participantes = GestionParticipantes.cargarParticipantesConCalificaciones();
    }

    // Método para obtener el promedio más alto entre los participantes
    public String obtenerPromedioMasAlto() {
        double promedioMasAlto = 0;
        Participante participanteConMejorPromedio = null;

        // Recorremos los participantes para encontrar el promedio más alto y quién lo tiene
        for (Participante participante : participantes) {
            double promedioActual = participante.obtenerPromedioGeneral();
            
            if (promedioActual > promedioMasAlto) {
                promedioMasAlto = promedioActual;
                participanteConMejorPromedio = participante;  // Guardamos al participante con el mejor promedio
            }
        }

        // Si encontramos un participante con un buen promedio, devolvemos el string formateado
        if (participanteConMejorPromedio != null) {
            return String.format("%.2f (%s %s, %s)", promedioMasAlto,
                                 participanteConMejorPromedio.getNombre(),
                                 participanteConMejorPromedio.getApellido(),
                                 participanteConMejorPromedio.getTelefono());
        } else {
            return "No se encontraron participantes.";
        }
    }
    
    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void iniciarConcurso() {
        // Ingreso de calificaciones
        Scanner scanner = new Scanner(System.in);
        final int NUM_JUECES = 5;

        for (Participante participante : participantes) {
            for (int j = 0; j < NUM_JUECES; j++) {
                for (String categoria : new String[]{"creatividad", "material", "tecnica"}) {
                    System.out.print("Ingrese la calificación para " + categoria + " de " + participante.getNombre() + " " + participante.getApellido() + ": ");
                    int calificacion = scanner.nextInt();
                    participante.agregarCalificacion(categoria, calificacion);
                }
            }
        }
        scanner.close();

        // Guardar las calificaciones en el archivo CSV
        guardarCalificacionesEnCSV();
    }

    private void guardarCalificacionesEnCSV() {
        String rutaCSV = "resources/data/calificacionesConcurso.csv";
        
        // Truncamos el archivo para borrar su contenido al principio
        try {
            Files.deleteIfExists(Paths.get(rutaCSV)); // Eliminar el archivo si existe
            Files.createFile(Paths.get(rutaCSV));     // Crear un nuevo archivo vacío
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al borrar o crear el archivo: " + e.getMessage());
            return;
        }

        // Usamos 'BufferedWriter' para escribir las nuevas calificaciones en el archivo
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(rutaCSV), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.WRITE)) {
            
            // Escribir el encabezado
            writer.write("Nombre;Apellido;Teléfono;Creatividad;Material;Técnica;Promedio General\n");
            
            // Escribir los datos de cada participante
            for (Participante participante : participantes) {
                // Escribir cada participante con sus calificaciones en formato CSV
                writer.write(participante.toCSVConCalificaciones());
                writer.newLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar las calificaciones: " + e.getMessage());
        }
    }
}
