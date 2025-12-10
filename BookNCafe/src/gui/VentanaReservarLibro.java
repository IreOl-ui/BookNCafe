package gui;

import javax.swing.*;
import java.awt.*;

import domain.Libro;
import domain.GestionLibros;
import io.Tickets;
import io.GestionReservasLibro;

public class VentanaReservarLibro extends JFrame {

    private JTextField txtIsbn;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JButton btnReservar;
    private JButton btnCancelar;

    public VentanaReservarLibro() {
        setTitle("Reservar Libro");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentral.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        panelCentral.add(txtIsbn);

        panelCentral.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelCentral.add(txtTitulo);

        panelCentral.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelCentral.add(txtAutor);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnReservar = new JButton("Reservar");
        JButton btnModificar = new JButton("Modificar reserva");
        JButton btnCancelarReserva = new JButton("Cancelar reserva");
        JButton btnTicket = new JButton("Generar ticket");
        btnCancelar = new JButton("Cerrar");

        // Orden de botones
        panelBotones.add(btnReservar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelarReserva);
        panelBotones.add(btnTicket);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnReservar.addActionListener(e -> reservarLibro());
        btnCancelar.addActionListener(e -> dispose());

        // Generar ticket 
        btnTicket.addActionListener(e -> {
            String isbn = txtIsbn.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();

            if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de generar el ticket.");
                return;
            }

            String nombreCliente = JOptionPane.showInputDialog(this, "Nombre del cliente:");
            if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe escribir un nombre.");
                return;
            }

            Tickets.guardarTicketReservaLibro(isbn, titulo, autor, nombreCliente);
            JOptionPane.showMessageDialog(this, "Ticket generado.\nArchivo: ticket_reserva_libro.txt");
        });

        // Modificar reserva (registro simple)
        btnModificar.addActionListener(e -> modificarReserva());

        // Cancelar reserva (registro simple)
        btnCancelarReserva.addActionListener(e -> cancelarReserva());
    }

    // Reservar: guarda el libro como hacías y registra la reserva en el CSV simple
    private void reservarLibro() {
        String isbn = txtIsbn.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();

        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cliente = JOptionPane.showInputDialog(this, "Nombre del cliente:");
        if (cliente == null || cliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe indicar el nombre del cliente.");
            return;
        }

        // Guardar el libro 
        Libro libro = new Libro(isbn, titulo, autor);
        GestionLibros.guardarLibro(libro);

        // Registrar la reserva 
        GestionReservasLibro.reservar(isbn, titulo, autor, cliente);

        JOptionPane.showMessageDialog(this, "Libro reservado y guardado para: " + cliente, "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }

    // Modificar
    private void modificarReserva() {
        String isbn = txtIsbn.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escriba el ISBN de la reserva a modificar.");
            return;
        }

        String nuevoTitulo  = JOptionPane.showInputDialog(this, "Nuevo título (opcional):", txtTitulo.getText());
        String nuevoAutor   = JOptionPane.showInputDialog(this, "Nuevo autor (opcional):", txtAutor.getText());
        String nuevoCliente = JOptionPane.showInputDialog(this, "Nuevo cliente (opcional):");

        GestionReservasLibro.modificar(isbn,
                (nuevoTitulo == null ? "" : nuevoTitulo),
                (nuevoAutor  == null ? "" : nuevoAutor),
                (nuevoCliente== null ? "" : nuevoCliente));

        JOptionPane.showMessageDialog(this, "Modificación registrada en reservas_libro.csv");
    }

    // Cancelar: marca la reserva como cancelada 
    private void cancelarReserva() {
        String isbn = txtIsbn.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escriba el ISBN de la reserva a cancelar.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "¿Cancelar la reserva del ISBN " + isbn + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        GestionReservasLibro.cancelar(isbn);
        JOptionPane.showMessageDialog(this, "Cancelación registrada en reservas_libro.csv");
    }

    private void limpiarCampos() {
        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaReservarLibro().setVisible(true));
    }
}

