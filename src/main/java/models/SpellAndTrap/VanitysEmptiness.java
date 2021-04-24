package models.SpellAndTrap;

import models.CardStufs.Type;

public class VanitysEmptiness extends SpellAndTrap {

    public VanitysEmptiness (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public VanitysEmptiness (Object object){
        super(((VanitysEmptiness)object).getName(), ((VanitysEmptiness)object).getCardsType(),
                ((VanitysEmptiness)object).getDescription(), ((VanitysEmptiness)object).getPrice(),
                ((VanitysEmptiness)object).getIcon(), ((VanitysEmptiness)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
