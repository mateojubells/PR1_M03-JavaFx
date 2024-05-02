package com.example.pr1_m03;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryController {

    @FXML
    private TextField categoryField;

    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @FXML
    private void newCategory(ActionEvent actionEvent) {
        String categoryName = categoryField.getText();

        if (!categoryName.isEmpty()) {
            categoryDAO.insertCategory(categoryName); // Llama al método insertCategory del DAO
        }
        closeView();
    }

    @FXML
    private void closeView() {
        Stage categoryStage = (Stage) categoryField.getScene().getWindow();
        categoryStage.close();
    }
}
