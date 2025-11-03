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
        setSize(400, 250);
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
        btnCancelar = new JButton("Cancelar");
        JButton btnTicket = new JButton("Generar ticket");

        panelBotones.add(btnTicket);
        panelBotones.add(btnReservar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        
        btnReservar.addActionListener(e -> reservarLibro());
        btnCancelar.addActionListener(e -> dispose());

        // gen ticket
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
    }

    private void reservarLibro() {
        String isbn = txtIsbn.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();

        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Libro libro = new Libro(isbn, titulo, autor);
        GestionLibros.guardarLibro(libro);
        JOptionPane.showMessageDialog(this, "Libro reservado y guardado:\n" + libro, "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
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
