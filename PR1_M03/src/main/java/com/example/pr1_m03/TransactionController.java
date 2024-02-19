package com.example.pr1_m03;

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

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @FXML
    private List<Category> categories = new ArrayList<>();


     TransactionList transactionList = new TransactionList();
    // Instancia de TransactionList
    public TransactionController() {
    }


    @FXML
    private void addTransaction(ActionEvent actionEvent) {
        Alert alert = null;
        try {
            LocalDate date = datePicker.getValue();
            String category = categoryComboBox.getValue();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            Transaction newTransaction = new Transaction(category, amount, description, date);

            transactionList.addTransaction(newTransaction);
            changeToMainPageView();

        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Error: La cantidad debe ser un número válido");
            alert.showAndWait();
        }
    }



    public void initialize() {
        loadCategoriesFromJson();
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        categoryComboBox.setItems(categoryNames);
    }

    private void loadCategoriesFromJson() {
        try (InputStream is = new FileInputStream("Categories.json");
             JsonReader jsonReader = Json.createReader(is)) {
            JsonArray jsonArray = jsonReader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                String name = jsonObject.getString("name");
                categories.add(new Category(name));
            }
        } catch (IOException e) {
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

