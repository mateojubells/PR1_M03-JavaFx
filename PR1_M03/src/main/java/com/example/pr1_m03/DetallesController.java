package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetallesController implements Initializable {

    public Button pieButton;
    @FXML
    private TableView<Transaction> detallesTable;
    @FXML
    private TableColumn<Transaction, String> transactionColumn;
    @FXML
    private TableColumn<Transaction, String> transactionColumn1;
    @FXML
    private TableColumn<Transaction, String> transactionColumn2;

    @FXML
    private TableColumn<Transaction, String> transactionColumn3;

    // Transaction list to store loaded transactions
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        transactionColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        transactionColumn1.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        transactionColumn2.setCellValueFactory(new PropertyValueFactory<>("Category"));
        transactionColumn3.setCellValueFactory(new PropertyValueFactory<>("Description"));

        loadTransactionsFromDatabase(); // Cargar transacciones desde la base de datos

        detallesTable.setItems(transactions);
    }

    // Método para cargar transacciones desde la base de datos
    private void loadTransactionsFromDatabase() {
        List<Transaction> loadedTransactions = getAllTransactions();
        transactions.addAll(loadedTransactions); // Agregar las transacciones al ObservableList
    }

    private void loadTransactionsFromJson() {
        String jsonFilePath = "Transactions.json";

        TransactionList transactionList = new TransactionList();
        transactionList.loadTransactionsFromJson(Paths.get(jsonFilePath));
        List<Transaction> loadedTransactions = transactionList.getAllTransactions();

        transactions.addAll(loadedTransactions);
    }


    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtener la conexión
            connection = ConnectDB.getInstance();

            // Preparar la consulta para obtener todas las transacciones
            String query = "SELECT * FROM transactions";
            statement = connection.prepareStatement(query);

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Procesar el resultado
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                // Si la categoría está vacía, establecerla en un espacio en blanco
                if (category == null || category.isEmpty()) {
                    category = " ";
                }
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                LocalDate date = resultSet.getDate("date").toLocalDate();

                // Crear una nueva transacción y agregarla a la lista
                Transaction transaction = new Transaction(category, amount, description, date);
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Cerrar el conjunto de resultados
                if (resultSet != null) {
                    resultSet.close();
                }

                // Cerrar la declaración
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }


    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) detallesTable.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Parent root = loader.load();

        stage.setTitle("TransactionApp");

        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    @FXML
    private void handlePieChartButtonAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pie-chart.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}