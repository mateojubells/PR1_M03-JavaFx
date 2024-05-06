package com.jdbc.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que implementa el patrón Singleton para establecer la conexión con la BBDD
 */
public class ConnectDB {
    private static Connection instance;

    private ConnectDB() {}

    public static Connection getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            instance = DriverManager.getConnection(MYSQLDEMOConnection.url,
                    MYSQLDEMOConnection.username,
                    MYSQLDEMOConnection.password);
            System.out.println("Open Database");

            // Crear la base de datos si no existe
            createDatabaseIfNotExists(instance);
        }
        return instance;
    }

    private static void createDatabaseIfNotExists(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String databaseName = MYSQLDEMOConnection.databaseName;
            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(sql);
            System.out.println("Database created if not exists: " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() throws SQLException {
        if (instance != null) {
            instance.close();
            System.out.println("Database Closed");
        }
    }
}
