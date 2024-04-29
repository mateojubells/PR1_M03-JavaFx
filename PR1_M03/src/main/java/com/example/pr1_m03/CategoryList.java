package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryList {
    private List<Category> allCategories = new ArrayList<>();

    public CategoryList() {
        loadCategoriesFromDatabase();
    }

    private void loadCategoriesFromDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecer conexión con la base de datos
            connection = ConnectDB.getInstance();

            // Consulta SQL para obtener las categorías
            String query = "SELECT name FROM category";
            statement = connection.prepareStatement(query);

            // Ejecutar la consulta
            resultSet = statement.executeQuery();

            // Iterar sobre los resultados y crear objetos Category
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Category category = new Category(name);
                allCategories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Cerrar la conexión y liberar recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Resto del código...
}
