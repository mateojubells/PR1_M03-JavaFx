package com.example.pr1_m03;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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

    public void addExpense() {
        LocalDate date = datePicker.getValue();
        String category = categoryComboBox.getValue();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();

        // Aquí puedes guardar la información como desees
        Expense newExpense = new Expense(date, category, amount, description);
        System.out.println("Gasto añadido: " + newExpense);
        // Lógica para guardar el gasto en una lista, base de datos, etc.
    }
}
