package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import domain.Cliente;

public class GestionClientes {
    public static final Path ruta_archivo_clientes = Paths.get("resources", "data", "clientes.csv"); // Ruta del archivo CSV
    private List<Cliente> listaClientes;

    public GestionClientes() {
        try {
            // Verifica si el directorio existe; si no, lo crea
            Files.createDirectories(ruta_archivo_clientes.getParent());
            // Verifica si el archivo existe; si no, lo crea
            File archivoClientes = ruta_archivo_clientes.toFile();
            if (!archivoClientes.exists()) {
                archivoClientes.createNewFile();
                System.out.println("Archivo clientes.csv creado en: " + ruta_archivo_clientes.toString());
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo o directorio de clientes");
        }

        this.listaClientes = cargarClientesCSV(ruta_archivo_clientes.toString()); // Cargar los clientes al inicializar
    }

    // Método para obtener un cliente por nombre
    public Cliente obtenerClientePorNombre(String nombre) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                return cliente; // Devuelve el cliente si coincide el nombre
            }
        }
        return null; // Retorna null si no encuentra el cliente
    }

    // Guardar el fichero desde clientes.csv
    public void guardarClientesCSV(Cliente cliente) {
        // Comprobamos si el cliente ya existe en el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta_archivo_clientes.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equals(cliente.toString())) {
                    System.out.println("El cliente ya existe en el archivo.");
                    return; // Si el cliente ya existe, no se guarda
                }
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al verificar si el cliente ya existe.");
            return;
        }

        // Si el cliente no existe, lo guardamos
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta_archivo_clientes.toFile(), true))) {
            writer.write(cliente.toString()); // Añade los datos del cliente al archivo
            writer.newLine(); // Nueva línea después de cada cliente
            System.out.println("Los datos del cliente se han guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error, no se han podido guardar los datos del cliente.");
        }
    }

    // Cargar el fichero desde clientes.csv
    private List<Cliente> cargarClientesCSV(String archivo) {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Saltar la cabecera del CSV
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length == 8) {                    
                    String nombre = campos[0].trim();
                	String apellido = campos[1].trim();
                	String DNI = campos[2].trim();
                	String email = campos[6].trim();
                	String tlf = campos[7].trim();

                    Cliente c = new Cliente(nombre, apellido, DNI, email, tlf);
                    clientes.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Error, no se ha podido leer el archivo CSV: " + e.getMessage());
        }
        
        return clientes;
    }

    // Método para obtener todos los clientes
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
    
}
