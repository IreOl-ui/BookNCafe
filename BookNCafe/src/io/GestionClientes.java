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
    public static final Path ruta_archivo_clientes = Paths.get("resources", "data", "clientes.csv");
    private List<Cliente> listaClientes;

    // Gestionar clientes
    public GestionClientes() {
        try {
            Files.createDirectories(ruta_archivo_clientes.getParent());
            File archivoClientes = ruta_archivo_clientes.toFile();
            if (!archivoClientes.exists()) {
                archivoClientes.createNewFile();
                System.out.println("Archivo clientes.csv creado en: " + ruta_archivo_clientes.toString());
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo o directorio de clientes");
        }

        this.listaClientes = cargarClientesCSV(ruta_archivo_clientes.toString());
    }

    // Obtener nombre de clientes
    public Cliente obtenerClientePorNombre(String nombre) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                return cliente;
            }
        }
        return null;
    }

    // Guardar clientes
    public void guardarClientesCSV(Cliente cliente) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta_archivo_clientes.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equals(cliente.toString())) {
                    System.out.println("El cliente ya existe en el archivo.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al verificar si el cliente ya existe.");
            return;
        }

        // Si cliente no existe, entonces guardamos
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta_archivo_clientes.toFile(), true))) {
            writer.write(cliente.toString());
            writer.newLine();
            System.out.println("Los datos del cliente se han guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error, no se han podido guardar los datos del cliente.");
        }
    }

    // Cargar clientes
    private List<Cliente> cargarClientesCSV(String archivo) {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length == 4) {                    
                    String nombre = campos[0].trim();
                	String DNI = campos[1].trim();
                	String email = campos[2].trim();
                	String tlf = campos[3].trim();

                    Cliente c = new Cliente(nombre, DNI, email, tlf);
                    clientes.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Error, no se ha podido leer el archivo CSV: " + e.getMessage());
        }
        
        return clientes;
    }

    // Lista de todos los clientes
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
    
}
