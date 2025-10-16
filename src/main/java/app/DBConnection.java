package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Update credentials as needed
    private static final String URL = "jdbc:mysql://localhost:3306/supermarket?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Joffy123456789@0";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}