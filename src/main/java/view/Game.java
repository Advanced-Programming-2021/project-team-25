package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Duelist;

import java.util.Objects;

public class Game {

    private static Game singleToneClass = null;
    final String backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_normal.bmp")).toExternalForm();
    final String DefaultCardInHandPath = Objects.requireNonNull(this.getClass().getResource("elements/default.png")).toExternalForm();
    final Image DefaultCardInHandImg = new Image(DefaultCardInHandPath);
    Image backGroundIMG = new Image(backGroundPath);
    static Canvas canvas = new Canvas(400, 400);
    static GraphicsContext graphic = canvas.getGraphicsContext2D();
    private Scene gameScene;
    private int defaultSize = 20;
    Canvas canvasHealthBar1 = new Canvas(100,15);
    Canvas canvasHealthBar2 = new Canvas(100,15);
    Canvas canvasHandBar1 = new Canvas(900,100);
    Canvas canvasHandBar2 = new Canvas(900,100);
    GraphicsContext graphic1 = canvasHealthBar1.getGraphicsContext2D();
    GraphicsContext graphic2 = canvasHealthBar2.getGraphicsContext2D();
    GraphicsContext graphicHand1 = canvasHandBar1.getGraphicsContext2D();
    GraphicsContext graphicHand2 = canvasHandBar2.getGraphicsContext2D();

    public static Game getInstance() {
        if(singleToneClass == null) singleToneClass = new Game();
        return singleToneClass;
    }

    public void runGame(Duelist duelist1,Duelist duelist2){
        Stage stage = Main.stage;
        //Controller is Battlefield
        BorderPane root = new BorderPane();
        StackPane base = new StackPane();

        base.getChildren().add(canvas);
        root.setCenter(base);
        //make left buttons
        VBox vBoxLeft = makeLeftBar();

        //make right buttons
        VBox vBoxRight = makeRightBar();

        //make up top things
        VBox vBoxTop = makeTopBar(duelist1, duelist2);


        //make down things (hand in here)
        //DefaultCardInHandImg
        for(int row=0;row<6;row++){
            graphicHand1.drawImage(DefaultCardInHandImg, 120 * row,10, 200, 70);
        }
        HBox hBoxDown = new HBox();
        hBoxDown.getChildren().addAll(canvasHandBar1);
        hBoxDown.setAlignment(Pos.CENTER);

        root.setRight(vBoxRight);
        root.setLeft(vBoxLeft);
        root.setTop(vBoxTop);
        root.setBottom(hBoxDown);

        gameScene = new Scene(root ,900 ,660);
        // Set the width of the Canvas
        canvas.setWidth(500);
        // Set the height of the Canvas
        canvas.setHeight(450);

        canvas.setStyle("-fx-padding: 20");
        String style= Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
        gameScene.getStylesheets().add(style);

        graphic.drawImage(backGroundIMG,0,0,500,450);
        stage.setScene(gameScene);
    }

    private VBox makeLeftBar() {
        Button btnExit = new Button("Exit!");
        Button btnNextPhase = new Button("next Phase!");
        Button btnMuteSounds = new Button("mute sounds");
        VBox vBoxLeft = new VBox();
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxLeft.getChildren().addAll(btnNextPhase,btnMuteSounds,btnExit);
        return vBoxLeft;
    }

    private VBox makeRightBar() {
        String imageDeck = Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm();
        ImageView imgDeck = new ImageView(imageDeck);
        imgDeck.setFitHeight(200);
        imgDeck.setFitWidth(150);
        VBox vBoxRight = new VBox();
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxRight.setSpacing(4);
        vBoxRight.getChildren().addAll(imgDeck);
        return vBoxRight;
    }

    private VBox makeTopBar(Duelist duelist1, Duelist duelist2) {
        refreshHealthBar(duelist1, duelist2);


        ImageView imgDuelist1 = new ImageView("file:"+ duelist1.getUser().getUsername()+".png");
        imgDuelist1.setFitHeight(100);
        imgDuelist1.setFitWidth(100);
        ImageView imgDuelist2 = new ImageView("file:"+ duelist2.getUser().getUsername()+".png");
        imgDuelist2.setFitHeight(100);
        imgDuelist2.setFitWidth(100);

        HBox hbox1 = new HBox();
        Label lblDuelist1Name = new Label(duelist1.getUser().getNickName());
        lblDuelist1Name.setAlignment(Pos.CENTER);
        Label lblDuelist1LP = new Label(" "+ duelist1.LP);
        lblDuelist1LP.setAlignment(Pos.CENTER);

        HBox hBoxUserDetail1 = new HBox();
        hBoxUserDetail1.getChildren().addAll(lblDuelist1Name,lblDuelist1LP);
        VBox vboxUserDetail1 = new VBox();
        vboxUserDetail1.setSpacing(30);
        vboxUserDetail1.getChildren().addAll(hBoxUserDetail1,canvasHealthBar1);

        hbox1.getChildren().addAll(imgDuelist1,vboxUserDetail1);
        hbox1.setSpacing(20);

        HBox hbox2 = new HBox();
        Label lblDuelist2Name = new Label(duelist2.getUser().getNickName());
        lblDuelist2Name.setAlignment(Pos.CENTER);
        Label lblDuelist2LP = new Label(" "+ duelist2.LP);
        lblDuelist2LP.setAlignment(Pos.CENTER);

        HBox hBoxUserDetail2 = new HBox();
        hBoxUserDetail2.getChildren().addAll(lblDuelist2Name,lblDuelist2LP);
        VBox vboxUserDetail2 = new VBox();
        vboxUserDetail2.setSpacing(30);
        vboxUserDetail2.getChildren().addAll(hBoxUserDetail2,canvasHealthBar2);

        hbox2.getChildren().addAll(vboxUserDetail2,imgDuelist2);
        hbox2.setSpacing(20);
        HBox hBoxTop = new HBox();
        VBox vboxTop = new VBox();
//        for(int row=0;row<6;row++){
//            graphicHand2.drawImage(DefaultCardInHandImg, defaultSize * row,defaultSize, defaultSize, defaultSize);
//        }
        hBoxTop.getChildren().addAll(hbox1,hbox2);
        hBoxTop.setSpacing(100);
//        vboxTop.getChildren().addAll(hBoxTop,canvasHandBar2);
        vboxTop.getChildren().addAll(hBoxTop);
        hBoxTop.setAlignment(Pos.CENTER);
        //top menu ready
        return vboxTop;
    }

    private void refreshHealthBar(Duelist duelist1, Duelist duelist2) {
        float percentage1 =((float) duelist1.LP/80)/100;
        float percentage2 =((float) duelist2.LP/80)/100;
        graphic1.setFill(Color.RED);
        graphic1.fillRect(0,0,100*percentage1,20);
        graphic2.setFill(Color.RED);
        graphic2.fillRect(0,0,100*percentage2,20);
    }
}
