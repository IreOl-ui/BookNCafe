package domain;

public class Menu {
    
    private String tipo;
    private String nombre;
    private double precio;
    private String descripcion;
    private Alergeno alergeno; 
    private boolean alcohol;

    
    public Menu(String tipo, String nombre, double precio, String descripcion, Alergeno alergeno, boolean alcohol) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.alergeno = alergeno;
        this.alcohol = alcohol;
    }

   
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Alergeno getAlergeno() {
        return alergeno;
    }

    public void setAlergeno(Alergeno alergeno) {
        this.alergeno = alergeno;
    }

    public boolean isAlcohol() {
        return alcohol;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }
    public void aplicarDescuento(double porcentaje) {
        this.precio = this.precio * (1 - porcentaje / 100);
    }
    public boolean esAptoMenores() {
        return !alcohol;
    }

}

