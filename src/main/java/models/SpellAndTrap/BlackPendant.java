package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class BlackPendant extends SpellAndTrap  implements Serializable {

    public BlackPendant (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public BlackPendant (Object object){
        super(((BlackPendant)object).getName(), ((BlackPendant)object).getCardsType(),
                ((BlackPendant)object).getDescription(), ((BlackPendant)object).getPrice(),
                ((BlackPendant)object).getIcon(), ((BlackPendant)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
