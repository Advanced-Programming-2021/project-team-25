package controllers.menues;

import controllers.Database.DataBase;
import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.Card;
import models.CardStufs.Type;
import models.Deck;
import models.Monster.*;
import models.SpellAndTrap.*;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;
import static models.Deck.allDecks;

public class DeckMenu {

    private final User currUser;

    public DeckMenu(User currUser){
        this.currUser=currUser;
    }

    public void runDeckMenu(){

        while (currentMenu == Menu.DECK_MENU) {

            String command = UserInterface.getUserInput();

            Matcher matcher;

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            //else if(Regex.getMatcher(command, Regex.menuEnter).matches()) directMenu(Regex.getMatcher(command, Regex.menuEnter));
            else if(Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if ((matcher = Regex.getMatcher(command, Regex.deckCreate)).matches()) createDeck(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.deckDelete)).matches()) deleteDeck(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.deckSetActive)).matches()) setActive(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.deckAddCard)).matches()) addCard(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.deckRemoveCard)).matches()) removeCard(matcher);
            else if (Regex.getMatcher(command, Regex.deckShowAll).matches()) deckShowAll();
            else if ((matcher = Regex.getMatcher(command, Regex.deckShowDeckName)).matches()) showDeck(matcher);
            else if (Regex.getMatcher(command, Regex.deckShowCards).matches()) deckShowCards();
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
            //DataBase.storeDecks();

        }
    }

    private void createDeck(Matcher matcher){
        String deckName = matcher.group(1);
            for (Deck deck : allDecks)
                if (deck.getDeckName().equals(deckName)) {
                    UserInterface.printResponse("Responses.INVALID_COMMAND");
                    return;
                }
        UserInterface.printResponse(Responses.DECK_CREATE_SUCCESS);
        new Deck(deckName , currUser.getUsername());
    }

    private void deleteDeck(Matcher matcher){
        boolean exists = false;
        for (Deck deck:allDecks)
            if (deck.getDeckName().equals(matcher.group(1))) {
                exists = true;
                break;
            }

        if (exists) {
            allDecks.removeIf(deck -> deck.getDeckName().equals(matcher.group(1)));
            UserInterface.printResponse(Responses.DECK_DELETE_SUCCESS);
        }
        else
            UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void setActive(Matcher matcher){
        String deckName = matcher.group(1);
        for (Deck deck:allDecks){
            if (deck.getDeckName().equals(deckName)){
                currUser.activeDeck = deck;
                UserInterface.printResponse(Responses.DECK_ACTIVE_SUCCESS);
                return;
            }
        }
        UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void addCard(Matcher matcher){
        String cardName = matcher.group(1) ,deckName = matcher.group(2);

        if(Deck.getDeckByName(deckName) == null ) UserInterface.printResponse("deck with name " + deckName + " does not exists");
        //else if(numberOfCards(cardName,deckName)) UserInterface.printResponse("card with name " + cardName + " does not exists");
        else if(Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.size() == 60) UserInterface.printResponse("main deck is full");
        else if(Deck.getNumberOfCardsInDeck(deckName , cardName) == 3) UserInterface.printResponse("there are already three cards with name " + cardName + " in deck " + deckName);
        else{
            Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.add(Cards(cardName));
            UserInterface.printResponse("card added to deck successfully");
        }
    }

    private void removeCard(Matcher matcher){
        String cardName = matcher.group(1) ,deckName = matcher.group(2);

        if(Deck.getDeckByName(deckName) == null ) UserInterface.printResponse("deck with " + deckName + "does not exists");
        else if(Deck.getNumberOfCardsInDeck(deckName , cardName) == 0) UserInterface.printResponse("card with name " + cardName + " does not exist in main deck");
        else{
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck)
                if(card.getName().equals(cardName)){
                    Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.remove(card);
                    UserInterface.printResponse("card removed from deck successfully");
                    return;
                }
        }
    }

    private void deckShowAll(){
        String activeDeckName = "";
        Deck activeDeck = currUser.activeDeck;

        UserInterface.printResponse("Decks:\nActive Deck:");
        if(activeDeck != null){
            UserInterface.printResponse(activeDeck.getDeckName() + ": " + activeDeck.mainDeck.size() + ", " + activeDeck.sideDeck.size() + ", " + "valid");
            activeDeckName = activeDeck.getDeckName();
        }
        UserInterface.printResponse("Other Decks:");
        ArrayList<Deck> decks = new ArrayList<>();
        for (Deck deck: allDecks)
            if(deck.getOwnerName().equals(currUser.getUsername()) && !deck.getDeckName().equals(activeDeckName))
                decks.add(deck);

        Comparator<Deck> orderedDecks = Comparator.comparing(Deck::getDeckName);

        decks.sort(orderedDecks);
        for (Deck deck: decks)
            UserInterface.printResponse(deck.getDeckName() + ": " + deck.mainDeck.size() + ", " + deck.sideDeck.size() + ", " + "valid");
    }

    private void showDeck(Matcher matcher){
        String deckName = matcher.group(1);
        if(Deck.getDeckByName(deckName)==null)
            UserInterface.printResponse("deck with name" + deckName + "does not exist");
        else {
            UserInterface.printResponse("Deck: " + deckName);
            UserInterface.printResponse("Main deck:");
            UserInterface.printResponse("Monsters:");
            ArrayList<Card> monsters = new ArrayList<>();
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck) {
                if(card.getCardsType() == Type.MONSTER) monsters.add(card);
            }
            Comparator<Card> orderedCards = Comparator.comparing(Card::getName);

            monsters.sort(orderedCards);
            for (Card card : monsters) {
                UserInterface.printResponse(card.getName() + " : " + card.getDescription());
            }

            UserInterface.printResponse("Spell and Traps:");
            ArrayList<Card> spellAndTraps = new ArrayList<>();
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck) {
                if(card.getCardsType() != Type.MONSTER) spellAndTraps.add(card);
            }

            spellAndTraps.sort(orderedCards);
            for (Card card : spellAndTraps) {
                UserInterface.printResponse(card.getName() + " : " + card.getDescription());
            }
        }
    }

    private void deckShowCards(){
        Collections.sort(currUser.cardsBought);
        for (String cardName : currUser.cardsBought)
            UserInterface.printResponse(cardName + " : " + Card.allCards.get(cardName).getDescription());
    }

    private Card Cards(String name) {
        Card temp;
        if (name.equals("Yomi Ship")) temp = new YomiShip(Card.allCards.get(name));
        else if (name.equals("Suijin")) temp = new Suijin(Card.allCards.get(name));
        else if (name.equals("Man-Eater Bug")) temp = new ManEaterBug(Card.allCards.get(name));
        else if (name.equals("Gate Guardian")) temp = new GateGuardian(Card.allCards.get(name));
        else if (name.equals("Scanner")) temp = new Scanner(Card.allCards.get(name));
        else if (name.equals("Marshmallon")) temp = new Marshmallon(Card.allCards.get(name));
        else if (name.equals("Beast King Barbaros")) temp = new BeastKingBarbaros(Card.allCards.get(name));
        else if (name.equals("Texchanger")) temp = new Texchanger(Card.allCards.get(name));
        else if (name.equals("The Calculator")) temp = new TheCalculator(Card.allCards.get(name));
        else if (name.equals("Mirage Dragon")) temp = new MirageDragon(Card.allCards.get(name));
        else if (name.equals("Herald of Creation")) temp = new HeraldOfCreation(Card.allCards.get(name));
        else if (name.equals("Exploder Dragon")) temp = new ExploderDragon(Card.allCards.get(name));
        else if (name.equals("Terratiger, the Empowered Warrior")) temp = new TerratigerTheEmpoweredWarrior(Card.allCards.get(name));
        else if (name.equals("The Tricky")) temp = new TheTricky(Card.allCards.get(name));
        else if (name.equals("Command Knight")) temp = new CommandKnight(Card.allCards.get(name));
        else if (name.equals("Trap Hole")) temp = new TrapHole(Card.allCards.get(name));
        else if (name.equals("Mirror Force")) temp = new MirrorForce(Card.allCards.get(name));
        else if (name.equals("Magic Cylinder")) temp = new MagicCylinder(Card.allCards.get(name));
        else if (name.equals("Mind Crush")) temp = new MindCrush(Card.allCards.get(name));
        else if (name.equals("Torrential Tribute")) temp = new TorrentialTribute(Card.allCards.get(name));
        else if (name.equals("Time Seal")) temp = new TimeSeal(Card.allCards.get(name));
        else if (name.equals("Negate Attack")) temp = new NegateAttack(Card.allCards.get(name));
        else if (name.equals("Solemn Warning")) temp = new SolemnWarning(Card.allCards.get(name));
        else if (name.equals("Magic Jamamer")) temp = new MagicJamamer(Card.allCards.get(name));
        else if (name.equals("Call of The Haunted")) temp = new CallOfTheHaunted(Card.allCards.get(name));
        else if (name.equals("Vanity's Emptiness")) temp = new VanitysEmptiness(Card.allCards.get(name));
        else if (name.equals("Wall of Revealing Light")) temp = new WallOfRevealingLight(Card.allCards.get(name));
        else if (name.equals("Monster Reborn")) temp = new MonsterReborn(Card.allCards.get(name));
        else if (name.equals("Terraforming")) temp = new Terraforming(Card.allCards.get(name));
        else if (name.equals("Pot of Greed")) temp = new PotOfGreed(Card.allCards.get(name));
        else if (name.equals("Raigeki")) temp = new Raigeki(Card.allCards.get(name));
        else if (name.equals("Change of Heart")) temp = new ChangeOfHeart(Card.allCards.get(name));
        else if (name.equals("Swords of Revealing Light")) temp = new SwordsOfRevealingLight(Card.allCards.get(name));
        else if (name.equals("Harpie's Feather Duster")) temp = new HarpiesFeatherDuster(Card.allCards.get(name));
        else if (name.equals("Dark Hole")) temp = new DarkHole(Card.allCards.get(name));
        else if (name.equals("Supply Squad")) temp = new SupplySquad(Card.allCards.get(name));
        else if (name.equals("Spell Absorption")) temp = new SpellAbsorption(Card.allCards.get(name));
        else if (name.equals("Messenger of peace")) temp = new MessengerOfPeace(Card.allCards.get(name));
        else if (name.equals("Twin Twisters")) temp = new TwinTwisters(Card.allCards.get(name));
        else if (name.equals("Mystical space typhoon")) temp = new MysticalSpaceTyphoon(Card.allCards.get(name));
        else if (name.equals("Ring of defense")) temp = new RingOfDefense(Card.allCards.get(name));
        else if (name.equals("Yami")) temp = new Yami(Card.allCards.get(name));
        else if (name.equals("Forest")) temp = new Forest(Card.allCards.get(name));
        else if (name.equals("Closed Forest")) temp = new ClosedForest(Card.allCards.get(name));
        else if (name.equals("Umiiruka")) temp = new Umiiruka(Card.allCards.get(name));
        else if (name.equals("Sword of dark destruction")) temp = new SwordOfDarkDestruction(Card.allCards.get(name));
        else if (name.equals("Black Pendant")) temp = new BlackPendant(Card.allCards.get(name));
        else if (name.equals("United We Stand")) temp = new UnitedWeStand(Card.allCards.get(name));
        else if (name.equals("Magnum Shield")) temp = new MagnumShield(Card.allCards.get(name));
        else temp = new Monster(Card.allCards.get(name));
        return temp;
    }

    private boolean numberOfCards(String cardName ,String deckName){
        int i = 0;
        for (String name: currUser.cardsBought)
            if(name.equals(cardName)) i++;

        return i == Deck.getNumberOfCardsInDeck(deckName, cardName);
    }

}