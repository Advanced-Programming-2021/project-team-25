package controllers.Battelfield;

import controllers.Regex;
import controllers.ShowCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.AI;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Location;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.CommandKnight;
import models.Monster.Monster;
import models.Monster.Scanner;
import models.SpellAndTrap.SpellAndTrap;
import models.SpellAndTrap.SupplySquad;

import view.Main;
import view.menus.DuelMenu;
import view.menus.Game;
import view.Responses;
import view.UserInterface;
import view.menus.subStage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Battlefield {

    public Duelist turn;
    public Duelist opponent;
    private Phase phase = Phase.DRAW_PHASE;
    public Duelist winner;
    public Card selectedCard;
    private final Game game;

    public ArrayList<SpellAndTrap> activeSpellAndTraps = new ArrayList<>();
    private boolean isRitualSummoned = false;
    private boolean isTurnChanged = false;
    private int countDraw6Cards = 0;
    private SpellAndTrap currSpell = null;
    public int changedTurnTime = 0;
    public Monster attackingMonster;
    public Monster attackedMonster;
    public int monsterChangedWithScanner = 0;
    public int attackedMonsterNum;
    public boolean isOneRound;
    private Stage currStage;

    public Battlefield(Duelist duelist1, Duelist duelist2) {
        whoStart(duelist1, duelist2);
        game = new Game(this);
        startGame();
        game.addChanges();
    }

    //getter methods
    public Duelist getWinner() {
        return winner;
    }
    public Duelist getTurn() {
        return turn;
    }
    public Duelist getOpponent() {
        return opponent;
    }
    public Card getSelectedCard() {
        return selectedCard;
    }

    //setter methods
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void runBattleField() {
        while (winner == null) {

            if (countDraw6Cards < 2 && isTurnChanged) startGame();

            if(turn.getName().equals(opponent.getName())) return;

//            String command = UserInterface.getUserInput();
//
//            Matcher matcher;
//
//            if (isRitualSummoned) UserInterface.printResponse("you should ritual summon right now");
//            else if ((matcher = Regex.getMatcher(command, Regex.selectOpponent)).matches()) selectOpponentCard(matcher);
//            else if (Regex.getMatcher(command, Regex.deselect).matches()) deselectCard();
//            else if ((matcher = Regex.getMatcher(command, Regex.select)).matches()) selectCard(matcher);
//            else if (Regex.getMatcher(command, Regex.nextPhase).matches()) nextPhase();
//            else if (Regex.getMatcher(command, Regex.summon).matches()) summon();
//            else if (Regex.getMatcher(command, Regex.set).matches()) set();
//            else if ((matcher = Regex.getMatcher(command, Regex.setPosition)).matches()) setPosition(matcher);
//            else if (Regex.getMatcher(command, Regex.flipSummon).matches()) flipSummon();
//            else if (Regex.getMatcher(command, Regex.attackDirect).matches()) directAttack();
//            else if ((matcher = Regex.getMatcher(command, Regex.attack)).matches()) attack(matcher);
//            else if (Regex.getMatcher(command, Regex.activateEffect).matches()) activeSpell();
//            else if (Regex.getMatcher(command, Regex.showGraveyard).matches()) showGraveyard();
//            else if (Regex.getMatcher(command, Regex.showSelectedCard).matches()) showSelectedCard();
//            else if ((matcher = Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher.group(1));
//            else if (Regex.getMatcher(command, Regex.surrender).matches()) winner = opponent;
//            else if (Regex.getMatcher(command, Regex.forceAddedCardToHand).matches()) forceAddedToHand(matcher);
//            else if (Regex.getMatcher(command, Regex.duelWinCheat).matches()) duelWinCheat(matcher);
//            else if (Regex.getMatcher(command, Regex.increaseLPCheat).matches()) increaseLPCheat(matcher);
//            else UserInterface.printResponse(Responses.INVALID_COMMAND);
//            showBattleField();
        }
    }

    //start & clean
    private void whoStart(Duelist duelist1, Duelist duelist2) {
        Random ran = new Random();
        if (ran.nextInt(2) == 0) chooseStarter(duelist2, duelist1);
        else chooseStarter(duelist1, duelist2);
    }
    private void chooseStarter(Duelist duelist1, Duelist duelist2) {
        UserInterface.printResponse("I flipped a coin and " + duelist2.getName() + " can decide who start’s\n1." + duelist2.getName() + "\n2." + duelist1.getName());
        String num = UserInterface.getUserInput();
        if (num.equals("1")) {
            turn = duelist2;
            opponent = duelist1;
        } else {
            turn = duelist1;
            opponent = duelist2;
        }
    }
    public void startGame() {
        countDraw6Cards++;
        //shuffling the cards
        Collections.shuffle(turn.field.deck);
        Collections.shuffle(opponent.field.deck);
        //draw 6 cards for opponent and turn
        for (int i = 0; i < 6; i++) {
            addCardToPlayersHands(turn, i);
            addCardToOpponentsHand();
        }
    }
    public void cleanTurn() {
        turn.hasPutMonster = false;
        opponent.hasPutMonster = false;
        for (int i = 0; i < 5; ++i) {
            if (turn.field.monsterZone.get(i) != null) {
                turn.field.monsterZone.get(i).setSetChanged(false);
                turn.field.monsterZone.get(i).setIsSetThisTurn(false);
                turn.field.monsterZone.get(i).setISAttackedThisTurn(false);
            }
            if (opponent.field.monsterZone.get(i) != null) {
                opponent.field.monsterZone.get(i).setSetChanged(false);
                opponent.field.monsterZone.get(i).setIsSetThisTurn(false);
                opponent.field.monsterZone.get(i).setISAttackedThisTurn(false);
            }
            if (turn.field.spellTrapZone.get(i) != null) {
                turn.field.spellTrapZone.get(i).setSetChanged(false);
                turn.field.spellTrapZone.get(i).setIsSetThisTurn(false);
            }
            if (opponent.field.spellTrapZone.get(i) != null) {
                opponent.field.spellTrapZone.get(i).setSetChanged(false);
                opponent.field.spellTrapZone.get(i).setIsSetThisTurn(false);
            }
        }
        if (turn.field.fieldZone != null) {
            turn.field.fieldZone.setSetChanged(false);
            turn.field.fieldZone.setIsSetThisTurn(false);
        }
        if (opponent.field.fieldZone != null) {
            opponent.field.fieldZone.setSetChanged(false);
            opponent.field.fieldZone.setIsSetThisTurn(false);
        }
    }

    //draw
    private void addCardToPlayersHands(Duelist turn,int i) {
        Card card = turn.field.deck.get(0);
        turn.field.hand.add(card);
        turn.field.deck.remove(0);
    }
    private void addCardToOpponentsHand(){
        Card card = opponent.field.deck.get(0);
        opponent.field.hand.add(card);
        opponent.field.deck.remove(0);
    }
    public void drawCard() {
        if (turn.field.deck.size() > 0) {
            if (changedTurnTime >= 2 && turn.field.hand.size()<6) {
                addCardToPlayersHands(turn,turn.field.hand.size());
            }
        } else winner = opponent;
    }

    //monster zone number 5 = index 0 of ArrayList in range of x = (309,375) and y = (345,421)
    //monster zone number 3 = index 1 of ArrayList in range of x = (377,442) and y = (345,421)
    //monster zone number 1 = index 2 of ArrayList in range of x = (444,511) and y = (345,421)
    //monster zone number 2 = index 3 of ArrayList in range of x = (513,580) and y = (345,421)
    //monster zone number 4 = index 4 of ArrayList in range of x = (582,646) and y = (345,421)
    //rival monster zone number 5 = index 0 of rival ArrayList in range of x = (585,644) and y = (258,325)
    //rival monster zone number 3 = index 1 of rival ArrayList in range of x = (517,576) and y = (258,325)
    //rival monster zone number 1 = index 2 of rival ArrayList in range of x = (447,508) and y = (258,325)
    //rival monster zone number 2 = index 3 of rival ArrayList in range of x = (379,440) and y = (258,325)
    //rival monster zone number 4 = index 4 of rival ArrayList in range of x = (312,373) and y = (258,325)
    public void attackGui (int index){
        if (turn.field.monsterZone.get(index) != null || turn.field.spellTrapZone.get(index) != null) {
            Button actionBtn = new Button("Action");

            actionBtn.setOnMouseClicked(e->{
                game.canvas.setOnMouseClicked(event -> {
                    mouseClicked(index, event);
                });
            });

            javafx.scene.control.Button attackBtn = new javafx.scene.control.Button("Attack");
            attackBtn.setOnMouseClicked(e-> {
                game.addChanges();
                if (index == 0) drawLines(342, 345);
                if (index == 1) drawLines(409, 345);
                if (index == 2) drawLines(477, 345);
                if (index == 3) drawLines(546, 345);
                if (index == 4) drawLines(614, 345);
                game.canvas.setOnMouseClicked(event -> {
                    mouseClicked(index, event);
                });
                currStage.close();
            });
            if(turn.field.monsterZone.get(index) != null)
                selectedCard =  turn.field.monsterZone.get(index);
            else
                selectedCard =  turn.field.spellTrapZone.get(index);
            Button addToGravYard = new Button("send to graveYard");

            addToGravYard.setOnMouseClicked(e->{
                if(selectedCard.getCardsType().equals(Type.MONSTER))
                    ((Monster)selectedCard).removeMonster(this);
                else
                    ((SpellAndTrap)selectedCard).removeSpellOrTrap(this);
            });
            Image imageForButton;
            if(selectedCard.getCardsType().equals(Type.MONSTER))
            imageForButton = new Image(Objects.requireNonNull(this.getClass().getResource("Monsters/" +
                    turn.field.monsterZone.get(index).getName().replace(" ","") + ".jpg")).toExternalForm(), 275, 275, false, false);
            else
                imageForButton = new Image(Objects.requireNonNull(this.getClass().getResource("SpellTrap/" +
                        turn.field.spellTrapZone.get(index).getName().replace(" ","") + ".jpg")).toExternalForm(), 275, 275, false, false);
            ImageView imageView = new ImageView(imageForButton);

            Label lblPositions= new Label("set_Position");
            Label lblAttack= new Label("Attack");
            Label lblAction= new Label("Action Spell or trap");
            Label lblFlipSummon= new Label("Flip summon");


            Button flipBtn = new Button("FlipSummon");
            Button back = new Button("back");
            back.setOnMouseClicked(e->{
                currStage.close();
            });
            flipBtn.setOnMouseClicked(e->{
                flipSummon();
                game.addChanges();
                currStage.close();
            });
            Button saveChanges = new Button("Save Changes");

            ChoiceBox<String> position = new ChoiceBox<>();
            position.setValue("Attack");
            // Add the items to the ChoiceBox
            position.getItems().addAll("Attack", "Defence");
            // Create the Selection Value Label
            Label selectedValueLbl = new Label();
            // Bind the value property to the text property of the Label
            selectedValueLbl.textProperty().bind(position.valueProperty());
            saveChanges(saveChanges, position);
            BorderPane borderPane = new BorderPane();
            if(selectedCard.getCardsType().equals(Type.MONSTER))
                borderPane = getBorderPane(addToGravYard,attackBtn, imageView, lblPositions, lblAttack, lblFlipSummon, flipBtn, back, saveChanges, position);
            else {
                VBox vBox = new VBox();
                vBox.getChildren().addAll(lblAction,actionBtn);
                vBox.setSpacing(15);
                borderPane.setLeft(vBox);
                borderPane.setRight(imageView);
                borderPane.setBottom(addToGravYard);
            }
            Scene scene = new Scene(borderPane,500,450);
            String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
            scene.getStylesheets().add(style);
            subStage sub = new subStage("Attack Gui",scene);
            currStage = sub.getStage();
        }
    }

    private void mouseClicked(int index, javafx.scene.input.MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        if (x >= 312 && x <= 373 && y >= 258 && y <= 325 && opponent.field.monsterZone.get(4) != null) {
            selectedCard = turn.field.monsterZone.get(index);
            Pattern pattern = Pattern.compile("^attack (.+)$");
            Matcher matcher = pattern.matcher("attack 4");
            matcher.find();
            attack(matcher);
            game.refreshHealthBar(turn, opponent);
            game.addChanges();
        } else if (x >= 379 && x <= 448 && y >= 258 && y <= 325 && opponent.field.monsterZone.get(3) != null) {
            selectedCard = turn.field.monsterZone.get(index);
            Pattern pattern = Pattern.compile("^attack (.+)$");
            Matcher matcher = pattern.matcher("attack 2");
            matcher.find();
            attack(matcher);
            game.refreshHealthBar(turn, opponent);
            game.addChanges();
        } else if (x >= 447 && x <= 508 && y >= 258 && y <= 325 && opponent.field.monsterZone.get(2) != null) {
            selectedCard = turn.field.monsterZone.get(index);
            Pattern pattern = Pattern.compile("^attack (.+)$");
            Matcher matcher = pattern.matcher("attack 1");
            matcher.find();
            attack(matcher);
            game.refreshHealthBar(turn, opponent);
            game.addChanges();
        } else if (x >= 517 && x <= 576 && y >= 258 && y <= 325 && opponent.field.monsterZone.get(1) != null) {
            selectedCard = turn.field.monsterZone.get(index);
            Pattern pattern = Pattern.compile("^attack (.+)$");
            Matcher matcher = pattern.matcher("attack 3");
            matcher.find();
            attack(matcher);
            game.refreshHealthBar(turn, opponent);
            game.addChanges();
        } else if (x >= 585 && x <= 644 && y >= 258 && y <= 325 && opponent.field.monsterZone.get(0) != null) {
            selectedCard = turn.field.monsterZone.get(index);
            Pattern pattern = Pattern.compile("^attack (.+)$");
            Matcher matcher = pattern.matcher("attack 5");
            matcher.find();
            attack(matcher);
            game.refreshHealthBar(turn, opponent);
            game.addChanges();
        } else if (x >= 408 && x <= 545 && y >= 125 && y <= 214 && isOpponentEmptyOfMonsters()) {
            selectedCard = turn.field.monsterZone.get(index);
            directAttack();
            game.refreshHealthBar(turn, opponent);
            game.addChanges();
        } //turn spell and trap zone
        else if (x >= 311 && x <= 370 && y >= 426 && y <= 490 && this.getTurn().field.spellTrapZone.get(0) != null){
            this.selectedCard = this.getTurn().field.spellTrapZone.get(0);
            this.activeSpell("notFirstTime");
            game.addChanges();
        }
        else if (x >= 378 && x <= 441 && y >= 426 && y <= 490 && this.getTurn().field.spellTrapZone.get(1) != null){
            this.selectedCard = this.getTurn().field.spellTrapZone.get(1);
            this.activeSpell("notFirstTime");
            game.addChanges();
        }
        else if (x >= 447 && x <= 507 && y >= 426 && y <= 490 && this.getTurn().field.spellTrapZone.get(2) != null){
            this.selectedCard = this.getTurn().field.spellTrapZone.get(2);
            this.activeSpell("notFirstTime");
            game.addChanges();
        }
        else if (x >= 516 && x <= 575 && y >= 426 && y <= 490 && this.getTurn().field.spellTrapZone.get(3) != null){
            this.selectedCard = this.getTurn().field.spellTrapZone.get(3);
            this.activeSpell("notFirstTime");
            game.addChanges();
        }
        else if (x >= 585 && x <= 642 && y >= 426 && y <= 490 && this.getTurn().field.spellTrapZone.get(4) != null){
            this.selectedCard = this.getTurn().field.spellTrapZone.get(4);
            this.activeSpell("notFirstTime");
            game.addChanges();
        } else
            game.addChanges();
        game.mouseEventClick();
    }

    private BorderPane getBorderPane(Button addToGraveYard,Button attackBtn, ImageView imageView, Label lblPositions, Label lblAttack, Label lblFlipSummon, Button flipBtn, Button back, Button saveChanges, ChoiceBox<String> position) {
        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        hBox1.getChildren().addAll(lblPositions, position);
        HBox hBox2 = new HBox();
        hBox2.setSpacing(15);
        hBox2.getChildren().addAll(lblAttack, attackBtn);
        HBox hBox3 = new HBox();
        hBox3.setSpacing(15);
        hBox3.getChildren().addAll(lblFlipSummon, flipBtn);
        HBox hBox4 = new HBox();
        hBox4.setSpacing(60);
        hBox4.getChildren().addAll(back,addToGraveYard,saveChanges);
        VBox vboxLeft = new VBox();
        vboxLeft.getChildren().addAll(hBox1,hBox2,hBox3);
        VBox vboxRight = new VBox();
        vboxLeft.setSpacing(40);
        vboxRight.getChildren().addAll(imageView);
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(vboxRight);
        borderPane.setLeft(vboxLeft);
        borderPane.setBottom(hBox4);
        return borderPane;
    }

    private void saveChanges(Button saveChanges, ChoiceBox<String> position) {
        saveChanges.setOnMouseClicked(e->{
            if (position.getValue().equals("Attack")) {
                if (!turn.field.monsterZone.contains(selectedCard))
                    UserInterface.printResponse("you can’t change this card position");
                else if (!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
                    UserInterface.printResponse("you can’t do this action in this phase");
                else if (selectedCard.getCardsFace() == FaceUp.ATTACK)
                    UserInterface.printResponse("this card is already in the wanted position");
                else if (selectedCard.getSetChanged())
                    UserInterface.printResponse("you already changed this card position in this turn");
                else {
                    selectedCard.setSetChanged(true);
                    selectedCard.setCardsFace(FaceUp.ATTACK);
                    UserInterface.printResponse("monster card position changed successfully");
                    selectedCard = null;
                }
            } else if (position.getValue().equals("Defence")) {
                if (!turn.field.monsterZone.contains(selectedCard))
                    UserInterface.printResponse("you can’t change this card position");
                else if (!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
                    UserInterface.printResponse("you can’t do this action in this phase");
                else if (selectedCard.getCardsFace() != FaceUp.ATTACK)
                    UserInterface.printResponse("this card is already in the wanted position");
                else if (selectedCard.getSetChanged())
                    UserInterface.printResponse("you already changed this card position in this turn");
                else {
                    selectedCard.setSetChanged(true);
                    selectedCard.setCardsFace(FaceUp.DEFENSE_FRONT);
                    UserInterface.printResponse("monster card position changed successfully");
                    selectedCard = null;
                }
            }
            game.addChanges();
        });
    }

    public void drawLines (double x, double y){
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (opponent.field.monsterZone.get(i) != null){
                counter += 1;
                if (i == 4){
                    game.getMainGraphic().setStroke(Color.GOLD);
                    game.getMainGraphic().setLineWidth(5);
                    game.getMainGraphic().strokeLine(342-226, 325-114, x-226, y-114);
                }
                if (i == 3){
                    game.getMainGraphic().setStroke(Color.GOLD);
                    game.getMainGraphic().setLineWidth(5);
                    game.getMainGraphic().strokeLine(409-226, 325-114, x-226, y-114);
                }
                if (i == 2){
                    game.getMainGraphic().setStroke(Color.GOLD);
                    game.getMainGraphic().setLineWidth(5);
                    game.getMainGraphic().strokeLine(477-226, 325-114, x-226, y-114);
                }
                if (i == 1){
                    game.getMainGraphic().setStroke(Color.GOLD);
                    game.getMainGraphic().setLineWidth(5);
                    game.getMainGraphic().strokeLine(546-226, 325-114, x-226, y-114);
                }
                if (i == 0){
                    game.getMainGraphic().setStroke(Color.GOLD);
                    game.getMainGraphic().setLineWidth(5);
                    game.getMainGraphic().strokeLine(614-226, 325-114, x-226, y-114);
                }
            }
        }
        if (counter == 0){
            game.getMainGraphic().setStroke(Color.GOLD);
            game.getMainGraphic().setLineWidth(5);
            game.getMainGraphic().strokeLine(473-226, 134-114, x-226, y-114);
        }
    }

    //showBattleField
    public void showBattleField() {
        UserInterface.printResponse(opponent.getName() + " : " + opponent.LP);
        for (Card card : opponent.field.hand) System.out.print("c\t");
        UserInterface.printResponse("\n" + opponent.field.deck.size() + "");

        System.out.print("\t");
        showSpellAndTrapsZone(3, opponent);
        showSpellAndTrapsZone(1, opponent);
        showSpellAndTrapsZone(0, opponent);
        showSpellAndTrapsZone(2, opponent);
        showSpellAndTrapsZone(4, opponent);

        System.out.print("\n\t");
        showMonsterZone(3, opponent);
        showMonsterZone(1, opponent);
        showMonsterZone(0, opponent);
        showMonsterZone(2, opponent);
        showMonsterZone(4, opponent);

        System.out.print("\n" + opponent.field.graveYard.size() + "\t\t\t\t\t\t");
        if (opponent.field.fieldZone == null) System.out.print("O\n");
        else System.out.print("E\n");

        UserInterface.printResponse("___________________________");

        if (turn.field.fieldZone == null) System.out.print("O");
        else System.out.print("E");
        System.out.print("\t\t\t\t\t\t" + turn.field.graveYard.size());

        System.out.print("\n\t");
        showMonsterZone(4, turn);
        showMonsterZone(2, turn);
        showMonsterZone(0, turn);
        showMonsterZone(1, turn);
        showMonsterZone(3, turn);

        System.out.print("\n\t");
        showSpellAndTrapsZone(4, turn);
        showSpellAndTrapsZone(2, turn);
        showSpellAndTrapsZone(0, turn);
        showSpellAndTrapsZone(1, turn);
        showSpellAndTrapsZone(3, turn);

        UserInterface.printResponse("\n\t\t\t\t\t\t" + turn.field.deck.size());
        for (Card card : turn.field.hand) System.out.print("c\t");

        UserInterface.printResponse("\n" + turn.getName() + " : " + turn.LP);
    }
    private void showSpellAndTrapsZone(int i, Duelist duelist) {
        if (duelist.field.spellTrapZone.get(i) == null) System.out.print("E\t");
        else if (duelist.field.spellTrapZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) System.out.print("H\t");
        else if (duelist.field.spellTrapZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT) System.out.print("O\t");
    }
    private void showMonsterZone(int i, Duelist duelist) {
        if (duelist.field.monsterZone.get(i) == null) System.out.print("E\t");
        else if (duelist.field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) System.out.print("DH\t");
        else if (duelist.field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT) System.out.print("DO\t");
        else if (duelist.field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK) System.out.print("OO\t");
    }
    public void showGraveyard() {
        if (turn.field.graveYard.isEmpty()) UserInterface.printResponse("graveyard empty");
        else {
            int i = 1;
            for (Card card : turn.field.graveYard)
                UserInterface.printResponse(i + ". " + card.getName() + " : " + card.getDescription());
        }
        UserInterface.getUserInput();
    }
    public void showSelectedCard() {
        if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if (selectedCard.getCardsFace() == FaceUp.DEFENSE_BACK &&
                (opponent.field.monsterZone.contains(selectedCard) ||
                        opponent.field.spellTrapZone.contains(selectedCard)))
            UserInterface.printResponse("card is not visible");
        else ShowCard.showCard(selectedCard.getName());
    }

    //select & deselect
    public void selectCard(Matcher matcher) {
        String restOfCommand = matcher.group(1);
        String[] temp = restOfCommand.split(" ");
        String[] brokenCommand = new String[5];
        int counter = 0;
        for (int i = 0; i < temp.length; ++i) {
            if (temp[i].length() > 0) {
                brokenCommand[counter] = temp[i];
                counter += 1;
            }
        }
        if (brokenCommand[0].equals("--monster")) {
            if (Integer.parseInt(brokenCommand[1]) < 1 || Integer.parseInt(brokenCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (turn.field.monsterZone.get(Integer.parseInt(brokenCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = turn.field.monsterZone.get(Integer.parseInt(brokenCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        } else if (brokenCommand[0].equals("--spell")) {
            if (Integer.parseInt(brokenCommand[1]) < 1 || Integer.parseInt(brokenCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (turn.field.spellTrapZone.get(Integer.parseInt(brokenCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = turn.field.spellTrapZone.get(Integer.parseInt(brokenCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        } else if (brokenCommand[0].equals("--field")) {
            if (turn.field.fieldZone == null)
                UserInterface.printResponse("no card found in the given position");
            else
                selectedCard = turn.field.fieldZone;
        } else if (brokenCommand[0].equals("--hand")) {
            if (Integer.parseInt(brokenCommand[1]) < 1 || Integer.parseInt(brokenCommand[1]) > turn.field.hand.size())
                UserInterface.printResponse("invalid selection");
            else if (turn.field.hand.get(Integer.parseInt(brokenCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = turn.field.hand.get(Integer.parseInt(brokenCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        } else {
            UserInterface.printResponse("invalid selection");
        }
    }
    public void selectOpponentCard(Matcher matcher) {
        String restOfCommand = matcher.group(1);
        String[] temp = restOfCommand.split(" ");
        String[] breakedCommand = new String[5];
        int counter = 0;
        for (int i = 0; i < temp.length; ++i) {
            if (temp[i].length() > 0) {
                breakedCommand[counter] = temp[i];
                counter += 1;
            }
        }
        if (breakedCommand[0].equals("--monster")) {
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (opponent.field.monsterZone.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = opponent.field.monsterZone.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        } else if (breakedCommand[0].equals("--spell")) {
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (opponent.field.spellTrapZone.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = opponent.field.spellTrapZone.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        } else if (breakedCommand[0].equals("--field")) {
            if (opponent.field.fieldZone == null)
                UserInterface.printResponse("no card found in the given position");
            else
                selectedCard = opponent.field.fieldZone;
        } else {
            UserInterface.printResponse("invalid selection");
        }
    }
    public void deselectCard() {
        if (Objects.isNull(selectedCard)) UserInterface.printResponse("no card is selected yet");
        else {
            UserInterface.printResponse("card deselected");
            selectedCard = null;
        }
    }

    //end phase & turn
    public void nextPhase() {
        // to active all needed spells
        phaseController();

        selectedCard = null;
        if (phase == Phase.DRAW_PHASE) phase = Phase.STANDBY_PHASE;
        else if (phase == Phase.STANDBY_PHASE)phase = Phase.MAIN1_PHASE;
        else if (phase == Phase.MAIN1_PHASE) phase = Phase.BATTLE_PHASE;
        else if (phase == Phase.BATTLE_PHASE) phase = Phase.MAIN2_PHASE;
        else if (phase == Phase.MAIN2_PHASE) phase = Phase.END_TURN;
        else if (phase == Phase.END_TURN) {

            changeTurn();

            cleanTurn();
            phase = Phase.DRAW_PHASE;

            UserInterface.printResponse("its " + turn.getName() + "’s turn");
        }
        UserInterface.printResponse("phase: " + phase);
        if (phase == Phase.DRAW_PHASE) drawCard();
    }
    public void changeTurn() {
        //timer increase
        changedTurnTime++;
        isTurnChanged = true;
        if (opponent.getName().equals("Game")) {
            if (opponent.field.deck.size() > 0) {
                if (changedTurnTime >= 2 && turn.field.hand.size()<6) {
                    addCardToPlayersHands(opponent,opponent.field.hand.size());
                }
            } else winner = turn;
            ((AI) opponent).runAi(this);
        } else {
            Duelist temp;
            temp = turn;
            turn = opponent;
            opponent = temp;
        }
    }

    //summon
    public void summon() {
        //checking is a card selected or not
        if (Objects.isNull(selectedCard)) UserInterface.printResponse("no card is selected yet");
        //checking that if we have monster
        else if (!turn.field.hand.contains(selectedCard)  || !(selectedCard.getCardsType() == Type.MONSTER))
            UserInterface.printResponse("you cant summon this card");
        else {
            //loading the monster from selected card
            Monster monster = (Monster) selectedCard;
            //checking the correct phase
            if (!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
                UserInterface.printResponse("action not allowed in this phase");
            //checking is the zone filled
            else if (getSizeOfMonsterZone() == 5)
                UserInterface.printResponse("monster card zone is full");
            //checking if turn can summon
            else if (turn.hasPutMonster)
                UserInterface.printResponse("you already summoned/set on this turn");
            //exception for King Barbaros
            else if (monster.getName().equals("Beast King Barbaros"))
                summonKingBarbaros(monster);
            //exception for gate guardian
            else if (monster.getName().equals("Gate Guardian"))
                summonOrSetGateGuardian("summoned successfully");
            else if (monster.getName().equals("Command Knight"))
                summonOrFlipSummonCommandKnight("summoned successfully");
            //summon level 5 or 6 monsters
            else if (monster.getLevel() == 5 || monster.getLevel() == 6) {
                summonLevel6Or5("summoned successfully");
                selectedCard = null;
            }
            //summon level 7 , 8 monsters
            else if (monster.getLevel() == 7 || monster.getLevel() == 8) {
                summonLevel8Or7(monster, "summoned successfully");
                selectedCard = null;
            }
            //normal summon
            else if (monster.getLevel() <= 4) {
                summonedMonster("summoned successfully");
            }
        }
    }
    private void summonKingBarbaros(Monster monster) {
        String command;
        UserInterface.printResponse("""
                you can summon this card without tribute two monster by normal summon
                but Please Not that this monster attack will be decrease 1900 point
                 enter your decision Y or N""");
        command = UserInterface.getUserInput();
        if (command.equals("Y")) {
            monster.setAttack(monster.getAttack() - 1900);
        } else if (command.equals("N")) {
            UserInterface.printResponse("you can summon this card by tribute three monsters of yours by this you can" +
                    "destroy all opponents monsters\n type Y for agreement and N for disagree");
            command = UserInterface.getUserInput();
            if (command.equals("Y")) {
                getThreeMonsterForTribute();
            } else if (command.equals("N")) summonLevel8Or7(monster, "summoned successfully");
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        } else UserInterface.printResponse(Responses.INVALID_COMMAND);

    }
    private void getThreeMonsterForTribute() {
        if (getSizeOfMonsterZone() < 3) UserInterface.printResponse("not enough monster");
        else {
            Monster monsterForTribute1, monsterForTribute2, monsterForTribute3;
            UserInterface.printResponse("please select there card to tribute!");
            UserInterface.printResponse("please select the first one");
            monsterForTribute1 = tributeOneMonster();
            UserInterface.printResponse("please select the next one");
            monsterForTribute2 = tributeOneMonster();
            UserInterface.printResponse("please select the next one");
            monsterForTribute3 = tributeOneMonster();
            //checking is error happened or not
            if (Objects.isNull(monsterForTribute1) || Objects.isNull(monsterForTribute2)
                    || Objects.isNull(monsterForTribute3))
                UserInterface.printResponse("no Valid monster has inputted");
            else {
                monsterForTribute1.removeMonster(this);
                monsterForTribute2.removeMonster(this);
                monsterForTribute3.removeMonster(this);
            }
        }
    }
    public void summonOrSetGateGuardian(String message) {
        int counter = 0;
        for (int i = 0; i < 5; ++i)
            if (turn.field.monsterZone.get(i) != null)
                counter += 1;

        if (counter < 3)
            if (message.equals("summoned successfully"))
                UserInterface.printResponse("You can't summon Gate Guardian.");
            else
                UserInterface.printResponse("You can't set Gate Guardian");

        else {
            Monster monster1, monster2, monster3;
            monster1 = getMonsterForTributeForGateGuardian();
            monster2 = getMonsterForTributeForGateGuardian();
            monster3 = getMonsterForTributeForGateGuardian();

            monster1.removeMonster(this);
            monster2.removeMonster(this);
            monster3.removeMonster(this);

            for (int i = 0; i < 5; ++i)
                if (turn.field.monsterZone.get(i) == null) {
                    turn.field.monsterZone.set(i, selectedCard);
                    turn.field.hand.remove(selectedCard);
                    selectedCard.setIsSetThisTurn(true);
                    turn.hasPutMonster = true;
                    if (message.equals("summon successfully"))
                        selectedCard.setCardsFace(FaceUp.ATTACK);
                    else
                        selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                    selectedCard = null;
                    UserInterface.printResponse(message);
                    break;
                }
        }
    }
    public Monster getMonsterForTributeForGateGuardian() {
        UserInterface.printResponse("Please select one monster name");
        for (int i = 0; i < 5; ++i)
            if (turn.field.monsterZone.get(i) != null)
                UserInterface.printResponse(turn.field.monsterZone.get(i).getName());

        String name = " ";
        while (true) {
            String command = UserInterface.getUserInput();
            for (Card trueMonster : turn.field.monsterZone) {
                if (trueMonster.getName().equals(command)) {
                    name = command;
                    break;
                }
            }
            if (name.equals(" "))
                UserInterface.printResponse("Insert a valid name please.");
            else
                break;
        }

        for (int i = 0; i < 5; ++i)
            if (turn.field.monsterZone.get(i) != null && turn.field.monsterZone.get(i).getName().equals(name)) {
                return (Monster) turn.field.monsterZone.get(i);
            }
        return null;
    }
    public void summonOrFlipSummonCommandKnight(String message) {
        CommandKnight commandKnight = (CommandKnight) selectedCard;

        for (int i = 0; i < 5; ++i) {
            if (turn.field.monsterZone.get(i) != null) {
                Monster temp = (Monster) turn.field.monsterZone.get(i);
                temp.setAttack(temp.getAttack() + 400);
                commandKnight.targetedMonsters.add(temp);
            }
            if (opponent.field.monsterZone.get(i) != null) {
                Monster temp = (Monster) opponent.field.monsterZone.get(i);
                temp.setAttack(temp.getAttack() + 400);
                commandKnight.targetedMonsters.add(temp);
            }
        }

        if (message.equals("summoned successfully")) {
            for (int i = 0; i < 5; ++i)
                if (turn.field.monsterZone.get(i) == null) {
                    turn.field.monsterZone.set(i, commandKnight);
                    turn.field.hand.remove(commandKnight);
                    commandKnight.setIsSetThisTurn(true);
                    turn.hasPutMonster = true;
                    commandKnight.setCardsFace(FaceUp.ATTACK);
                    selectedCard = null;
                    UserInterface.printResponse(message);
                    break;
                }
        } else {
            selectedCard.setCardsFace(FaceUp.ATTACK);
            UserInterface.printResponse(message);
        }

    }
    private void summonLevel8Or7(Monster monster, String message) {
        //checking if can tribute happened
        if (getSizeOfMonsterZone() < 2) UserInterface.printResponse("there are not enough cards for tribute");
        else {
            Monster monsterForTribute1, monsterForTribute2;
            UserInterface.printResponse("please select two card to tribute!");
            UserInterface.printResponse("please select the first one");
            monsterForTribute1 = tributeOneMonster();
            UserInterface.printResponse("please select the next one");
            monsterForTribute2 = tributeOneMonster();
            //checking is error happened or not
            if (Objects.isNull(monsterForTribute1) || Objects.isNull(monsterForTribute2))
                return;
            //checking the levels is enough or not
            assert false;
            if (monsterForTribute1.getLevel() + monsterForTribute2.getLevel() < monster.getLevel() && !message.equals("set successfully"))
                UserInterface.printResponse("selected monster levels don`t match with ritual monster");
            else {
                moveMonsterToGraveYard(monsterForTribute1);
                moveMonsterToGraveYard(monsterForTribute2);
                //summon
                summonedMonster(message);
                //check that monster put
                turn.hasPutMonster = true;
            }
        }
    }
    private void moveMonsterToGraveYard(Monster monsterForTribute1) {
        monsterForTribute1.removeMonster(this);
    }
    private void summonLevel6Or5(String message) {
        //get tribute Monster
        Monster monsterForTribute = null;
        //checking if can tribute happened
        if (turn.field.monsterZone.isEmpty()) UserInterface.printResponse("there are not enough cards for tribute");
        else {
            while (Objects.isNull(monsterForTribute)) {
                UserInterface.printResponse("""
                        please select one card to tribute!\s
                         by entering the address by this way\s
                         5 | 3 | 1 | 2 | 4""");
                monsterForTribute = tributeOneMonster();
            }
            moveMonsterToGraveYard(monsterForTribute);
            //summon
            summonedMonster(message);
            //check monster put
            turn.hasPutMonster = true;
        }
    }
    private void summonedMonster(String message) {
        //set turn put the monster
        turn.hasPutMonster = true;
        //change FaceUp
        selectedCard.setSetChanged(true);
        selectedCard.setIsSetThisTurn(true);
        selectedCard.setCardsLocation(Location.MONSTER_AREA);
        if (message.equals("summoned successfully"))
            selectedCard.setCardsFace(FaceUp.ATTACK);
        else
            selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
        //putting card in last monster zone
        turn.field.monsterZone.set(getSizeOfMonsterZone(), selectedCard);
        //delete monster from hand
        turn.field.hand.remove(selectedCard);
        UserInterface.printResponse(message);
    }
    private Monster tributeOneMonster() {
        //selecting card to tribute
        String command = UserInterface.getUserInput();
        //getting card address
        Matcher matcher = Regex.getMatcher(command, Regex.select);

        if (matcher.find()) {
            //get monster
            Monster monsterForTribute = (Monster) turn.field.monsterZone.get(getIndex(Integer.parseInt(matcher.group(1))));
            //checking not empty
            if (Objects.isNull(monsterForTribute))
                UserInterface.printResponse("no card found in the given position");
            //send tribute monster back
            return monsterForTribute;
        } else {
            UserInterface.printResponse(Responses.INVALID_CARD_SELECTION_ADDRESS);
            return null;
        }
    }
    public int getSizeOfMonsterZone() {
        int count = 0;
        for (int i = 0; i < 5; ++i)
            if (turn.field.monsterZone.get(i) != null) count += 1;
        return count;
    }
    public int getSizeOfSpellAndTrapZone() {
        int count = 0;
        for (int i = 0; i < 5; ++i)
            if (turn.field.spellTrapZone.get(i) != null) count += 1;
        return count;
    }
    public void specialSummon(Monster monster) {
        selectedCard = monster;
        summon();
    }
    public void flipSummon() {
        if (Objects.isNull(selectedCard)) UserInterface.printResponse(Responses.NO_CARD_SELECTED_ERROR);
        else if (!turn.field.monsterZone.contains(selectedCard))
            UserInterface.printResponse("you can`t change this card position");
        else if (!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
            UserInterface.printResponse("you can’t do this action in this phase");
        else if (selectedCard.getSetChanged() || selectedCard.getCardsFace() != FaceUp.DEFENSE_BACK)
            UserInterface.printResponse("you can’t flip summon this card");
        else {
            if (selectedCard.getName().equals("Man-Eater Bug")) {
                flipSummonForManEaterBug();
            } else if (selectedCard.getName().equals("Command Knight"))
                summonOrFlipSummonCommandKnight("flip summoned successfully");
            else {
                selectedCard.setCardsFace(FaceUp.ATTACK);
                UserInterface.printResponse("flip summoned successfully");
            }
        }

    }
    public void flipSummonForManEaterBug() {
        int counter = 0;
        for (int i = 0; i < 5; ++i)
            if (opponent.field.monsterZone.get(i) == null)
                counter += 1;

        if (counter == 5)
            UserInterface.printResponse("Your opponent does not have any monster to destroy it.");
        else {
            UserInterface.printResponse("Please select one of these cards to destroy it.");
            for (int i = 0; i < 5; ++i)
                if (opponent.field.monsterZone.get(i) != null)
                    UserInterface.printResponse(opponent.field.monsterZone.get(i).getName() + ":" +
                            opponent.field.monsterZone.get(i).getDescription());

            String name = " ";
            int index = 0;
            while (true) {
                String command = UserInterface.getUserInput();
                for (int i = 0; i < 5; ++i) {
                    if (opponent.field.monsterZone.get(i).getName().equals(command)) {
                        name = command;
                        index = i;
                        break;
                    }
                }
                if (name.equals(" "))
                    UserInterface.printResponse("Insert a valid name please.");
                else
                    break;
            }

            opponent.field.graveYard.add(opponent.field.monsterZone.get(index));
            opponent.field.monsterZone.set(index, null);
            UserInterface.printResponse("Opponents card destroyed successfully.");
            selectedCard.setCardsFace(FaceUp.ATTACK);
            UserInterface.printResponse("Flipped summon successfully.");
            selectedCard = null;
        }
    }
    public void ritualSummon() {
        String command;
        //getting the ritual monster in hand if exist
        Monster ritualMonster = getRitualMonsterInHand();
        //getting the sum of levels in monster zone
        int sumOfLevels = getSumOfLevelsInZone();
        if (Objects.isNull(ritualMonster) || sumOfLevels < 7)
            UserInterface.printResponse("there is no way you could ritual summon a monster");
        else {
            //checking not input another command
            isRitualSummoned = true;
            //get input command
            command = UserInterface.getUserInput();
            //force user to say summon
            while (!command.equals("summon"))
                UserInterface.printResponse("you should ritual summon right now");

            summonLevel8Or7(ritualMonster, "summoned successfully");
        }
    }
    private Monster getRitualMonsterInHand() {
        for (Card card : turn.field.hand) {
            if (card.getCardsType().equals(Type.MONSTER) && ((Monster) card).getCardTypeInExel().equals("Ritual")) {
                return (Monster) card;
            }
        }
        return null;
    }
    private int getSumOfLevelsInZone() {
        int sum = 0;
        for (Card card : turn.field.monsterZone) {
            sum += ((Monster) card).getLevel();
        }
        return sum;
    }

    public void set() {
        if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if (!turn.field.hand.contains(selectedCard))
            UserInterface.printResponse("you can't set this card");
        else if (!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
            UserInterface.printResponse("you can't do this action in this phase");
        else if (selectedCard.getCardsType() == Type.MONSTER) {
            int counter = getSizeOfMonsterZone();
            if (counter == 5)
                UserInterface.printResponse("monster card zone is full");
            else if (turn.hasPutMonster)
                UserInterface.printResponse("you already summoned/set on this turn");
            else if (((Monster) selectedCard).getLevel() == 5 || ((Monster) selectedCard).getLevel() == 6) {
                summonLevel6Or5("set successfully");
                selectedCard = null;
            } else if (((Monster) selectedCard).getLevel() == 7 || ((Monster) selectedCard).getLevel() == 8) {
                summonLevel8Or7((Monster) selectedCard, "set successfully");
                selectedCard = null;
            } else if (selectedCard.getName().equals("Gate Guardian")) {
                summonOrSetGateGuardian("set successfully");
            } else {
                UserInterface.printResponse("set successfully");
                for (int i = 0; i < 5; ++i) {
                    if (turn.field.monsterZone.get(i) == null) {
                        turn.field.monsterZone.set(i, selectedCard);
                        selectedCard.setIsSetThisTurn(true);
                        selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                        selectedCard.setCardsLocation(Location.MONSTER_AREA);
                        turn.field.hand.remove(selectedCard);
                        selectedCard = null;
                        break;
                    }
                }
                turn.hasPutMonster = true;
            }
        } else if (selectedCard.getCardsType() == Type.SPELL || selectedCard.getCardsType() == Type.TRAP) {
            int counter = 0;
            for (int i = 0; i < 5; ++i)
                if (turn.field.spellTrapZone.get(i) != null) counter += 1;
            if (counter == 5)
                UserInterface.printResponse("spell card zone is full");
            else {
                UserInterface.printResponse("set successfully");
                for (int i = 0; i < 5; ++i) {
                    if (turn.field.spellTrapZone.get(i) == null) {
                        turn.field.spellTrapZone.set(i, selectedCard);
                        selectedCard.setIsSetThisTurn(true);
                        selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                        selectedCard.setCardsLocation(Location.SPELL_AREA);
                        turn.field.hand.remove(selectedCard);
                        selectedCard = null;
                        break;
                    }
                }
            }
        }
    }

    //attack
    public void attack(Matcher matcher) {
        int monsterNum = Integer.parseInt(matcher.group(1));
        if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if (!turn.field.monsterZone.contains(selectedCard) || selectedCard.getCardsFace() != FaceUp.ATTACK)
            UserInterface.printResponse("you can’t attack with this card");
        else if (phase != Phase.BATTLE_PHASE) UserInterface.printResponse("you can’t do this action in this phase");
        else if (selectedCard.getIsAttackedThisTurn()) UserInterface.printResponse("this card already attacked");
        else if (getIndex(monsterNum) == -1) UserInterface.printResponse("invalid command");
        else if (opponent.field.monsterZone.get(getIndex(monsterNum)) == null)
            UserInterface.printResponse("there is no card to attack here");
        else if (canWeActiveSpell()) confirmAttack(monsterNum);
        else
            confirmAttack(monsterNum);
    }
    private void confirmAttack(int monsterNum) {
        attackedMonster = (Monster) opponent.field.monsterZone.get(getIndex(monsterNum));
        attackingMonster = (Monster) selectedCard;
        attackedMonsterNum = monsterNum;

        attackingMonster.action(this);
        selectedCard = null;
    }
    public void directAttack() {
        if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if (!turn.field.monsterZone.contains(selectedCard))
            UserInterface.printResponse("you can’t attack with this card");
        else if (phase != Phase.BATTLE_PHASE) UserInterface.printResponse("you can’t do this action in this phase");
        else if (selectedCard.getIsAttackedThisTurn()) UserInterface.printResponse("this card already attacked");
        else if (!isOpponentEmptyOfMonsters()) UserInterface.printResponse("you can’t attack the opponent directly");
        else {
            selectedCard.setISAttackedThisTurn(true);
            Monster monster = (Monster) selectedCard;
            opponent.LP = opponent.LP - monster.getAttack();
            UserInterface.printResponse("your opponent receives " + monster.getAttack() + " battle damage");
            selectedCard = null;
        }
    }
    public int getIndex(int num) {
        //1 in index 2
        if (num == 1) return 2;
        else if (num == 2) return 3;
        else if (num == 3) return 1;
        else if (num == 4) return 4;
        else if (num == 5) return 0;
        else return -1;
    }
    public int getIndexOfSelectedCardInMonsterZone() {
        for (int i = 0; i < 5; i++)
            if (selectedCard == turn.field.monsterZone.get(i)) return i;
        return -1;
    }
    public boolean isOpponentEmptyOfMonsters() {
        for (Card card : opponent.field.monsterZone)
            if (card != null) return false;
        return true;
    }

    //activeSpell
    public void activeSpell(String how) {
        SpellAndTrap spellAndTrap;
        if (Objects.isNull(selectedCard)) UserInterface.printResponse(Responses.NO_CARD_SELECTED_ERROR);
//        else if (!selectedCard.getCardsType().equals(Type.SPELL))
//            UserInterface.printResponse("active effect is only for spell cards.");
        else if (!phase.equals(Phase.MAIN1_PHASE))
            UserInterface.printResponse("you cant active an effect on this turn");
        else {
            spellAndTrap = (SpellAndTrap) selectedCard;
            if (activeSpellAndTraps.contains(spellAndTrap))
                UserInterface.printResponse("you have already activate this card");
            else if (getSizeOfSpellAndTrapZone() == 5)
                UserInterface.printResponse("spell card zone is full");
            else if (!canWeActiveSpell())
                UserInterface.printResponse("preparation of this spell are not done yet");
            else {
                activeSpellAndTraps.add(spellAndTrap);
                spellAndTrap.action(this);
//                if (how.equals("firstTime"))
//                    turn.field.spellTrapZone.set(getSizeOfSpellAndTrapZone() + 1, selectedCard);
            }
        }
    }
    //checking for that if that spell has a role that we can`t activate it
    private boolean canWeActiveSpell() {
        for (Card card : opponent.field.spellTrapZone) {
            if (!Objects.isNull(card)) {
                String name = card.getName();
                //preventing a true while
                if (Objects.isNull(currSpell) || !currSpell.getName().equals(card.getName())) {
                    if (name.equals("Harpie’s Feather Duster") ||
                            name.equals("Twin Twisters") ||
                            name.equals("Mystical space typhoon") ||
                            name.equals("Ring of Defense") ||
                            name.equals("Magic Jammer")) {
                        currSpell = (SpellAndTrap) card;
                        return isOpponentActiveSpellOrTrap();
                    }
                }
            }
        }
        return true;
    }
    private boolean isOpponentActiveSpellOrTrap() {
        //change turn
        UserInterface.printResponse("Ask" + opponent.getName() + "if want to active spell");
        changeTurn();
        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Active Spell or trap for Chain?","Make chain!",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.NO_OPTION){
            UserInterface.printResponse("turn will be back to " + opponent.getName());
            changeTurn();
            return true;
        }
        else {
            UserInterface.printResponse("choose the spell or trap you want to active");
            MakeChainScene(turn);
            changeTurn();
            game.addChanges();
        }
        return false;
    }

    private void MakeChainScene(Duelist duelist) {
        BorderPane root = new BorderPane();
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                "-fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white;");
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currStage.close();
            }
        });
        cancelBtn.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(cancelBtn);
        hBox.setAlignment(Pos.CENTER);


        javafx.scene.control.ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<Card> spellAndTrapCards = duelist.field.spellTrapZone;

        for (int i = 0; i<spellAndTrapCards.size(); ++i){
            String name = spellAndTrapCards.get(i).getName();
            if (name.equals("Harpie’s Feather Duster") ||
                        name.equals("Twin Twisters") ||
                        name.equals("Mystical space typhoon") ||
                        name.equals("Ring of Defense") ||
                        name.equals("Magic Jammer")) {
                Image image2 = new Image(Objects.requireNonNull(this.getClass().getResource("SpellTrap/" +
                        spellAndTrapCards.get(i).getName().replace(" ","") + ".jpg")).toExternalForm(), 230, 230, false, false);
                ImageView imageView2 = new ImageView(image2);
                int finalI = i;
                imageView2.setOnMouseClicked(e->{
                    if(canWeActiveSpell())
                        spellAndTrapCards.get(finalI).action(this);
                });
                gridPane.add(imageView2, i%2, i/2);
            }
        }
        scrollPane.setContent(vBox);
        Label lblTitle = new Label("If you want to make chain Select one spell or trap to active it Or click cancel!");
        lblTitle.setAlignment(Pos.CENTER);
        root.setTop(lblTitle);
        root.setCenter(scrollPane);
        Scene scene = new Scene(root,500,600);
        subStage stage = new subStage("Chain",scene);
        currStage = stage.getStage();
    }

    private void phaseController() {
        int lastChangedTurn = 0;
        //checking just one time turn changed
        if (changedTurnTime - lastChangedTurn == 1 && phase.equals(Phase.DRAW_PHASE)) {
            //changing scanner
            if (monsterChangedWithScanner != 0) {
                Monster monster = (Monster) turn.field.monsterZone.get(monsterChangedWithScanner);
                monster.removeMonster(this);
                turn.field.monsterZone.set(monsterChangedWithScanner, new Scanner(Card.allCards.get("Scanner")));
            } else if (!Objects.isNull(getSpellFromActiveSpells("Messenger of peace"))) {
                if (phase.equals(Phase.STANDBY_PHASE))
                    turn.LP -= 100;
            } else if (!Objects.isNull(getSpellFromActiveSpells("Supply Squad"))) {
                ((SupplySquad) Objects.requireNonNull(getSpellFromActiveSpells("Supply Squad"))).isFirsTimeCalled = true;
            }
        } else {
            //action all spells based on which phase they want
            for (SpellAndTrap spellAndTrap : activeSpellAndTraps)
                spellAndTrap.action(this);
            if (phase.equals(Phase.END_TURN))
                lastChangedTurn = changedTurnTime;
        }
    }
    private SpellAndTrap getSpellFromActiveSpells(String name) {
        for (SpellAndTrap spellAndTrap : activeSpellAndTraps) {
            if (spellAndTrap.getName().equals(name))
                return spellAndTrap;
        }
        return null;
    }

    //cheats
    public void forceAddedToHand(Matcher matcher) {
        String cardName = matcher.group("cardName");
        turn.field.hand.add(Card.getCardByName(cardName));
    }
    public void duelWinCheat(Matcher matcher) {
        String name = matcher.group("nickname");
        if (turn.getName().equals(name))
            winner = turn;
        else if (opponent.getName().equals(name))
            winner = opponent;
    }
    public void increaseLPCheat(Matcher matcher){
        int amount = Integer.parseInt(matcher.group(1));
        turn.LP += amount ;
    }

    public void rotateBoard(){
        Image imageBase = new Image(Objects.requireNonNull(this.getClass().getResource("elements/deck.png")).toExternalForm());
        Image image;

        GraphicsContext graphicsContext = game.getMainGraphic();
        graphicsContext.drawImage(game.getBackGroundIMG(),0,0,500,450);

        if (turn.field.monsterZone.get(0) != null)
            ImageAdapter.setMonsterOn4Rival(graphicsContext, imageBase);
        else if (turn.field.monsterZone.get(1) != null)
            ImageAdapter.setMonsterOn2Rival(graphicsContext, imageBase);
        else if (turn.field.monsterZone.get(2) != null)
            ImageAdapter.setMonsterOn1Rival(graphicsContext, imageBase);
        else if (turn.field.monsterZone.get(3) != null)
            ImageAdapter.setMonsterOn3Rival(graphicsContext, imageBase);
        else if (turn.field.monsterZone.get(4) != null)
            ImageAdapter.setMonsterOn5Rival(graphicsContext, imageBase);

        if (opponent.field.monsterZone.get(0) != null) {
            image = getImageOfOpponent(0);
            ImageAdapter.setMonsterOn5(graphicsContext, image);
        }
        else if (opponent.field.monsterZone.get(1) != null) {
            image = getImageOfOpponent(1);
            ImageAdapter.setMonsterOn3(graphicsContext, image);
        }
        else if (opponent.field.monsterZone.get(2) != null) {
            image = getImageOfOpponent(2);
            ImageAdapter.setMonsterOn2(graphicsContext, image);
        }
        else if (opponent.field.monsterZone.get(3) != null) {
            image = getImageOfOpponent(3);
            ImageAdapter.setMonsterOn1(graphicsContext, image);
        }
        else if (opponent.field.monsterZone.get(4) != null) {
            image = getImageOfOpponent(4);
            ImageAdapter.setMonsterOn5(graphicsContext, image);
        }
        game.initGraveYardAndFieldZone();
    }

    private Image getImageOfOpponent(int i) {
        return new Image(Objects.requireNonNull(this.getClass().getResource("Monsters/" + opponent.field.monsterZone.get(i).getName().replace(" ","") + ".jpg")).toExternalForm(), 275, 275, false, false);
    }
}
