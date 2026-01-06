package persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GestorBD {
	
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	private final String CSV_CALIFICACIONES_CONCURSO = "resources/data/calificacionesConcurso.csv";
	private final String CSV_CLIENTES = "resources/data/clientes.csv";
	private final String CSV_CONTRA_CLIENTES = "resources/data/contraClientes.csv";
	private final String CSV_MENU = "resources/data/menu.csv";
	private final String CSV_RESERVAS = "resources/data/reservas.csv";
	
    private Properties properties;
    private String driverName;
    private String databaseFile;
    private String connectionString;

    private static Logger logger = Logger.getLogger(GestorBD.class.getName());

    public GestorBD() {
        try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
            // Inicialización del Logger
            LogManager.getLogManager().readConfiguration(fis);

            // Lectura del fichero properties
            properties = new Properties();
            properties.load(new FileReader(PROPERTIES_FILE));

            driverName = properties.getProperty("driver");
            databaseFile = properties.getProperty("file");
            connectionString = properties.getProperty("connection");

            // Cargar el driver SQLite
            Class.forName(driverName);
        } catch (Exception e) {
            logger.warning(String.format("Error al cargar el driver de BBDD: %s", e.getMessage()));
        }
    }

    // Borrar la base de datos
    private void borrarBaseDeDatos() {
        if (Boolean.parseBoolean(properties.getProperty("deleteBBDD", "false"))) {
            try {
                Files.deleteIfExists(Paths.get(databaseFile));
                logger.info("Base de datos eliminada exitosamente.");
            } catch (Exception e) {
                logger.warning("Error al borrar la base de datos: " + e.getMessage());
            }
        } else {
            logger.info("La eliminación de la base de datos está desactivada.");
        }
    }

    // Conectar a la base de datos
    private Connection connect() throws Exception {
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(connectionString, properties.getProperty("db.username"), properties.getProperty("db.password"));
        } catch (Exception e) {
            logger.severe("Error al conectar a la base de datos: " + e.getMessage());
            throw new Exception("Error al conectar a la base de datos", e);
        }
    }

    // Crear la base de datos si no existe
    private void crearBaseDeDatos() {
        String url = properties.getProperty("connection");

        try (Connection con = DriverManager.getConnection(url)) {
            if (con != null) {
                logger.info("Conexión a la base de datos establecida.");
            }
        } catch (Exception e) {
            logger.severe("Error al crear o conectar a la base de datos: " + e.getMessage());
        }
    }

    // Crear las tablas de la base de datos
    public void crearTablas() {
        // Eliminar la base de datos si existe cafeteria.db
        borrarBaseDeDatos();
        
        // Volver a crear la base de datos
        crearBaseDeDatos();
    	
        // Tabla
        // -- Clientes
        String createClientesTable = "CREATE TABLE IF NOT EXISTS clientes ("
                + "numero INT PRIMARY KEY,"
                + "nombre VARCHAR(100),"
                + "dni VARCHAR(10) UNIQUE,"
                + "gmail VARCHAR(100),"
                + "contrasena VARCHAR(100)"
                + ");";
        // -- Calificaciones
        String createCalificacionesConcursoTable = "CREATE TABLE IF NOT EXISTS calificacionesConcurso ("
                + "nombre VARCHAR(100),"
                + "apellido VARCHAR(100),"
                + "telefono VARCHAR(15),"
                + "creatividad FLOAT,"
                + "material FLOAT,"
                + "tecnica FLOAT,"
                + "promedioGeneral FLOAT,"
                + "PRIMARY KEY (telefono)"
                + ");";
        // -- Menú
        String createMenuTable = "CREATE TABLE IF NOT EXISTS menu ("
                + "tipo VARCHAR(50),"
                + "nombre VARCHAR(100) PRIMARY KEY,"
                + "personaje VARCHAR(100),"
                + "precio DECIMAL(10, 2),"
                + "descripcion TEXT,"
                + "alergeno VARCHAR(200),"
                + "alcohol VARCHAR(3)"
                + ");";
        // -- Reservas
        String createReservasTable = "CREATE TABLE IF NOT EXISTS reservas ("
                + "fecha DATE PRIMARY KEY,"
                + "nombre_cliente VARCHAR(100),"
                + "tipo_evento VARCHAR(100),"
                + "FOREIGN KEY (nombre_cliente) REFERENCES clientes(nombre)"
                + ");";

        try (Connection con = connect();
        		Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createClientesTable);
            stmt.executeUpdate(createCalificacionesConcursoTable);
            stmt.executeUpdate(createMenuTable);
            stmt.executeUpdate(createReservasTable);

            // Crear índices
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_nombre_cliente ON clientes(nombre);");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_telefono ON calificacionesConcurso(telefono);");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_nombre ON menu(nombre);");
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_fecha ON reservas(fecha);");
        } catch (Exception e) {
            logger.severe("Error al crear las tablas: " + e.getMessage());
        }
    }

    // Carga los datos desde los archivos CSV
    public void cargarDatos() {
        cargarDatosDesdeCSV(CSV_CLIENTES, "clientes", true);
        cargarContrasenasClientes();
        cargarDatosDesdeCSV(CSV_CALIFICACIONES_CONCURSO, "calificacionesConcurso", true);
        cargarDatosDesdeCSV(CSV_MENU, "menu", true);
        cargarDatosDesdeCSV(CSV_RESERVAS, "reservas", false);
    }

    // Cargar las contraseñas de los clientes
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

                    try (Connection con = connect();
                    		PreparedStatement pstmt = con.prepareStatement(sql)) {
                        pstmt.setString(1, contrasena);
                        pstmt.setString(2, nombreCliente);
                        pstmt.executeUpdate();
                    } catch (Exception e) {
                        logger.severe("Error al actualizar la contraseña para el cliente con nombre " + nombreCliente + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Error al cargar las contraseñas desde el archivo: " + e.getMessage());
        }
    }

    // Cargar datos de un archivo CSV
    private void cargarDatosDesdeCSV(String archivoCSV, String tabla, boolean saltarseCabecera) {
        String sql = "";
        switch (tabla) {
            case "clientes":
                sql = "INSERT INTO clientes (numero, nombre, dni, gmail, contrasena) VALUES (?, ?, ?, ?, ?)";
                break;
            case "calificaciones_concurso":
                sql = "INSERT INTO calificacionesConcurso (nombre, apellido, telefono, creatividad, material, tecnica, promedioGeneral) VALUES (?, ?, ?, ?, ?, ?, ?)";
                break;
            case "menu":
                sql = "INSERT INTO menu (tipo, nombre, personaje, precio, descripcion, alergeno, alcohol) VALUES (?, ?, ?, ?, ?, ?, ?)";
                break;
            case "reservas":
                sql = "INSERT INTO reservas (fecha, nombre_cliente, tipo_evento) VALUES (?, ?, ?)";
                break;
            default:
                logger.severe("Tabla no reconocida: " + tabla);
                return;
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(archivoCSV))) {
            String line;

            if (saltarseCabecera) {
                br.readLine();
            }

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");

                if (data.length < 2) {
                    logger.warning("Línea con formato incorrecto: " + line);
                    continue;
                }

                try (Connection con = connect();
                		PreparedStatement pstmt = con.prepareStatement(sql)) {
                    for (int i = 0; i < data.length; i++) {
                        pstmt.setString(i + 1, data[i]);
                    }
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    logger.severe("Error al insertar los datos en la tabla " + tabla + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.severe("Error al cargar datos desde el archivo " + archivoCSV + ": " + e.getMessage());
        }
    }

    // Actualizar promedio general de calificaciones
    public void actualizarPromedios() {
        String updatePromedio = "UPDATE calificacionesConcurso " + "SET promedioGeneral = (creatividad + material + tecnica) / 3;";

        try (Connection con = connect();
        		Statement stmt = con.createStatement()) {
            stmt.executeUpdate(updatePromedio);
        } catch (Exception e) {
            logger.severe("Error al actualizar los promedios: " + e.getMessage());
        }
    }

    // Realizar consultas de prueba
    public void realizarConsultas() {
        String consultaClientes = "SELECT * FROM clientes LIMIT 10;";
        String consultaCalificaciones = "SELECT nombre, apellido, promedioGeneral FROM calificacionesConcurso ORDER BY promedioGeneral DESC LIMIT 10;";

        try (Connection con = connect();
        		Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(consultaClientes);
            while (rs.next()) {
                System.out.println("Cliente: " + rs.getString("nombre"));
            }

            rs = stmt.executeQuery(consultaCalificaciones);
            while (rs.next()) {
                System.out.println("Participante: " + rs.getString("nombre") + " " + rs.getString("apellido") + " - Promedio: " + rs.getFloat("promedioGeneral"));
            }
        } catch (Exception e) {
            logger.severe("Error al realizar consultas: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        GestorBD gestorBD = new GestorBD();
        gestorBD.crearTablas();
        gestorBD.cargarDatos();
        gestorBD.actualizarPromedios();
        gestorBD.realizarConsultas();
    }

}
