package models.SpellAndTrap;

import models.CardStufs.Type;

public class HarpiesFeatherDuster extends SpellAndTrap {

    public HarpiesFeatherDuster (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public HarpiesFeatherDuster (Object object){
        super(((HarpiesFeatherDuster)object).getName(), ((HarpiesFeatherDuster)object).getCardsType(),
                ((HarpiesFeatherDuster)object).getDescription(), ((HarpiesFeatherDuster)object).getPrice(),
                ((HarpiesFeatherDuster)object).getIcon(), ((HarpiesFeatherDuster)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
