package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

import domain.Producto;

public class VentanaTicket extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    // 👇 ahora recibe también numeroTarjeta y fechaVencimiento
    public VentanaTicket(Map<Producto, Integer> carrito, double total,
                         String nombreCliente, String numeroTarjeta, String fechaVencimiento) {
        setTitle("Ticket del Pedido");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel con datos del cliente y pago
        JPanel panelSuperior = new JPanel(new GridLayout(4, 1));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblCliente = new JLabel("Cliente: " + nombreCliente);
        JLabel lblTarjeta = new JLabel("Tarjeta: " + numeroTarjeta);
        JLabel lblVencimiento = new JLabel("Vencimiento: " + fechaVencimiento);
        JLabel lblTotal = new JLabel(String.format("Total pagado: %.2f€", total));

        panelSuperior.add(lblCliente);
        panelSuperior.add(lblTarjeta);
        panelSuperior.add(lblVencimiento);
        panelSuperior.add(lblTotal);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla con los productos del carrito
        String[] columnas = {"Producto", "Cantidad", "Subtotal"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        // Rellenar la tabla con los datos del carrito
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            double subtotal = cantidad * producto.getPrecio();

            Object[] fila = {
                    producto.getNombre(),
                    cantidad,
                    String.format("%.2f€", subtotal)
            };
            modelo.addRow(fila);
        }

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);
    }
}
