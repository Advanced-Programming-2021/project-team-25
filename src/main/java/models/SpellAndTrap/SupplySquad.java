package models.SpellAndTrap;

import models.CardStufs.Type;

public class SupplySquad extends SpellAndTrap {

    public SupplySquad (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SupplySquad (Object object){
        super(((SupplySquad)object).getName(), ((SupplySquad)object).getCardsType(),
                ((SupplySquad)object).getDescription(), ((SupplySquad)object).getPrice(),
                ((SupplySquad)object).getIcon(), ((SupplySquad)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
