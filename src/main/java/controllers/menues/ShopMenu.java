package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;

import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;
import static controllers.ShowCard.showCard;


public class ShopMenu {
    private User currUser;
    private static ShopMenu singleToneClass = null;

    private ShopMenu (User currUser){
        this.currUser = currUser;
    }

    public static ShopMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new ShopMenu(currUser);
        return singleToneClass;
    }

    public void runShopMenu(User currUser){
        Matcher matcher;
        this.currUser = currUser;

        while (currentMenu == Menu.SHOP_MENU) {
            String command = UserInterface.getUserInput();
            if ((matcher = Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher.group(1));
            else if ((matcher = Regex.getMatcher(command, Regex.shopBuy)).matches()) buyCard(matcher);
            else if (Regex.getMatcher(command, Regex.shopShowAll).matches()) showAllCards();
            else if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) return;
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    private void buyCard(Matcher matcher){
        String cardsName = matcher.group(1);
        if (!Card.allCards.containsKey(cardsName)) UserInterface.printResponse("there is no card with this name");
        else{
            if (Card.allCards.get(cardsName).getPrice() > currUser.getMoney())
                UserInterface.printResponse("not enough money");
            else{
                ArrayList<String> temp = currUser.getCardsBought();
                temp.add(cardsName);
                currUser.setCardsBought(temp);
                int money = currUser.getMoney();
                money -= Card.allCards.get(cardsName).getPrice();
                currUser.setMoney(money);
                UserInterface.printResponse("card bought successfully");
            }
        }
    }

    private void showAllCards(){
        ArrayList<String> allCards = new ArrayList<>();
        for (Map.Entry<String, Card> entry: Card.allCards.entrySet()){
            String key = entry.getKey();
            allCards.add(key);
        }

        Collections.sort(allCards);


        for (String allCard : allCards) {
            UserInterface.printResponse(allCard + ":" + Card.allCards.get(allCard).getPrice());
        }
    }

}
