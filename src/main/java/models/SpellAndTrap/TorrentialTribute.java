package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class TorrentialTribute extends SpellAndTrap implements Serializable {

    public TorrentialTribute (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public TorrentialTribute (Object object){
        super(((TorrentialTribute)object).getName(), ((TorrentialTribute)object).getCardsType(),
                ((TorrentialTribute)object).getDescription(), ((TorrentialTribute)object).getPrice(),
                ((TorrentialTribute)object).getIcon(), ((TorrentialTribute)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
