package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;

import java.io.Serializable;
import java.util.ArrayList;

public class SpellAndTrap extends Card implements Serializable {
    public ArrayList<Monster>targetedMonsters = new ArrayList<>();
    private String icon;
    private String status;
    int expireTime;

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


    public void removeSpellOrTrap (String name){

    }

    @Override
    public void action(Battlefield battlefield) {

    }
}
