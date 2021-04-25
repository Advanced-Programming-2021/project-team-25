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


public class ShopMenu {
    private final User currUser;
    private static ShopMenu singleToneClass = null;

    private ShopMenu (User currUser){
        this.currUser = currUser;
    }

    public static ShopMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new ShopMenu(currUser);
        return singleToneClass;
    }

    public void runShopMenu(){
        Matcher matcher;

        while (currentMenu == Menu.SHOP_MENU) {
            String command = UserInterface.getUserInput();
            if ((matcher = Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.shopBuy)).matches()) buyCard(matcher);
            else if (Regex.getMatcher(command, Regex.shopShowAll).matches()) showAllCards();
            else if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    private void showCard(Matcher matcher){
        String cardsName = matcher.group(1);
        if (!Card.allCards.containsKey(cardsName)) UserInterface.printResponse("there is no card with this name");
        else{
            if (Card.allCards.get(cardsName).getCardsType() == Type.MONSTER){
                Monster monster = (Monster) Card.allCards.get(cardsName);
                UserInterface.printResponse("Name: " + cardsName);
                UserInterface.printResponse("Level: " + monster.getLevel());
                UserInterface.printResponse("Type: " + monster.getCardTypeInExel());
                UserInterface.printResponse("ATK: " + monster.getAttack());
                UserInterface.printResponse("DEF" + monster.getDefence());
                UserInterface.printResponse("Description" + monster.getDescription());
            }
            else{
                SpellAndTrap spellAndTrap = (SpellAndTrap) Card.allCards.get(cardsName);
                UserInterface.printResponse("Name: " + cardsName);
                if (spellAndTrap.getCardsType() == Type.SPELL) UserInterface.printResponse("Spell");
                else UserInterface.printResponse("Trap");
                UserInterface.printResponse("Type: " + spellAndTrap.getIcon());
                UserInterface.printResponse("Description: " + spellAndTrap.getDescription());
            }
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
