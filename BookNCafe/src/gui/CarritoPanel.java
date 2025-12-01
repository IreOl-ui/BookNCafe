package gui;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import domain.Producto;
import io.HistorialPedidos;   

public class CarritoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel totalLabel;
    private JButton pagarButton;
    private JTable productosTable;
    private DefaultTableModel tableModel;
    private Map<Producto, Integer> carrito;
    private double total;
    
    public CarritoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Carrito de compras"));
        carrito = new HashMap<>();
        total = 0.0;

        // Configuración de la tabla
        String[] columnNames = {"Producto", "Cantidad", "Precio"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productosTable = new JTable(tableModel);
        productosTable.setFillsViewportHeight(true);
        productosTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        ajustarAnchoColumnas();

        JScrollPane scrollPane = new JScrollPane(productosTable);
        add(scrollPane);

        add(Box.createVerticalStrut(10));

        totalLabel = new JLabel("Total: 0.00€");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(totalLabel);

        pagarButton = new JButton("Pagar");
        pagarButton.setAlignmentX(CENTER_ALIGNMENT);
        pagarButton.setEnabled(false);
        pagarButton.addActionListener(e -> realizarPago());
        add(Box.createVerticalStrut(10));
        add(pagarButton);
    }

    public void agregarProducto(Producto producto) {
        carrito.put(producto, carrito.getOrDefault(producto, 0) + 1);
        total += producto.getPrecio();
        actualizarListaProductos();
        actualizarTotal();
    }

    private void actualizarListaProductos() {
        tableModel.setRowCount(0);

        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            Object[] row = {
                producto.getNombre(),
                cantidad,
                String.format("%.2f€", producto.getPrecio() * cantidad)
            };
            tableModel.addRow(row);
        }
    }

    private void actualizarTotal() {
        totalLabel.setText(String.format("Total: %.2f€", total));
        pagarButton.setEnabled(total > 0);
    }

    private void realizarPago() {

        JPanel panelPago = new JPanel();
        panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));

        JTextField numeroTarjetaField = new JTextField(16);
        JTextField nombreTitularField = new JTextField(20);
        JTextField fechaVencimientoField = new JTextField(7);
        JTextField codigoSeguridadField = new JTextField(3);

        panelPago.add(new JLabel("Número de tarjeta de crédito:"));
        panelPago.add(numeroTarjetaField);
        panelPago.add(Box.createVerticalStrut(10));

        panelPago.add(new JLabel("Nombre del titular de la tarjeta:"));
        panelPago.add(nombreTitularField);
        panelPago.add(Box.createVerticalStrut(10));

        panelPago.add(new JLabel("Fecha de vencimiento (MM/AAAA):"));
        panelPago.add(fechaVencimientoField);
        panelPago.add(Box.createVerticalStrut(10));

        panelPago.add(new JLabel("Código de seguridad (CVV):"));
        panelPago.add(codigoSeguridadField);

        int resultado = JOptionPane.showConfirmDialog(
            this,
            panelPago,
            "Datos de pago",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {

            String numeroTarjeta = numeroTarjetaField.getText();
            String nombreTitular = nombreTitularField.getText();
            String fechaVencimiento = fechaVencimientoField.getText();
            String codigoSeguridad = codigoSeguridadField.getText();

            if (numeroTarjeta.isEmpty() || !esTarjetaValida(numeroTarjeta)) {
                JOptionPane.showMessageDialog(this, "Número de tarjeta inválido.");
                return;
            }

            if (nombreTitular.isEmpty() || fechaVencimiento.isEmpty() || codigoSeguridad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Pago realizado. Total: " + String.format("%.2f€", total));

         
            HistorialPedidos.guardarPedido(new HashMap<>(carrito), total, nombreTitular);
        
            carrito.clear();
            total = 0.0;
            actualizarListaProductos();
            actualizarTotal();
        }
    }

    private boolean esTarjetaValida(String numeroTarjeta) {
        return numeroTarjeta.matches("\\d{16}");
    }

    private void ajustarAnchoColumnas() {
        TableColumn columnProducto = productosTable.getColumnModel().getColumn(0);
        columnProducto.setPreferredWidth(200); 

        TableColumn columnCantidad = productosTable.getColumnModel().getColumn(1);
        columnCantidad.setPreferredWidth(100);

        TableColumn columnPrecio = productosTable.getColumnModel().getColumn(2);
        columnPrecio.setPreferredWidth(100);
    }
}



