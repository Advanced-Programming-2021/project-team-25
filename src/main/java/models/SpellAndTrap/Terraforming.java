package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import controllers.ShowCard;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import view.Responses;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class Terraforming extends SpellAndTrap implements Serializable {
    Duelist turn;
    public Terraforming (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
        expireTime = 1;
    }

    public Terraforming (Object object){
        super(((Terraforming)object).getName(), ((Terraforming)object).getCardsType(),
                ((Terraforming)object).getDescription(), ((Terraforming)object).getPrice(),
                ((Terraforming)object).getIcon(), ((Terraforming)object).getStatus());
        expireTime = 1;
    }

    @Override
    public void action(Battlefield battlefield) {
        if(expireTime == 0){
            expireTime = 1;
            removeSpellOrTrap(battlefield);
        }
        else {
            expireTime--;
            turn = battlefield.getTurn();
            ArrayList<Card> fieldCards = new ArrayList<>();

            //checking not null
            if(!Objects.isNull(turn)){

                showFieldCardsToUser(fieldCards);
                //checking that is a field card found or not
                if(fieldCards.isEmpty()){
                    UserInterface.printResponse("No field card in deck");
                    return;
                }

                UserInterface.printResponse("""
                    please enter the card id you want by entering the id in command
                    "select [id]"
                     or type exit""");

                addFieldCardToHand(fieldCards);
            }
        }
    }

    private void addFieldCardToHand(ArrayList<Card> fieldCards) {
        boolean isChoiceEnded = false;
        Matcher matcher;
        String command;

        command = UserInterface.getUserInput();
        matcher = Regex.getMatcher(command,Regex.selectFieldCard);

        while(!isChoiceEnded){
            if(matcher.find()){
                //get User input
                int number = Integer.parseInt(matcher.group(1));

                if(countCardInHand()<6){
                    turn.field.hand.add(fieldCards.get(number));
                    turn.field.deck.remove(fieldCards.get(number));
                }
                else
                    UserInterface.printResponse(Responses.NOT_ENOUGH_SPACE_IN_HAND);
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

    private void showFieldCardsToUser(ArrayList<Card> fieldCards) {
        for(Card card : turn.field.deck){
            //checking if the card is spell
            //after that check if that is field card
            if(card.getCardsType().equals(Type.SPELL) &&
                    ((SpellAndTrap)card).getIcon().equals("Field")){
                ShowCard.showCard(card.getName());
                //add the card to array list
                fieldCards.add(card);
                //show the id to user
                UserInterface.printResponse("ID Of card :"+ fieldCards.indexOf(card));
            }
        }
    }

    private int countCardInHand(){
        int count = 0;
        for(Card card : turn.field.hand){
            if(card != null) count++;
        }
        return count;
    }
}
