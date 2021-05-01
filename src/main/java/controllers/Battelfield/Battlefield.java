package controllers.Battelfield;

import controllers.Menu;
import controllers.Regex;
import controllers.ShowCard;
import models.Card;
import models.CardStufs.FaceUp;
import models.Deck;
import models.Duelist;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;
import static controllers.ShowCard.showCard;

public class Battlefield {

    private Duelist turn;
    private Duelist opponent;
    private Duelist winner;
    private Card selectedCard;
    private Phase phase;
    private Deck opponentDeckInGame;
    private Deck turnDeckInGame;

    public Battlefield(Duelist duelist1, Duelist duelist2) {
        whoStart(duelist1, duelist2);
        startGame();
        runBattleField();
    }

    public void runBattleField(){
        while (winner == null) {
            String command = UserInterface.getUserInput();
            Matcher matcher;

            if ((matcher = Regex.getMatcher(command, Regex.select)).matches()) selectCard(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.selectOpponent)).matches()) selectOpponentCard(matcher);
            else if (Regex.getMatcher(command, Regex.deselect).matches()) deselectCard();
            else if (Regex.getMatcher(command, Regex.nextPhase).matches()) nextPhase();
            else if (Regex.getMatcher(command, Regex.summon).matches()) summon();
            else if (Regex.getMatcher(command, Regex.set).matches()) set();
            else if ((matcher = Regex.getMatcher(command, Regex.setPosition)).matches()) setPosition(matcher);
            else if (Regex.getMatcher(command, Regex.flipSummon).matches()) flipSummon();
            else if ((matcher = Regex.getMatcher(command, Regex.attack)).matches()) attack(matcher);
            else if (Regex.getMatcher(command, Regex.attackDirect).matches()) directAttack();
            else if (Regex.getMatcher(command, Regex.activateEffect).matches()) activeSpell();
            else if (Regex.getMatcher(command, Regex.showGraveyard).matches()) showGraveyard();
            else if ((matcher=Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher.group(1));
            else if (Regex.getMatcher(command, Regex.showSelectedCard).matches()) showSelectedCard();
            else if (Regex.getMatcher(command, Regex.surrender).matches()) surrender();
            //else if (Regex.getMatcher(command, Regex.cancel).matches())
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
            showBattleField();
        }
    }

    private void whoStart(Duelist duelist1, Duelist duelist2) {
        Random ran = new Random();
        if(ran.nextInt(2) == 0){
            turn = duelist1;
            opponent = duelist2;
        }
        else {
            turn = duelist2;
            opponent = duelist1;
        }
    }

    public void startGame(){
        if(opponent.field.hand.isEmpty() || turn.field.hand.isEmpty()){
            //shuffling the cards
            Collections.shuffle(opponent.field.deck);
            Collections.shuffle(turn.field.deck);
            //draw 6 cards
            for(int i=0;i<6;i++){

                opponent.field.hand.add(opponent.field.deck.get(i));
                opponent.field.deck.remove(opponent.field.deck.get(i));

                turn.field.hand.add(turn.field.deck.get(i));
                turn.field.deck.remove(turn.field.deck.get(i));
            }
        }
        //lp = 8000
        //cleanTurn();


    }

    public void showBattleField(){
        UserInterface.printResponse(opponent.getName() + " : " + opponent.LP);
        for (Card card: opponent.field.hand) System.out.print("c\t");
        System.out.println("");
        UserInterface.printResponse(opponent.field.deck.size() + "");

        System.out.print("\t");
        for(int i = 5 ; i > 0 ; i--) showSpellAndTrapsZone(i , opponent);

        System.out.print("\t");
        for(int i = 5 ; i > 0 ; i--) showMonsterZone(i, opponent);

        System.out.print(opponent.field.graveYard.size() + "\t\t\t\t\t\t");
        if(opponent.field.fieldZone == null ) System.out.print("O\n");
        else System.out.print("E\n");

        UserInterface.printResponse("____________________________________________");

        if(turn.field.fieldZone == null ) System.out.print("O\n");
        else System.out.print("E\n");
        System.out.print("\t\t\t\t\t\t" + turn.field.graveYard.size());

        System.out.print("\t");
        for(int i = 0 ; i < 5 ; i++) showMonsterZone(i, turn);

        System.out.print("\t");
        for(int i = 0 ; i < 5 ; i++) showSpellAndTrapsZone(i , turn);

        UserInterface.printResponse( "\t\t\t\t\t\t" + turn.field.deck.size());
        for (Card card: turn.field.hand) System.out.print("c\t");
        System.out.println("");

        UserInterface.printResponse(turn.getName() + " : " + turn.LP);
    }

    private void showSpellAndTrapsZone(int i , Duelist duelist) {
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

    public void selectCard(Matcher matcher){

    }

    public void selectOpponentCard(Matcher matcher){

    }

    public void deselectCard(){
        if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else{
            UserInterface.printResponse("card deselected");
            selectedCard = null;
        }
    }

    public void nextPhase(){
        if( phase == Phase.DRAW_PHASE ) phase = Phase.STANDBY_PHASE;
        else if( phase == Phase.STANDBY_PHASE ) phase = Phase.MAIN1_PHASE;
        else if( phase == Phase.MAIN1_PHASE ) phase = Phase.BATTLE_PHASE;
        else if( phase == Phase.BATTLE_PHASE ) phase = Phase.MAIN2_PHASE;
        //check needed for the END PHASE
        else if( phase == Phase.MAIN2_PHASE ){
            changeTurn();
            cleanTurn();
            phase = Phase.DRAW_PHASE;
            UserInterface.printResponse("its " + turn.getName() + "’s turn");
        }
        UserInterface.printResponse("phase: " + phase);
        if (phase == Phase.DRAW_PHASE ) drawCard();
    }

    public void summon(){

    }
    public void set(){

    }
    public void setPosition(Matcher matcher){

    }
    public void flipSummon(){

    }
    public void attack(Matcher matcher){

    }
    public void directAttack(){

    }
    public void activeSpell(){

    }
    public void ritualSummon(){

    }
    public void showGraveyard(){

    }

    public void showSelectedCard(){
        ShowCard.showCard(selectedCard.getName());
    }

    public void surrender(){

    }

    public void changeTurn(){
        Duelist temp;
        temp = turn;
        turn = opponent;
        opponent = temp;
    }

    public void cleanTurn(){

    }

    public void drawCard(){

    }

}
