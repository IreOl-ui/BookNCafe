package domain;

public class Cliente {

	private String nombre;
	private String apellido;
	private String dni;
	private String email;
	private String tlf;
	public Cliente(String nombre, String apellido, String dni, String email, String tlf) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
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
	public String getDNI() {
		return dni;
	}
	public void setDNI(String dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTlf() {
		return tlf;
	}
	public void setTlf(String tlf) {
		this.tlf = tlf;
	}
	@Override
	public String toString() {
		return nombre + ";" + apellido + ";" + dni + ";" + email + ";" + tlf + "]";
	}
	
}
