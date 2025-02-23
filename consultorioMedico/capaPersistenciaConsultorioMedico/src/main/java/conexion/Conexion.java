package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexión con la base de datos MySQL para la aplicación de
 * consultas médicas. Utiliza el controlador JDBC para establecer una conexión
 * con la base de datos mediante las credenciales configuradas.
 *
 * @author Jp
 */
public class Conexion {

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña del usuario de la base de datos
    private static final String PASS = "12345678";

    // URL de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/consultas_medicas";

    /**
     * Establece una conexión con la base de datos MySQL utilizando las
     * credenciales y la URL configuradas.
     *
     * @return Un objeto {@link Connection} que representa la conexión con la
     * base de datos.
     * @throws SQLException Si ocurre un error durante el intento de conexión
     * con la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
