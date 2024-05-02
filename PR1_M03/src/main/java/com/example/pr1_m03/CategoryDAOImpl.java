package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public void insertCategory(String categoryName) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectDB.getInstance();

            // Crear la tabla si no existe
            createTableIfNotExists(connection);

            statement = connection.prepareStatement(sql);
            statement.setString(1, categoryName);
            statement.executeUpdate();
            System.out.println("Category inserted successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> allCategories = new ArrayList<>();
        String sql = "SELECT name FROM category";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectDB.getInstance();
            // Comprobar si la tabla existe
            if (!tableExists(connection, "category")) {
                createTableIfNotExists(connection);
                insertDefaultCategories(connection);
            }

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Category category = new Category(name);
                allCategories.add(category);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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
        return allCategories;
    }

    // Método para comprobar si la tabla existe
    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        }
    }

    // Método para insertar categorías predeterminadas
    private void insertDefaultCategories(Connection connection) throws SQLException {
        String[] defaultCategories = {"bizum", "nomina", "ingreso", "gasto"};
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String category : defaultCategories) {
                statement.setString(1, category);
                statement.executeUpdate();
            }
            System.out.println("Default categories inserted successfully.");
        }
    }


    @Override
    public void updateCategory(Category category) {
        // Implementar actualización de categoría según sea necesario
    }

    @Override
    public void deleteCategory(Category category) {
        // Implementar eliminación de categoría según sea necesario
    }

    // Método para crear la tabla si no existe
    private void createTableIfNotExists(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS category (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255))";
            statement.executeUpdate(sql);
            System.out.println("Table 'category' created if not exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
