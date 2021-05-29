module AP {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.junit.jupiter.api;


    opens controllers to javafx.fxml;
    exports controllers;

    opens view to javafx.fxml;
    exports view;
}