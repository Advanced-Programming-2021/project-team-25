package models;

import java.util.ArrayList;

public class Card {
    public static ArrayList<Card> allCards = new ArrayList<>();
    private String name;
    private int level;
    private String attribute;
    private String monsterType;
    private String cardType;
    private int attack;
    private int defence;
    private String description;
    private int price;

    public Card(String name , int level , String attribute
    , String monsterType , String cardType , int attack , int defence , String description
    , int price){
        this.name = name;
        this.level = level;
        this.attribute = attribute;
        this.monsterType = monsterType;
        this.cardType = cardType;
        this.attack = attack;
        this.defence = defence;
        this.description = description;
        this.price = price;
        allCards.add(this);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nLevel: " + level +
                "\nType: " + monsterType +
                "\nATK: " + attack +
                "\nDEF: " + defence +
                "\nDescription: " + description
                ;
    }

    public int getPrice() {
        return price;
    }
}
