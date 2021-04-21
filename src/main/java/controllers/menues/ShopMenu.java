package controllers.menues;

import controllers.ProgramController;
import controllers.Regex;

import models.Card;
import models.Monster.Monster;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;


public class ShopMenu {

//    public void runShopMenu(String command){
//        Matcher matcher;
//
//        if ((matcher = Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher);
//        else if ((matcher = Regex.getMatcher(command, Regex.shopBuy)).matches()) buyCard(matcher);
//        else if (Regex.getMatcher(command, Regex.shopShowAll).matches()) showAllCards();
//        else UserInterface.printResponse(Responses.INVALID_COMMAND);
//    }
//
//    private void showCard(Matcher matcher){
//        for (Card card: allMonsters)
//            if(card.getName().equals(matcher.group(1))){
//                System.out.println(card.toString());
//                return;
//            }
//        System.out.println("there is no card with this name");
//    }
//
//    private void buyCard(Matcher matcher){
//        for (Monster card: allMonsters)
//            if(card.getName().equals(matcher.group(1))){
//                new ProgramController().loggedUser.cardsBought.add(card);
//                return;
//            }
//        System.out.println("there is no card with this name");
//    }
//
//    private void showAllCards(){
//        for (Monster card: allMonsters)
//            System.out.println(card.getName() + ":" + card.getPrice());
//    }

}
