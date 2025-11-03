package io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


import domain.Producto;

public class Tickets {

    //ticket del carro
    public static void guardarTicketPedido(Map<Producto, Integer> carrito, double total, String nombreCliente) {
        try {
            
            String nombreArchivo = "ticket_pedido_" + System.currentTimeMillis() + ".txt";

            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));

            pw.println("=== BOOK N' CAFE ===");
            pw.println("TICKET DE PEDIDO");
            pw.println("----------------------");
            pw.println("Cliente: " + nombreCliente);
            pw.println("");

            // recorremos el carro
            for (Map.Entry<Producto, Integer> entrada : carrito.entrySet()) {
                Producto p = entrada.getKey();
                int cantidad = entrada.getValue();
                double subtotal = p.getPrecio() * cantidad;
                pw.println(p.getNombre() + " x" + cantidad + " = " + subtotal + " €");
            }

            pw.println("----------------------");
            pw.println("TOTAL: " + total + " €");
            pw.println("Gracias por su compra!");

            pw.close();

            System.out.println("Ticket guardado en: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al guardar el ticket: " + e.getMessage());
        }
    }

    // ticket reserva
    public static void guardarTicketReserva(String id, String nombreCliente, String mesa,
                                            String fecha, String hora, String estado) {
        try {
            String nombreArchivo = "ticket_reserva_" + System.currentTimeMillis() + ".txt";

            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));

            pw.println("=== BOOK N' CAFE ===");
            pw.println("TICKET DE RESERVA");
            pw.println("----------------------");
            pw.println("ID Reserva: " + id);
            pw.println("Cliente: " + nombreCliente);
            pw.println("Mesa: " + mesa);
            pw.println("Fecha: " + fecha);
            pw.println("Hora: " + hora);
            pw.println("Estado: " + estado);
            pw.println("----------------------");
            pw.println("Por favor, muestre este ticket al llegar.");
            pw.close();

            System.out.println("Ticket guardado en: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al guardar el ticket: " + e.getMessage());
        }
    }
}
