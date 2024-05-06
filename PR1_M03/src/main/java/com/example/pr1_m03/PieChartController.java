package com.example.pr1_m03;

import com.jdbc.utilities.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PieChartController implements Initializable {

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChartDataFromDatabase();
    }

    private void loadChartDataFromDatabase() {
        try {
            Connection connection = ConnectDB.getInstance();
            String sql = "SELECT category, COUNT(*) AS count FROM transactions GROUP BY category";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                int count = resultSet.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }

            pieChart.setData(pieChartData);
            pieChart.setTitle("Uso de Categorías");

            resultSet.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
