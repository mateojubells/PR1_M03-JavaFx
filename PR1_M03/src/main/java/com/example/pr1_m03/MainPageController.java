package com.example.pr1_m03;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public PieChart chrt_gastos;
    public Button btn_ingresos;
    public Button btn_gastos;
    public Label lbl_gastos;
    public Label lbl_ingresos;

    private Scene scene;
    private Parent root;

    /**
     * Función que abre una ventana con la página de añadir ingresos
     *
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
     *
     * @param actionEvent N/A
     * @throws IOException Excepción en caso de error
     */
    public void goGastos(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-transaction.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Agregar Gasto");

        newStage.show();
        // Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    @FXML
    private void initialize() {
        ObservableList<PieChart.Data> ingresosChartData = FXCollections.observableArrayList();

        chrt_ingresos.setData(ingresosChartData);
        ObservableList<PieChart.Data> gastosChartData = FXCollections.observableArrayList(

        );

        chrt_gastos.setData(gastosChartData);
    }

    /**
     * Función que permite añadir información a la gráfica de ingresos
     */
    private void addDataIngresosChart() {
        ObservableList<PieChart.Data> ingresosChartData = chrt_ingresos.getData();
        // TODO: Acabar función para añadir los ingresos creados
    }

    /**
     * Función que permite añadir información a la gráfica de gastos.
     */
    private void addDataGastosChart() {
        ObservableList<PieChart.Data> gastosChartData = chrt_gastos.getData();
        // TODO: Acabar funcion para añadir los gastos creados
    }

    /**
     * Función que permite actualizar las label resumen de gastos e ingresos cada vez que se añada
     * información
     */
    private void actualizarTextosResumen() {
        // TODO: Acabar la funcón para actualizar las label resumen de gasto e ingresos
    }

}
