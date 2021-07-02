package view.menus;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class subStage {
    Stage stage;
    public subStage(String title, Scene scene) {
        Stage subStage = new Stage();
        this.stage = subStage;
        subStage.setTitle(title);
        subStage.setScene(scene);
        subStage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
