package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class Yami extends SpellAndTrap implements Serializable {

    public Yami (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Yami (Object object){
        super(((Yami)object).getName(), ((Yami)object).getCardsType(),
                ((Yami)object).getDescription(), ((Yami)object).getPrice(),
                ((Yami)object).getIcon(), ((Yami)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
