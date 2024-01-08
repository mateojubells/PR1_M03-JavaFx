package com.example.pr1_m03;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
public class ExpenseController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private TextField descriptionField;

    @FXML
    private void openMainPage() {
        try {
            MainPageApplication mainPageApplication = new MainPageApplication();
            mainPageApplication.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addExpense() {
        // Lógica para añadir un gasto
        LocalDate date = datePicker.getValue();
        String category = categoryComboBox.getValue();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();

        Expense newExpense = new Expense(date, category, amount, description);
        System.out.println("Gasto añadido: " + newExpense);

        openMainPage();
    }

    // Otros métodos del controlador...
}