package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jp
 */
public class Conexion {
    private static final String USER = "root";
    private static final String PASS = "12345678";
    private static final String URL = "jdbc:mysql://localhost:3306/consultas_medicas";

    
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASS);  
    }
}
