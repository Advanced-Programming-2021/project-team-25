package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import controllers.ShowCard;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.Responses;
import view.UserInterface;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;

public class MonsterReborn extends SpellAndTrap  implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;

    public MonsterReborn (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MonsterReborn (Object object){
        super(((MonsterReborn)object).getName(), ((MonsterReborn)object).getCardsType(),
                ((MonsterReborn)object).getDescription(), ((MonsterReborn)object).getPrice(),
                ((MonsterReborn)object).getIcon(), ((MonsterReborn)object).getStatus());
    }

    @Override
    public void action() {
        //User Input
        boolean isFoundMonsterInTurnGraveyard = false;
        boolean isFoundMonsterInOpponentGraveyard = false;
        //getting the gam field
        turn = Battlefield.getTurn();
        opponent = Battlefield.getOpponent();
        //checking not null
        if(!Objects.isNull(turn) && !Objects.isNull(opponent)){
            //show graveyards
            if(turn.field.graveYard.isEmpty()) UserInterface.printResponse("your graveyard is empty");
            else isFoundMonsterInTurnGraveyard = showGraveYard(turn);

            if(opponent.field.graveYard.isEmpty()) UserInterface.printResponse("your rival graveyard is empty");
            else isFoundMonsterInOpponentGraveyard = showGraveYard(opponent);

            //get monster from user input
            if(isFoundMonsterInTurnGraveyard || isFoundMonsterInOpponentGraveyard) initializeForSpell();

        }
    }

    private void initializeForSpell() {
        boolean isChoiceEnded = false;
        //get user input
        String command;
        UserInterface.printResponse("please select a Monster By this command:\n" +
                " M for your graveyard And H for rival graveyard Or type exit ");
        UserInterface.printResponse("select --M/H [card number]");
        command = UserInterface.getUserInput();
        //check valid input
        Matcher matcher = Regex.getMatcher(command,Regex.selectCardFromGraveyard);
        //deciding
        while(!isChoiceEnded){
            if(matcher.find()){
                //get User input
                String type = matcher.group(1);

                int number = Integer.parseInt(matcher.group(2));

                if(type.equals("M")){
                    specialSummon(number, turn);
                    
                }else{
                    specialSummon(number, opponent);
                }
                //end choice
                isChoiceEnded = true;
            }
            else if(command.equals("exit")) return;
            else{
                UserInterface.printResponse(Responses.INVALID_COMMAND);
                command = UserInterface.getUserInput();
            }
        }
    }

    private void specialSummon(int number, Duelist duelist) {
        Monster monster = (Monster) duelist.field.graveYard.get(number);
        if(Objects.isNull(monster)) UserInterface.printResponse("No Monster Found");
        else{
            duelist.field.graveYard.remove(number);
            monster.setActiveSpell(this);
            Battlefield.specialSummon(monster);
        }
    }

    private boolean showGraveYard(Duelist duelist) {
        boolean isFoundMonster = false;
        int i = 1;
        for (Card card : duelist.field.graveYard) {
            if(card.getCardsType().equals(Type.MONSTER)){
                ShowCard.showCard(card.getName());
                isFoundMonster = true;
            }
        }
        if(!isFoundMonster) UserInterface.printResponse("No Monster Found in "+duelist.getName()+" graveyard");
        return isFoundMonster;
    }
}
