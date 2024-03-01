package com.example.pr1_m03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageApplication extends Application {
    private TransactionList transactionList = new TransactionList();

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Parent root = fxmlLoader.load();

        MainPageController mainPageController = fxmlLoader.getController();
        mainPageController.setTransactionList(transactionList);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Página principal");
        stage.setScene(scene);
        stage.setUserData(mainPageController); 
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}

