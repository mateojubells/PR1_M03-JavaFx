package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;
import javafx.beans.property.SimpleObjectProperty;
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

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.scene.control.Button;


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

    @FXML
    private TableColumn<Transaction, Void> transactionColumn4;

    @FXML
    private TableColumn<Transaction, Void> transactionColumn5;


    // Transaction list to store loaded transactions
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        transactionColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        transactionColumn1.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        transactionColumn2.setCellValueFactory(new PropertyValueFactory<>("Category"));
        transactionColumn3.setCellValueFactory(new PropertyValueFactory<>("Description"));
        transactionColumn4.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        transactionColumn5.setCellValueFactory(param -> new SimpleObjectProperty<>(null));

        loadTransactionsFromDatabase(); // Cargar transacciones desde la base de datos

        detallesTable.setItems(transactions);
        Callback<TableColumn<Transaction, Void>, TableCell<Transaction, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Transaction, Void> call(final TableColumn<Transaction, Void> param) {
                        return new TableCell<Transaction, Void>() {
                            private final Button btn = new Button("Edit");

                            {
                                btn.setOnAction(event -> {
                                    Transaction transaction = getTableView().getItems().get(getIndex());
                                    abrirFormularioEdicion(transaction);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                    }
                };

        transactionColumn4.setCellFactory(cellFactory);

        Callback<TableColumn<Transaction, Void>, TableCell<Transaction, Void>> deleteCellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Transaction, Void> call(final TableColumn<Transaction, Void> param) {
                        return new TableCell<Transaction, Void>() {
                            private final Button btn2 = new Button("Delete");

                            {
                                btn2.setOnAction(event -> {
                                    Transaction transaction = getTableView().getItems().get(getIndex());
                                    eliminarTransaccion(transaction);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn2);
                                }
                            }
                        };
                    }
                };

        transactionColumn5.setCellFactory(deleteCellFactory);
    }

    private void eliminarTransaccion(Transaction transaction) {
        try {
            TransactionDAO transactionDAO = new TransactionDAOImpl();
            transactionDAO.deleteTransaction(transaction.getId()); // Suponiendo que tienes un método en tu DAO para eliminar una transacción por su ID

            transactions.remove(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirFormularioEdicion(Transaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-transaction.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle la transacción a editar
            TransactionController transactionController = loader.getController();
            transactionController.setTransaction(transaction);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Transacción");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            connection = ConnectDB.getInstance();

            // Preparar la consulta para obtener todas las transacciones
            String query = "SELECT id, category, amount, description, date FROM transactions";  // Incluye el campo 'id'
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            // Procesar el resultado
            while (resultSet.next()) {
                int id = resultSet.getInt("id");  // Obtener el ID
                String category = resultSet.getString("category");
                if (category == null || category.isEmpty()) {
                    category = " ";
                }
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");
                LocalDate date = resultSet.getDate("date").toLocalDate();

                Transaction transaction = new Transaction(id, category, amount, description, date);  // Agregar ID
                transactions.add(transaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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