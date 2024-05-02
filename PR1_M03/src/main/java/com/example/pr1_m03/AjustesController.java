package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjustesController {

    private Stage primaryStage; // Referencia al Stage de MainPage
    private MainPageController mainPageController;
    private Connection connection; // Mantener la conexión abierta mientras la aplicación esté en ejecución

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Método para cerrar la conexión cuando se cierra la aplicación
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void reiniciarTransacciones(ActionEvent event) {
        mostrarAlertaConfirmacion("Reiniciar Transacciones", "¿Estás seguro de que quieres reiniciar todas las transacciones?", () -> {
            try {
                // Eliminar todas las transacciones de la base de datos
                eliminarTodasLasTransacciones();
                mostrarAlertaInformativa("Transacciones Eliminadas", "Se han eliminado todas las transacciones.");
                llamarMetodoReiniciar();
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlertaError("Error al Eliminar Transacciones", "Ha ocurrido un error al eliminar las transacciones.");
            }
        });
    }

    @FXML
    private void reiniciarCategorias(ActionEvent event) {
        mostrarAlertaConfirmacion("Reiniciar Categorías", "¿Estás seguro de que quieres reiniciar todas las categorías?", () -> {
            try {
                // Eliminar todas las categorías de la base de datos
                eliminarTodasLasCategorias();
                mostrarAlertaInformativa("Categorías Eliminadas", "Se han eliminado todas las categorías.");
                llamarMetodoReiniciar();
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlertaError("Error al Eliminar Categorías", "Ha ocurrido un error al eliminar las categorías.");
            }
        });
    }

    @FXML
    private void cerrarAplicacion() {
        cerrarConexion(); // Cerrar la conexión al cerrar la aplicación
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

            AnchorPane anchorPane = (AnchorPane) primaryStage.getScene().getRoot();
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarTodasLasTransacciones() throws SQLException {
        PreparedStatement statement = null;

        try {

            connection = ConnectDB.getInstance();

            String query = "DELETE FROM transactions";
            statement = connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void eliminarTodasLasCategorias() throws SQLException {
        PreparedStatement statement = null;

        try {
            connection = ConnectDB.getInstance();

            String query = "DELETE FROM category";
            statement = connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
