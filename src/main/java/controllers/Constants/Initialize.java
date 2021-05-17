package controllers.Constants;

import controllers.Database.DataBase;
import models.Card;
import models.Deck;
import models.Monster.*;
import models.SpellAndTrap.*;
import models.User;

import java.io.File;
import java.io.IOException;

import static models.Deck.allDecks;

public class Initialize {

    public static void init() {
        try {
            DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initUserList();
        initDeckList();
    }

    public static void initUserList(){
        File tmpDir = new File("savedList.list");
        if(tmpDir.exists()) User.setUsers(DataBase.loadTheList());
        else {
            new User("admin","admin","Game");
            makeAI();
        }
    }

    private static void makeAI() {
        User user = User.getUserByUsername("admin");
        Deck currDeck = null;
        for(int i=0;i<3;i++){
            assert user != null;
            user.cardsBought.add("Magic Cylinder");
            user.cardsBought.add("Mirror Force");
            user.cardsBought.add("Trap Hole");
            user.cardsBought.add("Torrential Tribute");
            user.cardsBought.add("Negate Attack");
            user.cardsBought.add("Solemn Warning");
            user.cardsBought.add("Time Seal");
            user.cardsBought.add("Battle OX");
            user.cardsBought.add("Axe Raider");
            user.cardsBought.add("Horn Imp");
            user.cardsBought.add("Baby dragon");
            user.cardsBought.add("Battle warrior");
            user.cardsBought.add("Crab Turtle");
        }
        String deckName = "AI";
        for (Deck deck : allDecks) {
            if (deck.getDeckName().equals(deckName)) {
                currDeck = deck;
                break;
            }
        }
        if(currDeck == null) {
            currDeck = new Deck(deckName , "admin");
            for(int i=0;i<2;i++) {
                currDeck.mainDeck.add(new YomiShip(Card.allCards.get("Yomi Ship")));
                currDeck.mainDeck.add(new Suijin(Card.allCards.get("Suijin")));
                currDeck.mainDeck.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
                currDeck.mainDeck.add(Card.getCardByName("Dark Blade"));
                currDeck.mainDeck.add(new BeastKingBarbaros(Card.allCards.get("Beast King Barbaros")));
                currDeck.mainDeck.add(Card.getCardByName("Blue-Eyes white dragon"));
                currDeck.mainDeck.add(new MirageDragon(Card.allCards.get("Mirage Dragon")));
                currDeck.mainDeck.add(new HeraldOfCreation(Card.allCards.get("Herald of Creation")));
                currDeck.mainDeck.add(new CommandKnight(Card.allCards.get("Command Knight")));
                currDeck.mainDeck.add(new TrapHole(Card.allCards.get("Trap Hole")));
                currDeck.mainDeck.add(new MirrorForce(Card.allCards.get("Mirror Force")));
                currDeck.mainDeck.add(new MindCrush(Card.allCards.get("Mind Crush")));
                currDeck.mainDeck.add(new NegateAttack(Card.allCards.get("Negate Attack")));
                currDeck.mainDeck.add(new SolemnWarning(Card.allCards.get("Solemn Warning")));
//                currDeck.mainDeck.add(new MagicJammer(Card.allCards.get("Magic Jammer")));
                currDeck.mainDeck.add(new MonsterReborn(Card.allCards.get("Monster Reborn")));
                currDeck.mainDeck.add(new SwordsOfRevealingLight(Card.allCards.get("Swords of Revealing Light")));
                currDeck.mainDeck.add(new MessengerOfPeace(Card.allCards.get("Messenger of peace")));
                currDeck.mainDeck.add(new TwinTwisters(Card.allCards.get("Twin Twisters")));
                currDeck.mainDeck.add(new MysticalSpaceTyphoon(Card.allCards.get("Mystical space typhoon")));
                currDeck.mainDeck.add(new RingOfDefense(Card.allCards.get("Ring of defense")));
                currDeck.mainDeck.add(new Forest(Card.allCards.get("Forest")));
                currDeck.mainDeck.add(new BlackPendant(Card.allCards.get("Black Pendant")));
                currDeck.mainDeck.add(new AdvancedRitualArt(Card.allCards.get("Advanced Ritual Art")));
            }

            user.setActiveDeck(currDeck);
            DataBase.storeDecks(allDecks);
        }
    }

    public static void initDeckList(){
        File tmpDir = new File("Decks.list");
        if(tmpDir.exists()) Deck.allDecks = DataBase.restoreDecks();
    }

}
