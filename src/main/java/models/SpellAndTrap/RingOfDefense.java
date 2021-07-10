package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class RingOfDefense extends SpellAndTrap implements Serializable {

    public RingOfDefense (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public RingOfDefense (Object object){
        super(((RingOfDefense)object).getName(), ((RingOfDefense)object).getCardsType(),
                ((RingOfDefense)object).getDescription(), ((RingOfDefense)object).getPrice(),
                ((RingOfDefense)object).getIcon(), ((RingOfDefense)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
