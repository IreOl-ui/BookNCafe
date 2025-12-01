package persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GestorBD {
	
	private final String PROPERTIES_FILE = "resources/data/clientes.csv";
	private final String CSV_CALIFICACIONES_CONCURSO = "resources/data/calificacionesConcurso.csv";
	private final String CSV_CLIENTES = "resources/data/clientes.csv";
	private final String CSV_CONTRA_CLIENTES = "resources/data/contraClientes.csv";
	private final String CSV_MENU = "resources/data/menu.csv";
	private final String CSV_RESERVAS = "resources/data/reservas.csv";
	
	private Properties properties;
    private String driverName;
    private String databaseFile;
    private String connectionString;
	
	private static Logger log = Logger.getLogger(GestorBD.class.getName());
	
	// Gestionar BD
	public void gestorBD() {
		try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
            // Inicialización del Logger
            LogManager.getLogManager().readConfiguration(fis);

            // Lectura del fichero properties
            properties = new Properties();
            properties.load(new FileReader(PROPERTIES_FILE));

            driverName = properties.getProperty("driver");
            databaseFile = properties.getProperty("file");

            // Cargar el driver SQLite
            Class.forName(driverName);
		} catch (Exception e) {
			log.warning(String.format("Error al cargar el driver de BBDD: %s", e.getMessage()));
		}
	}
	
	// Borrar BD
	private void borrarBD() {
		if (Boolean.parseBoolean(properties.getProperty("deleteBBDD", "false"))) {
			try {
				Files.deleteIfExists(Paths.get(databaseFile));
				log.info("Base de datos eliminada exitosamente.");
			} catch (IOException e) {
				log.warning("Error al borrar la base de datos: " + e.getMessage());
				log.log(Level.WARNING, "Error al borrar la base de datos: ", e);
			}
		} else {
			log.info("La eliminación de la base de datos está desactivada.");
		}
	}
	
	// Crear BD si no existe
	private void crearBD() {
		String url = properties.getProperty("connection");
		
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				log.info("Conexión a la base de datos establecida.");
			}
		} catch (Exception e) {
			log.severe("Error al crear o conectar a la base de datos: " + e.getMessage());
		}
	}
	
    // Conectar a la BD
    private Connection connect() throws Exception {
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(connectionString, properties.getProperty("db.username"), properties.getProperty("db.password"));
        } catch (Exception e) {
            log.severe("Error al conectar a la base de datos: " + e.getMessage());
            throw new Exception("Error al conectar a la base de datos", e);
        }
    }
	
	// Crear tablas de BD
	public void crearTablasBD() {
		// Primero borrar datos de bbdd
		borrarBD();
		
		// Luego crear datos de bbdd
		crearBD();
		
		// Y finalmente crear nuevas tablas:
		// -- Cliente
		String createClientesTable = "CREATE TABLE IF NOT EXISTS clientes ("
                + "numero INT PRIMARY KEY, "
                + "nombre VARCHAR(100), "
                + "dni VARCHAR(10) UNIQUE, "
                + "gmail VARCHAR(100), "
                + "contrasena VARCHAR(100));";
		// -- Menú
		String createMenuTable = "CREATE TABLE IF NOT EXISTS menu ("
                + "tipo VARCHAR(50), "
                + "nombre VARCHAR(100) PRIMARY KEY, "
                + "personaje VARCHAR(100), "
                + "precio DECIMAL(10, 2), "
                + "descripcion TEXT, "
                + "alergeno VARCHAR(200), "
                + "alcohol VARCHAR(3));";
		// -- Calificaciones
		String createCalificacionesConcursoTable = "CREATE TABLE IF NOT EXISTS calificaciones_concurso ("
                + "nombre VARCHAR(100), "
                + "apellido VARCHAR(100), "
                + "telefono VARCHAR(15), "
                + "creatividad FLOAT, "
                + "material FLOAT, "
                + "tecnica FLOAT, "
                + "promedio_general FLOAT, "
                + "PRIMARY KEY (telefono));";
		// -- Reservas
		String createReservasTable = "CREATE TABLE IF NOT EXISTS reservas ("
                + "fecha DATE PRIMARY KEY, "
                + "nombre_cliente VARCHAR(100), "
                + "tipo_evento VARCHAR(100), "
                + "FOREIGN KEY (nombre_cliente) REFERENCES clientes(nombre));";
		
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
        	stmt.executeUpdate(createClientesTable);
            stmt.executeUpdate(createMenuTable);
            stmt.executeUpdate(createCalificacionesConcursoTable);
            stmt.executeUpdate(createReservasTable);
            
            // Crear index (índices)
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_nombre_cliente ON clientes(nombre);");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_nombre ON menu(nombre);");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_telefono ON calificaciones_concurso(telefono);");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_fecha ON reservas(fecha);");
            
        } catch (Exception e) {
            log.severe("Error al crear las tablas: " + e.getMessage());
        }
	}
	
	// Cargar BD
	public void cargarBD() {
        cargarDatosDesdeCSV(CSV_CLIENTES, "clientes", true);
        cargarContrasenasClientes(); // Cargar las contraseñas después de los clientes
        cargarDatosDesdeCSV(CSV_MENU, "menu", true);
        cargarDatosDesdeCSV(CSV_CALIFICACIONES_CONCURSO, "calificaciones_concurso", true);
        cargarDatosDesdeCSV(CSV_RESERVAS, "reservas", false);
	}

	private void cargarContrasenasClientes() {
        String sql = "UPDATE clientes SET contrasena = ? WHERE nombre = ?";

        try (BufferedReader br = Files.newBufferedReader(Paths.get(CSV_CONTRA_CLIENTES))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                if (data.length == 2) {
                    String nombreCliente = data[0];
                    String contrasena = data[1];

                    try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, contrasena);
                        ps.setString(2, nombreCliente);
                        ps.executeUpdate();
                    } catch (Exception e) {
                        log.severe("Error al actualizar la contraseña para el cliente con nombre " + nombreCliente + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            log.severe("Error al cargar las contraseñas desde el archivo: " + e.getMessage());
        }
	}

	private void cargarDatosDesdeCSV(String archivoCSV, String tabla, boolean saltarseCabecera) {
		String sql = "";
        switch (tabla) {
            case "clientes":
                sql = "INSERT INTO clientes (numero, nombre, dni, gmail, contrasena) VALUES (?, ?, ?, ?, ?)";
                break;
            case "calificaciones_concurso":
                sql = "INSERT INTO calificaciones_concurso (nombre, apellido, telefono, creatividad, material, tecnica, promedio_general) VALUES (?, ?, ?, ?, ?, ?, ?)";
                break;
            case "menu":
                sql = "INSERT INTO menu (tipo, nombre, personaje, precio, descripcion, alergeno, alcohol) VALUES (?, ?, ?, ?, ?, ?, ?)";
                break;
            case "reservas":
                sql = "INSERT INTO reservas (fecha, nombre_cliente, tipo_evento) VALUES (?, ?, ?)";
                break;
            default:
                log.severe("Tabla no reconocida: " + tabla);
                return;
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(archivoCSV))) {
            String line;

            if (saltarseCabecera) {
                br.readLine(); // Descartar la primera línea (cabecera)
            }

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");

                if (data.length < 2) {
                    log.warning("Línea con formato incorrecto: " + line);
                    continue;
                }

                try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
                    for (int i = 0; i < data.length; i++) {
                        ps.setString(i + 1, data[i]);
                    }
                    ps.executeUpdate();
                } catch (Exception e) {
                    log.severe("Error al insertar los datos en la tabla " + tabla + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.severe("Error al cargar datos desde el archivo " + archivoCSV + ": " + e.getMessage());
        }
	}
	
	// Realizar consultas de BD clientes y calificaciones
	public void realizarConsultas() {
        String consultaClientes = "SELECT * FROM clientes;";
        String consultaCalificaciones = "SELECT nombre, apellido, promedio_general FROM calificaciones_concurso ORDER BY promedio_general DESC LIMIT 10;";

        try (Connection conn = connect();
        	Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(consultaClientes);
            while (rs.next()) {
                System.out.println("Cliente: " + rs.getString("nombre"));
            }
            rs = stmt.executeQuery(consultaCalificaciones);
            while (rs.next()) {
                System.out.println("Participante: " + rs.getString("nombre") + " " + rs.getString("apellidos") + " - Promedio: " + rs.getFloat("promedio_general"));
            }
        } catch (Exception e) {
            log.severe("Error al realizar consultas: " + e.getMessage());
        }
	}
	
    // Actualizar promedio general de calificaciones.csv
    public void actualizarPromedios() {
        String updatePromedio = "UPDATE calificaciones_concurso "
                + "SET promedio_general = (creatividad + material + tecnica) / 3;";

        try (Connection conn = connect();
        	Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updatePromedio);
        } catch (Exception e) {
            log.severe("Error al actualizar los promedios: " + e.getMessage());
        }
    }

	public static void main(String[] args) {
		GestorBD gestor = new GestorBD();
		gestor.crearTablasBD();
		gestor.cargarBD();
        gestor.realizarConsultas();
        gestor.actualizarPromedios();
	}

}
