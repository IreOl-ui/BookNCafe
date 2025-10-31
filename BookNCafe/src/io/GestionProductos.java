package io;

import domain.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GestionProductos {
	public static Set<Producto> cargarProductosCSV() {
		Set<Producto> productos = new HashSet<Producto>();
		
		File f = new File("resources/data/menu.csv");
		try {
			Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            if (sc.hasNextLine()) {
                sc.nextLine(); // Se salta la primera línea que contiene los nombres de los campos.
            }
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				Producto nuevo;
                Set<Alergeno> alergenos = new HashSet<>(); // Separa los alérgenos por comas y los convierte a campos del enum ignorando si está en mayúsculas o minúsculas.
                if (!campos[5].isEmpty() && !campos[5].equalsIgnoreCase("NUL")) {
                    String[] listaAlergenos = campos[5].split(",");
                    for (String nombreAlergeno : listaAlergenos) {
                        try {
                            Alergeno alergeno = Alergeno.valueOf(nombreAlergeno.trim().toUpperCase());
                            alergenos.add(alergeno);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Alergeno desconocido: " + nombreAlergeno);
                        }
                    }
                }
				if (campos[0].equals("Bebida")) { // Separa los productos en función de si son bebidas, dulce o salado.
					nuevo = new Bebida(campos[1], campos[2], Double.parseDouble(campos[3]), campos[4], alergenos, "Si".equalsIgnoreCase(campos[6]));
				} else if (campos[0].equals("Dulce")) {
					nuevo = new Dulce(campos[1], campos[2], Double.parseDouble(campos[3]), campos[4], alergenos);
				}
				else {
					nuevo = new Salado(campos[1], campos[2], Double.parseDouble(campos[3]), campos[4], alergenos);
				}
				
				productos.add(nuevo);
			}
			
			sc.close(); // Declaraciones catch para capturar los posibles errores.
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Codificación no soportada: " + e.getMessage());
        }
		
		return productos;
	}
}
