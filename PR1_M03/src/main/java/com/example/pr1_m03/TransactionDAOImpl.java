package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtener la conexión
            connection = ConnectDB.getInstance();

            // Preparar la consulta para obtener todas las transacciones
            String query = "SELECT * FROM transactions";
            statement = connection.prepareStatement(query);

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Procesar el resultado
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                // Si la categoría está vacía, establecerla en un espacio en blanco
                if (category == null || category.isEmpty()) {
                    category = " ";
                }
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                LocalDate date = resultSet.getDate("date").toLocalDate();

                // Crear una nueva transacción y agregarla a la lista
                Transaction transaction = new Transaction(category, amount, description, date);
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Cerrar el conjunto de resultados
                if (resultSet != null) {
                    resultSet.close();
                }

                // Cerrar la declaración
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
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
