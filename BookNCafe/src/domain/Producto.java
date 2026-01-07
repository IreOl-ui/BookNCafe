package domain;

import java.util.Objects;
import java.util.Set;

public abstract class Producto {
	protected String nombre;
	protected String personaje;
	protected double precio;
	protected String descripcion;
	protected Set<Alergeno> alergeno;
	
	public Producto(String nombre, String personaje, double precio, String descripcion, Set<Alergeno> alergeno) {
		super();
		this.nombre = nombre;
		this.personaje = personaje;
		this.precio = precio;
		this.descripcion = descripcion;
		this.alergeno = alergeno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPersonaje() {
		return personaje;
	}

	public void setPersonaje(String personaje) {
		this.personaje = personaje;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Alergeno> getAlergeno() {
		return alergeno;
	}

	public void setAlergeno(Set<Alergeno> alergeno) {
		this.alergeno = alergeno;
	}

	@Override
	public String toString() {
		return nombre + " (" + personaje + ") " + precio + "€" + ": " + descripcion + ", alérgenos: " + alergeno;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alergeno, descripcion, nombre, personaje, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return alergeno == other.alergeno && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(personaje, other.personaje)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}
	public double getPrecioConIva() {
	    return this.precio * 1.21;
	}

}