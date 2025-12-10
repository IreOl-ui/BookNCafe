package domain;

import domain.Libro;
import java.io.*;
import java.util.*;

public class GestionLibros {

    private static final String ARCHIVO = "libros.csv";

    // Guarda un libro en el CSV
    public static void guardarLibro(Libro libro) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println(libro.getIsbn() + "," + libro.getTitulo() + "," + libro.getAutor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carga todos los libros desde el CSV
    public static List<Libro> cargarLibros() {
        List<Libro> libros = new ArrayList<>();

        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return libros; // Si no existe el archivo, devolvemos lista vacía
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    libros.add(new Libro(partes[0], partes[1], partes[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return libros;
    }

    // Elimina un libro por ISBN (para devoluciones)
    public static boolean eliminarLibro(String isbn) {
        List<Libro> libros = cargarLibros();
        boolean eliminado = libros.removeIf(libro -> libro.getIsbn().equalsIgnoreCase(isbn));

        if (eliminado) {
            guardarListaCompleta(libros);
        }

        return eliminado;
    }

    // Guarda toda la lista actualizada en el CSV
    private static void guardarListaCompleta(List<Libro> libros) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Libro libro : libros) {
                pw.println(libro.getIsbn() + "," + libro.getTitulo() + "," + libro.getAutor());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
