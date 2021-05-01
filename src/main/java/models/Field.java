package models;

import java.util.ArrayList;

public class Field {
    public ArrayList<Card> deck = new ArrayList<>();
    public ArrayList<Card> graveYard = new ArrayList<>();
    public Card[] monsterZone = new Card[5];
    public Card[] spellTrapZone = new Card[5];
    public Card[] hand = new Card[6];
    public Card fieldZone = null;
}
