package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {

    public ArrayList<Card> deck = new ArrayList<>();
    public ArrayList<Card> graveYard = new ArrayList<>();
    public ArrayList<Card> monsterZone = new ArrayList<>();
    public ArrayList<Card> spellTrapZone = new ArrayList<>();
    public ArrayList<Card> hand = new ArrayList<>();
    public Card fieldZone = null;

    public Field (){
        for (int i = 0; i < 5; ++i){
            monsterZone.add(null);
            spellTrapZone.add(null);
        }
    }
}
