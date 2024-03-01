package com.example.pr1_m03;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AjustesController {

    private static final String JSON_TRANSACCIONES = "Transactions.json";
    private static final String JSON_CATEGORIAS = "Categories.json";
    private Stage primaryStage; // Referencia al Stage de MainPage
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    private void reiniciarTransacciones(ActionEvent event) {
        mostrarAlertaConfirmacion("Reiniciar Transacciones", "¿Estás seguro de que quieres reiniciar todas las transacciones?", () -> {
            try {
                Files.deleteIfExists(Paths.get(JSON_TRANSACCIONES));
                mostrarAlertaInformativa("Transacciones Eliminadas", "Se han eliminado todas las transacciones.");
                llamarMetodoReiniciar();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlertaError("Error al Eliminar Transacciones", "Ha ocurrido un error al eliminar las transacciones.");
            }
        });
    }

    @FXML
    private void reiniciarCategorias(ActionEvent event) {
        mostrarAlertaConfirmacion("Reiniciar Categorías", "¿Estás seguro de que quieres reiniciar todas las categorías?", () -> {
            try {
                Files.deleteIfExists(Paths.get(JSON_CATEGORIAS));
                mostrarAlertaInformativa("Categorías Eliminadas", "Se han eliminado todas las categorías.");
                llamarMetodoReiniciar();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlertaError("Error al Eliminar Categorías", "Ha ocurrido un error al eliminar las categorías.");
            }
        });
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        Platform.exit();
    }

    private void mostrarAlertaConfirmacion(String titulo, String contenido, Runnable onAceptar) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);

        ButtonType botonAceptar = new ButtonType("Aceptar");
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == botonAceptar) {
                onAceptar.run();
            }
        });
    }

    private void mostrarAlertaInformativa(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    @FXML
    private void llamarMetodoReiniciar() {
        try {
            mainPageController.reiniciar();

            // Obtener la referencia al AnchorPane actual (puede ser cualquier nodo presente en la vista)
            AnchorPane anchorPane = (AnchorPane) primaryStage.getScene().getRoot();
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
