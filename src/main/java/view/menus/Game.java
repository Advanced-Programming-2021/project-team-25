package view.menus;

import controllers.Battelfield.Battlefield;
import controllers.Battelfield.ImageAdapter;
import controllers.Database.DataBase;
import controllers.ProgramController;
import controllers.Regex;
import controllers.menues.DuelMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Card;
import models.CardStufs.FaceUp;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import models.User;
import view.Main;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Game {

    String backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_normal.bmp")).toExternalForm();
    final String mainPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/Main.png")).toExternalForm();
    final String endPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/EndPhase.png")).toExternalForm();
    final String drawPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/DrawPhase.png")).toExternalForm();
    final String battlePhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/BattlePhase.png")).toExternalForm();
    final String duelPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/DuelPhase.png")).toExternalForm();
    final String standbyPhasePath = Objects.requireNonNull(this.getClass().getResource("game/phases/Standby.png")).toExternalForm();
    final String graveYardPath = Objects.requireNonNull(this.getClass().getResource("elements/graveYard.png")).toExternalForm();

    final String fieldPath = Objects.requireNonNull(this.getClass().getResource("elements/field.png")).toExternalForm();
    final String dragDropPath = Objects.requireNonNull(this.getClass().getResource("img.png")).toExternalForm();

    Image backGroundIMG = new Image(backGroundPath);
    Image mainPhaseIMG = new Image(mainPhasePath,200,100,false,false);
    Image endPhaseIMG = new Image(endPhasePath);
    Image drawPhaseIMG = new Image(drawPhasePath);
    Image battlePhaseIMG = new Image(battlePhasePath);
    Image duelPhaseIMG = new Image(duelPhasePath);
    Image standbyPhaseIMG = new Image(standbyPhasePath);
    Image graveYardIMG = new Image(graveYardPath);
    Image fieldIMG = new Image(fieldPath);
    Image dragDropImg = new Image(dragDropPath);
    AudioClip audioClip;

    public String stringForCheat = "";

    public Canvas canvas = new Canvas(500, 450);
    GraphicsContext graphic = canvas.getGraphicsContext2D();
    private Scene gameScene;
    private Card dragCard = null;
    public int dragPosition = -1;
    Canvas canvasHealthBar1 = new Canvas(100,15);
    GraphicsContext graphic1 = canvasHealthBar1.getGraphicsContext2D();
    Canvas canvasHealthBar2 = new Canvas(100,15);
    GraphicsContext graphic2 = canvasHealthBar2.getGraphicsContext2D();
    BorderPane root = new BorderPane();
    private Stage currStage;
    Battlefield battlefield;
    public Game(Battlefield battlefield){
        this.battlefield = battlefield;
        audioClip = new AudioClip(getClass().getResource("/music/game.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
        audioClip.play();
    }

    public void addChanges(){

        keyPressed();

        mouseEventClick();

        setFieldZoneBackground();

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
        VBox vBoxLeft = makeLeftBar(battlefield.getTurn(), battlefield.getOpponent());

        //update Turn hand
        HBox handTurn = new HBox();
        for (int i = 0; i < battlefield.getTurn().field.hand.size(); i++) {
            Card card = battlefield.getTurn().field.hand.get(i);
            Image image2;
            ImageView img;
            if (card.getCardsType().equals(Type.MONSTER))
                image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + card.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);
            else
                image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + card.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);

            img = new ImageView(image2);
            img.setOnDragDone(event -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (card.getCardsType() == Type.MONSTER) {
                    BorderPane borderPane = new BorderPane();
                    VBox vBox1 = new VBox();
                    Label label2 = new Label("Set Or Summon");
                    label2.setTextFill(Color.web("black"));
                    label2.setFont(Font.font(20));
                    Button summonBtn = new Button("Summon");
                    summonBtn.setOnAction(actionEvent -> {
                        battlefield.tributeBtn.setDisable(false);
                        AudioClip audioClip = new AudioClip(getClass().getResource("/music/SUMMON&SET.mp3").toExternalForm());
                        audioClip.play();
                        battlefield.selectedCard = card;
                        if (dragPosition != -1) battlefield.summon(dragPosition);
                        addChanges();
                        currStage.close();
                    });
                    Button setBtn = new Button("Set");
                    setBtn.setOnAction(actionEvent -> {
                        AudioClip audioClip = new AudioClip(getClass().getResource("/music/SUMMON&SET.mp3").toExternalForm());
                        audioClip.play();
                        battlefield.selectedCard = card;
                        if (dragPosition != -1) battlefield.set(dragPosition);
                        addChanges();
                        currStage.close();
                    });
                    Button backBtn = new Button("Back");
                    backBtn.setOnMouseClicked(e -> currStage.close());

                    Label textField = new Label("Place of your card : " + dragPosition);
                    vBox1.getChildren().addAll(label2, setBtn, summonBtn, textField);
                    borderPane.setLeft(vBox1);
                    borderPane.setBottom(backBtn);
                    Image imageOfCard = new Image(img.getImage().getUrl(), 200, 200, false, false);
                    borderPane.setRight(new ImageView(imageOfCard));
                    Scene scene = new Scene(borderPane, 500, 400);
                    String style = Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
                    scene.getStylesheets().add(style);
                    subStage stg = new subStage("Set/Summon", scene);
                    currStage = stg.getStage();
                }
                else{
                    BorderPane borderPane = new BorderPane();
                    VBox vBox1 = new VBox();
                    Label label2 = new Label("Set Or Active");
                    label2.setTextFill(Color.web("black"));
                    label2.setFont(Font.font(20));
                    Button summonBtn = new Button("Active");
                    summonBtn.setOnAction(actionEvent -> {
                        battlefield.selectedCard = card;
                        card.setCardsFace(FaceUp.ATTACK);
                        if (dragPosition != -1) battlefield.activeSpell("firstTime");
                        addChanges();
                        currStage.close();
                    });
                    Button setBtn = new Button("Set");
                    setBtn.setOnAction(actionEvent -> {
                        AudioClip audioClip = new AudioClip(getClass().getResource("/music/SUMMON&SET.mp3").toExternalForm());
                        audioClip.play();
                        battlefield.selectedCard = card;
                        if (dragPosition != -1) {
                            System.out.println("heheheheheheheheh");
                            battlefield.set(dragPosition);
                        }
                        addChanges();
                        currStage.close();
                    });
                    Button backBtn = new Button("Back");
                    backBtn.setOnMouseClicked(e -> currStage.close());

                    Label textField = new Label("Place of your card : " + dragPosition);
                    vBox1.getChildren().addAll(label2, setBtn, summonBtn, textField);
                    borderPane.setLeft(vBox1);
                    borderPane.setBottom(backBtn);
                    Image imageOfCard = new Image(img.getImage().getUrl(), 200, 200, false, false);
                    borderPane.setRight(new ImageView(imageOfCard));
                    Scene scene = new Scene(borderPane, 500, 400);
                    String style = Objects.requireNonNull(this.getClass().getResource("game/game.css")).toExternalForm();
                    scene.getStylesheets().add(style);
                    subStage stg = new subStage("Set/Active", scene);
                    currStage = stg.getStage();
                }
            });
            img.setOnDragDetected(e->{
                System.out.println("image on drag");
                Dragboard db = img.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(img.getImage());
                db.setContent(content);
            });

            handTurn.getChildren().add(img);
            handTurn.setAlignment(Pos.CENTER);
        }

        //update Opponent hand
        HBox handOpponent = new HBox();
        for (int i = 0; i < battlefield.getOpponent().field.hand.size(); i++) {
            Image image2 = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm(), 50, 100, false, false);

            ImageView img = new ImageView(image2);

            handOpponent.getChildren().add(img);
            handOpponent.setAlignment(Pos.CENTER);
        }

        root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(makeRightBar());
        root.setLeft(vBoxLeft);
        root.setTop(handOpponent);
        root.setBottom(handTurn);

        gameScene = new Scene(root ,900 ,660);

        stylingGame();

        Main.stage.setScene(gameScene);
        checkEnd();
    }

    private void setFieldZoneBackground() {
        if(battlefield.opponent.field.fieldZone != null){
            switch (battlefield.opponent.field.fieldZone.getName()) {
                case "Yami" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_yami.bmp")).toExternalForm();
                case "Forest" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_mori.bmp")).toExternalForm();
                case "Closed Forest" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_sougen.bmp")).toExternalForm();
                case "Umiiruka" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_umi.bmp")).toExternalForm();
                case "Sword of dark destruction" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_yama.bmp")).toExternalForm();
            }
            backGroundIMG = new Image(backGroundPath);
        }

        if(battlefield.turn.field.fieldZone != null){
            switch (battlefield.turn.field.fieldZone.getName()) {
                case "Yami" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_yami.bmp")).toExternalForm();
                case "Forest" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_mori.bmp")).toExternalForm();
                case "Closed Forest" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_sougen.bmp")).toExternalForm();
                case "Umiiruka" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_umi.bmp")).toExternalForm();
                case "Sword of dark destruction" -> backGroundPath = Objects.requireNonNull(this.getClass().getResource("field/fie_yama.bmp")).toExternalForm();
            }
            backGroundIMG = new Image(backGroundPath);
        }
    }

    private void checkEnd() {
        if(battlefield.turn.LP <= 0){
            battlefield.winner = battlefield.opponent;
            DuelMenuController duelMenuController =  DuelMenuController.getInstance(ProgramController.currUser);
            if(battlefield.isOneRound) {
                duelMenuController.finishround1(battlefield.opponent, battlefield.turn, battlefield);
                DataBase.saveTheUserList(User.getUsers());
                audioClip.stop();
                Main.audioClip.play();
                DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
            }
            else {
                if(battlefield.round == 1) duelMenuController.finishRound1(battlefield.turn,battlefield.opponent,battlefield);
                else if(battlefield.round == 2) duelMenuController.finishRound2(battlefield.turn,battlefield.opponent,battlefield);
                else if(battlefield.round == 3){
                    duelMenuController.finishRound3(battlefield.turn,battlefield.opponent,battlefield);
                    duelMenuController.matchFinish(battlefield.turn,battlefield.opponent);
                    DataBase.saveTheUserList(User.getUsers());
                    audioClip.stop();
                    Main.audioClip.play();
                    DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
                }
            }
        }
        if(battlefield.opponent.LP <= 0){
            battlefield.winner = battlefield.turn;
            DuelMenuController duelMenuController =  DuelMenuController.getInstance(ProgramController.currUser);
            if(battlefield.isOneRound) {
                duelMenuController.finishround1(battlefield.opponent, battlefield.turn, battlefield);
                DataBase.saveTheUserList(User.getUsers());
                audioClip.stop();
                Main.audioClip.play();
                DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
            }
            else {
                if(battlefield.round == 1) duelMenuController.finishRound1(battlefield.turn,battlefield.opponent,battlefield);
                else if(battlefield.round == 2) duelMenuController.finishRound2(battlefield.turn,battlefield.opponent,battlefield);
                else if(battlefield.round == 3){
                    duelMenuController.finishRound3(battlefield.turn,battlefield.opponent,battlefield);
                    duelMenuController.matchFinish(battlefield.turn,battlefield.opponent);
                    DataBase.saveTheUserList(User.getUsers());
                    audioClip.stop();
                    Main.audioClip.play();
                    DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
                }
            }
        }
    }


    public void keyPressed (){
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equalsIgnoreCase("ctrl")){
                    stringForCheat += keyEvent.getCode().getName();
                }
                if (keyEvent.getCode().getName().equalsIgnoreCase("shift")){
                    if (stringForCheat.equalsIgnoreCase("ctrl"))
                        stringForCheat += keyEvent.getCode().getName();
                    else
                        stringForCheat = "";
                }
                if (keyEvent.getCode().getName().equalsIgnoreCase("c")){
                    if (stringForCheat.equalsIgnoreCase("ctrlshift")){
                        String command = UserInterface.getUserInput();
                        if(Objects.isNull(command));
                        else if(command.startsWith("select")) {
                            //play Sound needed
                            battlefield.forceAddedToHand(command);
                        }
                        else if(command.startsWith("duel set-winner")){
                            battlefield.duelWinCheat(Regex.getMatcher(command,Regex.duelWinCheat));
                        }
                        else if(command.startsWith("increase"))
                            battlefield.increaseLPCheat(Regex.getMatcher(command,Regex.increaseLPCheat));
                        stringForCheat = "";
                    }
                    else
                        stringForCheat = "";
                }
            }
        });
    }


    public void mouseEventClick (){

        canvas.setOnDragOver(e->{
            double x = e.getSceneX();
            double y = e.getSceneY();
            //5 | 3 | 1 | 2 | 4
            if (x >= 315 && x <= 373 && y >= 355 && y <= 413 && battlefield.getTurn().field.monsterZone.get(0) == null) {
                dragPosition = 0;
                dragCard = battlefield.turn.field.monsterZone.get(0);
            }
            else if (x >= 382 && x <= 438 && y >= 352 && y <= 412 && battlefield.getTurn().field.monsterZone.get(1) == null) {
                dragPosition = 1;
                dragCard = battlefield.turn.field.monsterZone.get(1);
            }
            else if (x >= 444.0 && x <= 511 && y >= 345 && y <= 421 && battlefield.getTurn().field.monsterZone.get(2) == null) {
                dragPosition = 2;
                dragCard = battlefield.turn.field.monsterZone.get(2);
            }
            else if (x >= 518 && x <= 575 && y >= 353 && y <= 415 && battlefield.getTurn().field.monsterZone.get(3) == null) {
                dragPosition = 3;
                dragCard = battlefield.turn.field.monsterZone.get(3);
            }
            else if (x >= 582.0 && x <= 646 && y >= 345 && y <= 421 && battlefield.getTurn().field.monsterZone.get(4) == null) {
                dragPosition = 4;
                dragCard = battlefield.turn.field.monsterZone.get(4);
            }
            else if (x >= 294 && x <= 361 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(0) == null){
                dragPosition = 0;
                dragCard = battlefield.turn.field.spellTrapZone.get(0);
            }
            else if (x >= 362 && x <= 420 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(1) == null){
                dragPosition = 1;
                dragCard = battlefield.turn.field.spellTrapZone.get(1);
            }
            else if (x >= 421 && x <= 489 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(2) == null){
                dragPosition = 2;
                dragCard = battlefield.turn.field.spellTrapZone.get(2);
            }
            else if (x >= 501 && x <= 556 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(3) == null){
                dragPosition = 3;
                dragCard = battlefield.turn.field.spellTrapZone.get(3);
            }
            else if (x >= 569 && x <= 626 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(4) == null){
                dragPosition = 4;
                dragCard = battlefield.turn.field.spellTrapZone.get(4);
            }
        });
        canvas.setOnMouseClicked(event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println(x);
            System.out.println(y);
            //turn monster zone
            if (x >= 309.0 && x <= 375 && y >= 345 && y <= 421) {
                battlefield.isSpellSelected = false;
                battlefield.selectedCard = battlefield.turn.field.monsterZone.get(0);
                battlefield.selectedCardPosition = 0;
                battlefield.attackGui(0);
            }
            else if (x >= 377.0 && x <= 442 && y >= 345 && y <= 421) {
                battlefield.isSpellSelected = false;
                battlefield.selectedCard = battlefield.turn.field.monsterZone.get(1);
                battlefield.selectedCardPosition = 1;
                battlefield.attackGui(1);
            }
            else if (x >= 444.0 && x <= 511 && y >= 345 && y <= 421) {
                battlefield.isSpellSelected = false;
                battlefield.selectedCard = battlefield.turn.field.monsterZone.get(2);
                battlefield.selectedCardPosition = 2;
                battlefield.attackGui(2);
            }
            else if (x >= 513.0 && x <= 580 && y >= 345 && y <= 421) {
                battlefield.isSpellSelected = false;
                battlefield.selectedCard = battlefield.turn.field.monsterZone.get(3);
                battlefield.selectedCardPosition = 3;
                battlefield.attackGui(3);
            }
            else if (x >= 582.0 && x <= 646 && y >= 345 && y <= 421) {
                battlefield.isSpellSelected = false;
                battlefield.selectedCard = battlefield.turn.field.monsterZone.get(4);
                battlefield.selectedCardPosition = 4;
                battlefield.attackGui(4);
            }
            //turn graveyard
            else if (x >= 656 && x <= 710 && y >= 347 && y <= 419)
                showGraveyard(battlefield.getTurn());
            //turn FieldZone
            else if (x >= 250 && x <= 290 && y >= 349 && y <= 402)
                showFieldZone(battlefield.getTurn());
            //opponent FieldZone
            else if (x >= 665 && x <= 700 && y >= 255 && y <= 319)
                showFieldZone(battlefield.getOpponent());
            //opponent graveyard
            else if (x >= 233 && x <= 295 && y >= 251 && y <= 317)
                showGraveyard(battlefield.getOpponent());
            //opponent monster zone
            else if (x >= 311 && x <= 370 && y >= 259 && y <= 325 && battlefield.getOpponent().field.monsterZone.get(4) != null && battlefield.getOpponent().field.monsterZone.get(4).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.monsterZone.get(4);
                addChanges();
            }
            else if (x >= 378 && x <= 441 && y >= 259 && y <= 325 && battlefield.getOpponent().field.monsterZone.get(3) != null && battlefield.getOpponent().field.monsterZone.get(3).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.monsterZone.get(3);
                addChanges();
            }
            else if (x >= 447 && x <= 507 && y >= 259 && y <= 325 && battlefield.getOpponent().field.monsterZone.get(2) != null && battlefield.getOpponent().field.monsterZone.get(2).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.monsterZone.get(2);
                addChanges();
            }
            else if (x >= 516 && x <= 575 && y >= 259 && y <= 325 && battlefield.getOpponent().field.monsterZone.get(1) != null && battlefield.getOpponent().field.monsterZone.get(1).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.monsterZone.get(1);
                addChanges();
            }
            else if (x >= 585 && x <= 642 && y >= 259 && y <= 325 && battlefield.getOpponent().field.monsterZone.get(0) != null && battlefield.getOpponent().field.monsterZone.get(0).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.monsterZone.get(0);
                addChanges();
            }
            //opponent spell and trap zone
            else if (x >= 311 && x <= 370 && y >= 183 && y <= 253 && battlefield.getOpponent().field.spellTrapZone.get(4) != null && battlefield.getOpponent().field.spellTrapZone.get(4).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.spellTrapZone.get(4);
                addChanges();
            }
            else if (x >= 378 && x <= 441 && y >= 183 && y <= 253 && battlefield.getOpponent().field.spellTrapZone.get(3) != null && battlefield.getOpponent().field.spellTrapZone.get(3).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.spellTrapZone.get(3);
                addChanges();
            }
            else if (x >= 447 && x <= 507 && y >= 183 && y <= 253 && battlefield.getOpponent().field.spellTrapZone.get(2) != null && battlefield.getOpponent().field.spellTrapZone.get(2).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.spellTrapZone.get(2);
                addChanges();
            }
            else if (x >= 516 && x <= 575 && y >= 183 && y <= 253 && battlefield.getOpponent().field.spellTrapZone.get(1) != null && battlefield.getOpponent().field.spellTrapZone.get(1).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.spellTrapZone.get(1);
                addChanges();
            }
            else if (x >= 585 && x <= 642 && y >= 183 && y <= 253 && battlefield.getOpponent().field.spellTrapZone.get(0) != null && battlefield.getOpponent().field.spellTrapZone.get(0).getCardsFace() != FaceUp.DEFENSE_BACK){
                battlefield.selectedCard = battlefield.getOpponent().field.spellTrapZone.get(0);
                addChanges();
            }
            //turn spell and trap zone
            else if (x >= 294 && x <= 361 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(0) != null){
                battlefield.isSpellSelected = true;
                battlefield.attackGui(0);
            }
            else if (x >= 362 && x <= 420 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(1) != null){
                battlefield.isSpellSelected = true;
                battlefield.attackGui(1);
            }
            else if (x >= 431 && x <= 489 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(2) != null){
                battlefield.isSpellSelected = true;
                battlefield.attackGui(2);
            }
            else if (x >= 501 && x <= 556 && y >= 426 && y <= 490 && battlefield.getTurn().field.spellTrapZone.get(3) != null){
                battlefield.isSpellSelected = true;
                battlefield.attackGui(3);
            }
            else if (x >= 569 && x <= 626 && y >= 430 && y <= 427 && battlefield.getTurn().field.spellTrapZone.get(4) != null){
                battlefield.isSpellSelected = true;
                battlefield.attackGui(4);
            }
        });
    }

    private void showFieldZone(Duelist duelist) {
        Canvas newCanvas = new Canvas();
        GraphicsContext newGraphic = newCanvas.getGraphicsContext2D();
        newGraphic.clearRect(0, 0, 500, 450);

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER);

        Card fieldZone = duelist.field.fieldZone;
        if(fieldZone == null) {
            UserInterface.printResponse("No card in fieldZone");
            return;
        }
        else if (fieldZone.getCardsType() == Type.MONSTER) {
            Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + fieldZone.getName() + ".jpg")).toExternalForm(), 300, 300, false, false);
            ImageView imageView2 = new ImageView(image2);
            gridPane.add(imageView2,0,1);
        }
        else {
            Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + fieldZone.getName() + ".jpg")).toExternalForm(), 300, 300, false, false);
            ImageView imageView2 = new ImageView(image2);
            gridPane.add(imageView2, 0, 1);
        }
        Button addToGravYard = new Button("send to graveYard");

        addToGravYard.setOnMouseClicked(e->{
            if(battlefield.selectedCard.getCardsType().equals(Type.MONSTER))
                ((Monster)battlefield.selectedCard).removeMonster(battlefield);
            else
                ((SpellAndTrap)battlefield.selectedCard).removeSpellOrTrap(battlefield);
        });
        scrollPane.setContent(vBox);
        BorderPane root2 = new BorderPane();
        root2.setCenter(scrollPane);
        root2.setBottom(addToGravYard);
        Scene scene = new Scene(root2);
        new subStage("Field Zone",scene);
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
            if (battlefield.selectedCard.getCardsType().equals(Type.MONSTER))
                imageDeck = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + battlefield.selectedCard.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);
            else
                imageDeck = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + battlefield.selectedCard.getName() + ".jpg")).toExternalForm(), 50, 100, false, false);
        }
        ImageView imgDeck = new ImageView((imageDeck));
        imgDeck.setOnMouseClicked(e->{
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.TOP_CENTER);
            VBox vBox = new VBox(gridPane);
            vBox.setAlignment(Pos.CENTER);

            ArrayList<Card> deckCards = battlefield.getTurn().field.deck;
            makeListOfCardsForShowing(gridPane, deckCards);

            Button button = new Button("Back");
            button.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                    "-fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white;");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    root.setCenter(canvas);
                    addChanges();
                }
            });

            gridPane.add(button, 0, 1000);
            Collections.shuffle(battlefield.getTurn().field.deck);

            scrollPane.setContent(vBox);
            root.setCenter(scrollPane);
        });
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
        vboxLeft.getChildren().addAll(vbox2, vBoxRight, vbox1);

        return vboxLeft;
    }

    public void makeListOfCardsForShowing(GridPane gridPane, ArrayList<Card> deckCards) {
        for (int i = 0; i<deckCards.size(); ++i){
            String name = deckCards.get(i).getName();
            if (deckCards.get(i).getCardsType() == Type.MONSTER){
                Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + deckCards.get(i).getName() + ".jpg")).toExternalForm(), 230, 230, false, false);
                ImageView imageView2 = new ImageView(image2);
                gridPane.add(imageView2, i%3, i/3);
            }
            else{
                Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + deckCards.get(i).getName() + ".jpg")).toExternalForm(), 230, 230, false, false);
                ImageView imageView2 = new ImageView(image2);
                gridPane.add(imageView2, i%3, i/3);
            }
        }
    }

    private VBox makeRightBar() {
        Button btnExit = new Button("Surrender");
        btnExit.setOnMouseClicked(e -> {
            battlefield.winner = battlefield.getOpponent();
            DuelMenuController duelMenuController =  DuelMenuController.getInstance(ProgramController.currUser);
            if(battlefield.isOneRound) {
                duelMenuController.finishround1(battlefield.opponent, battlefield.turn, battlefield);
                DataBase.saveTheUserList(User.getUsers());
                audioClip.stop();
                Main.audioClip.play();
                DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
            }
            else {
                if(battlefield.round == 1) duelMenuController.finishRound1(battlefield.getOpponent(),battlefield.getWinner(),battlefield);
                else if(battlefield.round == 2) duelMenuController.finishRound2(battlefield.getOpponent(),battlefield.getWinner(),battlefield);
                else if(battlefield.round == 3){
                    duelMenuController.finishRound3(battlefield.getOpponent(),battlefield.getWinner(),battlefield);
                    duelMenuController.matchFinish(battlefield.getOpponent(),battlefield.getWinner());
                    DataBase.saveTheUserList(User.getUsers());
                    audioClip.stop();
                    Main.audioClip.play();
                    DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
                }
            }
        });
        Button btnNextPhase = new Button("next Phase!");
        btnNextPhase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                battlefield.nextPhase();
                addChanges();
            }
        });
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

    public void showGraveyard (Duelist duelist){
        graphic.clearRect(0, 0, 500, 450);
        Button button = new Button("Back");
        button.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                "-fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.setCenter(canvas);
                addChanges();
            }
        });
        button.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(button);
        hBox.setAlignment(Pos.CENTER);


        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<Card> graveyardCards = duelist.field.graveYard;
        makeListOfCardsForShowing(gridPane, graveyardCards);

        gridPane.add(button, 0, 1000);



        scrollPane.setContent(vBox);
        root.setCenter(scrollPane);
    }
}
