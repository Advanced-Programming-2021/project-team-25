package models.Monster;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.FaceUp;
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
    int expireTime;


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

        if(battlefield.selectedCard.getCardsFace().equals(FaceUp.ATTACK))
            attack(battlefield);

    }
    public void removeMonster(Battlefield battlefield){
        if (battlefield.getTurn().field.monsterZone.contains(this)){
            battlefield.getTurn().field.graveYard.add(this);
            for (int i = 0; i<5; ++i){
                if (battlefield.getTurn().field.monsterZone.get(i) == this){
                    battlefield.getTurn().field.monsterZone.set(i, null);
                    break;
                }
            }
        }
        else if (battlefield.getOpponent().field.monsterZone.contains(this)){
            battlefield.getOpponent().field.graveYard.add(this);
            for (int i = 0; i<5; ++i){
                if (battlefield.getOpponent().field.monsterZone.get(i) == this){
                    battlefield.getOpponent().field.monsterZone.set(i, null);
                    break;
                }
            }
        }
    }
    public void attack(Battlefield battlefield){
        Monster attackedMonster = battlefield.attackedMonster;
        Duelist opponent = battlefield.getOpponent();
        Duelist turn = battlefield.getTurn();

        //if attacking defeated remove our monster
        // -1 means defeated
        // 1 means attacked
        // 0 means equal
        // 2 means nothing happened
        int condition = attackedMonster.defenceFunc(battlefield);
        if(condition == 1){
            attackedMonster.removeMonster(battlefield);
            opponent.field.monsterZone.set(battlefield.getIndex(battlefield.attackedMonsterNum) , null);
            int damage = this.getAttack() - attackedMonster.getAttack();
            opponent.LP = opponent.LP - damage;

            if(attackedMonster.getCardsFace().equals(FaceUp.ATTACK))
                UserInterface.printResponse("your opponent’s monster is destroyed and your opponent receives" + damage + "battle damage");
            else
                UserInterface.printResponse("the defense position monster is destroyed");
        }
        else if(condition == -1){
            battlefield.selectedCard.setISAttackedThisTurn(true);
            this.removeMonster(battlefield);
            turn.field.monsterZone.set(battlefield.getIndex(battlefield.attackedMonsterNum) , null);
            int damage = attackedMonster.getAttack() - this.getAttack();
            turn.LP = turn.LP - damage;
            UserInterface.printResponse("Your monster card is destroyed and you received " + damage + " battle damage");
        }
        else if(condition == 0){
            this.removeMonster(battlefield);
            opponent.field.monsterZone.set(battlefield.getIndex(battlefield.attackedMonsterNum) , null);
            attackedMonster.removeMonster(battlefield);
            turn.field.monsterZone.set(battlefield.getIndex(battlefield.getIndexOfSelectedCardInMonsterZone()) , null);
            UserInterface.printResponse("both you and your opponent monster cards are destroyed and no one receives damage");
        }
        else if(condition == 2) {
            battlefield.selectedCard.setISAttackedThisTurn(true);
            UserInterface.printResponse("opponent’s monster card was " + attackedMonster.getName() + " and no card is destroyed");
            opponent.field.monsterZone.get(battlefield.getIndex(battlefield.attackedMonsterNum)).setCardsFace(FaceUp.DEFENSE_FRONT);

            UserInterface.printResponse("no card is destroyed");
        }

        battlefield.selectedCard = null;
    }
    public int defenceFunc(Battlefield battlefield){
        Monster attackingMonster = battlefield.attackingMonster;

        int attackMonsterHero = attackingMonster.getAttack();
        int defenseMonsterHero;
        if(this.getCardsFace() == FaceUp.ATTACK) defenseMonsterHero = this.attack;
        else defenseMonsterHero = this.defence;
        if(attackMonsterHero > defenseMonsterHero){
            if(this.getCardsFace() == FaceUp.DEFENSE_BACK)
                return 2;
            else
                return 1;
        }
        else if(attackMonsterHero<defenseMonsterHero) {
            if(this.getCardsFace() == FaceUp.DEFENSE_BACK)
              return 2;
            else
              return -1;
        }
        else
            return 0;
    }
}
