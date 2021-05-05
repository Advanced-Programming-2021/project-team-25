package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;

public class CallOfTheHaunted extends SpellAndTrap implements Serializable {

    public CallOfTheHaunted (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public CallOfTheHaunted (Object object){
        super(((CallOfTheHaunted)object).getName(), ((CallOfTheHaunted)object).getCardsType(),
                ((CallOfTheHaunted)object).getDescription(), ((CallOfTheHaunted)object).getPrice(),
                ((CallOfTheHaunted)object).getIcon(), ((CallOfTheHaunted)object).getStatus());
    }

    @Override
    public void action() {
        UserInterface.printResponse("Enter name of the card you want to bring from graveyard : ");
        int i = 0;
        outer : while(i < 3) {
            String name = UserInterface.getUserInput();
            for (Card card : Battlefield.getTurn().field.graveYard) {
                if (card.getName().equals(name) && card.getCardsType() == Type.MONSTER) {
                    summonThisMonster((Monster) card);
                    break outer;
                }
            }
            UserInterface.printResponse("try another : ");
            i++;
        }

        //if(i != 3) Battlefield.getTurn().field.spellTrapZone.set(battlefield.getIndexOfCardInSpellAndTrapZone(), null);
    }

    public void summonThisMonster(Monster monster){
        if(getSizeOfMonsterZone()==5) UserInterface.printResponse("monster card zone is full");
        else Battlefield.getTurn().field.monsterZone.set(getSizeOfMonsterZone() + 1,monster);
    }

    public int getSizeOfMonsterZone(){
        int count=0;
        for (int i = 0; i<5; ++i)
            if (Battlefield.getTurn().field.monsterZone.get(i) != null) count += 1;
        return count;
    }
}
