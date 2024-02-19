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
import java.util.Map;


public class CategoryController {

    @FXML
    private TextField categoryField;

    CategoryList categoryList = new CategoryList();
    private Scene scene;
    private Parent root;
    TransactionController transactionController;
    MainPageController mainController;

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
        try{
            categoryField.clear();
            Stage categoryStage = (Stage) categoryField.getScene().getWindow();
            categoryStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-transaction.fxml"));
            root = fxmlLoader.load();
            scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Agregar Gasto");

            newStage.show();
        }catch (IOException r) {
            System.out.printf("Error");
        }


    }


}