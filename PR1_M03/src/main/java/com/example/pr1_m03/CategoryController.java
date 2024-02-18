package com.example.pr1_m03;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.json.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class CategoryController {
    private TransactionController transactionController;
    public void setTransactionController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }
    @FXML
    private TextField categoryField;

    CategoryList categoryList = new CategoryList();

    @FXML
    private void newCategory(ActionEvent actionEvent) {
        String categoryName = categoryField.getText();

        if (!categoryName.isEmpty()) {
            Category category = new Category(categoryName);
            categoryList.addCategories(category);
        }
        closeView();

    }

    @FXML
    private void closeView(){
        categoryField.clear();
        Stage categoryStage = (Stage) categoryField.getScene().getWindow();
        categoryStage.close();

        // Reiniciar la vista de agregar transacción
        if (transactionController != null) {
            transactionController.resetView();
        }
    }


}