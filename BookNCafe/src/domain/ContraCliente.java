package domain;

// Importación de clases necesarias para la encriptación
import java.security.MessageDigest;  // Para realizar el algoritmo de encriptación
import java.security.NoSuchAlgorithmException;  // Para manejar la excepción si el algoritmo no es encontrado

public class ContraCliente {  // Definición de la clase ContraseñaCliente
    private String nombre;  // Atributo para almacenar el nombre del cliente
    private String contraEncriptada;  // Atributo para almacenar la contraseña encriptada
    
    // Constructor que recibe el nombre y la contraseña del cliente
    public ContraCliente(String nombre, String contra) {
        this.nombre = nombre;  // Asigna el valor del nombre recibido al atributo 'nombre'
        this.contraEncriptada = encriptarContraseña(contra);  // Encripta la contraseña y la asigna al atributo 'contraseñaEncriptada'
    }
    
    // Método privado para encriptar la contraseña utilizando SHA-256
    protected String encriptarContraseña(String contraseña) {
        try {
            // Crea una instancia de MessageDigest utilizando el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  
            
            // Convierte la contraseña a bytes y la encripta con SHA-256
            byte[] hashBytes = digest.digest(contraseña.getBytes());  
            
            // StringBuilder para almacenar la representación en hexadecimal del hash
            StringBuilder hexString = new StringBuilder();
            // Itera sobre cada byte del resultado de la encriptación
            for (byte b : hashBytes) {
                // Convierte cada byte a su representación hexadecimal y la agrega al StringBuilder
                hexString.append(String.format("%02x", b));  
            }
            
            return hexString.toString();  // Devuelve la cadena hexadecimal (hash) resultante
        } catch (NoSuchAlgorithmException e) {  // Captura si el algoritmo de encriptación no está disponible
            throw new RuntimeException("Error al encriptar la contraseña", e);  // Lanza una excepción en caso de error
        }
    }

    // Método getter para obtener el nombre del cliente
    public String getNombre() {
        return nombre;  // Retorna el nombre del cliente
    }

    // Método getter para obtener la contraseña encriptada del cliente
    public String getContraseñaEncriptada() {
        return contraEncriptada;  // Retorna la contraseña encriptada
    }
    
    // Método toString para transformar los datos de la clase a un formato legible (como una cadena de texto)
    @Override 
    public String toString() { 
        // Devuelve una cadena con el nombre y la contraseña encriptada separados por ";"
        return nombre + ";" + contraEncriptada; 
    }
}