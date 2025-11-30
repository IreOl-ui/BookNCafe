package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;




/**Problemas:
 * 	No guarda los clientes
 * 
 */
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

        // Crear la tabla
        tablaHorarios = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaHorarios);

        // Crear el botón
        btnReservar = new JButton("Reservar horario");
        btnReservar.addActionListener(e -> reservar());

        // Agregar los componentes al JFrame
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
        }
    }
}