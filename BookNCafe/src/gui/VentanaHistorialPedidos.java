package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VentanaHistorialPedidos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaHistorialPedidos() {
        setTitle("Historial de pedidos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"Fecha y hora", "Cliente", "Total (€)", "Detalle"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("historial_pedidos.csv"));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", 4); // 4 columnas máximo
                if (partes.length == 4) {
                    modelo.addRow(new Object[]{partes[0], partes[1], partes[2], partes[3]});
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe aún, no hacemos nada
            System.out.println("No se pudo leer historial_pedidos.csv: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // ignoramos
                }
            }
        }
    }
}
