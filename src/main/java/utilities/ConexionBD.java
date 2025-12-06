package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionBD {
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(Paths.UrlBaseDatos, Paths.USER, Paths.PASSWORD);
    }
}
