package view.menus;

import controllers.Battelfield.ImageAdapter;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Duelist;
import view.Main;

import java.util.Objects;

public class Game {
    final String backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_normal.bmp")).toExternalForm();
    final String DefaultCardInHandPath = Objects.requireNonNull(this.getClass().getResource("elements/default.png")).toExternalForm();
    final Image DefaultCardInHandImg = new Image(DefaultCardInHandPath);
    final String mainPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/Main.png")).toExternalForm();
    final String endPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/EndPhase.png")).toExternalForm();
    final String drawPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/DrawPhase.png")).toExternalForm();
    final String battlePhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/BattlePhase.png")).toExternalForm();
    final String duelPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/DuelPhase.png")).toExternalForm();
    final String standbyPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/Standby.png")).toExternalForm();
    final String graveYardPath = Objects.requireNonNull(this.getClass().getResource("elements/graveYard.png")).toExternalForm();
    final String fieldPath = Objects.requireNonNull(this.getClass().getResource("elements/field.png")).toExternalForm();
    Image mainPhaseIMG = new Image(mainPhasePath);
    Image endPhaseIMG = new Image(endPhasePath);
    Image drawPhaseIMG = new Image(drawPhasePath);
    Image battlePhaseIMG = new Image(battlePhasePath);
    Image duelPhaseIMG = new Image(duelPhasePath);
    Image standbyPhaseIMG = new Image(standbyPhasePath);
    Image graveYardIMG = new Image(graveYardPath);
    Image fieldIMG = new Image(fieldPath);
    public Canvas canvas= new Canvas(400, 400);
    public ImageView imgDuelist1;
    Image backGroundIMG = new Image(backGroundPath);

    GraphicsContext graphic = canvas.getGraphicsContext2D();
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
    BorderPane root = new BorderPane();
    StackPane base = new StackPane();
    Duelist turn, opponent;

    public Game(Duelist turn, Duelist opponent){
        this.turn = turn;
        this.opponent = opponent;
    }
    public void runGame(){
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSceneX());
                System.out.println(event.getSceneY());
            }
        });
        //Default player is duelist1
        Stage stage = Main.stage;
        //Controller is Battlefield

        base.getChildren().add(canvas);
        root.setCenter(base);
        //make left buttons
        VBox vBoxLeft = makeLeftBar(turn, opponent);

        //make right buttons
        VBox vBoxRight = makeRightBar();

        //make up top things
        VBox vBoxTop = makeTopBar(turn, opponent);

        //init graveYard
        initGraveYardAndFieldZone();

        //make down things (hand in here)
        HBox hBoxDown = makeDownBar();

        root.setRight(vBoxRight);
        root.setLeft(vBoxLeft);
        root.setTop(vBoxTop);
        root.setBottom(hBoxDown);

        gameScene = new Scene(root ,900 ,660);

        stylingGame();

        stage.setScene(gameScene);
    }

    public void initGraveYardAndFieldZone() {
        ImageAdapter.setCardOnOpponentGraveYard(graphic,graveYardIMG);
        ImageAdapter.setCardOnTurnGraveYard(graphic,graveYardIMG);
        ImageAdapter.setCardOnOpponentFieldZone(graphic,fieldIMG);
        ImageAdapter.setCardOnTurnFieldZone(graphic,fieldIMG);
    }

    private void stylingGame() {
        // Set the width of the Canvas
        canvas.setWidth(500);
        // Set the height of the Canvas
        canvas.setHeight(450);
        canvas.setStyle("-fx-padding: 20");
        String style= Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
        gameScene.getStylesheets().add(style);
        graphic.drawImage(backGroundIMG,0,0,500,450);
    }

    private HBox makeDownBar() {
        for(int row=0;row<6;row++){
            graphicHand1.drawImage(DefaultCardInHandImg, 120 * row,10, 200, 70);
        }

        HBox hBoxDown = new HBox();
        hBoxDown.getChildren().addAll(canvasHandBar1);
        hBoxDown.setAlignment(Pos.CENTER);
        return hBoxDown;
    }

    private VBox makeLeftBar(Duelist duelist1, Duelist duelist2) {
        refreshHealthBar(duelist1, duelist2);


        ImageView imgDuelist1 = new ImageView("file:"+ duelist1.getUser().getUsername()+".png");
        imgDuelist1.setFitHeight(100);
        imgDuelist1.setFitWidth(100);
        ImageView imgDuelist2 = new ImageView("file:"+ duelist2.getUser().getUsername()+".png");
        imgDuelist2.setFitHeight(100);
        imgDuelist2.setFitWidth(100);

        VBox vbox1 = new VBox();
        Label lblDuelist1Name = new Label(duelist1.getUser().getNickName());
        lblDuelist1Name.setAlignment(Pos.CENTER);
        Label lblDuelist1LP = new Label(" "+ duelist1.LP);
        lblDuelist1LP.setAlignment(Pos.CENTER);

        VBox hBoxUserDetail1 = new VBox();
        hBoxUserDetail1.getChildren().addAll(lblDuelist1Name,lblDuelist1LP);
        VBox vboxUserDetail1 = new VBox();
        vboxUserDetail1.setSpacing(30);
        vboxUserDetail1.getChildren().addAll(hBoxUserDetail1,canvasHealthBar1);

        vbox1.getChildren().addAll(imgDuelist1,vboxUserDetail1);
        vbox1.setSpacing(20);

        VBox vbox2 = new VBox();
        Label lblDuelist2Name = new Label(duelist2.getUser().getNickName());
        lblDuelist2Name.setAlignment(Pos.CENTER);
        Label lblDuelist2LP = new Label(" "+ duelist2.LP);
        lblDuelist2LP.setAlignment(Pos.CENTER);

        VBox hBoxUserDetail2 = new VBox();
        hBoxUserDetail2.getChildren().addAll(lblDuelist2Name,lblDuelist2LP);
        VBox vboxUserDetail2 = new VBox();
        vboxUserDetail2.setSpacing(30);
        vboxUserDetail2.getChildren().addAll(hBoxUserDetail2,canvasHealthBar2);

        vbox2.getChildren().addAll(vboxUserDetail2,imgDuelist2);
        vbox2.setSpacing(20);
        VBox hBoxTop = new VBox();
        VBox vboxTop = new VBox();
        hBoxTop.getChildren().addAll(vbox1,vbox2);
        hBoxTop.setSpacing(100);
        vboxTop.getChildren().addAll(hBoxTop);
        hBoxTop.setAlignment(Pos.CENTER);
        //top menu ready
        return vboxTop;
//        Circle cMain = new Circle(150, 45, 25);
//        cMain.setFill(new ImagePattern(mainPhaseIMG));
//        Circle cEnd = new Circle(150, 105, 25);
//        cEnd.setFill(new ImagePattern(endPhaseIMG));
//        Circle cDraw = new Circle(150, 165, 25);
//        cDraw.setFill(new ImagePattern(drawPhaseIMG));
//        Circle cBattle = new Circle(150, 225, 25);
//        cBattle.setFill(new ImagePattern(battlePhaseIMG));
//        Circle cDuel = new Circle(150, 285, 25);
//        cDuel.setFill(new ImagePattern(duelPhaseIMG));
//        Circle cStandby = new Circle(150, 345, 25);
//        cStandby.setFill(new ImagePattern(standbyPhaseIMG));
//
//
//        Button btnExit = new Button("Exit!");
//        Button btnNextPhase = new Button("next Phase!");
//        Button btnMuteSounds = new Button("mute sounds");
//        btnNextPhase.setOnMouseClicked(e->{
//
//        });
//        VBox vBoxLeft = new VBox();
//        vBoxLeft.setAlignment(Pos.CENTER);
//        vBoxLeft.getChildren().addAll(btnNextPhase,btnMuteSounds,btnExit);
//        return vBoxLeft;
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


        imgDuelist1 = new ImageView("file:"+ duelist1.getUser().getUsername()+".png");
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
        hBoxTop.getChildren().addAll(hbox1,hbox2);
        hBoxTop.setSpacing(100);
        vboxTop.getChildren().addAll(hBoxTop);
        hBoxTop.setAlignment(Pos.CENTER);
        //top menu ready
        return vboxTop;
    }

    public void refreshHealthBar(Duelist duelist1, Duelist duelist2) {
        float percentage1 =((float) duelist1.LP/80)/100;
        float percentage2 =((float) duelist2.LP/80)/100;
        graphic1.setFill(Color.RED);
        graphic1.fillRect(0,0,100*percentage1,20);
        graphic2.setFill(Color.RED);
        graphic2.fillRect(0,0,100*percentage2,20);
    }

    public GraphicsContext getGraphicHand1() {
        return graphicHand1;
    }

    public GraphicsContext getMainGraphic() {
        return graphic;
    }

    public Image getBackGroundIMG() {
        return backGroundIMG;
    }

    public Scene getGameScene() {
        return gameScene;
    }
}
