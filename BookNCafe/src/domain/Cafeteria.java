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
    public List<Producto> filtrarSinAlergeno(Alergeno a) {
        List<Producto> aptos = new ArrayList<>();
        for (Producto p : inventario) {
            if (!p.getAlergeno().contains(a)) {
                aptos.add(p);
            }
        }
        return aptos;
    }

    public List<Producto> filtrarPorPrecioMaximo(double precioMax) {
        List<Producto> baratos = new ArrayList<>();
        for (Producto p : inventario) {
            if (p.getPrecio() <= precioMax) {
                baratos.add(p);
            }
        }
        return baratos;
    }


    public double calcularPrecioMedio() {
        if (inventario.isEmpty()) return 0.0;
        double suma = 0;
        for (Producto p : inventario) {
            suma += p.getPrecio();
        }
        return suma / inventario.size();
    }

    public Producto obtenerProductoMasCaro() {
        if (inventario.isEmpty()) return null;
        Producto masCaro = inventario.get(0);
        for (Producto p : inventario) {
            if (p.getPrecio() > masCaro.getPrecio()) {
                masCaro = p;
            }
        }
        return masCaro;
    }


    public void setUsuarioIdentificado(Cliente c) {
        this.usuarioIdentificado = c;
    }

    public Cliente getUsuarioIdentificado() {
        return usuarioIdentificado;
    }
}
