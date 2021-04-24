package models.SpellAndTrap;

import models.CardStufs.Type;

public class SolemnWarning extends SpellAndTrap {

    public SolemnWarning (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SolemnWarning (Object object){
        super(((SolemnWarning)object).getName(), ((SolemnWarning)object).getCardsType(),
                ((SolemnWarning)object).getDescription(), ((SolemnWarning)object).getPrice(),
                ((SolemnWarning)object).getIcon(), ((SolemnWarning)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
