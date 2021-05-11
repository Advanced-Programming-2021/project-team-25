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
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class ChangeOfHeart extends SpellAndTrap implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;
    private int positionOfMonster;
    public ChangeOfHeart (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
        expireTime = 1;
    }

    public ChangeOfHeart (Object object){
        super(((ChangeOfHeart)object).getName(), ((ChangeOfHeart)object).getCardsType(),
                ((ChangeOfHeart)object).getDescription(), ((ChangeOfHeart)object).getPrice(),
                ((ChangeOfHeart)object).getIcon(), ((ChangeOfHeart)object).getStatus());
        expireTime = 1;
    }

    @Override
    public void action(Battlefield battlefield) {
        if(expireTime == 0){
            setExpired(true);
            //decide to remove or not
            removeSpellOrTrap();
        }
        else {
            //because just for one time that`s work perfectly
            expireTime--;

            ArrayList<Card> monsterZoneCards = new ArrayList<>();
            //getting the gam field
            turn = battlefield.getTurn();
            opponent = battlefield.getOpponent();
            //checking not null
            if(!Objects.isNull(turn) && !Objects.isNull(opponent)){
                //for checking rival monsterZone not empty
                if(opponent.field.monsterZone.isEmpty()) UserInterface.printResponse("your rival Monster zone is empty");
                    //show monster zone
                else{
                    showMonstersOfOpponentToUser(monsterZoneCards);

                    UserInterface.printResponse("""
                    please enter the card id you want by entering the id in command
                    "select [id]"
                     or type exit""");

                    addMonsterCardToHand(monsterZoneCards);
                }

            }
        }
    }

    private void addMonsterCardToHand(ArrayList<Card> monsterZoneCards) {
        boolean isChoiceEnded = false;
        Matcher matcher;
        String command;

        command = UserInterface.getUserInput();
        matcher = Regex.getMatcher(command,Regex.selectFieldCard);
        //Curr Monster
        Monster monster;
        while(!isChoiceEnded){
            if(matcher.find()){
                //get User input
                positionOfMonster = Integer.parseInt(matcher.group(1));
                monster = (Monster)monsterZoneCards.get(positionOfMonster);
                targetedMonsters.add(monster);
                if(getSizeOfMonsterZone()<5){
                    turn.field.monsterZone.set(getSizeOfMonsterZone()+1,monster);
                    opponent.field.monsterZone.set(opponent.field.monsterZone.indexOf(monster),null);
                }
                else
                    UserInterface.printResponse(Responses.NOT_ENOUGH_SPACE_IN_MONSTER_ZONE);
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

    private void showMonstersOfOpponentToUser(ArrayList<Card> fieldCards) {
        for(Card card : opponent.field.monsterZone){
            //checking if the card is Monster
            if(card.getCardsType().equals(Type.MONSTER)){
                ShowCard.showCard(card.getName());
                //add the card to array list
                fieldCards.add(card);
                //show the id to user
                UserInterface.printResponse("ID Of card :"+ fieldCards.indexOf(card));
            }
        }
    }
    private int getSizeOfMonsterZone(){
        int count=0;
        for (int i = 0; i<5; ++i)
            if (turn.field.monsterZone.get(i) != null) count += 1;
        return count;
    }
    public void removeSpellOrTrap (){
        turn.field.monsterZone.set(positionOfMonster,null);
        opponent.field.monsterZone.set(positionOfMonster, targetedMonsters.get(0));
    };
}
