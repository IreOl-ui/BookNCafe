package domain;

public class Calificaciones {
    
    private String nombre;
    private String apellido;
    private String tlf;
    private double creatividad;
    private double material;
    private double tecnica;
    private double promedioGeneral;

    
    public Calificaciones(String nombre, String apellido, String tlf, double creatividad, double material, double tecnica) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tlf = tlf;
        this.creatividad = creatividad;
        this.material = material;
        this.tecnica = tecnica;
        calcularPromedio(); 
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

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public double getCreatividad() {
        return creatividad;
    }

    public void setCreatividad(double creatividad) {
        this.creatividad = creatividad;
        calcularPromedio();
    }

    public double getMaterial() {
        return material;
    }

    public void setMaterial(double material) {
        this.material = material;
        calcularPromedio();
    }

    public double getTecnica() {
        return tecnica;
    }

    public void setTecnica(double tecnica) {
        this.tecnica = tecnica;
        calcularPromedio();
    }

    public double getPromedioGeneral() {
        return promedioGeneral;
    }

    
    private void calcularPromedio() {
        this.promedioGeneral = (creatividad + material + tecnica) / 3;
    }

}

