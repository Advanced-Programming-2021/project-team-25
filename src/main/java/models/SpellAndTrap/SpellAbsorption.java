package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;

import java.io.Serializable;

public class SpellAbsorption extends SpellAndTrap implements Serializable {

    public SpellAbsorption (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SpellAbsorption (Object object){
        super(((SpellAbsorption)object).getName(), ((SpellAbsorption)object).getCardsType(),
                ((SpellAbsorption)object).getDescription(), ((SpellAbsorption)object).getPrice(),
                ((SpellAbsorption)object).getIcon(), ((SpellAbsorption)object).getStatus());
    }

    @Override
    public void action() {
        Duelist turn = Battlefield.getTurn();
        turn.LP+=500;
    }
}
