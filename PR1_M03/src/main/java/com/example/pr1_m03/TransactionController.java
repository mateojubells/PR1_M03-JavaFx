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
            // Obtener valores del formulario
            LocalDate date = datePicker.getValue();
            String category = categoryComboBox.getValue();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            // Crear una nueva transacción
            Transaction newTransaction = new Transaction( category, amount, description, date);

            // Agregar la transacción a la lista en TransactionList
            transactionList.addTransaction(newTransaction);

            // Puedes imprimir la transacción para verificar que se ha creado correctamente
            System.out.println(newTransaction);

            // Limpiar los campos después de agregar la transacción
            clearFields();
            changeToMainPageView();

        } catch (NumberFormatException e) {
            // Manejar el caso en el que la cantidad no sea un número válido
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
            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
            Parent root = loader.load();

            Stage transactionStage = (Stage) datePicker.getScene().getWindow();

            // Cerrar la ventana de transacciones
            transactionStage.close();

            // Configurar el controlador si es necesario (puedes obtenerlo del loader)
            // MainController mainController = loader.getController();
            // mainController.initialize(); // Ejemplo si necesitas inicializar algo en el controlador

            // Configurar la escena
            Scene scene = new Scene(root);

            // Obtener el escenario actual (ventana)
            Stage stage = (Stage) datePicker.getScene().getWindow();

            // Configurar el escenario con la nueva escena
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace(); // Manejar el error de carga de la nueva vista
        }
    }
}

