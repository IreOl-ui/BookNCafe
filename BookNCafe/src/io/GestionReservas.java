package io;

import domain.Reserva;
import domain.Cliente;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionReservas {

    private static final String ARCHIVO = "reservas.csv";

    
    public static List<Reserva> cargarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        int uno;
//h
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombreCliente = partes[0].trim();
                    LocalDate fecha = LocalDate.parse(partes[1].trim());
                    Cliente cliente = new Cliente(nombreCliente);
                    reservas.add(new Reserva(fecha, cliente));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo al guardar.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    
    public static void guardarReservas(List<Reserva> reservas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Reserva r : reservas) {
                bw.write(r.getCliente().getNombre() + "," + r.getFecha());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
