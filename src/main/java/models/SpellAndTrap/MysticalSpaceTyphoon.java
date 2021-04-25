package models.SpellAndTrap;

import models.CardStufs.Type;

import java.io.Serializable;

public class MysticalSpaceTyphoon extends SpellAndTrap implements Serializable {

    public MysticalSpaceTyphoon (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MysticalSpaceTyphoon (Object object){
        super(((MysticalSpaceTyphoon)object).getName(), ((MysticalSpaceTyphoon)object).getCardsType(),
                ((MysticalSpaceTyphoon)object).getDescription(), ((MysticalSpaceTyphoon)object).getPrice(),
                ((MysticalSpaceTyphoon)object).getIcon(), ((MysticalSpaceTyphoon)object).getStatus());
    }

//    @Override
//    public void action() {
//
//    }
}
