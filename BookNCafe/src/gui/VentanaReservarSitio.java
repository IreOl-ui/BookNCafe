package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;

public class VentanaReservarSitio extends JFrame {

    private JTable tablaHorarios;
    private DefaultTableModel modelo;
    private JButton btnReservar;

    public VentanaReservarSitio() {
        setTitle("Reservar sitio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de tabla
        String[] columnas = {"Hora", "Reservado por"};
        modelo = new DefaultTableModel(columnas, 0);

        // Llenar las horas de 9 a 22
        for (int hora = 9; hora <= 22; hora++) {
            modelo.addRow(new Object[]{hora + ":00", "Disponible"});
        }

        tablaHorarios = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaHorarios);

        btnReservar = new JButton("Reservar horario");
        btnReservar.addActionListener(e -> reservar());

        add(scroll, "Center");
        add(btnReservar, "South");
    }

    private void reservar() {
        int fila = tablaHorarios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una hora primero.");
            return;
        }

        String estado = (String) modelo.getValueAt(fila, 1);
        if (!estado.equals("Disponible")) {
            JOptionPane.showMessageDialog(this, "Esa hora ya está reservada.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Nombre del cliente:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            modelo.setValueAt(nombre, fila, 1);

            // Obtener la hora
            String hora = (String) modelo.getValueAt(fila, 0);

            // Guardar en CSV
            guardarReservaEnCSV(hora, nombre);

            JOptionPane.showMessageDialog(this, "Reserva guardada.");
        }
    }

    private void guardarReservaEnCSV(String hora, String nombre) {
        try (FileWriter fw = new FileWriter("reservas.csv", true)) {
            fw.write(hora + "," + nombre + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo CSV.");
        }
    }
}
