package domain;

import java.util.ArrayList;
import java.util.List;

public class Participante {
    private String nombre;
    private String apellido;
    private String telefono;
    private List<Double> calificacionesCreatividad;
    private List<Double> calificacionesMaterial;
    private List<Double> calificacionesTecnica;

    public Participante(String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.calificacionesCreatividad = new ArrayList<>();
        this.calificacionesMaterial = new ArrayList<>();
        this.calificacionesTecnica = new ArrayList<>();
    }

    public void agregarCalificacion(String categoria, double creatividad) {
        if (creatividad >= 0 && creatividad <= 10) {
            switch (categoria) {
                case "creatividad":
                    calificacionesCreatividad.add((double) creatividad);
                    break;
                case "material":
                    calificacionesMaterial.add((double) creatividad);
                    break;
                case "tecnica":
                    calificacionesTecnica.add((double) creatividad);
                    break;
            }
        }
    }

    public double obtenerPromedioGeneral() {
        double promedioCreatividad = obtenerPromedio("creatividad");
        double promedioMaterial = obtenerPromedio("material");
        double promedioTecnica = obtenerPromedio("tecnica");
        double promedioGeneral = (promedioCreatividad + promedioMaterial + promedioTecnica) / 3;
        return Math.round(promedioGeneral * 100.0) / 100.0;
    }

    public double obtenerPromedio(String categoria) {
        List<Double> calificaciones;
        switch (categoria) {
            case "creatividad":
                calificaciones = calificacionesCreatividad;
                break;
            case "material":
                calificaciones = calificacionesMaterial;
                break;
            case "tecnica":
                calificaciones = calificacionesTecnica;
                break;
            default:
                return 0;
        }
        if (calificaciones.isEmpty()) {
            return 0;
        }

        double suma = 0.0;
        for (Double calificacion : calificaciones) {
            suma += calificacion;
        }
        return suma / calificaciones.size();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    // Método que convierte el participante y sus calificaciones en formato CSV
    public String toCSVConCalificaciones() {
        return nombre + ";" + apellido + ";" + telefono + ";" 
            + obtenerPromedio("creatividad") + ";" 
            + obtenerPromedio("material") + ";" 
            + obtenerPromedio("tecnica") + ";" 
            + obtenerPromedioGeneral();
    }
}