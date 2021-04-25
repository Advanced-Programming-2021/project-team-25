package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class PotOfGreed extends SpellAndTrap implements Serializable {

    public PotOfGreed (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public PotOfGreed (Object object){
        super(((PotOfGreed)object).getName(), ((PotOfGreed)object).getCardsType(),
                ((PotOfGreed)object).getDescription(), ((PotOfGreed)object).getPrice(),
                ((PotOfGreed)object).getIcon(), ((PotOfGreed)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
