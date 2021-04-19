package controllers.menues;

import controllers.ProgramController;
import controllers.Regex;
import models.Card;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

import static models.Card.allCards;

public class ShopMenu {
    public void runShopMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.shopBuy)).matches()) buyCard(matcher);
        else if (Regex.getMatcher(command, Regex.shopShowAll).matches()) showAllCards();
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void showCard(Matcher matcher){
        for (Card card: allCards)
            if(card.getName().equals(matcher.group(1))){
                System.out.println(card.toString());
                return;
            }
        System.out.println("there is no card with this name");
    }

    private void buyCard(Matcher matcher){
        for (Card card: allCards)
            if(card.getName().equals(matcher.group(1))){
                new ProgramController().loggedUser.cardsBought.add(card);
                return;
            }
        System.out.println("there is no card with this name");
    }

    private void showAllCards(){
        for (Card card: allCards)
            System.out.println(card.getName() + ":" + card.getPrice());
    }
}
