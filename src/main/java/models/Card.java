package models;

import models.CardStufs.FaceUp;
import models.CardStufs.Location;
import models.CardStufs.Type;

import java.util.HashMap;

public abstract class Card {
    public static HashMap<String, Card> allCards = new HashMap<>();
    private String name;
    private Type cardsType;
    private FaceUp cardsFace;
    private Location cardsLocation;
    private boolean setChanged;
    private boolean isSetThisTurn;
    private String description;
    private int price;

    public Card(String name, Type cardsType, String description, int price) {
        this.name = name;
        this.cardsType = cardsType;
        this.description = description;
        this.price = price;
        allCards.put(name, this);
    }

    public String getName() {
        return name;
    }

    public Type getCardsType() {
        return cardsType;
    }

    public FaceUp getCardsFace() {
        return cardsFace;
    }

    public void setCardsFace(FaceUp cardsFace) {
        this.cardsFace = cardsFace;
    }

    public Location getCardsLocation() {
        return cardsLocation;
    }

    public void setCardsLocation(Location cardsLocation) {
        this.cardsLocation = cardsLocation;
    }

    public boolean getSetChanged() {
        return setChanged;
    }

    public void setSetChanged(boolean trueOrFalse) {
        this.setChanged = trueOrFalse;
    }

    public boolean getIsSetThisTurn() {
        return isSetThisTurn;
    }

    public void setIsSetThisTurn(boolean trueOrFalse) {
        this.isSetThisTurn = trueOrFalse;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
