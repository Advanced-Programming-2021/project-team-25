package models.SpellAndTrap;

import models.CardStufs.Type;

public class MirrorForce extends SpellAndTrap {

    public MirrorForce (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MirrorForce (Object object){
        super(((MirrorForce)object).getName(), ((MirrorForce)object).getCardsType(),
                ((MirrorForce)object).getDescription(), ((MirrorForce)object).getPrice(),
                ((MirrorForce)object).getIcon(), ((MirrorForce)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
