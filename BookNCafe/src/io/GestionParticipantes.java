package io;

import domain.Participante;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class GestionParticipantes {

    private static final Path CSV_FILE = Paths.get("resources", "data", "participantes.csv");

    // Constructor para crear el archivo si no existe
    public GestionParticipantes() {
        try {
            // Verifica si el directorio existe, si no lo crea
            Files.createDirectories(CSV_FILE.getParent());

            // Verifica si el archivo existe, si no lo crea
            File archivoParticipantes = CSV_FILE.toFile();
            if (!archivoParticipantes.exists()) {
                archivoParticipantes.createNewFile();
                System.out.println("Archivo participantes.csv creado en: " + CSV_FILE.toString());
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo o directorio de participantes.");
        }
    }

    // Cargar los participantes desde el CSV
    public static List<Participante> cargarParticipantesCSV() {
        List<Participante> participantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Separar los campos de cada línea
                String[] datos = line.split(";");
                if (datos.length == 3) {
                    String nombre = datos[0];
                    String apellido = datos[1];
                    String telefono = datos[2];
                    // Crear el participante y agregarlo a la lista
                    participantes.add(new Participante(nombre, apellido, telefono));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return participantes;
    }

    // Guardar un participante en el CSV, verificando que no se repita
    public static void guardarParticipante(Participante participante) {
        // Verificar si el participante ya existe en el archivo
        if (existeParticipante(participante)) {
            System.out.println("El participante ya está registrado en el concurso.");
            return;  // No guardar el participante si ya existe
        }

        // Si no existe, agregarlo al archivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE.toFile(), true))) {
            String participanteInfo = participante.getNombre() + ";" + participante.getApellido() + ";" + participante.getTelefono() + "\n";
            writer.write(participanteInfo);
            System.out.println("Participante guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el participante: " + e.getMessage());
        }
    }

    // Método para verificar si un participante ya existe en el archivo
    private static boolean existeParticipante(Participante participante) {
        List<Participante> participantes = cargarParticipantesCSV(); // Cargar todos los participantes
        for (Participante p : participantes) {
            // Si el nombre, apellido y teléfono coinciden, se considera duplicado
            if (p.getNombre().equals(participante.getNombre()) && p.getApellido().equals(participante.getApellido()) && p.getTelefono().equals(participante.getTelefono())) {
                return true;  // El participante ya existe
            }
        }
        return false;  // El participante no existe
    }
    
    public static List<Participante> cargarParticipantesConCalificaciones() {
        List<Participante> participantes = new ArrayList<>();
        String rutaCSV = "resources/data/calificacionesConcurso.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;
            br.readLine(); // Saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String nombre = datos[0];
                String apellido = datos[1];
                String telefono = datos[2];
                double creatividad = Double.parseDouble(datos[3]);
                double material = Double.parseDouble(datos[4]);
                double tecnica = Double.parseDouble(datos[5]);

                Participante participante = new Participante(nombre, apellido, telefono);
                participante.agregarCalificacion("creatividad", creatividad);
                participante.agregarCalificacion("material", material);
                participante.agregarCalificacion("tecnica", tecnica);

                participantes.add(participante);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de calificaciones: " + e.getMessage());
        }

        return participantes;
    }
}
