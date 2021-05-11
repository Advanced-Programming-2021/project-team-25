package models;

import controllers.Battelfield.Battlefield;
import models.CardStufs.FaceUp;
import models.CardStufs.Location;
import models.CardStufs.Type;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Card implements Serializable {
    public static HashMap<String, Card> allCards = new HashMap<>();
    private String name;
    private Type cardsType;
    private FaceUp cardsFace;
    private Location cardsLocation;
    private boolean setChanged = false;
    private boolean isSetThisTurn = false;
    private boolean isAttackedThisTurn = false;
    private String description;
    private int price;
    private boolean isExpired;
    int expireTime;

    public Card(String name, Type cardsType, String description, int price) {
        this.name = name;
        this.cardsType = cardsType;
        this.description = description;
        this.price = price;
        isExpired = false;
        if (!allCards.containsKey(name)) allCards.put(name, this);
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

    public boolean getIsAttackedThisTurn() {
        return isAttackedThisTurn;
    }

    public void setISAttackedThisTurn (boolean trueOrFalse){
        this.isAttackedThisTurn = trueOrFalse;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", cardsType=" + cardsType +
                ", cardsFace=" + cardsFace +
                ", cardsLocation=" + cardsLocation +
                ", setChanged=" + setChanged +
                ", isSetThisTurn=" + isSetThisTurn +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    public abstract void action (Battlefield battlefield);
}
