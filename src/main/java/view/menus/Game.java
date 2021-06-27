package view.menus;

import controllers.Battelfield.Battlefield;
import controllers.Battelfield.ImageAdapter;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import models.Card;
import models.CardStufs.FaceUp;
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
import models.CardStufs.Type;
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

    public Canvas canvas = new Canvas(500, 450);
    GraphicsContext graphic = canvas.getGraphicsContext2D();
    private Scene gameScene;

    Canvas canvasHealthBar1 = new Canvas(100,15);
    GraphicsContext graphic1 = canvasHealthBar1.getGraphicsContext2D();
    Canvas canvasHealthBar2 = new Canvas(100,15);
    GraphicsContext graphic2 = canvasHealthBar2.getGraphicsContext2D();

    HBox handTurn;
    HBox handOpponent;
    VBox vBoxLeft;

    Battlefield battlefield;

    public Game(Battlefield battlefield){
        this.battlefield = battlefield;
    }

    public void addChanges(){
        mouseEventClick();
//        mouseEventInitialize();

        graphic.clearRect(0, 0, 500, 450);
        graphic.drawImage(backGroundIMG,0,0,500,450);
        initGraveYardAndFieldZone();

        //building rivals spell and trap zone
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.spellTrapZone.get(i) != null && battlefield.getOpponent().field.spellTrapZone.get(i).getCardsFace() == FaceUp.ATTACK) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" +
                        battlefield.getOpponent().field.spellTrapZone.get(i).getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
                if (i == 0) ImageAdapter.setSpellOrTrapOn5Rival(graphic, image);
                if (i == 1) ImageAdapter.setSpellOrTrapOn3Rival(graphic, image);
                if (i == 2) ImageAdapter.setSpellOrTrapOn1Rival(graphic, image);
                if (i == 3) ImageAdapter.setSpellOrTrapOn2Rival(graphic, image);
                if (i == 4) ImageAdapter.setSpellOrTrapOn4Rival(graphic, image);
            }
            else if (battlefield.getOpponent().field.spellTrapZone.get(i) != null && battlefield.getOpponent().field.spellTrapZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) {
                Image image = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 275, 275, false, false);
                if (i == 0) ImageAdapter.setSpellOrTrapOn5Rival(graphic, image);
                if (i == 1) ImageAdapter.setSpellOrTrapOn3Rival(graphic, image);
                if (i == 2) ImageAdapter.setSpellOrTrapOn1Rival(graphic, image);
                if (i == 3) ImageAdapter.setSpellOrTrapOn2Rival(graphic, image);
                if (i == 4) ImageAdapter.setSpellOrTrapOn4Rival(graphic, image);
            }
        }

        //building rivals monster zone
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null && battlefield.getOpponent().field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" +
                        battlefield.getOpponent().field.monsterZone.get(i).getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
                if (i == 0) ImageAdapter.setMonsterOn5Rival(graphic, image);
                if (i == 1) ImageAdapter.setMonsterOn3Rival(graphic, image);
                if (i == 2) ImageAdapter.setMonsterOn1Rival(graphic, image);
                if (i == 3) ImageAdapter.setMonsterOn2Rival(graphic, image);
                if (i == 4) ImageAdapter.setMonsterOn4Rival(graphic, image);
            }
            else if (battlefield.getOpponent().field.monsterZone.get(i) != null && battlefield.getOpponent().field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) {
                Image image = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 275, 275, false, false);
                //
                ImageView iv = new ImageView(image);
                iv.setRotate(90);

                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);

                Image rotatedImage = iv.snapshot(params, null);
                //
                if (i == 0) ImageAdapter.setMonsterOn5Rival(graphic, rotatedImage);
                if (i == 1) ImageAdapter.setMonsterOn3Rival(graphic, rotatedImage);
                if (i == 2) ImageAdapter.setMonsterOn1Rival(graphic, rotatedImage);
                if (i == 3) ImageAdapter.setMonsterOn2Rival(graphic, rotatedImage);
                if (i == 4) ImageAdapter.setMonsterOn4Rival(graphic, rotatedImage);
            }
            else if (battlefield.getOpponent().field.monsterZone.get(i) != null && battlefield.getOpponent().field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" +
                        battlefield.getOpponent().field.monsterZone.get(i).getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
                ImageView iv = new ImageView(image);
                iv.setRotate(90);

                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);

                Image rotatedImage = iv.snapshot(params, null);
                if (i == 0) ImageAdapter.setMonsterOn5Rival(graphic, rotatedImage);
                if (i == 1) ImageAdapter.setMonsterOn3Rival(graphic, rotatedImage);
                if (i == 2) ImageAdapter.setMonsterOn1Rival(graphic, rotatedImage);
                if (i == 3) ImageAdapter.setMonsterOn2Rival(graphic, rotatedImage);
                if (i == 4) ImageAdapter.setMonsterOn4Rival(graphic, rotatedImage);
            }
        }

        //building rivals graveyard
        if (battlefield.getOpponent().field.graveYard.size() > 0){
            Image image = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 275, 275, false, false);
            ImageAdapter.setCardOnOpponentGraveYard(graphic, image);
        }

        //building rivals field zone spell
        if (battlefield.getOpponent().field.fieldZone != null){
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" +
                    battlefield.getOpponent().field.fieldZone.getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
            ImageAdapter.setCardOnOpponentFieldZone(graphic, image);
        }

        //building turn field zone spell
        if (battlefield.getTurn().field.fieldZone != null){
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" +
                    battlefield.getTurn().field.fieldZone.getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
            ImageAdapter.setCardOnTurnFieldZone(graphic, image);
        }

        //building turn graveyard
        if (battlefield.getTurn().field.graveYard.size() > 0){
            Image image = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 275, 275, false, false);
            ImageAdapter.setCardOnTurnGraveYard(graphic, image);
        }

        //building turn monster zone
        for (int i = 0; i<5; ++i){
            if (battlefield.getTurn().field.monsterZone.get(i) != null && battlefield.getTurn().field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" +
                        battlefield.getTurn().field.monsterZone.get(i).getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
                if (i == 0) ImageAdapter.setMonsterOn5(graphic, image);
                if (i == 1) ImageAdapter.setMonsterOn3(graphic, image);
                if (i == 2) ImageAdapter.setMonsterOn1(graphic, image);
                if (i == 3) ImageAdapter.setMonsterOn2(graphic, image);
                if (i == 4) ImageAdapter.setMonsterOn4(graphic, image);
            }
            else if (battlefield.getTurn().field.monsterZone.get(i) != null && battlefield.getTurn().field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) {
                Image image = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 275, 275, false, false);
                ImageView iv = new ImageView(image);
                iv.setRotate(90);

                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);

                Image rotatedImage = iv.snapshot(params, null);
                if (i == 0) ImageAdapter.setMonsterOn5(graphic, rotatedImage);
                if (i == 1) ImageAdapter.setMonsterOn3(graphic, rotatedImage);
                if (i == 2) ImageAdapter.setMonsterOn1(graphic, rotatedImage);
                if (i == 3) ImageAdapter.setMonsterOn2(graphic, rotatedImage);
                if (i == 4) ImageAdapter.setMonsterOn4(graphic, rotatedImage);
            }
            else if (battlefield.getTurn().field.monsterZone.get(i) != null && battlefield.getTurn().field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" +
                        battlefield.getTurn().field.monsterZone.get(i).getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
                ImageView iv = new ImageView(image);
                iv.setRotate(90);

                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);

                Image rotatedImage = iv.snapshot(params, null);
                if (i == 0) ImageAdapter.setMonsterOn5(graphic, rotatedImage);
                if (i == 1) ImageAdapter.setMonsterOn3(graphic, rotatedImage);
                if (i == 2) ImageAdapter.setMonsterOn1(graphic, rotatedImage);
                if (i == 3) ImageAdapter.setMonsterOn2(graphic, rotatedImage);
                if (i == 4) ImageAdapter.setMonsterOn4(graphic, rotatedImage);
            }
        }

        //building turn spell zone
        for (int i = 0; i<5; ++i){
            if (battlefield.getTurn().field.spellTrapZone.get(i) != null && battlefield.getTurn().field.spellTrapZone.get(i).getCardsFace() == FaceUp.ATTACK) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" +
                        battlefield.getTurn().field.spellTrapZone.get(i).getName() + ".jpg")).toExternalForm(), 275, 275, false, false);
                if (i == 0) ImageAdapter.setSpellOrTrapOn5(graphic, image);
                if (i == 1) ImageAdapter.setSpellOrTrapOn3(graphic, image);
                if (i == 2) ImageAdapter.setSpellOrTrapOn1(graphic, image);
                if (i == 3) ImageAdapter.setSpellOrTrapOn2(graphic, image);
                if (i == 4) ImageAdapter.setSpellOrTrapOn4(graphic, image);
            }
            else if (battlefield.getTurn().field.spellTrapZone.get(i) != null && battlefield.getTurn().field.spellTrapZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) {
                Image image = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 275, 275, false, false);
                if (i == 0) ImageAdapter.setSpellOrTrapOn5(graphic, image);
                if (i == 1) ImageAdapter.setSpellOrTrapOn3(graphic, image);
                if (i == 2) ImageAdapter.setSpellOrTrapOn1(graphic, image);
                if (i == 3) ImageAdapter.setSpellOrTrapOn2(graphic, image);
                if (i == 4) ImageAdapter.setSpellOrTrapOn4(graphic, image);
            }
        }

        //update left
        vBoxLeft = new VBox();
        vBoxLeft = makeLeftBar(battlefield.getTurn(), battlefield.getOpponent());

        //update Turn hand
        handTurn = new HBox();
        for (int i = 0; i < battlefield.getTurn().field.hand.size(); i++) {
            Card card = battlefield.getTurn().field.hand.get(i);
            Image image2;
            ImageView img;
            System.out.println(card.getName());
            if (card.getCardsType().equals(Type.MONSTER))
                image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + card.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);
            else
                image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + card.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);

            img = new ImageView(image2);
            img.setOnMouseClicked(event -> {
                UserInterface.printResponse("Set Or Summon");
                String num = UserInterface.getUserInput();
                if (num.equals("Set")) {
                    battlefield.selectedCard = card;
                    battlefield.set();
                    addChanges();
                } else if (num.equals("See")) {
                    battlefield.selectedCard = card;
                    addChanges();
                } else {
                    battlefield.selectedCard = card;
                    battlefield.summon();
                    addChanges();
                }
            });

            handTurn.getChildren().add(img);
            handTurn.setAlignment(Pos.CENTER);
        }

        //update Opponent hand
        handOpponent = new HBox();
        for (int i = 0; i < battlefield.getOpponent().field.hand.size(); i++) {
            Image image2 = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 50, 100, false, false);

            ImageView img = new ImageView(image2);

            handOpponent.getChildren().add(img);
            handOpponent.setAlignment(Pos.CENTER);
        }

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(makeRightBar());
        root.setLeft(vBoxLeft);
        root.setTop(handOpponent);
        root.setBottom(handTurn);

        gameScene = new Scene(root ,900 ,660);

        stylingGame();

        Main.stage.setScene(gameScene);
    }

    public void mouseEventClick (){
        canvas.setOnMouseClicked(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println(x);
            System.out.println(y);
            if (x >= 309.0 && x <= 375 && y >= 345 && y <= 421)
                battlefield.attackGui(0);
            else if (x >= 377.0 && x <= 442 && y >= 345 && y <= 421)
                battlefield.attackGui(1);
            else if (x >= 444.0 && x <= 511 && y >= 345 && y <= 421)
                battlefield.attackGui(2);
            else if (x >= 513.0 && x <= 580 && y >= 345 && y <= 421)
                battlefield.attackGui(3);
            else if (x >= 582.0 && x <= 646 && y >= 345 && y <= 421)
                battlefield.attackGui(4);
        });
    }

    private void mouseEventInitialize() {
        canvas.setOnMouseClicked(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            //first place
            if(x<374 && x>315 && y<415 && y>351)
                battlefield.selectedCard = battlefield.getTurn().field.monsterZone.get(0);

            else if(x<442 && x>380 && y<414 && y>352)
                battlefield.selectedCard = battlefield.getTurn().field.monsterZone.get(1);

            else if(x<508 && x>449 && y<413 && y>351)
                battlefield.selectedCard = battlefield.getTurn().field.monsterZone.get(2);

            else if(x<579 && x>519 && y<415 && y>351)
                battlefield.selectedCard = battlefield.getTurn().field.monsterZone.get(3);

            else if(x<645 && x>588 && y<413 && y>352)
                battlefield.selectedCard = battlefield.getTurn().field.monsterZone.get(4);

            //spell and trap
            else if(x<374 && x>316 && y<490 && y>427)
                battlefield.selectedCard = battlefield.getTurn().field.spellTrapZone.get(0);

            else if(x<441 && x>380 && y<490 && y>428)
                battlefield.selectedCard = battlefield.getTurn().field.spellTrapZone.get(1);

            else if(x<510 && x>450 && y<491 && y>429)
                battlefield.selectedCard = battlefield.getTurn().field.spellTrapZone.get(2);

            else if(x<576 && x>520 && y<489 && y>429)
                battlefield.selectedCard = battlefield.getTurn().field.spellTrapZone.get(3);

            else if(x<646 && x>586 && y<491 && y>426)
                battlefield.selectedCard = battlefield.getTurn().field.spellTrapZone.get(4);

            if(battlefield.selectedCard != null)
                UserInterface.printResponse("your selected card is"+battlefield.selectedCard.getName());

            //turn fieldZone
            else if(x<289 && x>249 && y<403 && y>348){

            }
            //turn GraveYard
            else if(x<702 && x>660 && y<421 && y>361){

            }
            //opponent fieldZone
            else if(x<699 && x>662 && y<316 && y>256){

            }
            //opponent GraveYard
            else if(x<301 && x>256 && y<317 && y>250){

            }

        });
    }

    public void initGraveYardAndFieldZone() {
        ImageAdapter.setCardOnOpponentGraveYard(graphic,graveYardIMG);
        ImageAdapter.setCardOnTurnGraveYard(graphic,graveYardIMG);
        ImageAdapter.setCardOnOpponentFieldZone(graphic,fieldIMG);
        ImageAdapter.setCardOnTurnFieldZone(graphic,fieldIMG);
    }

    private void stylingGame() {
        canvas.setStyle("-fx-padding: 20");
        String style= Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
        gameScene.getStylesheets().add(style);
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
        Label lblDuelist1LP = new Label("LP : " + duelist1.LP);
        lblDuelist1LP.setAlignment(Pos.CENTER);

        vbox1.getChildren().addAll(imgDuelist1,lblDuelist1Name,lblDuelist1LP,canvasHealthBar1);

        Image imageDeck ;
        if(battlefield.selectedCard == null) imageDeck = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 50, 100, false, false);
        else {
            System.out.println("Seleceese : " + battlefield.selectedCard.getName());
            if (battlefield.selectedCard.getCardsType().equals(Type.MONSTER)) {
                imageDeck = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + battlefield.selectedCard.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);
            } else
                imageDeck = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + battlefield.selectedCard.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);
        }
        ImageView imgDeck = new ImageView((imageDeck));
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
        btnExit.setOnMouseClicked(e -> battlefield.winner = battlefield.getOpponent() );
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
}
