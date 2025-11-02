package main;

import gui.VentanaInformacionEmpleados;

public class MainVentanaInformacionEmpleados {
    public static void main(String[] args) {
        // Lanza la ventana de empleados
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaInformacionEmpleados();
        });
    }
}

