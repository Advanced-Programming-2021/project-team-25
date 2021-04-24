package models.SpellAndTrap;

import models.CardStufs.Type;

public class TwinTwisters extends SpellAndTrap {

    public TwinTwisters (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public TwinTwisters (Object object){
        super(((TwinTwisters)object).getName(), ((TwinTwisters)object).getCardsType(),
                ((TwinTwisters)object).getDescription(), ((TwinTwisters)object).getPrice(),
                ((TwinTwisters)object).getIcon(), ((TwinTwisters)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
