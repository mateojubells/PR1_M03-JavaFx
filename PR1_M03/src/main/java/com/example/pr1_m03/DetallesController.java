package com.example.pr1_m03;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class DetallesController implements Initializable {

    @FXML
    private TableView<Transaction> detallesTable;

    @FXML
    private TableColumn<Transaction, String> transactionColumn;

    // Transaction list to store loaded transactions
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up columns
        transactionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));

        // Load transactions from JSON
        loadTransactionsFromJson();

        // Populate table with transactions
        detallesTable.setItems(transactions);
    }

    private void loadTransactionsFromJson() {
        // Replace this path with your actual file path
        String jsonFilePath = "Transactions.json";

        // Load transactions from JSON
        TransactionList transactionList = new TransactionList();
        transactionList.loadTransactionsFromJson(Paths.get(jsonFilePath));
        List<Transaction> loadedTransactions = transactionList.getAllTransactions();

        // Add loaded transactions to the observable list
        transactions.addAll(loadedTransactions);
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) detallesTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("main-page.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
