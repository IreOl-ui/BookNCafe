package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ContraCliente {
    private String nombre;
    private String contraEncriptada;
    public ContraCliente(String nombre, String contra) {
        this.nombre = nombre;
        this.contraEncriptada = encriptarContraseña(contra);
    }
    
    // Método protected para encriptar la contraseña utilizando SHA-256
    protected String encriptarContraseña(String contraseña) {
        try {
            // Creamos una instancia de MessageDigest para luego convertir la contra a bytes y encriptarla
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  
            byte[] hashBytes = digest.digest(contraseña.getBytes());  
            
            // StringBuilder para almacenar la representación en hexadecimal del hash
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));  
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    public String getNombre() {
        return nombre;
    }
    public String getContraseñaEncriptada() {
        return contraEncriptada;
    }
    @Override 
    public String toString() {
        return nombre + ";" + contraEncriptada; 
    }
    
}