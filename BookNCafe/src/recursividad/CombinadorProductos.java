package recursividad;

import domain.Producto;
import io.GestionProductos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CombinadorProductos {

    public static List<List<Producto>> calcularCombinaciones(List<Producto> productos, double presupuesto) {
        List<List<Producto>> resultado = new ArrayList<>();
        generarCombinaciones(productos, 0, new ArrayList<>(), presupuesto, resultado);
        return resultado;
    }

    private static void generarCombinaciones(List<Producto> productos, int index, List<Producto> combinacionActual,
                                             double presupuestoRestante, List<List<Producto>> resultado) {
        // Caso base: si no hay presupuesto o hemos recorrido todos los productos
        if (presupuestoRestante < 0) {
            return;
        }
        if (index == productos.size()) {
            if (!combinacionActual.isEmpty()) {
                resultado.add(new ArrayList<>(combinacionActual));
            }
            return;
        }

        // No incluir el producto actual
        generarCombinaciones(productos, index + 1, combinacionActual, presupuestoRestante, resultado);

        // Incluir el producto actual
        Producto producto = productos.get(index);
        combinacionActual.add(producto);
        generarCombinaciones(productos, index + 1, combinacionActual, presupuestoRestante - producto.getPrecio(), resultado);

        // Retroceder
        combinacionActual.remove(combinacionActual.size() - 1);
    }

    public static void main(String[] args) {
        // Cargar productos desde el archivo CSV
        Set<Producto> productosCargados = GestionProductos.cargarProductosCSV();

        if (productosCargados.isEmpty()) {
            System.err.println("No se cargaron productos desde el archivo CSV.");
            return;
        }

        // Convertir el Set en una List para trabajar con los índices
        List<Producto> productos = new ArrayList<>(productosCargados);

        // Calcular combinaciones con un presupuesto de ejemplo (por ejemplo, 25€)
        double presupuesto = 25.0;
        List<List<Producto>> combinaciones = calcularCombinaciones(productos, presupuesto);

        // Mostrar las combinaciones posibles
        System.out.println("Combinaciones posibles dentro del presupuesto de " + presupuesto + "€:");
        for (List<Producto> combinacion : combinaciones) {
            StringBuilder salida = new StringBuilder();
            double sumaPrecios = 0;

            for (Producto producto : combinacion) {
                salida.append(producto.getNombre())
                      .append(" (")
                      .append(producto.getPrecio())
                      .append("€), ");
                sumaPrecios += producto.getPrecio();
            }

            // Eliminar la última coma y espacio, e incluir la suma total del precio
            if (salida.length() > 2) {
                salida.setLength(salida.length() - 2);
            }
            salida.append(" - Total: ").append(sumaPrecios).append("€");

            System.out.println(salida);
        }
    }
}
