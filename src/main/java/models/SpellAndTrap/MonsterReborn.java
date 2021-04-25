package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class MonsterReborn extends SpellAndTrap  implements Serializable {

    public MonsterReborn (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MonsterReborn (Object object){
        super(((MonsterReborn)object).getName(), ((MonsterReborn)object).getCardsType(),
                ((MonsterReborn)object).getDescription(), ((MonsterReborn)object).getPrice(),
                ((MonsterReborn)object).getIcon(), ((MonsterReborn)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
