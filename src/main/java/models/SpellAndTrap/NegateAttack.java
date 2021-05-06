package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import controllers.Battelfield.Phase;
import models.CardStufs.Type;

import java.io.Serializable;

public class NegateAttack extends SpellAndTrap implements Serializable {

    public NegateAttack (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public NegateAttack (Object object){
        super(((NegateAttack)object).getName(), ((NegateAttack)object).getCardsType(),
                ((NegateAttack)object).getDescription(), ((NegateAttack)object).getPrice(),
                ((NegateAttack)object).getIcon(), ((NegateAttack)object).getStatus());
    }

    @Override
    public void action() {
        Battlefield.getOpponent().field.graveYard.add(Battlefield.getSelectedCard());
        Battlefield.getOpponent().field.monsterZone.set(getIndexOfCard(),null);
        Battlefield.setPhase(Phase.MAIN2_PHASE);
    }

    public int getIndexOfCard(){
        for (int i = 0 ; i < 5 ; i++ ) {
            if(Battlefield.getSelectedCard() == Battlefield.getTurn().field.monsterZone.get(i)) return i;
        }
        return -1;
    }
}
