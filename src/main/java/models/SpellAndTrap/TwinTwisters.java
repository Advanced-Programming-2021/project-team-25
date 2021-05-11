package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class TwinTwisters extends SpellAndTrap implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;

    public TwinTwisters (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public TwinTwisters (Object object){
        super(((TwinTwisters)object).getName(), ((TwinTwisters)object).getCardsType(),
                ((TwinTwisters)object).getDescription(), ((TwinTwisters)object).getPrice(),
                ((TwinTwisters)object).getIcon(), ((TwinTwisters)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();
        opponent = battlefield.getOpponent();


        int counter = 0;
        for (int i = 0; i<6; ++i)
            if (turn.field.hand.get(i) == null) counter += 1;


        int counterForOpponentSpellZone = 0;
        for (int i = 0; i<5; ++i)
            if (opponent.field.spellTrapZone.get(i) == null) counterForOpponentSpellZone += 1;


        if (counter == 6) UserInterface.printResponse("You can't activate this spell because you don't " +
                "have any card in your hand to send it to graveyard");

        else if (counterForOpponentSpellZone == 5) UserInterface.printResponse("Your opponent does not have" +
                "any spell or trap for destroying");


        else{
            for (int i = 0; i<6; ++i)
                if (turn.field.hand.get(i) != null)
                    UserInterface.printResponse(turn.field.hand.get(i).getName() + ":" + turn.field.hand.get(i).getDescription());
            String cardsName = " ";
            UserInterface.printResponse("Choose one of these cards to send it to graveyard.");
            while (true){
                String command = UserInterface.getUserInput();
                for (int i = 0; i<6; ++i)
                    if (turn.field.hand.get(i) != null && turn.field.hand.get(i).getName().equals(command)) {
                        cardsName = command;
                        break;
                    }
                if (command.equals("cancel"))
                    return;
                if (cardsName.equals(" "))
                    UserInterface.printResponse("You don't have this card in your hand. please write a valid name.");
                else
                    break;
            }
            for (int i = 0; i<6; ++i)
                if (turn.field.hand.get(i) != null && turn.field.hand.get(i).getName().equals(cardsName)){
                    turn.field.graveYard.add(turn.field.hand.get(i));
                    turn.field.hand.remove(i);
                    break;
                }


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
                if (command2.equals("cancel"))
                    return;
                if (cardsName2.equals(" ")) UserInterface.printResponse("Your opponent does not have this spell.");
                else break;
            }
            removeSpellOrTrapInThisCard(cardsName2);

            if (counterForOpponentSpellZone < 4){
                UserInterface.printResponse("You can destroy one more spell or trap of your opponent. do you want to " +
                        "do that?(yes/no)");
                String yesOrNo = UserInterface.getUserInput();
                if (yesOrNo.equalsIgnoreCase("yes")){
                    UserInterface.printResponse("Now you must select one spell or trap of your opponent to destroy");
                    for (int i = 0; i<5; ++i)
                        if (opponent.field.spellTrapZone.get(i) != null)
                            UserInterface.printResponse(opponent.field.spellTrapZone.get(i).getName() + ":" +
                                    opponent.field.spellTrapZone.get(i).getDescription());
                    String cardsName3 = " ";
                    while (true){
                        String command3 = UserInterface.getUserInput();
                        for (int i = 0; i<5; ++i)
                            if (opponent.field.spellTrapZone.get(i) != null && opponent.field.spellTrapZone.get(i).getName().equals(command3)) {
                                cardsName3 = command3;
                                break;
                            }
                        if (command3.equals("cancel"))
                            return;
                        if (cardsName3.equals(" ")) UserInterface.printResponse("Your opponent does not have this spell.");
                        else break;
                    }
                    removeSpellOrTrapInThisCard(cardsName3);
                }
            }
        }


    }

    public void removeSpellOrTrapInThisCard (String name){
        for (int i = 0; i<5; ++i){
            if (opponent.field.spellTrapZone.get(i) != null && opponent.field.spellTrapZone.get(i).getName().equals(name)){
                if (opponent.field.spellTrapZone.get(i).getCardsType() == Type.SPELL){
//                    for (int j = 0; j<((SpellAndTrap)opponent.field.spellTrapZone.get(i)).targetedMonsters.size(); ++j){
//                        Monster temp = ((SpellAndTrap)opponent.field.spellTrapZone.get(i)).targetedMonsters.get(j);
//                        temp.setAttack(((Monster)allCards.get(temp.getName())).getAttack());
//                        temp.setDefence(((Monster)allCards.get(temp.getName())).getDefence());
//                    }
                    ((SpellAndTrap)opponent.field.spellTrapZone.get(i)).removeSpellOrTrap("nothing");
//                    ((SpellAndTrap) opponent.field.spellTrapZone.get(i)).targetedMonsters = new ArrayList<>();
//                    opponent.field.graveYard.add(opponent.field.spellTrapZone.get(i));
//                    opponent.field.spellTrapZone.set(i, null);
                }
                else{
                    ((SpellAndTrap)opponent.field.spellTrapZone.get(i)).removeSpellOrTrap("nothing");
//                    ((SpellAndTrap)opponent.field.spellTrapZone.get(i)).targetedMonsters = new ArrayList<>();
//                    opponent.field.graveYard.add(opponent.field.spellTrapZone.get(i));
//                    opponent.field.spellTrapZone.set(i, null);
                }
            }
        }
    }

    @Override
    public void removeSpellOrTrap(String name) {
        targetedMonsters = new ArrayList<>();
        turn.field.graveYard.add(this);
        for (int i = 0; i<5; ++i){
            if (turn.field.spellTrapZone.get(i) == this){
                turn.field.spellTrapZone.set(i, null);
                break;
            }
        }
    }


}
