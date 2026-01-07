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
}
