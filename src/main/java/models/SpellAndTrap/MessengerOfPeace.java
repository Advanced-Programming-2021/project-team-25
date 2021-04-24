package models.SpellAndTrap;

import models.CardStufs.Type;

public class MessengerOfPeace extends SpellAndTrap {

    public MessengerOfPeace (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MessengerOfPeace (Object object){
        super(((MessengerOfPeace)object).getName(), ((MessengerOfPeace)object).getCardsType(),
                ((MessengerOfPeace)object).getDescription(), ((MessengerOfPeace)object).getPrice(),
                ((MessengerOfPeace)object).getIcon(), ((MessengerOfPeace)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
