package domain;

import java.util.Set;

public class Salado extends Producto {

	public Salado(String nombre, String personaje, double precio, String descripcion, Set<Alergeno> alergeno) {
		super(nombre, personaje, precio, descripcion, alergeno);
	}
}