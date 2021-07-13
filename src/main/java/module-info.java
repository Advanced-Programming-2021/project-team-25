module AP {

    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires java.logging;
    requires json.simple;
    requires gson;
    requires javafx.swing;
    requires javafx.controls;
    //requires opencv;


    opens controllers to javafx.fxml;
    exports controllers;

    opens view to javafx.fxml;
    exports view;

    opens models to javafx.fxml;
    exports models;

}