package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class TimeSeal extends SpellAndTrap implements Serializable {

    public TimeSeal (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public TimeSeal (Object object){
        super(((TimeSeal)object).getName(), ((TimeSeal)object).getCardsType(),
                ((TimeSeal)object).getDescription(), ((TimeSeal)object).getPrice(),
                ((TimeSeal)object).getIcon(), ((TimeSeal)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
