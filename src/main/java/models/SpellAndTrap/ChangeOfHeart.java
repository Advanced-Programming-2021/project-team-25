package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class ChangeOfHeart extends SpellAndTrap implements Serializable {

    public ChangeOfHeart (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public ChangeOfHeart (Object object){
        super(((ChangeOfHeart)object).getName(), ((ChangeOfHeart)object).getCardsType(),
                ((ChangeOfHeart)object).getDescription(), ((ChangeOfHeart)object).getPrice(),
                ((ChangeOfHeart)object).getIcon(), ((ChangeOfHeart)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
