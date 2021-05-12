package models.Monster;

import controllers.Battelfield.Battlefield;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Duelist;

import java.io.Serializable;

public class BeastKingBarbaros extends Monster implements Serializable {
    Duelist turn;
    public BeastKingBarbaros (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public BeastKingBarbaros (Object object){
        super(((BeastKingBarbaros)object).getName(), ((BeastKingBarbaros)object).getCardsType(),
                ((BeastKingBarbaros)object).getDescription(), ((BeastKingBarbaros)object).getPrice(),
                ((BeastKingBarbaros)object).getLevel(), ((BeastKingBarbaros)object).getAttribute(),
                ((BeastKingBarbaros)object).getMonsterType(), ((BeastKingBarbaros)object).getCardTypeInExel(),
                ((BeastKingBarbaros)object).getAttack(), ((BeastKingBarbaros)object).getDefence());
    }
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();
    }
    @Override
    public void removeMonster(Battlefield battlefield){
        this.setAttack(3000);
        super.removeMonster(battlefield);
    }
}
