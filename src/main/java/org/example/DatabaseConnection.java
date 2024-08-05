package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "August_04");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection!= null) {
            connection.close();
        }
    }
}