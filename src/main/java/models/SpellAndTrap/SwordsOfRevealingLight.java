package models.SpellAndTrap;

import models.CardStufs.Type;

public class SwordsOfRevealingLight extends SpellAndTrap {

    public SwordsOfRevealingLight (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SwordsOfRevealingLight (Object object){
        super(((SwordsOfRevealingLight)object).getName(), ((SwordsOfRevealingLight)object).getCardsType(),
                ((SwordsOfRevealingLight)object).getDescription(), ((SwordsOfRevealingLight)object).getPrice(),
                ((SwordsOfRevealingLight)object).getIcon(), ((SwordsOfRevealingLight)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
