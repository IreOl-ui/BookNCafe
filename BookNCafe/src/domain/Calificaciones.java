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
    public static double sumaPromedios(Calificaciones[] alumnos, int index) {
        if (index < 0) {
            return 0; 
        }
        
        return alumnos[index].getPromedioGeneral() + sumaPromedios(alumnos, index - 1);
    }

    public static void main(String[] args) {
        
        Calificaciones[] alumnos = new Calificaciones[3];
        alumnos[0] = new Calificaciones("Ana", "Gómez", "555-1234", 8.5, 9.0, 7.5);
        alumnos[1] = new Calificaciones("Luis", "Pérez", "555-5678", 7.0, 6.5, 8.0);
        alumnos[2] = new Calificaciones("Marta", "López", "555-9012", 9.0, 8.5, 9.5);

        double suma = sumaPromedios(alumnos, alumnos.length - 1);
        double promedioGeneral = suma / alumnos.length;

        System.out.println("Suma de promedios: " + suma);
        System.out.println("Promedio general de todos los alumnos: " + promedioGeneral);
    }
}

