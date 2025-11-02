package gui;

import javax.swing.*;
import io.GestionProductos;
import domain.Cliente;
import domain.Perfil;
import domain.Producto;

import java.util.List;
import java.util.Set;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Cliente clienteActual;

    public VentanaPrincipal(Cliente cliente) {
        this.clienteActual = cliente;

        setTitle("Bienvenid@, " + cliente.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Mostrar una barra de progreso durante la carga
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

        SwingWorker<Object, Void> worker = new SwingWorker<>() {
            private List<Participante> participantes;
            private Set<Producto> productos;

            @Override
            protected Object doInBackground() throws Exception {
                // Simular tareas de carga
                Thread.sleep(500); // Simula la inicialización
                participantes = GestionParticipantes.cargarParticipantesCSV();
                productos = GestionProductos.cargarProductosCSV();
                return null;
            }

            @Override
            protected void done() {
                dialogoCarga.dispose();
                try {
                    get(); // Captura cualquier excepción
                    inicializarVentanaPrincipal(participantes, productos, cliente);
                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        };

        worker.execute();
        dialogoCarga.setVisible(true);
    }

    private void inicializarVentanaPrincipal(List<Participante> participantes, Set<Producto> productos, Cliente cliente) {
        // Crear el panel de fondo
        FondoPanel fondoPanel = new FondoPanel("resources/images/iconos/Fondo.jpeg");
        fondoPanel.setLayout(new BorderLayout());

        // Panel superior para el logo y botones
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel bienvenida = new JLabel("¡Hola, " + cliente.getNombre() + "!");
        bienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        bienvenida.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bienvenida.setForeground(Color.BLACK);
        bienvenida.setOpaque(true);
        bienvenida.setBackground(new Color(255, 255, 255, 128));

        panelSuperior.add(bienvenida, BorderLayout.NORTH);

        // Panel derecho con botones
        JPanel panelSuperiorDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperiorDerecho.setBackground(Color.WHITE);

        JButton btnConcurso = crearBotonConIcono("resources/images/iconos/Trofeo.jpeg", "Concurso", 64, 64);
        btnConcurso.addActionListener(e -> new VentanaConcurso(participantes).setVisible(true));

        JButton btnReservas = crearBotonConIcono("resources/images/iconos/Reservas.jpeg", "Reservas", 64, 64);
        btnReservas.addActionListener(e -> new VentanaReserva().setVisible(true));

        JButton btnCompras = crearBotonConIcono("resources/images/iconos/Compra.jpeg", "Compras", 64, 64);
        btnCompras.addActionListener(e -> new VentanaCompra(productos).setVisible(true));

        JButton btnPerfil = crearBotonConIcono("resources/images/iconos/Perfil.jpeg", "Perfil", 64, 64);
        btnPerfil.addActionListener(e -> new VentanaPerfil(cliente, new Perfil(cliente.getNombre(), "resources/images/Perfiles/" + cliente.getNombre() + ".jpg", 100.0)).setVisible(true));

        panelSuperiorDerecho.add(btnConcurso);
        panelSuperiorDerecho.add(btnReservas);
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

        JButton boton = new JButton(iconoRedimensionado);
        boton.setToolTipText(toolTip);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);

        return boton;
    }
}
