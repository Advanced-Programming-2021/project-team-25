package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class SwordOfDarkDestruction extends SpellAndTrap implements Serializable {

    public SwordOfDarkDestruction (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SwordOfDarkDestruction (Object object){
        super(((SwordOfDarkDestruction)object).getName(), ((SwordOfDarkDestruction)object).getCardsType(),
                ((SwordOfDarkDestruction)object).getDescription(), ((SwordOfDarkDestruction)object).getPrice(),
                ((SwordOfDarkDestruction)object).getIcon(), ((SwordOfDarkDestruction)object).getStatus());
    }

    @Override
    public void action() {

    }

    @Override
    public void removeSpellOrTrap(String name) {

    }

}
