package com.example.pr1_m03;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    public PieChart chrt_ingresos;
    public PieChart chart_gastos;
    public Button btn_ingresos;
    public Button btn_gastos;
    public Label lbl_gastos;
    public Label lbl_ingresos;

    private Scene scene;
    private Parent root;

    /**
     * Función que abre una ventana con la página de añadir ingresos
     * @param actionEvent N/A
     * @throws IOException Excepción en caso de error
     */
    public void goIngresos(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-earning.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Agregar Ingreso");

        newStage.show();
        // Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    /**
     * Función que abre una ventana con la página de añadir gastos
     * @param actionEvent N/A
     * @throws IOException Excepción en caso de error
     */
    public void goGastos(ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-expense.fxml"));
            root = fxmlLoader.load();
            scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Agregar Gasto");

            newStage.show();
            // Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }
}
