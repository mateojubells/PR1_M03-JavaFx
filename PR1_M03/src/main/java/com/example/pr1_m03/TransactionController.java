package com.example.pr1_m03;
import com.jdbc.utilities.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class TransactionController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<String> categoryComboBox;

    private ObservableList<String> categoryNames = FXCollections.observableArrayList();

    public TransactionController() {
    }

    @FXML
    private void addTransaction(ActionEvent actionEvent) {
        Alert alert = null;
        try {
            LocalDate date = datePicker.getValue();
            if (date == null) {
                date = LocalDate.now();
            }

            String category = categoryComboBox.getValue();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            // Verificar si la categoría es nula y asignar una cadena vacía en su lugar
            if (category == null) {
                category = "";
            }

            if (description == null) {
                description = "";
            }
            saveTransactionToDatabase(category, amount, description, date);

            changeToMainPageView();

        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Error: La cantidad debe ser un número válido");
            alert.showAndWait();
        }
    }

    private void saveTransactionToDatabase(String category, double amount, String description, LocalDate date) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectDB.getInstance();

            String query = "INSERT INTO transactions (category, amount, description, date) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, category);
            statement.setDouble(2, amount);
            statement.setString(3, description);
            statement.setDate(4, Date.valueOf(date));

            statement.executeUpdate();
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

    public void initialize() {
        loadCategoriesFromDatabase();
        categoryComboBox.setItems(categoryNames);
    }

    private void loadCategoriesFromDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance();

            String query = "SELECT name FROM category";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                categoryNames.add(name);
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
    }

    @FXML
    private void openNewCategory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newCategory.fxml"));
            Parent root = loader.load();
            Stage transactionStage = (Stage) datePicker.getScene().getWindow();

            transactionStage.close();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void changeToMainPageView() {
        try {
            datePicker.setValue(null);
            categoryComboBox.setValue(null);
            amountField.clear();
            descriptionField.clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
            Parent root = loader.load();

            Stage transactionStage = (Stage) datePicker.getScene().getWindow();

            transactionStage.close();

            Scene scene = new Scene(root);

            Stage stage = (Stage) datePicker.getScene().getWindow();

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
