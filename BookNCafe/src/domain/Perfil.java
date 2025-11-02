package domain;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Perfil {
    private String nombre;
    private String fotoPerfil;
    private double dineroGastado;
    private Color colorBorde;
    public Perfil(String nombre, String fotoPerfil, double dineroGastado) {
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.dineroGastado = dineroGastado;
        this.colorBorde = Color.WHITE; // Color predeterminado
        // E intentemos cargar el color de borde desde un archivo
        cargarColorBorde();
    }
    public String getNombre() {
        return nombre;
    }
    public Color getColorBorde() {
        return colorBorde;
    }
    public String getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
        // Guardamos el color de borde cuando se cambia
        guardarColorBorde();
    }
    public int calcularPuntos() {
        return (int) (dineroGastado * 5);
    }

    // Métodos:
    // Guardar el color del borde en un archivo
    private void guardarColorBorde() {
        Properties propiedades = new Properties();
        propiedades.setProperty(nombre + "_colorBorde", String.valueOf(colorBorde.getRGB()));

        try (FileOutputStream outputStream = new FileOutputStream("resources/config/configUsuarios.properties", true)) {
            propiedades.store(outputStream, "Configuración del perfil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Cargar el color de borde desde el archivo
    private void cargarColorBorde() {
        Properties propiedades = new Properties();

        try (FileInputStream inputStream = new FileInputStream("resources/config/configUsuarios.properties")) {
            propiedades.load(inputStream);

            String colorRgbStr = propiedades.getProperty(nombre + "_colorBorde");
            if (colorRgbStr != null) {
                int rgb = Integer.parseInt(colorRgbStr);
                this.colorBorde = new Color(rgb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
