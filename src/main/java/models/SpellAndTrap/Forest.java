package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class Forest extends SpellAndTrap implements Serializable {

    public Forest (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Forest (Object object){
        super(((Forest)object).getName(), ((Forest)object).getCardsType(),
                ((Forest)object).getDescription(), ((Forest)object).getPrice(),
                ((Forest)object).getIcon(), ((Forest)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
