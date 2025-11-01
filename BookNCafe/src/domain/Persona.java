 package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Persona {
	private String nombre;
	private String apellido;
	private int DNI;
	public enum Genero{
		MASCULINO, FEMENINO
	}
	private int edad;
	private LocalDate fechaNac;
	private String email;
	private int tlf;
	public Persona(String nombre, String apellido, int dNI, int edad, LocalDate fechaNac, String email, int tlf) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		DNI = dNI;
		this.edad = edad;
		this.fechaNac = fechaNac;
		this.email = email;
		this.tlf = tlf;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getDNI() {
		return DNI;
	}
	public void setDNI(int dNI) {
		DNI = dNI;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public LocalDate getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTlf() {
		return tlf;
	}
	public void setTlf(int tlf) {
		this.tlf = tlf;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", DNI=" + DNI + ", edad=" + edad
				+ ", fechaNac=" + fechaNac + ", email=" + email + ", tlf=" + tlf + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(DNI, email, tlf);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return DNI == other.DNI && Objects.equals(email, other.email) && tlf == other.tlf;
	}
	
}
