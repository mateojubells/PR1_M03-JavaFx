module com.example.pr1_m03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;
    requires java.sql;


    opens com.example.pr1_m03 to javafx.fxml;
    exports com.example.pr1_m03;
}