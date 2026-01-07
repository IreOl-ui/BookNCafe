package domain;

import java.util.ArrayList;
import java.util.List;

public class Cafeteria {
    private List<Producto> inventario;
    private List<Cliente> listaClientes;
    private Cliente usuarioIdentificado;

    public Cafeteria() {
        this.inventario = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.usuarioIdentificado = null;
    }

    public void añadirProducto(Producto p) {
        if (p != null) {
            this.inventario.add(p);
        }
    }

    public List<Producto> getInventario() {
        return inventario;
    }
    public Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : inventario) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> buscarPorPersonaje(String personaje) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : inventario) {
            if (p.getPersonaje().toLowerCase().contains(personaje.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
}
