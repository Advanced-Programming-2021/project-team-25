package view.menus;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;

public class subStage {
    Stage stage;
    public subStage(String title, Scene scene) {
        Stage subStage = new Stage();
        this.stage = subStage;
        subStage.setTitle(title);
        subStage.setScene(scene);
        subStage.initModality(Modality.WINDOW_MODAL);
        subStage.initOwner(Main.stage);
        subStage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
