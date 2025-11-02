package gui;

import javax.swing.*;
import io.GestionProductos;
import domain.Cliente;
import domain.Perfil;
import domain.Producto;

import java.util.Set;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Cliente clienteActual;

    public VentanaPrincipal(Cliente cliente) {
        this.clienteActual = cliente;

        setTitle("¡Bienvenid@, " + cliente.getNombre() + "!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        cargarConBarraDeProgreso(cliente);
    }

    private void cargarConBarraDeProgreso(Cliente cliente) {
        JDialog dialogoCarga = new JDialog(this, "Cargando...", true);
        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setIndeterminate(true);
        barraProgreso.setStringPainted(true);
        dialogoCarga.add(BorderLayout.CENTER, barraProgreso);
        dialogoCarga.setSize(300, 75);
        dialogoCarga.setLocationRelativeTo(this);

        SwingWorker<Set<Producto>, Void> worker = new SwingWorker<>() {
            private Set<Producto> productos;

            @Override
            protected Set<Producto> doInBackground() throws Exception {
                Thread.sleep(500);
                productos = GestionProductos.cargarProductosCSV();
                return null;
            }

            @Override
            protected void done() {
                dialogoCarga.dispose();
                try {
                	get(); // Get para capturar cualquier excepcion
                    inicializarVentanaPrincipal(productos, cliente);
                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        };

        worker.execute();
        dialogoCarga.setVisible(true);
    }

    private void inicializarVentanaPrincipal(Set<Producto> productos, Cliente cliente) {
        FondoPanel fondoPanel = new FondoPanel("resources/images/iconos/Fondo.jpeg");
        fondoPanel.setLayout(new BorderLayout());
        
        // JPanel
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        // JLabel
        JLabel bienvenida = new JLabel("¡Hola, " + cliente.getNombre() + "!");
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        bienvenida.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bienvenida.setForeground(Color.BLACK);
        bienvenida.setOpaque(true);
        bienvenida.setBackground(new Color(255, 255, 255, 128));

        panelSuperior.add(bienvenida, BorderLayout.NORTH);
        
        JPanel panelSuperiorDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperiorDerecho.setBackground(Color.WHITE);

        // JButton de compra y perfil
        JButton btnCompras = crearBotonConIcono("resources/images/iconos/Compra.jpeg", "Compras", 64, 64);
        btnCompras.addActionListener(e -> new VentanaCompra(productos).setVisible(true));
        JButton btnPerfil = crearBotonConIcono("resources/images/iconos/Perfil.jpeg", "Perfil", 64, 64);
        btnPerfil.addActionListener(e -> new VentanaPerfil(cliente, new Perfil(cliente.getNombre(), "resources/images/Perfiles/" + cliente.getNombre() + ".jpg", 100.0)).setVisible(true));

        panelSuperiorDerecho.add(btnCompras);
        panelSuperiorDerecho.add(btnPerfil);

        panelSuperior.add(panelSuperiorDerecho, BorderLayout.EAST);
        fondoPanel.add(panelSuperior, BorderLayout.NORTH);
        setContentPane(fondoPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton crearBotonConIcono(String rutaIcono, String toolTip, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(rutaIcono);
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

        // JButton
        JButton boton = new JButton(iconoRedimensionado);
        boton.setToolTipText(toolTip);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);

        return boton;
    }
}
