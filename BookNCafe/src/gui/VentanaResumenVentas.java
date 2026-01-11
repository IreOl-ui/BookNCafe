package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;

public class VentanaResumenVentas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel lblPedidos;
    private JLabel lblTotal;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaResumenVentas() {
        setTitle("Resumen de ventas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelArriba = new JPanel(new GridLayout(2, 1));
        lblPedidos = new JLabel("Pedidos: 0");
        lblTotal = new JLabel("Total vendido: 0.00€");
        panelArriba.add(lblPedidos);
        panelArriba.add(lblTotal);
        add(panelArriba, BorderLayout.NORTH);

        String[] cols = {"Fecha", "Cliente", "Total"};
        modelo = new DefaultTableModel(cols, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelAbajo = new JPanel();
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCerrar = new JButton("Cerrar");
        panelAbajo.add(btnActualizar);
        panelAbajo.add(btnCerrar);
        add(panelAbajo, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargar());
        btnCerrar.addActionListener(e -> dispose());

        cargar();
    }

    private void cargar() {
        modelo.setRowCount(0);

        int pedidos = 0;
        double total = 0.0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("historial_pedidos.csv"));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", 4);
                if (partes.length >= 3) {
                    String fecha = partes[0];
                    String cliente = partes[1];
                    String totalStr = partes[2].replace(",", ".");
                    double t = 0.0;

                    try {
                        t = Double.parseDouble(totalStr);
                    } catch (Exception ex) {
                        t = 0.0;
                    }

                    pedidos++;
                    total += t;

                    modelo.addRow(new Object[]{fecha, cliente, String.format("%.2f€", t)});
                }
            }

            br.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Todavía no hay historial (historial_pedidos.csv).");
        }

        lblPedidos.setText("Pedidos: " + pedidos);
        lblTotal.setText(String.format("Total vendido: %.2f€", total));
    }
}
