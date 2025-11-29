package io;

import domain.Participante;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class GestionParti {

    private static final Path CSV_FILE = Paths.get("resources", "data", "participantes.csv");

    // Constructor para crear el archivo si no existe
    public GestionParti() {
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
}