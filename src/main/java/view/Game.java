package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class Game {
    private static Game singleToneClass = null;
    final String backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_normal.bmp")).toExternalForm();
    Image backGroundIMG = new Image(backGroundPath);
    static Canvas canvas = new Canvas(400, 400);
    static GraphicsContext graphic = canvas.getGraphicsContext2D();
    private Scene gameScene;
    public static Game getInstance() {
        if(singleToneClass == null) singleToneClass = new Game();
        return singleToneClass;
    }
    public void runGame(){
        Stage stage = MainMenu.gameStage;
        //Controller is Battlefield
        BorderPane root = new BorderPane();
        StackPane base = new StackPane();

        base.getChildren().add(canvas);
        root.setCenter(base);
        //make left buttons
        Button btnExit = new Button("Exit!");
        Button btnNextPhase = new Button("next Phase!");
        Button btnMuteSounds = new Button("mute sounds");
        VBox vBoxLeft = new VBox();
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxLeft.getChildren().addAll(btnNextPhase,btnMuteSounds,btnExit);

        //make right buttons
        Button btnGetFromHand = new Button("hand!");
        VBox vBoxRight = new VBox();
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxRight.setSpacing(4);
        vBoxRight.getChildren().addAll(btnGetFromHand,btnMuteSounds,btnExit);
        //make up top things
        Button Duelist = new Button("duelists");
        HBox hBoxTop = new HBox();
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxTop.getChildren().addAll(Duelist);
        //make down things (hand in here)
        Button hand = new Button("hand");
        HBox hBoxDown = new HBox();
        hBoxDown.getChildren().addAll(hand);
        hBoxDown.setAlignment(Pos.CENTER);

        root.setRight(vBoxRight);
        root.setLeft(vBoxLeft);
        root.setTop(hBoxTop);
        root.setBottom(hBoxDown);

        gameScene = new Scene(root ,750 ,600);
        // Set the width of the Canvas
        canvas.setWidth(500);
        // Set the height of the Canvas
        canvas.setHeight(450);
        String style= Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
        gameScene.getStylesheets().add(style);

        graphic.drawImage(backGroundIMG,0,0,500,450);
        stage.setScene(gameScene);
    }
}
