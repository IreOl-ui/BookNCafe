package domain;

public class Cliente {

	private String nombre;
	private String dni;
	private String email;
	private String tlf;
	public Cliente(String nombre, String dni, String email, String tlf) {
		super();
		this.nombre = nombre;
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
		return nombre + ";" + dni + ";" + email + ";" + tlf + "]";
	}
	public Cliente(String nombre) {
	    this.nombre = nombre;
	    this.dni = "";
	    this.email = "";
	    this.tlf = "";
	}

	
}
