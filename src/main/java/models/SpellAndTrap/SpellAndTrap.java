package models.SpellAndTrap;

import models.Card;
import models.CardStufs.Type;

import java.io.Serializable;

public class SpellAndTrap extends Card implements Serializable {

    private String icon;
    private String status;

    public SpellAndTrap(String name, Type cardType, String description, int price, String icon, String status) {
        super(name, cardType, description, price);
        this.icon = icon;
        this.status = status;
    }

    //this is copy constructor of SpellAndTrap class
    public SpellAndTrap(Object object) {
        super(((SpellAndTrap) object).getName(), ((SpellAndTrap) object).getCardsType(), ((SpellAndTrap) object).getDescription(),
                ((SpellAndTrap) object).getPrice());
        SpellAndTrap spellAndTrap = (SpellAndTrap) object;
        this.icon = spellAndTrap.icon;
        this.status = spellAndTrap.status;
    }

    public String getIcon() {
        return icon;
    }

    public String getStatus() {
        return status;
    }


//    @Override
//    public void action() {
//
//    }
}
