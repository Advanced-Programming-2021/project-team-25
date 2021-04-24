package models.SpellAndTrap;

import models.CardStufs.Type;

public class MagnumShield extends SpellAndTrap {

    public MagnumShield (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MagnumShield (Object object){
        super(((MagnumShield)object).getName(), ((MagnumShield)object).getCardsType(),
                ((MagnumShield)object).getDescription(), ((MagnumShield)object).getPrice(),
                ((MagnumShield)object).getIcon(), ((MagnumShield)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
