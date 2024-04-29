package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryController {

    @FXML
    private TextField categoryField;

    @FXML
    private void newCategory(ActionEvent actionEvent) {
        String categoryName = categoryField.getText();

        if (!categoryName.isEmpty()) {
            insertCategory(categoryName);
        }
        closeView();
    }

    private void insertCategory(String categoryName) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectDB.getInstance();
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

    @FXML
    private void closeView(){
        Stage categoryStage = (Stage) categoryField.getScene().getWindow();
        categoryStage.close();
    }
}
