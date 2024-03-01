package com.example.pr1_m03;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    private Stage primaryStage;

    private Scene scene;
    private Parent root;
    private XYChart.Series total = new XYChart.Series<>();
    private XYChart.Series earnings = new XYChart.Series<>();
    private XYChart.Series spendings = new  XYChart.Series<>();

    public void setTransactionList(TransactionList transactionList) {
        // Puedes inicializar las gráficas y otras configuraciones aquí si es necesario.
    }
    public void setPrimaryStage(Stage primaryStage) {


        this.primaryStage = primaryStage;
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
            total.getData().clear();
            earnings.getData().clear();
            spendings.getData().clear();

            addTransactionData();
        });
        newStage.show();
    }

    public void goDetalles(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detalles.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Detalles");

        newStage.show();
    }

    @FXML
    void initialize() {
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
                        case JANUARY:
                            monthsTotals[0] += amount;
                            if (amount > 0) {
                                monthsEarnings[0] += amount;
                            } else {
                                monthsSpendings[0] += amount;
                            }
                            break;
                        case FEBRUARY:
                            monthsTotals[1] += amount;
                            if (amount > 0) {
                                monthsEarnings[1] += amount;
                            } else {
                                monthsSpendings[1] += amount;
                            }
                            break;
                        case MARCH:
                            monthsTotals[2] += amount;
                            if (amount > 0) {
                                monthsEarnings[2] += amount;
                            } else {
                                monthsSpendings[2] += amount;
                            }
                            break;
                        case APRIL:
                            monthsTotals[3] += amount;
                            if (amount > 0) {
                                monthsEarnings[3] += amount;
                            } else {
                                monthsSpendings[3] += amount;
                            }
                            break;
                        case MAY:
                            monthsTotals[4] += amount;
                            if (amount > 0) {
                                monthsEarnings[4] += amount;
                            } else {
                                monthsSpendings[4] += amount;
                            }
                            break;
                        case JUNE:
                            monthsTotals[5] += amount;
                            if (amount > 0) {
                                monthsEarnings[5] += amount;
                            } else {
                                monthsSpendings[5] += amount;
                            }
                            break;
                        case JULY:
                            monthsTotals[6] += amount;
                            if (amount > 0) {
                                monthsEarnings[6] += amount;
                            } else {
                                monthsSpendings[6] += amount;
                            }
                            break;
                        case AUGUST:
                            monthsTotals[7] += amount;
                            if (amount > 0) {
                                monthsEarnings[7] += amount;
                            } else {
                                monthsSpendings[7] += amount;
                            }
                            break;
                        case SEPTEMBER:
                            monthsTotals[8] += amount;
                            if (amount > 0) {
                                monthsEarnings[8] += amount;
                            } else {
                                monthsSpendings[8] += amount;
                            }
                            break;
                        case OCTOBER:
                            monthsTotals[9] += amount;
                            if (amount > 0) {
                                monthsEarnings[9] += amount;
                            } else {
                                monthsSpendings[9] += amount;
                            }
                            break;
                        case NOVEMBER:
                            monthsTotals[10] += amount;
                            if (amount > 0) {
                                monthsEarnings[10] += amount;
                            } else {
                                monthsSpendings[10] += amount;
                            }
                            break;
                        case DECEMBER:
                            monthsTotals[11] += amount;
                            if (amount > 0) {
                                monthsEarnings[11] += amount;
                            } else {
                                monthsSpendings[11] += amount;
                            }
                            break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Total year
        for (int i = 0; i < 12; i++) {
            total.getData().add(new XYChart.Data<>(String.valueOf(i + 1), monthsTotals[i]));
        }
        chrt_balance.getData().add(total);

        // Total earnings
        for (int i = 0; i < 12; i++) {
            earnings.getData().add(new XYChart.Data<>(String.valueOf(i + 1), monthsEarnings[i]));
        }
        chrt_balance.getData().add(earnings);

        // Total spendings
        for (int i = 0; i < 12; i++) {
            spendings.getData().add(new XYChart.Data<>(String.valueOf(i + 1), monthsSpendings[i]));
        }
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
    @FXML
    void reiniciar() throws IOException {
        Stage mainPageStage = (Stage) btn_detalles.getScene().getWindow();
        mainPageStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Nueva Página");
        newStage.show();
    }

    @FXML
    private void ajustes() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ajustes.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        AjustesController ajustesController = fxmlLoader.getController();
        ajustesController.setPrimaryStage(primaryStage);
        ajustesController.setMainPageController(this); // Establecer referencia al MainPageController

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Ajustes");
        newStage.show();
    }


}
