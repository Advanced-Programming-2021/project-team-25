package models.SpellAndTrap;

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

//    @Override
//    public void action() {
//
//    }
}
