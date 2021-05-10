package models.Monster;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.SpellAndTrap.SpellAndTrap;

import java.io.Serializable;

public class Monster extends Card implements Serializable {

    private int level;
    private String attribute;
    private String monsterType;
    private String cardType;
    private int attack;
    private int defence;
    private boolean canAttack = true;
    private SpellAndTrap activeSpell;


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

    public boolean CanAttack() {
        return canAttack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setActiveSpell(SpellAndTrap activeSpell) {
        this.activeSpell = activeSpell;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    @Override
    public void action() {

    }
    public void removeMonster(Battlefield battlefield){
        battlefield.getOpponent().field.graveYard.add(this);
    };
}
