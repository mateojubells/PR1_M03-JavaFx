package com.example.pr1_m03;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PieChartController implements Initializable {

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChartData();
    }

    private void loadChartData() {
        // Obtener la lista de transacciones
        TransactionList transactionList = new TransactionList();
        transactionList.loadTransactionsFromJson(Paths.get("Transactions.json"));

        // Contar la cantidad de transacciones por categoría
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Transaction transaction : transactionList.getAllTransactions()) {
            String category = transaction.getCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }

        // Crear los datos para la gráfica circular
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        categoryCounts.forEach((category, count) -> {
            pieChartData.add(new PieChart.Data(category, count));
        });

        // Configurar la gráfica circular
        pieChart.setData(pieChartData);
        pieChart.setTitle("Uso de Categorías");
    }
}
