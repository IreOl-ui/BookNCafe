package io;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class GestionConcurso {


	public static void subirYGuardarImagen() {
        // Crear un JFileChooser para seleccionar la imagen
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione una imagen para subir");

        // Filtrar solo imágenes
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg", "gif"));

        // Mostrar el diálogo de selección de archivo
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Ruta de destino donde quieres guardar la imagen
            String rutaDestino = "resources/images/Concurso/" + archivoSeleccionado.getName();

            try {
                // Copiar la imagen seleccionada al directorio de destino
                Files.copy(archivoSeleccionado.toPath(), Paths.get(rutaDestino), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Imagen guardada exitosamente en: " + rutaDestino);
            } catch (IOException e) {
                System.err.println("Error al guardar la imagen: " + e.getMessage());
            }
        } else {
            System.out.println("No se seleccionó ninguna imagen.");
        }
    }
}
