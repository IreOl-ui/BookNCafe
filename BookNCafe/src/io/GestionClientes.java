package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Cliente;
import domain.Genero;

public class GestionClientes {
	// Cargar el fichero clientes.csv
	public static List<Cliente> cargarClientesCSV() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		File f = new File("resources/data/clientes.csv");
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()){
				String line = sc.nextLine();
				String[] campos = line.split(";");
				
				String nombre = campos[0];
				String apellido = campos[1];
				int DNI = Integer.parseInt(campos[2]);
				Genero genero = Genero.valueOf(campos[3].toUpperCase());
				int edad = Integer.parseInt(campos[4]);
				LocalDate fechaNac = LocalDate.parse(campos[5]);
				String email = campos[6];
				int tlf = Integer.parseInt(campos[7]);
				
				Cliente c = new Cliente(nombre, apellido, DNI, genero, edad, fechaNac, email, tlf);
				clientes.add(c);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error, no se ha podido cargar el fichero." + e);
		}
		
		return clientes;
	}
	
	// Guardar el fichero clientes.csv
	public static void guardarClientesCSV(List<Cliente> clientes, String ruta) {
		try (FileWriter writer = new FileWriter(ruta)) {
            // Encabezado
            writer.append("Nombre;Apellido;DNI;Genero;Edad;FechaDeNacimiento;Email;Tlf\n");

            // Escribir cada cliente
            for (Cliente c : clientes) {
            	writer.append(c.getNombre());
            	writer.append(c.getApellido());
            	writer.append(String.valueOf(c.getDNI()));
            	writer.append(String.valueOf(c.getGenero()));
            	writer.append(String.valueOf(c.getEdad()));
            	writer.append(String.valueOf(c.getFechaNac()));
            	writer.append(c.getEmail());
            	writer.append(String.valueOf(c.getTlf()));
                writer.append("\n");
            }

        } catch (IOException e) {
            System.err.println("Error, no se ha podido guardar datos al fichero." + e);
        }
	}
}
