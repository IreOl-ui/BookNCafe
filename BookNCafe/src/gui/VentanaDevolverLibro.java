package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import domain.GestionLibros;

public class VentanaDevolverLibro extends JFrame {

    private JTextField txtIsbn;
    private JButton btnDevolver;
    private JButton btnCancelar;

    public VentanaDevolverLibro() {
        setTitle("Devolver Libro");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCentral.add(new JLabel("ISBN del libro:"));
        txtIsbn = new JTextField();
        panelCentral.add(txtIsbn);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnDevolver = new JButton("Devolver");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnDevolver);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnDevolver.addActionListener(e -> devolverLibro());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void devolverLibro() {
        String isbn = txtIsbn.getText().trim();

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ISBN del libro a devolver.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean eliminado = GestionLibros.eliminarLibro(isbn);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Libro con ISBN " + isbn + " devuelto correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un libro con ese ISBN.", "Error", JOptionPane.WARNING_MESSAGE);
        }

        txtIsbn.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaDevolverLibro().setVisible(true));
    }
}
