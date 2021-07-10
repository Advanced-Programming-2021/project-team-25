package controllers.Battelfield;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
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

import view.menus.Game;
import view.Responses;
import view.UserInterface;
import view.menus.subStage;

import javax.swing.*;
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
    public int round;
    public int selectedCardPosition;
    public Card tributeCard = null;
    public ArrayList<Card> tributeCards = new ArrayList<>();
    public ArrayList<Integer> tributeCardsPosition = new ArrayList<>();
    public Button tributeBtn = new Button("Tribute");
    public boolean isSpellSelected = false;
    //needed to be active when user click on summon btn@!
    public Battlefield(Duelist duelist1, Duelist duelist2, int round) {
        this.round = round;
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

    //start & clean
    private void whoStart(Duelist duelist1, Duelist duelist2) {
        Random ran = new Random();
        if (ran.nextInt(2) == 0) chooseStarter(duelist2, duelist1);
        else chooseStarter(duelist1, duelist2);
    }
    private void chooseStarter(Duelist duelist1, Duelist duelist2) {
        if (!duelist1.getName().equals("Game") && !duelist2.getName().equals("Game")) {
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
        else{
            if (duelist1.getName().equals("Game")){
                turn = duelist2;
                opponent = duelist1;
            }
            else{
                turn = duelist1;
                opponent = duelist2;
            }
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
        turn.field.hand.set(0, Card.getCardByName("United We Stand"));
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

            javafx.scene.control.Button attackBtn = new javafx.scene.control.Button("Attack");
            attackBtn.setOnMouseClicked(e-> {
                AudioClip audioClip = new AudioClip(getClass().getResource("/music/CARD_CRASH.mp3").toExternalForm());
                audioClip.play();
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
            if(!isSpellSelected)
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

            tributeBtn.setOnMouseClicked(e->{
                if(!tributeCardsPosition.contains(selectedCardPosition)){
                    tributeCards.add(selectedCard);
                    tributeCardsPosition.add(selectedCardPosition);
                    UserInterface.printResponse("Ok one tribute selected!");
                }
                else
                    UserInterface.printResponse("you already selected this card!");

                currStage.close();
                game.mouseEventClick();
            });

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
                Button summonBtn = new Button("Active");
                summonBtn.setOnAction(actionEvent -> {
                    activeSpell("notFirstTime");
                    game.addChanges();
                    currStage.close();
                });
                VBox vBox = new VBox();
                vBox.getChildren().addAll(lblAction, summonBtn);
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
            //tributeBtn.setDisable(true);

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
        } else if (x >= 379 && x <= 448 && y >= 258 && y <= 325 && turn.field.monsterZone.get(3) != null) {
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
        }
        else
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
        vboxLeft.getChildren().addAll(hBox1,hBox2,hBox3,tributeBtn);
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
    public void summon(int position) {

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
            else if (monster.getName().equals("Beast King Barbaros")) {
                summonKingBarbaros(monster, position);
            }
            //exception for gate guardian
            else if (monster.getName().equals("Gate Guardian")) {
                summonOrSetGateGuardian("summoned successfully", position);
            }
            else if (monster.getName().equals("Command Knight")) {
                summonOrFlipSummonCommandKnight("summoned successfully", position);
            }
            //summon level 5 or 6 monsters
            else if (monster.getLevel() == 5 || monster.getLevel() == 6) {
                summonLevel6Or5("summoned successfully",position);
                selectedCard = null;
            }
            //summon level 7 , 8 monsters
            else if (monster.getLevel() == 7 || monster.getLevel() == 8) {
                summonLevel8Or7(monster, "summoned successfully",position);
                selectedCard = null;
            }
            //normal summon
            else if (monster.getLevel() <= 4) {
                summonedMonster("summoned successfully",position);
            }
        }
    }
    private void summonKingBarbaros(Monster monster,int position) {
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
            } else if (command.equals("N")) summonLevel8Or7(monster, "summoned successfully",position);
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        } else UserInterface.printResponse(Responses.INVALID_COMMAND);

    }
    private void getThreeMonsterForTribute() {
        if (getSizeOfMonsterZone() < 3) UserInterface.printResponse("not enough monster");
        else {
            Monster monsterForTribute1 = null, monsterForTribute2 = null, monsterForTribute3 = null;
            if(tributeCards.size() >= 3){
                monsterForTribute1 = (Monster)tributeCards.get(0);
                monsterForTribute2 = (Monster)tributeCards.get(1);
                monsterForTribute3 = (Monster)tributeCards.get(2);
                tributeCards.clear();
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
            else
                UserInterface.printResponse("please select three monster for tribute");

        }
    }
    public void summonOrSetGateGuardian(String message,int position) {
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


            if (turn.field.monsterZone.get(position) == null) {
                turn.field.monsterZone.set(position, selectedCard);
                turn.field.hand.remove(selectedCard);
                selectedCard.setIsSetThisTurn(true);
                turn.hasPutMonster = true;
                if (message.equals("summon successfully"))
                    selectedCard.setCardsFace(FaceUp.ATTACK);
                else
                    selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                selectedCard = null;
                UserInterface.printResponse(message);

            }
            else
                UserInterface.printResponse("place is full!");
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
    public void summonOrFlipSummonCommandKnight(String message,int position) {
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

            if (turn.field.monsterZone.get(position) == null) {
                turn.field.monsterZone.set(position, commandKnight);
                turn.field.hand.remove(commandKnight);
                commandKnight.setIsSetThisTurn(true);
                turn.hasPutMonster = true;
                commandKnight.setCardsFace(FaceUp.ATTACK);
                selectedCard = null;
                UserInterface.printResponse(message);
            }
            else
                UserInterface.printResponse("place is full!");
        } else {
            selectedCard.setCardsFace(FaceUp.ATTACK);
            UserInterface.printResponse(message);
        }

    }
    private void summonLevel8Or7(Monster monster, String message,int position) {
        //checking if can tribute happened
        if (getSizeOfMonsterZone() < 2) UserInterface.printResponse("there are not enough cards for tribute");
        else {
            Monster monsterForTribute1 = null, monsterForTribute2 = null;
            if(tributeCards.size() >= 2 ){
                monsterForTribute1 = (Monster)tributeCards.get(0);
                monsterForTribute2 = (Monster)tributeCards.get(1);
                tributeCards.clear();
                //checking is error happened or not
                if (Objects.isNull(monsterForTribute1) || Objects.isNull(monsterForTribute2))
                    return;
                //checking the levels is enough or not
                assert false;
                if (monsterForTribute1.getLevel() + monsterForTribute2.getLevel() < monster.getLevel() && !message.equals("set successfully"))
                    UserInterface.printResponse("selected monster levels don`t match with ritual monster");
                else {
                    monsterForTribute1.removeMonster(this);
                    monsterForTribute2.removeMonster(this);
                    //summon
                    summonedMonster(message,position);
                    //check that monster put
                    turn.hasPutMonster = true;
                }
            }
            else
                UserInterface.printResponse("please select two card for tribute");
        }
    }

    private void summonLevel6Or5(String message,int position) {
        //get tribute Monster
        Monster monsterForTribute = null;
        //checking if can tribute happened
        if (turn.field.monsterZone.isEmpty()) UserInterface.printResponse("there are not enough cards for tribute");
        else {
            if(tributeCards.size()>=1){
                monsterForTribute = (Monster) tributeCards.get(0);
                tributeCards.clear();
                monsterForTribute.removeMonster(this);
                //summon
                summonedMonster(message,position);
                //check monster put
                turn.hasPutMonster = true;
            }
            else
                UserInterface.printResponse("please select one card for tribute");

        }
    }
    private void summonedMonster(String message,int position) {
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
        turn.field.monsterZone.set(position, selectedCard);
        //delete monster from hand
        turn.field.hand.remove(selectedCard);
        UserInterface.printResponse(message);
    }
    private void tributeOneMonster() {
        if(tributeCard!=null) {
            tributeCards.add(tributeCard);
            tributeCard = null;
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
        summon(game.dragPosition);
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
                summonOrFlipSummonCommandKnight("flip summoned successfully",game.dragPosition);
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
        int position = game.dragPosition;
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

            summonLevel8Or7(ritualMonster, "summoned successfully",position);
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

    public void set(int position) {
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
                summonLevel6Or5("set successfully",position);
                selectedCard = null;
            } else if (((Monster) selectedCard).getLevel() == 7 || ((Monster) selectedCard).getLevel() == 8) {
                summonLevel8Or7((Monster) selectedCard, "set successfully",position);
                selectedCard = null;
            } else if (selectedCard.getName().equals("Gate Guardian")) {
                summonOrSetGateGuardian("set successfully",position);
            } else {
                UserInterface.printResponse("set successfully");
                    if (turn.field.monsterZone.get(position) == null) {
                        turn.field.monsterZone.set(position, selectedCard);
                        selectedCard.setIsSetThisTurn(true);
                        selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                        selectedCard.setCardsLocation(Location.MONSTER_AREA);
                        turn.field.hand.remove(selectedCard);
                        selectedCard = null;
                    }
                    else
                        UserInterface.printResponse("place is full!");
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
                if (turn.field.spellTrapZone.get(position) == null) {
                    turn.field.spellTrapZone.set(position, selectedCard);
                    selectedCard.setIsSetThisTurn(true);
                    selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                    selectedCard.setCardsLocation(Location.SPELL_AREA);
                    turn.field.hand.remove(selectedCard);
                    selectedCard = null;
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
        else if (!selectedCard.getCardsType().equals(Type.SPELL))
            UserInterface.printResponse("active effect is only for spell cards.");
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
                spellAndTrap.setCardsFace(FaceUp.ATTACK);
                spellAndTrap.action(this);
                if (how.equals("firstTime"))
                    turn.field.spellTrapZone.set(getSizeOfSpellAndTrapZone(), selectedCard);
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
            for (SpellAndTrap spellAndTrap : activeSpellAndTraps) {
                if(spellAndTrap.getName().equals("United We Stand") || spellAndTrap.getName().equals("Magnum Shield") ||
                        spellAndTrap.getName().equals("Black Pendant") || spellAndTrap.getName().equals("Sword of Dark Destruction"))
                    continue;
                spellAndTrap.action(this);
            }
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
}