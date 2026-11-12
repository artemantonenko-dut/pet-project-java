package ua.edu.planday;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/plan_day";
    private static final String DEFAULT_USER = "postgres";

    public static Connection getConnection() throws SQLException {
        String url = getValue("PLANDAY_DB_URL", DEFAULT_URL);
        String user = getValue("PLANDAY_DB_USER", DEFAULT_USER);
        String password = getValue("PLANDAY_DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }

    private static String getValue(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
