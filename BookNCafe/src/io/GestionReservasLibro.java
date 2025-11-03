
package io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestionReservasLibro {

    private static final String ARCHIVO = "reservas_libro.csv"; // se crea al vuelo si no existe

    // Reserva nueva
    public static void reservar(String isbn, String titulo, String autor, String cliente) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println("RESERVAR;" + isbn + ";" + titulo + ";" + autor + ";" + cliente);
        } catch (IOException e) {
            System.out.println("Error escribiendo reserva: " + e.getMessage());
        }
    }

    // Modificar datos de la reserva (por ISBN) – guardamos lo nuevo tal cual
    public static void modificar(String isbn, String nuevoTitulo, String nuevoAutor, String nuevoCliente) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println("MODIFICAR;" + isbn + ";" + (nuevoTitulo == null ? "" : nuevoTitulo) + ";"
                    + (nuevoAutor == null ? "" : nuevoAutor) + ";" + (nuevoCliente == null ? "" : nuevoCliente));
        } catch (IOException e) {
            System.out.println("Error escribiendo modificación: " + e.getMessage());
        }
    }

    // Cancelar reserva (por ISBN)
    public static void cancelar(String isbn) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.println("CANCELAR;" + isbn);
        } catch (IOException e) {
            System.out.println("Error escribiendo cancelación: " + e.getMessage());
        }
    }
}
