package models.SpellAndTrap;

import models.CardStufs.Type;

public class TrapHole extends SpellAndTrap {

    public TrapHole (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public TrapHole (Object object){
        super(((TrapHole)object).getName(), ((TrapHole)object).getCardsType(),
                ((TrapHole)object).getDescription(), ((TrapHole)object).getPrice(),
                ((TrapHole)object).getIcon(), ((TrapHole)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
