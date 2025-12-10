package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import domain.Producto;

public class HistorialPedidos {

    private static final String ARCHIVO = "historial_pedidos.csv";

    
    public static void guardarPedido(Map<Producto, Integer> carrito, double total, String cliente) {
        
        StringBuilder detalle = new StringBuilder();
        boolean primero = true;

        for (Map.Entry<Producto, Integer> entrada : carrito.entrySet()) {
            Producto p = entrada.getKey();
            int cantidad = entrada.getValue();

            if (!primero) {
                detalle.append(", ");
            }
            detalle.append(p.getNombre()).append(" x").append(cantidad);
            primero = false;
        }

      
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaHora = ahora.format(formato);

        
        String linea = fechaHora + ";" + cliente + ";" + String.format("%.2f", total) + ";" + detalle.toString();

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(ARCHIVO, true)); 
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar en historial_pedidos: " + e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                   
                }
            }
        }
    }
}

