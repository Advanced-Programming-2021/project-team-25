package controllers.Battelfield;

import controllers.Regex;
import controllers.ShowCard;
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
import view.Responses;
import view.UserInterface;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;

import static controllers.ShowCard.showCard;

public class Battlefield{

    public ArrayList<SpellAndTrap> activeSpellAndTraps = new ArrayList<>();
    public HashMap<Duelist,Socket> connectedDuelists = new HashMap<>();
    public int roundToPlay;
    private Duelist turn;
    private Duelist opponent;
    public Duelist duelist1;
    public Duelist duelist2;
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
    public boolean isEndGame = false;
    public Object inputObj = null;
    public OutputStream outputStream1;
    public ObjectOutputStream objectOutputStream1;
    public InputStream inputStream1;
    public ObjectInputStream objectInputStream1;
    public OutputStream outputStream2;
    public ObjectOutputStream objectOutputStream2;
    public InputStream inputStream2;
    public ObjectInputStream objectInputStream2;

    public void run(){
        while(!isEndGame) {
            if(winner != null)
                return;
        }

    }
    public Battlefield(Duelist duelist1, Duelist duelist2, Socket socDuelist1, Socket socDuelist2) {
        this.duelist1 = duelist1;
        this.duelist2 = duelist2;
        connectedDuelists.put(duelist1,socDuelist1);
        connectedDuelists.put(duelist2,socDuelist2);
        whoStart(duelist1, duelist2);
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


    //start & clean
    private void whoStart(Duelist duelist1, Duelist duelist2) {
        Random ran = new Random();
        if (ran.nextInt(2) == 0){
            turn = duelist1;
            opponent = duelist2;
        }
        else {
            turn = duelist2;
            opponent = duelist1;
        }
    }

    public void setOpponent(Duelist opponent) {
        this.opponent = opponent;
    }

    public void setTurn(Duelist turn) {
        this.turn = turn;
    }

    public void specialSummon(Monster monster) {
    }

    public int getIndex(int attackedMonsterNum) {
        return 0;
    }

    public int getIndexOfSelectedCardInMonsterZone() {
        return 0;
    }

    public void ritualSummon() {
    }

    public void set() {
    }

    public void selectCard(Matcher matcher) {
    }

    public void summon() {
    }
}