package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class CallOfTheHaunted extends SpellAndTrap  implements Serializable {

    public CallOfTheHaunted (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public CallOfTheHaunted (Object object){
        super(((CallOfTheHaunted)object).getName(), ((CallOfTheHaunted)object).getCardsType(),
                ((CallOfTheHaunted)object).getDescription(), ((CallOfTheHaunted)object).getPrice(),
                ((CallOfTheHaunted)object).getIcon(), ((CallOfTheHaunted)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
