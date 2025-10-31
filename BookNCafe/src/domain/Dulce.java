package domain;

import java.util.Set;

public class Dulce extends Producto {

	public Dulce(String nombre, String personaje, double precio, String descripcion, Set<Alergeno> alergeno) {
		super(nombre, personaje, precio, descripcion, alergeno);
	}
}