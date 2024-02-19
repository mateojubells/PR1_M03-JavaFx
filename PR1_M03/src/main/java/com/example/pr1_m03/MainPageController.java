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
    private final XYChart.Series total = new XYChart.Series<>();
    private final XYChart.Series earnings = new XYChart.Series<>();
    private final XYChart.Series spendings = new XYChart.Series<>();

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

        newStage.setOnHidden(event -> {
            total.getData().clear();
            earnings.getData().clear();
            spendings.getData().clear();
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
        total.setName("Total mensual");
        earnings.setName("Ingresos mensuales");
        spendings.setName("Gastos mensuales");
        
        addTransactionData();
    }

    /**
     * Función que permite añadir información a la gráfica de ingresos
     */
    private void addTransactionData() {
        total.getData().clear();
        int[] monthsTotals = new int[12];
        int[] monthsEarnings = new int[12];
        int[] monthsSpendings = new int[12];
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

                    switch (month) {
                        case JANUARY -> {
                            monthsTotals[0] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case FEBRUARY -> {
                            monthsTotals[1] += amount;
                            if (amount > 0) {
                                monthsEarnings[1] += amount;
                            } else {
                                monthsSpendings[1] += amount;
                            }
                        }
                        case MARCH -> {
                            monthsTotals[2] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case APRIL -> {
                            monthsTotals[3] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case MAY -> {
                            monthsTotals[4] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case JUNE -> {
                            monthsTotals[5] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case JULY -> {
                            monthsTotals[6] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case AUGUST -> {
                            monthsTotals[7] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case SEPTEMBER -> {
                            monthsTotals[8] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case OCTOBER -> {
                            monthsTotals[9] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case NOVEMBER -> {
                            monthsTotals[10] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                        case DECEMBER -> {
                            monthsTotals[11] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Total year
        total.getData().add(new XYChart.Data<>("1", monthsTotals[0]));
        total.getData().add(new XYChart.Data<>("2", monthsTotals[1]));
        total.getData().add(new XYChart.Data<>("3", monthsTotals[2]));
        total.getData().add(new XYChart.Data<>("4", monthsTotals[3]));
        total.getData().add(new XYChart.Data<>("5", monthsTotals[4]));
        total.getData().add(new XYChart.Data<>("6", monthsTotals[5]));
        total.getData().add(new XYChart.Data<>("7", monthsTotals[6]));
        total.getData().add(new XYChart.Data<>("8", monthsTotals[7]));
        total.getData().add(new XYChart.Data<>("9", monthsTotals[8]));
        total.getData().add(new XYChart.Data<>("10", monthsTotals[9]));
        total.getData().add(new XYChart.Data<>("11", monthsTotals[10]));
        total.getData().add(new XYChart.Data<>("12", monthsTotals[11]));
        chrt_balance.getData().add(total);

        // Total earnings
        earnings.getData().add(new XYChart.Data<>("1", monthsEarnings[0]));
        earnings.getData().add(new XYChart.Data<>("2", monthsEarnings[1]));
        earnings.getData().add(new XYChart.Data<>("3", monthsEarnings[2]));
        earnings.getData().add(new XYChart.Data<>("4", monthsEarnings[3]));
        earnings.getData().add(new XYChart.Data<>("5", monthsEarnings[4]));
        earnings.getData().add(new XYChart.Data<>("6", monthsEarnings[5]));
        earnings.getData().add(new XYChart.Data<>("7", monthsEarnings[6]));
        earnings.getData().add(new XYChart.Data<>("8", monthsEarnings[7]));
        earnings.getData().add(new XYChart.Data<>("9", monthsEarnings[8]));
        earnings.getData().add(new XYChart.Data<>("10", monthsEarnings[9]));
        earnings.getData().add(new XYChart.Data<>("11", monthsEarnings[10]));
        earnings.getData().add(new XYChart.Data<>("12", monthsEarnings[11]));
        chrt_balance.getData().add(earnings);

        // Total spendings
        spendings.getData().add(new XYChart.Data<>("1", monthsSpendings[0]));
        spendings.getData().add(new XYChart.Data<>("2", monthsSpendings[1]));
        spendings.getData().add(new XYChart.Data<>("3", monthsSpendings[2]));
        spendings.getData().add(new XYChart.Data<>("4", monthsSpendings[3]));
        spendings.getData().add(new XYChart.Data<>("5", monthsSpendings[4]));
        spendings.getData().add(new XYChart.Data<>("6", monthsSpendings[5]));
        spendings.getData().add(new XYChart.Data<>("7", monthsSpendings[6]));
        spendings.getData().add(new XYChart.Data<>("8", monthsSpendings[7]));
        spendings.getData().add(new XYChart.Data<>("9", monthsSpendings[8]));
        spendings.getData().add(new XYChart.Data<>("10", monthsSpendings[9]));
        spendings.getData().add(new XYChart.Data<>("11", monthsSpendings[10]));
        spendings.getData().add(new XYChart.Data<>("12", monthsSpendings[11]));
        chrt_balance.getData().add(spendings);

        for (int monthsTotal : monthsTotals) {
            yearTotal += monthsTotal;
        }

        if (yearTotal <= 0) {
            lbl_total.setTextFill(Color.RED);
            lbl_total.setText(yearTotal + "€");
        } else {
            lbl_total.setTextFill(Color.GREEN);
            lbl_total.setText(yearTotal + "€");
        }
    }
}
