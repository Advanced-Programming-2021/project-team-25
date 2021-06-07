package view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Game {
    private static Game singleToneClass = null;
    private Scene gameScene;
    public static Game getInstance() {
        if(singleToneClass == null) singleToneClass = new Game();
        return singleToneClass;
    }
    public void runGame(){
        Stage stage = MainMenu.gameStage;
        //Controller is Battlefield
        GridPane gridPane = CreateGrid.createGridPane();
        gameScene = new Scene(gridPane ,700 ,600);
        String style= Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
        gameScene.getStylesheets().add(style);
        stage.setScene(gameScene);
    }
}
