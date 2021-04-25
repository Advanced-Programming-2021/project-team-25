package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class ClosedForest extends SpellAndTrap  implements Serializable {

    public ClosedForest (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public ClosedForest (Object object){
        super(((ClosedForest)object).getName(), ((ClosedForest)object).getCardsType(),
                ((ClosedForest)object).getDescription(), ((ClosedForest)object).getPrice(),
                ((ClosedForest)object).getIcon(), ((ClosedForest)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
