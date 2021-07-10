package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;

import java.io.Serializable;

public class MagicJammer extends SpellAndTrap  implements Serializable {

    public MagicJammer(String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MagicJammer(Object object){
        super(((MagicJammer)object).getName(), ((MagicJammer)object).getCardsType(),
                ((MagicJammer)object).getDescription(), ((MagicJammer)object).getPrice(),
                ((MagicJammer)object).getIcon(), ((MagicJammer)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {

    }
}
