package domain;

public class Cliente {

	private String DNI;
	private String nombre;
	private String telefono;
	private String email;

	// constructor -> recibe datos del cliente
	public Cliente(String telefono, String nombre, String DNI, String email) {
		// asigna valor de DNI (parametro) al atributo de la clase
		this.DNI = DNI;
		// asigna valor de nombre (parametro) al atributo de la clase
		this.nombre = nombre;
		// asigna valor de telefono (parametro) al atributo de la clase
		this.telefono = telefono;
		// asigna valor de email (parametro) al atributo de la clase
		this.email = email;
	}

	// getters
	// getter para obteber DNI
	public String getDNI() {
		return DNI;
	}

	// getter para obtener nombre
	public String getNombre() {
		return nombre;
	}

	// getter para obtener telefono
	public String getTelefono() {
		return telefono;
	}

	// getter para obteber email
	public String getEmail() { // getter para obteber email
		return email;
	}

	@Override
	// transforma los datos del cliente en String
	public String toString() {
		// devuelve cadena de texto con los datos del cliente separados por ";" (en
		// formato .csv)
		return telefono + ";" + nombre + ";" + DNI + ";" + email;
	}

}
