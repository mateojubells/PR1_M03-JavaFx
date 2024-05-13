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
            connection = ConnectDB.getInstance();

            String query = "INSERT INTO transactions (category, amount, description, date) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, transaction.getCategory());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(transaction.getDate()));

            // Ejecutar la inserción
            statement.executeUpdate();

            // Obtener el ID generado
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                transaction.setId(generatedId);  // Asignar el ID a la transacción
            }

            generatedKeys.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateTransaction(Transaction transaction) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectDB.getInstance();

            String query = "UPDATE transactions SET category = ?, amount = ?, description = ?, date = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, transaction.getCategory());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            statement.setInt(5, transaction.getId()); // Suponiendo que hay un campo `id` en Transaction

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteTransaction(int transactionId){
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectDB.getInstance();

            // Preparar la consulta para eliminar la transacción por su ID
            String query = "DELETE FROM transactions WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);

            // Ejecutar la consulta
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance();

            // Preparar la consulta para obtener todas las transacciones
            String query = "SELECT id, category, amount, description, date FROM transactions";  // Incluye el campo 'id'
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            // Procesar el resultado
            while (resultSet.next()) {
                int id = resultSet.getInt("id");  // Obtener el ID
                String category = resultSet.getString("category");
                if (category == null || category.isEmpty()) {
                    category = " ";
                }
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                LocalDate date = resultSet.getDate("date").toLocalDate();

                Transaction transaction = new Transaction(id, category, amount, description, date);  // Agregar ID
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
