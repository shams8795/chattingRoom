module com.example.demo11 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.ged to javafx.fxml;
    exports com.example.ged to javafx.graphics;  // Add this line
}
