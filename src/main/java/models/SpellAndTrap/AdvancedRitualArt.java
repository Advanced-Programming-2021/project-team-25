package models.SpellAndTrap;

import models.CardStufs.Type;

public class AdvancedRitualArt extends SpellAndTrap {

    public AdvancedRitualArt (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public AdvancedRitualArt (Object object){
        super(((AdvancedRitualArt)object).getName(), ((AdvancedRitualArt)object).getCardsType(),
                ((AdvancedRitualArt)object).getDescription(), ((AdvancedRitualArt)object).getPrice(),
                ((AdvancedRitualArt)object).getIcon(), ((AdvancedRitualArt)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
