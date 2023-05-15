module com.example.lopu_projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lopu_projekt to javafx.fxml;
    exports com.example.lopu_projekt;
}