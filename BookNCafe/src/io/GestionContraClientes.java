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

import domain.ContraCliente;

public class GestionContraClientes {
    public static final Path ruta_archivo_clientes = Paths.get("resources", "data", "contraseñasClientes.csv");

    public GestionContraClientes() {
        try {
            Files.createDirectories(ruta_archivo_clientes.getParent());
            File archivoContraseñasClientes = ruta_archivo_clientes.toFile();
            if (!archivoContraseñasClientes.exists()) {
                archivoContraseñasClientes.createNewFile();
                System.out.println("Archivo contraseñasClientes.csv creado en: " + ruta_archivo_clientes.toString());
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo o directorio de clientes");
        }
    }

    public void guardarContraseñaCliente(ContraCliente cliente) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta_archivo_clientes.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Divide la línea en nombre y contraseña encriptada
                String[] datos = linea.split(";");
                if (datos.length == 2 && datos[0].equals(cliente.getNombre())) {
                    System.out.println("El cliente ya existe en el archivo.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al verificar si el cliente ya existe.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta_archivo_clientes.toFile(), true))) {
            writer.write(cliente.toString());
            writer.newLine();
            System.out.println("Los datos del cliente se han guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error, no se han podido guardar los datos del cliente.");
        }
    }

    public ContraCliente buscarClientePorNombre(String nombre) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta_archivo_clientes.toFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 2 && datos[0].equals(nombre)) {
                    return new ContraCliente(datos[0], datos[1]); // Recupera el cliente usando nombre y contraseña encriptada
                }
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al buscar el cliente.");
        }
        return null; // Si no encuentra el cliente, retorna null
    }
}
