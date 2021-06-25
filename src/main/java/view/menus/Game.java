package view.menus;

import controllers.Battelfield.Battlefield;
import controllers.Battelfield.ImageAdapter;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Duelist;
import view.Main;
import view.UserInterface;

import java.util.Objects;

public class Game {

    final String backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_normal.bmp")).toExternalForm();
    final String mainPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/Main.png")).toExternalForm();
    final String endPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/EndPhase.png")).toExternalForm();
    final String drawPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/DrawPhase.png")).toExternalForm();
    final String battlePhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/BattlePhase.png")).toExternalForm();
    final String duelPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/DuelPhase.png")).toExternalForm();
    final String standbyPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/Standby.png")).toExternalForm();
    final String graveYardPath = Objects.requireNonNull(this.getClass().getResource("elements/graveYard.png")).toExternalForm();
    final String fieldPath = Objects.requireNonNull(this.getClass().getResource("elements/field.png")).toExternalForm();

    Image backGroundIMG = new Image(backGroundPath);
    Image mainPhaseIMG = new Image(mainPhasePath);
    Image endPhaseIMG = new Image(endPhasePath);
    Image drawPhaseIMG = new Image(drawPhasePath);
    Image battlePhaseIMG = new Image(battlePhasePath);
    Image duelPhaseIMG = new Image(duelPhasePath);
    Image standbyPhaseIMG = new Image(standbyPhasePath);
    Image graveYardIMG = new Image(graveYardPath);
    Image fieldIMG = new Image(fieldPath);

    public Image selectedCard;

    public Canvas canvas= new Canvas(400, 400);
    GraphicsContext graphic = canvas.getGraphicsContext2D();

    private Scene gameScene;
    private final int defaultSize = 20;
    Canvas canvasHealthBar1 = new Canvas(100,15);
    Canvas canvasHealthBar2 = new Canvas(100,15);

    public HBox handTurn = new HBox();
    public HBox handOpponent = new HBox();
    GraphicsContext graphic1 = canvasHealthBar1.getGraphicsContext2D();
    GraphicsContext graphic2 = canvasHealthBar2.getGraphicsContext2D();

    BorderPane root = new BorderPane();
    StackPane base = new StackPane();
    Duelist turn, opponent;
    Battlefield battlefield;

    public Game(Battlefield battlefield, Duelist turn, Duelist opponent){
        this.turn = turn;
        this.opponent = opponent;
        this.battlefield = battlefield;
    }

    public void runGame(){
        mouseEventInitialize();

        //Default player is duelist1
        //Controller is Battlefield

        base.getChildren().add(canvas);
        root.setCenter(base);
        //make left buttons
        VBox vBoxLeft = makeLeftBar(turn, opponent);

        //make right buttons
        VBox vBoxRight = makeRightBar();

        //make up top things
        handOpponent.setAlignment(Pos.CENTER);

        //init graveYard
        initGraveYardAndFieldZone();

        //make down things (hand in here)
        handTurn.setAlignment(Pos.CENTER);

        root.setRight(vBoxRight);
        root.setLeft(vBoxLeft);
        root.setTop(handOpponent);
        root.setBottom(handTurn);

        gameScene = new Scene(root ,900 ,660);

        stylingGame();

        Main.stage.setScene(gameScene);
    }

    private void mouseEventInitialize() {
        canvas.setOnMouseClicked(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            //first place
            if(x<374 && x>315 && y<415 && y>351)
                battlefield.selectedCard = turn.field.monsterZone.get(0);

            else if(x<442 && x>380 && y<414 && y>352)
                battlefield.selectedCard = turn.field.monsterZone.get(1);

            else if(x<508 && x>449 && y<413 && y>351)
                battlefield.selectedCard = turn.field.monsterZone.get(2);

            else if(x<579 && x>519 && y<415 && y>351)
                battlefield.selectedCard = turn.field.monsterZone.get(3);

            else if(x<645 && x>588 && y<413 && y>352)
                battlefield.selectedCard = turn.field.monsterZone.get(4);

            //spell and trap
            else if(x<374 && x>316 && y<490 && y>427)
                battlefield.selectedCard = turn.field.spellTrapZone.get(0);

            else if(x<441 && x>380 && y<490 && y>428)
                battlefield.selectedCard = turn.field.spellTrapZone.get(1);

            else if(x<510 && x>450 && y<491 && y>429)
                battlefield.selectedCard = turn.field.spellTrapZone.get(2);

            else if(x<576 && x>520 && y<489 && y>429)
                battlefield.selectedCard = turn.field.spellTrapZone.get(3);

            else if(x<646 && x>586 && y<491 && y>426)
                battlefield.selectedCard = turn.field.spellTrapZone.get(4);

            if(battlefield.selectedCard != null)
                UserInterface.printResponse("your selected card is"+battlefield.selectedCard.getName());

//            //turn fieldZone
//            else if(x<289 && x>249 && y<403 && y>348){
//
//            }
//            //turn GraveYard
//            else if(x<702 && x>660 && y<421 && y>361){
//
//            }
//            //opponent fieldZone
//            else if(x<699 && x>662 && y<316 && y>256){
//
//            }
//            //opponent GraveYard
//            else if(x<301 && x>256 && y<317 && y>250){
//
//            }

        });
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

    private VBox makeLeftBar(Duelist duelist1, Duelist duelist2) {
        refreshHealthBar(duelist1, duelist2);

        ImageView imgDuelist1 = new ImageView("file:"+ duelist1.getUser().getUsername()+".png");
        imgDuelist1.setFitHeight(100);
        imgDuelist1.setFitWidth(100);
        ImageView imgDuelist2 = new ImageView("file:"+ duelist2.getUser().getUsername()+".png");
        imgDuelist2.setFitHeight(100);
        imgDuelist2.setFitWidth(100);

        VBox vbox1 = new VBox();
        Label lblDuelist1Name = new Label("Nickname : " + duelist1.getUser().getNickName());
        lblDuelist1Name.setAlignment(Pos.CENTER);
        Label lblDuelist1LP = new Label("LP : "+ duelist1.LP);
        lblDuelist1LP.setAlignment(Pos.CENTER);

        vbox1.getChildren().addAll(imgDuelist1,lblDuelist1Name,lblDuelist1LP,canvasHealthBar1);

        String imageDeck = Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm();
        selectedCard = new Image(imageDeck);
        ImageView imgDeck = new ImageView(selectedCard);
        imgDeck.setFitHeight(150);
        imgDeck.setFitWidth(100);
        VBox vBoxRight = new VBox();
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxRight.setSpacing(4);
        vBoxRight.getChildren().addAll(imgDeck);

        VBox vbox2 = new VBox();
        Label lblDuelist2Name = new Label("Nickname : " + duelist2.getUser().getNickName());
        lblDuelist2Name.setAlignment(Pos.CENTER);
        Label lblDuelist2LP = new Label("LP : " + duelist2.LP);
        lblDuelist2LP.setAlignment(Pos.CENTER);

        vbox2.getChildren().addAll(canvasHealthBar2, lblDuelist2LP, lblDuelist2Name, imgDuelist2);

        VBox vboxLeft = new VBox();
        vboxLeft.getChildren().addAll(vbox1, vBoxRight, vbox2);

        return vboxLeft;
    }

    private VBox makeRightBar() {
        Circle cMain = new Circle(150, 45, 25);
        cMain.setFill(new ImagePattern(mainPhaseIMG));
        Circle cEnd = new Circle(150, 105, 25);
        cEnd.setFill(new ImagePattern(endPhaseIMG));
        Circle cDraw = new Circle(150, 165, 25);
        cDraw.setFill(new ImagePattern(drawPhaseIMG));
        Circle cBattle = new Circle(150, 225, 25);
        cBattle.setFill(new ImagePattern(battlePhaseIMG));
        Circle cDuel = new Circle(150, 285, 25);
        cDuel.setFill(new ImagePattern(duelPhaseIMG));
        Circle cStandby = new Circle(150, 345, 25);
        cStandby.setFill(new ImagePattern(standbyPhaseIMG));


        Button btnExit = new Button("Surrender");
        btnExit.setOnMouseClicked(e -> battlefield.winner = opponent );
        Button btnNextPhase = new Button("next Phase!");
        btnNextPhase.setOnMouseClicked(e-> battlefield.nextPhase());
        Button btnMuteSounds = new Button("mute sounds");

        VBox vBoxLeft = new VBox();
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxLeft.getChildren().addAll(btnNextPhase,btnMuteSounds,btnExit);
        vBoxLeft.setSpacing(10);
        return vBoxLeft;
    }

    public void refreshHealthBar(Duelist duelist1, Duelist duelist2) {
        float percentage1 =((float) duelist1.LP/80)/100;
        float percentage2 =((float) duelist2.LP/80)/100;
        graphic1.setFill(Color.RED);
        graphic1.fillRect(0,0,100*percentage1,20);
        graphic2.setFill(Color.RED);
        graphic2.fillRect(0,0,100*percentage2,20);
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
