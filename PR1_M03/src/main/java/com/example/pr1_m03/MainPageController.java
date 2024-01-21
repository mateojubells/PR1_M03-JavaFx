package com.example.pr1_m03;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    public Button btn_gastos;
    public Label lbl_total;
    public Button btn_detalles;
    public LineChart chrt_balance;

    private TransactionList transactionList;


    private Scene scene;
    private Parent root;
    private XYChart.Series series = new XYChart.Series();
    private TransactionList tList = new TransactionList();

    public void setTransactionList(TransactionList transactionList) {
        this.transactionList = transactionList;
        // Puedes inicializar las gráficas y otras configuraciones aquí si es necesario.
    }

    /**
     * Función que abre una ventana con la página de añadir gastos
     *
     * @param actionEvent N/A
     * @throws IOException Excepción en caso de error
     */
    public void goTransactions(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-transaction.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Agregar Gasto");

        newStage.show();
        addTransactionData();
        // Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    public void goDetalles(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detalles.fxml"));
        //FIXME: Agregar la vista correcta
        root = fxmlLoader.load();
        scene = new Scene(root);

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Detalles");

        newStage.show();
        // Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    @FXML
    private void initialize() {
        series.setName("Total mensual");
        //TODO: Importar datos de JSON
    }

    /**
     * Función que permite añadir información a la gráfica de ingresos
     */
    private void addTransactionData() {
        // TODO: Acabar función para añadir los ingresos creados
        /*series.getData().add(new XYChart.Data<>("1", 500.23));
        series.getData().add(new XYChart.Data<>("2", 200.55));
        series.getData().add(new XYChart.Data<>("4", -300));
        series.getData().add(new XYChart.Data<>("5", -22.4));
        series.getData().add(new XYChart.Data<>("6", 432.54));
        series.getData().add(new XYChart.Data<>("7", 399.99));
        series.getData().add(new XYChart.Data<>("8", 232.12));
        series.getData().add(new XYChart.Data<>("9", 134.54));
        series.getData().add(new XYChart.Data<>("10", 50.03));
        series.getData().add(new XYChart.Data<>("11", 0.03));
        series.getData().add(new XYChart.Data<>("12", -3));
        chrt_balance.getData().add(series);*/
    }
}
