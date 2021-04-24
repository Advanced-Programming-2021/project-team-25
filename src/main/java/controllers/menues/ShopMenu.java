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
import java.util.Map;
import java.util.regex.Matcher;


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

        while (true) {
            String command = UserInterface.getUserInput();
            if ((matcher = Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.shopBuy)).matches()) buyCard(matcher);
            else if (Regex.getMatcher(command, Regex.shopShowAll).matches()) showAllCards();
            else if (Regex.getMatcher(command, Regex.menuShowCurrent).matches())
                UserInterface.printResponse("shop menu");
            else if (command.startsWith("menu enter"))
                UserInterface.printResponse("menu navigation is not possible");
            else if (command.equals("menu exit")){
                ProgramController.currentMenu = Menu.MAIN_MENU;
                break;
            }
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

        for (int i = 0; i < allCards.size(); ++i){
            for (int j = 0; j < allCards.size(); ++j){
                if (allCards.get(i).compareTo(allCards.get(j)) > 0){
                    String temp = allCards.get(i);
                    allCards.set(i, allCards.get(j));
                    allCards.set(j, temp);
                }
            }
        }

        for (int i = 0; i<allCards.size(); ++i){
            UserInterface.printResponse(allCards.get(i) + ":" + Card.allCards.get(allCards.get(i)).getPrice());
        }
    }

}
