package models.Monster;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import models.CardStufs.Type;
import models.Duelist;
import models.SpellAndTrap.CommonFunctions;
import view.Responses;
import view.UserInterface;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;

public class Scanner extends Monster implements Serializable , CommonFunctions {
    Battlefield battlefield;
    Duelist turn , opponent;

    public Scanner (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public Scanner (Object object){
        super(((Scanner)object).getName(), ((Scanner)object).getCardsType(),
                ((Scanner)object).getDescription(), ((Scanner)object).getPrice(),
                ((Scanner)object).getLevel(), ((Scanner)object).getAttribute(),
                ((Scanner)object).getMonsterType(), ((Scanner)object).getCardTypeInExel(),
                ((Scanner)object).getAttack(), ((Scanner)object).getDefence());
    }

    @Override
    public void action(Battlefield battlefield) {

        this.battlefield = battlefield;
        //User Input
        boolean isFoundMonsterInOpponentGraveyard = false;
        //getting the game field
        turn = battlefield.getTurn();
        opponent = battlefield.getOpponent();
        //checking not null
        if(!Objects.isNull(opponent)){
            //show graveyard
            if(opponent.field.graveYard.isEmpty()) UserInterface.printResponse("your rival graveyard is empty");
            else isFoundMonsterInOpponentGraveyard = showGraveYard(opponent);

            //get monster from user input
            if(isFoundMonsterInOpponentGraveyard) initializeForScanner();
        }
    }

    private void initializeForScanner() {
        boolean isChoiceEnded = false;
        //get user input
        String command;
        UserInterface.printResponse("please select a Monster By this command:\n" +
                "Or type exit ");
        UserInterface.printResponse("select [card number]");
        command = UserInterface.getUserInput();
        //check valid input
        Matcher matcher = Regex.getMatcher(command,Regex.selectFieldCard);
        //deciding
        while(!isChoiceEnded){

            if(matcher.find()){
                //get User input
                int number = Integer.parseInt(matcher.group(2));

                copyMonsterToScanner(number);

                isChoiceEnded = true;
            }
            else if(command.equals("exit")) return;
            else{
                UserInterface.printResponse(Responses.INVALID_COMMAND);
                command = UserInterface.getUserInput();
                matcher = Regex.getMatcher(command,Regex.selectFieldCard);
            }
        }

    }

    private void copyMonsterToScanner(int number) {

        Monster monster = (Monster)opponent.field.graveYard.get(number);

        battlefield.monsterChangedWithScanner = battlefield.attackedMonsterNum;

        turn.field.monsterZone.set(battlefield.attackedMonsterNum,monster);
        //in the end of turn will be replaced by scanner
    }
}
