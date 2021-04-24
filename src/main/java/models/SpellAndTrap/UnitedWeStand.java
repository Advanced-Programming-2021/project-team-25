package models.SpellAndTrap;

import models.CardStufs.Type;

public class UnitedWeStand extends SpellAndTrap {

    public UnitedWeStand (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public UnitedWeStand (Object object){
        super(((UnitedWeStand)object).getName(), ((UnitedWeStand)object).getCardsType(),
                ((UnitedWeStand)object).getDescription(), ((UnitedWeStand)object).getPrice(),
                ((UnitedWeStand)object).getIcon(), ((UnitedWeStand)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
