package models.SpellAndTrap;

import models.CardStufs.Type;

public class WallOfRevealingLight extends SpellAndTrap {

    public WallOfRevealingLight (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public WallOfRevealingLight (Object object){
        super(((WallOfRevealingLight)object).getName(), ((WallOfRevealingLight)object).getCardsType(),
                ((WallOfRevealingLight)object).getDescription(), ((WallOfRevealingLight)object).getPrice(),
                ((WallOfRevealingLight)object).getIcon(), ((WallOfRevealingLight)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
