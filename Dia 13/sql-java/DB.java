import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection getConnection() throws SQLException {
        String url  = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASS");

        if (url == null || user == null || pass == null) {
            throw new IllegalStateException("⚠️ Variables de entorno no configuradas correctamente");
        }

        return DriverManager.getConnection(url, user, pass);
    }
}
