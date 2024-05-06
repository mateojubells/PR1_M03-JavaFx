package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class MainPageApplication extends Application {
    private TransactionList transactionList = new TransactionList();
    private Connection connection;

    @Override
    public void init() throws Exception {
        connection = ConnectDB.getInstance();
        System.out.println("Conexion establecida");
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Parent root = fxmlLoader.load();

        MainPageController mainPageController = fxmlLoader.getController();
        mainPageController.setTransactionList(transactionList);

        // Pasar la conexión al controlador AjustesController

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Página principal");
        stage.setScene(scene);
        stage.setUserData(mainPageController);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
