package controllers.Battelfield;

import models.Card;
import models.Duelist;

import java.util.Random;

public class Battlefield {
    private Duelist turn;
    private Duelist opponent;
    private Duelist winner;
    private Card selectedCard;
    private Phase phase;

    public Battlefield(Duelist duelist, Duelist duelist1) {
        Random ran = new Random();
        if(ran.nextInt(2) == 0){
            turn = duelist;
            opponent = duelist1;
        }
        else {
            turn = duelist1;
            opponent = duelist;
        }
        runBattleField();
    }

    public void runBattleField(){

    }

    public void showBattleField(){

    }
    public void selectCard(){

    }
    public void deselectCard(){

    }
    public void nextPhase(){

    }
    public void summon(){

    }
    public void set(){

    }
    public void setPosition(){

    }
    public void flipSummon(){

    }
    public void attack(){

    }
    public void directAttack(){

    }
    public void activeSpell(){

    }
    public void ritualSummon(){

    }
    public void showGraveyard(){

    }
    public void showCard(){

    }
    public void surrender(){

    }
    public void getWinner(){

    }

    public void setWinner(Duelist winner) {
        this.winner = winner;
    }
    public void changeTurn(){

    }
    public void startGame(){

    }
    public void drawCard(){

    }
    public void cleanTurn(){

    }
}
