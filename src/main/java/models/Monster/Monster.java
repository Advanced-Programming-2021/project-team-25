package models.Monster;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import models.SpellAndTrap.SpellAndTrap;
import view.UserInterface;

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
    public void action(Battlefield battlefield) {

    }
    public void removeMonster(Battlefield battlefield){
        battlefield.getOpponent().field.graveYard.add(this);
    }
    public void defence(Battlefield battlefield){

    }
    public void attack(Battlefield battlefield, int monsterNum){
        Duelist opponent = battlefield.getOpponent();
        Duelist turn = battlefield.getTurn();
        Card selectedCard = battlefield.getSelectedCard();
        Monster attackedMonster = (Monster) battlefield.getOpponent().field.monsterZone.get(battlefield.getIndex(monsterNum));
        Monster attackingMonster = (Monster) battlefield.getSelectedCard();

        if(attackingMonster.getAttack() > attackedMonster.getAttack()){
            selectedCard.setISAttackedThisTurn(true);
            ((Monster) opponent.field.monsterZone.get(battlefield.getIndex(monsterNum))).removeMonster(battlefield);
            opponent.field.monsterZone.set(battlefield.getIndex(monsterNum) , null);
            int damage = attackingMonster.getAttack() - attackedMonster.getAttack();
            opponent.LP = opponent.LP - damage;
            UserInterface.printResponse("your opponent’s monster is destroyed and your opponent receives" + damage + "battle damage");
        }

        else if(attackingMonster.getAttack() == attackedMonster.getAttack()){
            ((Monster) opponent.field.monsterZone.get(battlefield.getIndex(monsterNum))).removeMonster(battlefield);
            opponent.field.monsterZone.set(battlefield.getIndex(monsterNum) , null);
            turn.field.graveYard.add(selectedCard);
            turn.field.monsterZone.set(battlefield.getIndex(battlefield.getIndexOfSelectedCardInMonsterZone()) , null);
            UserInterface.printResponse("both you and your opponent monster cards are destroyed and no one receives damage");
        }

        else{
            ((Monster) selectedCard).removeMonster(battlefield);
            turn.field.monsterZone.set(battlefield.getIndex(battlefield.getIndexOfSelectedCardInMonsterZone()) , null);
            int damage = attackedMonster.getAttack() - attackingMonster.getAttack();
            turn.LP = turn.LP - damage;
            UserInterface.printResponse("Your monster card is destroyed and you received " + damage + " battle damage");
        }
    }
}
