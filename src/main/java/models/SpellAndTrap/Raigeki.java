package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class Raigeki extends SpellAndTrap implements Serializable {

    public Raigeki (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Raigeki (Object object){
        super(((Raigeki)object).getName(), ((Raigeki)object).getCardsType(),
                ((Raigeki)object).getDescription(), ((Raigeki)object).getPrice(),
                ((Raigeki)object).getIcon(), ((Raigeki)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
