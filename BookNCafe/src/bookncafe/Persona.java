package bookncafe;

import java.time.LocalDate;

public class Persona {
	private String nombres;
	private String apellidos;
	private int edad;
	private LocalDate fechaNac;
	private String email;
	public Persona(String nombres, String apellidos, int edad, LocalDate fechaNac, String email) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.edad = edad;
		this.fechaNac = fechaNac;
		this.email = email;
	}
	public String getNombre() {
		return nombres;
	}
	public void setNombre(String nombres) {
		this.nombres = nombres;
	}
	public String getApellido() {
		return apellidos;
	}
	public void setApellido(String apellidos) {
		this.apellidos = apellidos;
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
	@Override
	public String toString() {
		return "Persona [nombre=" + nombres + ", apellido=" + apellidos + ", edad=" + edad + ", fechaNac=" + fechaNac
				+ ", email=" + email + "]";
	}
	
}
