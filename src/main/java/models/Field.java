package models;

import java.util.ArrayList;

public class Field {
    public ArrayList<Card> deck = new ArrayList<>();
    public ArrayList<Card> graveYard = new ArrayList<>();
    public ArrayList<Card> monsterZone = new ArrayList<>();
    public ArrayList<Card> spellTrapZone = new ArrayList<>();
    public ArrayList<Card> hand = new ArrayList<>();
    public Card fieldZone = null;


    public Field (){
        for (int i = 0; i<5; ++i){
            monsterZone.add(null);
            spellTrapZone.add(null);
        }
        for (int i = 0; i<6; ++i){
            hand.add(null);
        }
    }
}
