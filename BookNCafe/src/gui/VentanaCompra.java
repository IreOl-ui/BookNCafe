package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Set;
import domain.Producto;

public class VentanaCompra extends JFrame {
    private CarritoPanel carritoPanel;

    public VentanaCompra(Set<Producto> productos) {
        mostrarBarraDeProgreso(productos);
    }

    private void mostrarBarraDeProgreso(Set<Producto> productos) {
        JDialog dialogoCarga = new JDialog(this, "Cargando Productos...", true);
        JProgressBar barraProgreso = new JProgressBar(0, 100); // Barra con valores de 0 a 100
        barraProgreso.setStringPainted(true); // Muestra el porcentaje como texto
        dialogoCarga.add(BorderLayout.CENTER, barraProgreso);
        dialogoCarga.setSize(300, 75);
        dialogoCarga.setLocationRelativeTo(this);

        // Hilo de trabajo para cargar y configurar la ventana
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simula una carga por pasos
                int totalPasos = productos.size();
                int pasoActual = 0;

                // Simular carga de productos (puedes usar lógica real aquí)
                for (Producto producto : productos) {
                    // Simular un pequeño retraso por producto
                    Thread.sleep(50);
                    pasoActual++;
                    int progreso = (int) ((double) pasoActual / totalPasos * 100);
                    publish(progreso); // Publicar el progreso para actualizar la barra
                }

                SwingUtilities.invokeAndWait(() -> inicializarVentanaCompra(productos));
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                // Actualizar la barra de progreso con el último valor publicado
                int progreso = chunks.get(chunks.size() - 1);
                barraProgreso.setValue(progreso);
            }

            @Override
            protected void done() {
                dialogoCarga.dispose(); // Cerrar el diálogo al completar la tarea
            }
        };

        worker.execute();
        dialogoCarga.setVisible(true); // Bloquea el hilo principal mientras el worker está activo
    }

    private void inicializarVentanaCompra(Set<Producto> productos) {
        setTitle("Compras - Catálogo de Productos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de productos (grid layout)
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(9, 0, 0, 5));
        panelProductos.setBackground(Color.WHITE);
        panelProductos.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Panel del carrito de compras en el lado derecho
        carritoPanel = new CarritoPanel();
        carritoPanel.setPreferredSize(new Dimension(350, getHeight()));

        // Añadir productos al grid layout
        for (Producto producto : productos) {
            ProductoPanel productoPanel = new ProductoPanel(producto);
            productoPanel.setBackground(Color.WHITE);

            // Configurar para añadir al carrito al hacer clic
            productoPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            productoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    carritoPanel.agregarProducto(producto);
                }
            });

            panelProductos.add(productoPanel);
        }

        // Añadir el panel de productos y el carrito a la ventana
        JScrollPane scrollPaneProductos = new JScrollPane(panelProductos);
        scrollPaneProductos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPaneProductos, BorderLayout.CENTER);
        add(carritoPanel, BorderLayout.EAST);

        // Mostrar la ventana después de la configuración
        setVisible(true);
    }
}