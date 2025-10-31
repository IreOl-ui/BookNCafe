package domain;

import java.util.Set;

public class Bebida extends Producto {
	protected boolean alcohol;

	public Bebida(String nombre, String personaje, double precio, String descripcion, Set<Alergeno> alergeno, boolean alcohol) {
		super(nombre, personaje, precio, descripcion, alergeno);
		this.alcohol = alcohol;
	}

	public boolean isAlcohol() {
		return alcohol;
	}

	public void setAlcohol(boolean alcohol) {
		this.alcohol = alcohol;
	}
	
	@Override
	public String toString() {
		return nombre + " (" + personaje + ") " + precio + "€" + ": " + descripcion + ", alérgenos: " + alergeno + ", Alcohol? " + alcohol;
	}
}
