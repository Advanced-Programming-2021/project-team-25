package view.menus;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class subStage {
    public subStage(String title, Scene scene) {
        Stage subStage = new Stage();
        subStage.setTitle(title);
        subStage.setScene(scene);
        subStage.show();
    }

}
