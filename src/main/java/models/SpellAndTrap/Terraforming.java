package models.SpellAndTrap;

import models.CardStufs.Type;

public class Terraforming extends SpellAndTrap {

    public Terraforming (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Terraforming (Object object){
        super(((Terraforming)object).getName(), ((Terraforming)object).getCardsType(),
                ((Terraforming)object).getDescription(), ((Terraforming)object).getPrice(),
                ((Terraforming)object).getIcon(), ((Terraforming)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
