package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class DarkHole extends SpellAndTrap implements Serializable {

    public DarkHole (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public DarkHole (Object object){
        super(((DarkHole)object).getName(), ((DarkHole)object).getCardsType(),
                ((DarkHole)object).getDescription(), ((DarkHole)object).getPrice(),
                ((DarkHole)object).getIcon(), ((DarkHole)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
