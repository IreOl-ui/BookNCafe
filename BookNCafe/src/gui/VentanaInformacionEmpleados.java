package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class VentanaInformacionEmpleados extends JFrame {

    private Map<String, String[]> empleados; // DNI -> [nombre, sueldo]

    public VentanaInformacionEmpleados() {
        // Crear datos de ejemplo
        empleados = new HashMap<>();
        empleados.put("1111", new String[]{"Ana López", "1200"});
        empleados.put("2222", new String[]{"Carlos Pérez", "1500"});
        empleados.put("3333", new String[]{"María Gómez", "1800"});

        // Mostrar login primero
        pedirIdentificacion();
    }

    private void pedirIdentificacion() {
        String dni = JOptionPane.showInputDialog(this, "Ingrese su DNI de empleado:");

        if (dni == null) {
            // Si el usuario cancela
            System.exit(0);
        }

        if (empleados.containsKey(dni)) {
            
            mostrarListaEmpleados();
        } else {
            JOptionPane.showMessageDialog(this, "DNI no encontrado. Inténtelo de nuevo.");
            pedirIdentificacion(); 
        }
    }

    private void mostrarListaEmpleados() {
        setTitle("Lista de empleados");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"Nombre", "DNI", "Sueldo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);


        for (Map.Entry<String, String[]> entry : empleados.entrySet()) {
            String dni = entry.getKey();
            String nombre = entry.getValue()[0];
            String sueldo = entry.getValue()[1];
            modelo.addRow(new Object[]{nombre, dni, sueldo});
        }

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll);

        setVisible(true);
    }


}

