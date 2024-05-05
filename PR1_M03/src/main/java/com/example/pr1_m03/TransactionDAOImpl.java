package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;

import java.sql.*;

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
    public void getAllTransactions() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtener la conexión
            connection = ConnectDB.getInstance();

            // Preparar la consulta para obtener todas las transacciones
            String query = "SELECT * FROM transactions";
            statement = connection.createStatement();

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery(query);

            // Procesar los resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");

                // Aquí puedes hacer lo que quieras con los datos de la transacción, como imprimirlos por ejemplo
                System.out.println("ID: " + id + ", Category: " + category + ", Amount: " + amount + ", Description: " + description + ", Date: " + date);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
