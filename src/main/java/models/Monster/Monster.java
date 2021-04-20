package models.Monster;


import models.Card;
import models.CardStufs.Type;

public class Monster extends Card {

    private int level;
    private String attribute;
    private String monsterType;
    private String cardType;
    private int attack;
    private int defence;


    public Monster(String name, Type cardType, String description, int price, int level, String attribute,
                   String monsterType, String cardTypeInExel, int attack, int defence) {
        super(name, cardType, description, price);
        this.level = level;
        this.attribute = attribute;
        this.monsterType = monsterType;
        this.cardType = cardTypeInExel;
        this.attack = attack;
        this.defence = defence;
    }

    //this is copy constructor of Monster class
    public Monster(Object object) {
        super(((Monster) object).getName(), ((Monster) object).getCardsType(), ((Monster) object).getDescription(),
                ((Monster) object).getPrice());
        Monster monster = (Monster) object;
        this.level = monster.level;
        this.attribute = monster.attribute;
        this.monsterType = monster.monsterType;
        this.cardType = monster.cardType;
        this.attack = monster.attack;
        this.defence = monster.defence;
    }

    public int getLevel() {
        return level;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getCardTypeInExel() {
        return cardType;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }
}