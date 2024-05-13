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
import java.util.List;

public class TransactionController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<String> categoryComboBox;

    private Transaction transaction;

    private ObservableList<String> categoryNames = FXCollections.observableArrayList();
    private TransactionDAO transactionDAO;

    public TransactionController() {
        this.transactionDAO = new TransactionDAOImpl();
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        cargarDatosTransaccion();
    }

    private void cargarDatosTransaccion() {
        if (transaction != null) {
            datePicker.setValue(transaction.getDate());
            amountField.setText(Double.toString(transaction.getAmount()));
            descriptionField.setText(transaction.getDescription());
            categoryComboBox.setValue(transaction.getCategory());
        }
    }

    @FXML
    private void guardarCambios() {
        LocalDate date = datePicker.getValue();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String category = categoryComboBox.getValue();

        transaction.setDate(date);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCategory(category);

        // Actualizar la base de datos
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        transactionDAO.updateTransaction(transaction);

        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) datePicker.getScene().getWindow();
        stage.close();
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

            // Crear una nueva transacción
            Transaction transaction = new Transaction(0,category, amount, description, date);

            // Guardar la transacción utilizando el DAO
            transactionDAO.saveTransaction(transaction);

            changeToMainPageView();

        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Error: La cantidad debe ser un número válido");
            alert.showAndWait();
        }
    }

    public void initialize() {
        loadCategoriesFromDatabase();
        categoryComboBox.setItems(categoryNames);
    }

    private void loadCategoriesFromDatabase() {
        try {
            // Utilizar el DAO para obtener todas las categorías
            CategoryDAO categoryDAO = new CategoryDAOImpl();
            List<Category> categories = categoryDAO.getAllCategories();

            // Limpiar la lista actual de nombres de categorías
            categoryNames.clear();

            // Agregar los nombres de las categorías obtenidas al ObservableList
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
