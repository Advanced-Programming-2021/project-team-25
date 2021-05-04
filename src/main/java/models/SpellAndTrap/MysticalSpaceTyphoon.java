package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;

public class MysticalSpaceTyphoon extends SpellAndTrap implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;

    public MysticalSpaceTyphoon (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MysticalSpaceTyphoon (Object object){
        super(((MysticalSpaceTyphoon)object).getName(), ((MysticalSpaceTyphoon)object).getCardsType(),
                ((MysticalSpaceTyphoon)object).getDescription(), ((MysticalSpaceTyphoon)object).getPrice(),
                ((MysticalSpaceTyphoon)object).getIcon(), ((MysticalSpaceTyphoon)object).getStatus());
    }

    @Override
    public void action() {
        turn = Battlefield.getTurn();
        opponent = Battlefield.getOpponent();


        int counterForOpponentSpellZone = 0;
        for (int i = 0; i<5; ++i)
            if (opponent.field.spellTrapZone.get(i) == null) counterForOpponentSpellZone += 1;

        if (counterForOpponentSpellZone == 5) UserInterface.printResponse("Your opponent does not have" +
                "any spell or trap for destroying");

        else{
            UserInterface.printResponse("Now you must select one spell or trap of your opponent to destroy");
            for (int i = 0; i<5; ++i)
                if (opponent.field.spellTrapZone.get(i) != null)
                    UserInterface.printResponse(opponent.field.spellTrapZone.get(i).getName() + ":" +
                            opponent.field.spellTrapZone.get(i).getDescription());
            String cardsName2 = " ";
            while (true){
                String command2 = UserInterface.getUserInput();
                for (int i = 0; i<5; ++i)
                    if (opponent.field.spellTrapZone.get(i) != null && opponent.field.spellTrapZone.get(i).getName().equals(command2)) {
                        cardsName2 = command2;
                        break;
                    }
                if (cardsName2.equals(" ")) UserInterface.printResponse("Your opponent does not have this spell.");
                else break;
            }
            removeSpellOrTrap(cardsName2);
        }
    }

    public void removeSpellOrTrap (String name){
        for (int i = 0; i<5; ++i){
            if (opponent.field.spellTrapZone.get(i) != null && opponent.field.spellTrapZone.get(i).getName().equals(name)){
                if (opponent.field.spellTrapZone.get(i).getCardsType() == Type.SPELL){
                    for (int j = 0; j<((SpellAndTrap)opponent.field.spellTrapZone.get(i)).targetedMonsters.size(); ++j){
                        Monster temp = ((SpellAndTrap)opponent.field.spellTrapZone.get(i)).targetedMonsters.get(j);
                        temp.setAttack(((Monster)allCards.get(temp.getName())).getAttack());
                        temp.setDefence(((Monster)allCards.get(temp.getName())).getDefence());
                    }
                    opponent.field.graveYard.add(opponent.field.spellTrapZone.get(i));
                    opponent.field.spellTrapZone.set(i, null);
                }
                else{
                    opponent.field.graveYard.add(opponent.field.spellTrapZone.get(i));
                    opponent.field.spellTrapZone.set(i, null);
                }
            }
        }
    }
}
