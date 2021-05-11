package models.Monster;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import models.CardStufs.Type;
import models.Duelist;
import models.SpellAndTrap.CommonFunctions;
import view.Responses;
import view.UserInterface;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class Texchanger extends Monster implements Serializable, CommonFunctions {
    Battlefield battlefield;
    Duelist turn , opponent;
    int numTurn;
    boolean isFirstTimeDefeated = true;
    public Texchanger (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public Texchanger (Object object){
        super(((Texchanger)object).getName(), ((Texchanger)object).getCardsType(),
                ((Texchanger)object).getDescription(), ((Texchanger)object).getPrice(),
                ((Texchanger)object).getLevel(), ((Texchanger)object).getAttribute(),
                ((Texchanger)object).getMonsterType(), ((Texchanger)object).getCardTypeInExel(),
                ((Texchanger)object).getAttack(), ((Texchanger)object).getDefence());
    }

    @Override
    public void action(Battlefield battlefield) {
        //initialized
       this.battlefield = battlefield;
       turn = battlefield.getTurn();
       opponent = battlefield.getOpponent();

        int difference = getDifference(battlefield);
        if(difference == 0){
            showHand(turn);
            showDeck(turn);
            showGraveYard(turn);
            //getting card from user and special summon
            getCardFromUser();
        }

    }

    private void getCardFromUser() {
        //for special summon
        Monster monster = null;

        boolean isChoiceEnded = false;
        //get user input
        String command;
        UserInterface.printResponse("please select a Monster By this command:\n" +
                " G for your graveyard And H for hand And D for deck Or type exit ");
        UserInterface.printResponse("select --G/D/H [card number]");
        command = UserInterface.getUserInput();
        //check valid input
        Matcher matcher = Regex.getMatcher(command,Regex.selectCardInAllFields);
        //deciding
        while(!isChoiceEnded){
            if(matcher.find()){
                //get User input
                String type = matcher.group(1);

                int number = Integer.parseInt(matcher.group(2));

                switch (type) {
                    case "G" -> monster = (Monster) turn.field.graveYard.get(number);
                    case "D" -> monster = (Monster) turn.field.deck.get(number);
                    case "H" -> monster = (Monster) turn.field.hand.get(number);
                }
                //end choice if monster not null
                if(!Objects.isNull(monster)){
                    //special summon
                    battlefield.specialSummon(monster);
                    UserInterface.printResponse("special summoned successfully");
                    isChoiceEnded = true;
                }
                else{
                    UserInterface.printResponse("no monster selected! please try again.");
                    command = UserInterface.getUserInput();
                    matcher = Regex.getMatcher(command,Regex.selectCardInAllFields);
                }

            }
            else if(command.equals("exit")) return;
            else{
                UserInterface.printResponse(Responses.INVALID_COMMAND);
                command = UserInterface.getUserInput();
                matcher = Regex.getMatcher(command,Regex.selectCardInAllFields);
            }
        }
    }

    private int getDifference(Battlefield battlefield) {
        if(isFirstTimeDefeated){
            numTurn = battlefield.changedTurnTime;
            isFirstTimeDefeated = false;
        }
        return numTurn - battlefield.changedTurnTime;
    }

    @Override
    public int defenceFunc(Battlefield battlefield){
        int difference = getDifference(battlefield);
        if(difference == 0){
            UserInterface.printResponse("you can not attack to this card in first time in this turn");
        }
        else
            super.defenceFunc(battlefield);
        return 0;
    }
}
