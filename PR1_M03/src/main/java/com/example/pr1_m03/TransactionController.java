package com.example.pr1_m03;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;


public class TransactionController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;


     TransactionList transactionList = new TransactionList();
    // Instancia de TransactionList
    public TransactionController() {
    }
    public TransactionController(TransactionList transactionList) {

        this.transactionList = transactionList;
    }

    @FXML
    private void addTransaction(ActionEvent actionEvent) {
        try {
            LocalDate date = datePicker.getValue();
            String category = categoryComboBox.getValue();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            Transaction newTransaction = new Transaction( category, amount, description, date);

            transactionList.addTransaction(newTransaction);

            System.out.println(newTransaction);

            clearFields();
            changeToMainPageView();

        } catch (NumberFormatException e) {
            System.out.println("Error: La cantidad debe ser un número válido.");
        }
    }

    private void clearFields() {
        // Limpiar los campos del formulario después de agregar la transacción
        datePicker.setValue(null);
        categoryComboBox.setValue(null);
        amountField.clear();
        descriptionField.clear();
    }
    private void changeToMainPageView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
            Parent root = loader.load();

            Stage transactionStage = (Stage) datePicker.getScene().getWindow();

            transactionStage.close();

            Scene scene = new Scene(root);

            Stage stage = (Stage) datePicker.getScene().getWindow();

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace(); // Manejar el error de carga de la nueva vista
        }
    }
}

