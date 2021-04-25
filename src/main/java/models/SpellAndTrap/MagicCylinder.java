package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class MagicCylinder extends SpellAndTrap implements Serializable {

    public MagicCylinder (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MagicCylinder (Object object){
        super(((MagicCylinder)object).getName(), ((MagicCylinder)object).getCardsType(),
                ((MagicCylinder)object).getDescription(), ((MagicCylinder)object).getPrice(),
                ((MagicCylinder)object).getIcon(), ((MagicCylinder)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
