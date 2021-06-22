package controllers.Battelfield;

import controllers.Regex;
import controllers.ShowCard;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.util.*;
import java.util.regex.Matcher;

public class Battlefield {

    public ArrayList<SpellAndTrap> activeSpellAndTraps = new ArrayList<>();
    private Duelist turn;
    private Duelist opponent;
    private Phase phase = Phase.DRAW_PHASE;
    private boolean isRitualSummoned = false;
    public Duelist winner;
    private boolean isTurnChanged = false;
    private int countDraw6Cards = 0;
    private SpellAndTrap currSpell = null;
    public Card selectedCard;
    public int changedTurnTime = 0;
    public Monster attackingMonster;
    public Monster attackedMonster;
    public int monsterChangedWithScanner = 0;
    public int attackedMonsterNum;
    private Game game;

    public Battlefield(Duelist duelist1, Duelist duelist2) {
        whoStart(duelist1, duelist2);
        game = new Game(turn,opponent);
        game.runGame();
        startGame();
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
    public Phase getPhase() {
        return phase;
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
        Image image = new Image(Objects.requireNonNull(this.getClass().getResource("Monsters/" + turn.field.deck.get(0).getName().replace(" ","") + ".jpg")).toExternalForm(), 275, 275, false, false);
        GraphicsContext mainGraphic = game.getMainGraphic();
        ImageAdapter.setMonsterOn5(mainGraphic,image);
        ImageAdapter.setMonsterOn4(mainGraphic,image);
        ImageAdapter.setMonsterOn3(mainGraphic,image);
        ImageAdapter.setMonsterOn2(mainGraphic,image);
        ImageAdapter.setMonsterOn1(mainGraphic,image);
        ImageAdapter.setSpellOrTrapOn5(mainGraphic,image);
        ImageAdapter.setSpellOrTrapOn4(mainGraphic,image);
        ImageAdapter.setSpellOrTrapOn3(mainGraphic,image);
        ImageAdapter.setSpellOrTrapOn2(mainGraphic,image);
        ImageAdapter.setSpellOrTrapOn1(mainGraphic,image);

        countDraw6Cards++;
        //shuffling the cards
        Collections.shuffle(turn.field.deck);
        //draw 6 cards for opponent and turn
        for (int i = 0; i < 6; i++)
            addCardToPlayersHands(turn,i);
    }
    public void drawImageOnXY(int x,int y,Image image){
        GraphicsContext mainGraphic = game.getMainGraphic();
        mainGraphic.drawImage(image,85+68*x,230+82*y,75,80);
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
   /* private void addCardToPlayersHands(Duelist turn,int i) {

       *//* turn.field.hand.add(turn.field.deck.get(0));
        Image image2;
        ImageView img;
        Card card = turn.field.deck.get(0);
        if(turn.field.deck.get(0).getCardsType().equals(Type.MONSTER))
            image2 = new Image(Objects.requireNonNull(this.getClass().getResource("Monsters/" + turn.field.deck.get(0).getName().replace(" ", "") + ".jpg")).toExternalForm(), 50, 100, false, false);
        else
            image2 = new Image(Objects.requireNonNull(this.getClass().getResource("SpellTrap/" + turn.field.deck.get(0).getName().replace(" ","") + ".jpg")).toExternalForm(), 50, 100, false, false);
        img = new ImageView(image2);
        img.setOnMouseClicked(event -> {
            UserInterface.printResponse("Set Or Summon");
            String num = UserInterface.getUserInput();
            if (num.equals("Set")) {
                selectedCard = card;
                set();
            } else {
                selectedCard = card;
                summon();
            }
        });
        //graphic.drawImage(image2, 120 * i,10, 200, 70);
        game.hand.getChildren().add(img);
        turn.field.deck.remove(0);*//*
    }*/
    private void addCardToPlayersHands(Duelist turn,int i) {
        turn.field.hand.add(turn.field.deck.get(0));

        GraphicsContext graphic = game.getGraphicHand1();
        Image image2;
        if(turn.field.deck.get(0).getCardsType().equals(Type.MONSTER))
            image2 = new Image(Objects.requireNonNull(this.getClass().getResource("Monsters/" + turn.field.deck.get(0).getName().replace(" ","") + ".jpg")).toExternalForm(), 275, 275, false, false);
        else
            image2 = new Image(Objects.requireNonNull(this.getClass().getResource("SpellTrap/" + turn.field.deck.get(0).getName().replace(" ","") + ".jpg")).toExternalForm(), 275, 275, false, false);
        graphic.drawImage(image2, 120 * i,10, 200, 70);

        turn.field.deck.remove(0);
    }
    public void drawCard() {
        if (turn.field.deck.size() > 0) {
            if (changedTurnTime >= 2 && turn.field.hand.size()<6) {
                addCardToPlayersHands(turn,turn.field.hand.size());
            }
        } else winner = opponent;
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
        else if (phase == Phase.STANDBY_PHASE) phase = Phase.MAIN1_PHASE;
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
        if (opponent.getName().equals("admin")) {
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
        else if (!turn.field.hand.contains(selectedCard)
                || !(selectedCard.getCardsType() == Type.MONSTER))
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
                //check that monster put
                turn.hasPutMonster = true;
                selectedCard = null;
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

        if (message.equals("summon successfully")) {
            for (int i = 0; i < 5; ++i)
                if (turn.field.monsterZone.get(i) == null) {
                    turn.field.monsterZone.set(i, selectedCard);
                    turn.field.hand.remove(selectedCard);
                    selectedCard.setIsSetThisTurn(true);
                    turn.hasPutMonster = true;
                    selectedCard.setCardsFace(FaceUp.ATTACK);
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

    public void setPosition(Matcher matcher) {
        if (matcher.group(1).equals("attack")) {
            if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
            else if (!turn.field.monsterZone.contains(selectedCard))
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
        } else if (matcher.group(1).equals("defence")) {
            if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
            else if (!turn.field.monsterZone.contains(selectedCard))
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
            System.out.println("your opponent receives " + monster.getAttack() + " battle damage");
            selectedCard = null;
        }
    }
    public int getIndex(int num) {
        if (num == 1) return 2;
        else if (num == 2) return 1;
        else if (num == 3) return 3;
        else if (num == 4) return 0;
        else if (num == 5) return 4;
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
    public void activeSpell() {
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
                spellAndTrap.action(this);
                turn.field.spellTrapZone.set(getSizeOfSpellAndTrapZone() + 1, selectedCard);
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
        UserInterface.printResponse("now it will be " + opponent.getName() + "’s turn");
        changeTurn();
        showBattleField();
        UserInterface.printResponse("do you want to activate your trap or spell?" + "\n enter yes or no");
        String yesOrNo = UserInterface.getUserInput();
        //decide what to do
        if (yesOrNo.equals("no")) {
            UserInterface.printResponse("now it will be " + opponent.getName() + "’s turn");
            changeTurn();
            showBattleField();
            return true;
        } else if (yesOrNo.equals("yes")) {
            UserInterface.printResponse("enter number of Trap house : " + "or type \"cancel\" by numbering 5 | 3 | 1 | 2 | 4");
            while (true) {
                //get user input
                String input = UserInterface.getUserInput();
                if (input.equals("cancel")) break;
                //get spell or trap
                int num = Integer.parseInt(input);
                SpellAndTrap spellAndTrap = (SpellAndTrap) turn.field.spellTrapZone.get(num);
                //checking not null
                if (Objects.isNull(spellAndTrap)) {
                    UserInterface.printResponse("try again");
                    continue;
                }
                String trapName = spellAndTrap.getName();
                if (trapName.equals("Magic Cylinder") || trapName.equals("Mirror Force") || trapName.equals("Torrential Tribute")) {
                    turn.field.spellTrapZone.get(getIndex(num)).action(this);
                    turn.field.graveYard.add(turn.field.spellTrapZone.get(num));
                    turn.field.spellTrapZone.set(getIndex(num), null);
                    UserInterface.printResponse("Trap activated");
                } else UserInterface.printResponse("it’s not your turn to play this kind of moves\n try again!");
                //make chain
                if (canWeActiveSpell())
                    spellAndTrap.action(this);
            }
        }
        return false;
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
    private void forceAddedToHand(Matcher matcher) {
        String cardName = matcher.group("cardName");
        turn.field.hand.add(Card.getCardByName(cardName));
    }
    private void duelWinCheat(Matcher matcher) {
        String name = matcher.group("nickname");
        if (turn.getName().equals(name))
            winner = turn;
        else if (opponent.getName().equals(name))
            winner = opponent;

    }

    private void increaseLPCheat(Matcher matcher){
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
