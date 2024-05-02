package com.example.pr1_m03;

import com.example.pr1_m03.Transaction;
import com.jdbc.utilities.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public void saveTransaction(Transaction transaction) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Obtener la conexión
            connection = ConnectDB.getInstance();

            // Crear la tabla si no existe
            createTableIfNotExists(connection);

            // Preparar la consulta para insertar la transacción
            String query = "INSERT INTO transactions (category, amount, description, date) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, transaction.getCategory());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(transaction.getDate()));

            // Ejecutar la consulta
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Cerrar la declaración
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para crear la tabla si no existe
    private void createTableIfNotExists(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "category VARCHAR(255)," +
                    "amount DECIMAL(10, 2)," +
                    "description VARCHAR(255)," +
                    "date DATE)";
            statement.executeUpdate(sql);
            System.out.println("Table 'transactions' created if not exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
