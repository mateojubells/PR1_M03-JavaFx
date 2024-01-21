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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class MainPageController {
    public Button btn_gastos;
    public Label lbl_total;
    public Button btn_detalles;
    public LineChart chrt_balance;

    private Scene scene;
    private Parent root;
    private XYChart.Series series = new XYChart.Series();

    public void setTransactionList(TransactionList transactionList) {
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

        newStage.setOnHidden(event->{
            addTransactionData();
        });
        newStage.show();
        // Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    public void goDetalles(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detalles.fxml"));
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
        addTransactionData();
    }

    /**
     * Función que permite añadir información a la gráfica de ingresos
     */
    private void addTransactionData() {
        series.getData().clear();
        int [] monthsTotals = new int[12];
        int yearTotal = 0;
        try (JsonReader reader = Json.createReader(new FileReader("Transactions.json"))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                int amount = jsonObject.getInt("amount");
                String dateString = jsonObject.getString("date");

                LocalDate date = LocalDate.parse(dateString);
                LocalDate currentDate = LocalDate.now();

                if (date.getYear() == currentDate.getYear()) {
                    Month month = date.getMonth();

                    switch (month){
                        case JANUARY -> monthsTotals[0] += amount;
                        case FEBRUARY -> monthsTotals[1] += amount;
                        case MARCH -> monthsTotals[2] += amount;
                        case APRIL -> monthsTotals[3] += amount;
                        case MAY -> monthsTotals[4] += amount;
                        case JUNE -> monthsTotals[5] += amount;
                        case JULY -> monthsTotals[6] += amount;
                        case AUGUST -> monthsTotals[7] += amount;
                        case SEPTEMBER -> monthsTotals[8] += amount;
                        case OCTOBER -> monthsTotals[9] += amount;
                        case NOVEMBER -> monthsTotals[10] += amount;
                        case DECEMBER -> monthsTotals[11] += amount;
                    }
                }
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
        series.getData().add(new XYChart.Data<>("1", monthsTotals[0]));
        series.getData().add(new XYChart.Data<>("2", monthsTotals[1]));
        series.getData().add(new XYChart.Data<>("3", monthsTotals[2]));
        series.getData().add(new XYChart.Data<>("4", monthsTotals[3]));
        series.getData().add(new XYChart.Data<>("5", monthsTotals[4]));
        series.getData().add(new XYChart.Data<>("6", monthsTotals[5]));
        series.getData().add(new XYChart.Data<>("7", monthsTotals[6]));
        series.getData().add(new XYChart.Data<>("8", monthsTotals[7]));
        series.getData().add(new XYChart.Data<>("9", monthsTotals[8]));
        series.getData().add(new XYChart.Data<>("10", monthsTotals[9]));
        series.getData().add(new XYChart.Data<>("11", monthsTotals[10]));
        series.getData().add(new XYChart.Data<>("12", monthsTotals[11]));
        chrt_balance.getData().add(series);

        for (int i = 0; i < monthsTotals.length; i++) {
            yearTotal += monthsTotals[i];
        }

        if (yearTotal <= 0){
            lbl_total.setTextFill(Color.RED);
            lbl_total.setText(yearTotal + "€");
        } else {
            lbl_total.setTextFill(Color.GREEN);
            lbl_total.setText(yearTotal + "€");
        }
    }
}
