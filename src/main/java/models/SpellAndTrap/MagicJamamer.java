package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class MagicJamamer extends SpellAndTrap  implements Serializable {

    public MagicJamamer (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MagicJamamer (Object object){
        super(((MagicJamamer)object).getName(), ((MagicJamamer)object).getCardsType(),
                ((MagicJamamer)object).getDescription(), ((MagicJamamer)object).getPrice(),
                ((MagicJamamer)object).getIcon(), ((MagicJamamer)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
