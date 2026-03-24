module com.example.survivethenight {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.survivethenight to javafx.fxml;
    exports com.example.survivethenight;
}