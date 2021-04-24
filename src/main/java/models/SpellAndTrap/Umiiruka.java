package models.SpellAndTrap;

import models.CardStufs.Type;

public class Umiiruka extends SpellAndTrap {

    public Umiiruka (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Umiiruka (Object object){
        super(((Umiiruka)object).getName(), ((Umiiruka)object).getCardsType(),
                ((Umiiruka)object).getDescription(), ((Umiiruka)object).getPrice(),
                ((Umiiruka)object).getIcon(), ((Umiiruka)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
