package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class MindCrush extends SpellAndTrap  implements Serializable {

    public MindCrush (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MindCrush (Object object){
        super(((MindCrush)object).getName(), ((MindCrush)object).getCardsType(),
                ((MindCrush)object).getDescription(), ((MindCrush)object).getPrice(),
                ((MindCrush)object).getIcon(), ((MindCrush)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
