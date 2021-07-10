package controllers.Constants;

import controllers.Database.DataBase;
import models.Card;
import models.Deck;
import models.Monster.*;
import models.SpellAndTrap.*;
import models.User;
import view.SendReceiveData;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    public static void makeAI() {
        User user = User.getUserByUsername("admin");
        if(Objects.isNull(user)) user = new User("admin","admin","Game");
        Deck currDeck = null;
        for(int i=0;i<3;i++){
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

            currDeck.mainDeck.add(new CommandKnight(Card.allCards.get("Command Knight")));
            currDeck.mainDeck.add(new CommandKnight(Card.allCards.get("Command Knight")));
            currDeck.mainDeck.add(new YomiShip(Card.allCards.get("Yomi Ship")));
            currDeck.mainDeck.add(new YomiShip(Card.allCards.get("Yomi Ship")));
            currDeck.mainDeck.add(new Suijin(Card.allCards.get("Suijin")));
            currDeck.mainDeck.add(new Suijin(Card.allCards.get("Suijin")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Crab Turtle")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Skull Guardian")));
            currDeck.mainDeck.add(new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
            currDeck.mainDeck.add(new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
            currDeck.mainDeck.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
            currDeck.mainDeck.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
            currDeck.mainDeck.add(new Marshmallon(Card.allCards.get("Marshmallon")));
            currDeck.mainDeck.add(new Marshmallon(Card.allCards.get("Marshmallon")));
            currDeck.mainDeck.add(new BeastKingBarbaros(Card.allCards.get("Beast King Barbaros")));
            currDeck.mainDeck.add(new BeastKingBarbaros(Card.allCards.get("Beast King Barbaros")));
            currDeck.mainDeck.add(new HeraldOfCreation(Card.allCards.get("Herald of Creation")));
            currDeck.mainDeck.add(new HeraldOfCreation(Card.allCards.get("Herald of Creation")));
            currDeck.mainDeck.add(new TerratigerTheEmpoweredWarrior(Card.allCards.get("Terratiger, the Empowered Warrior")));
            currDeck.mainDeck.add(new TerratigerTheEmpoweredWarrior(Card.allCards.get("Terratiger, the Empowered Warrior")));
            currDeck.mainDeck.add(new TheTricky(Card.allCards.get("The Tricky")));
            currDeck.mainDeck.add(new TheTricky(Card.allCards.get("The Tricky")));
            currDeck.mainDeck.add(new MonsterReborn(Card.allCards.get("Monster Reborn")));
            currDeck.mainDeck.add(new Raigeki(Card.allCards.get("Raigeki")));
            currDeck.mainDeck.add(new HarpiesFeatherDuster(Card.allCards.get("Harpie's Feather Duster")));
            currDeck.mainDeck.add(new DarkHole(Card.allCards.get("Dark Hole")));
            currDeck.mainDeck.add(new TwinTwisters(Card.allCards.get("Twin Twisters")));
            currDeck.mainDeck.add(new MysticalSpaceTyphoon(Card.allCards.get("Mystical space typhoon")));
            currDeck.mainDeck.add(new Yami(Card.allCards.get("Yami")));
            currDeck.mainDeck.add(new Forest(Card.allCards.get("Forest")));
            currDeck.mainDeck.add(new ClosedForest(Card.allCards.get("Closed Forest")));
            currDeck.mainDeck.add(new Umiiruka(Card.allCards.get("Umiiruka")));
            currDeck.mainDeck.add(new SwordOfDarkDestruction(Card.allCards.get("Sword of dark destruction")));
            currDeck.mainDeck.add(new BlackPendant(Card.allCards.get("Black Pendant")));
            currDeck.mainDeck.add(new UnitedWeStand(Card.allCards.get("United We Stand")));
            currDeck.mainDeck.add(new MagnumShield(Card.allCards.get("Magnum Shield")));
            currDeck.mainDeck.add(new AdvancedRitualArt(Card.allCards.get("Advanced Ritual Art")));
            currDeck.mainDeck.add(new AdvancedRitualArt(Card.allCards.get("Advanced Ritual Art")));
            currDeck.mainDeck.add(new MagicCylinder(Card.allCards.get("Magic Cylinder")));
            currDeck.mainDeck.add(new MirrorForce(Card.allCards.get("Mirror Force")));
            currDeck.mainDeck.add(new MindCrush(Card.allCards.get("Mind Crush")));
            currDeck.mainDeck.add(new TrapHole(Card.allCards.get("Trap Hole")));
            currDeck.mainDeck.add(new TorrentialTribute(Card.allCards.get("Torrential Tribute")));
            currDeck.mainDeck.add(new TimeSeal(Card.allCards.get("Time Seal")));
            currDeck.mainDeck.add(new NegateAttack(Card.allCards.get("Negate Attack")));
            currDeck.mainDeck.add(new SolemnWarning(Card.allCards.get("Solemn Warning")));
            currDeck.mainDeck.add(new MagicJammer(Card.allCards.get("Magic Jammer")));
            currDeck.mainDeck.add(new CallOfTheHaunted(Card.allCards.get("Call of The Haunted")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Battle OX")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Axe Raider")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Horn Imp")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Fireyarou")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Dark magician")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Battle warrior")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Crawling dragon")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Flame manipulator")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Leotron")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Alexandrite Dragon")));
            currDeck.mainDeck.add(new Monster(Card.allCards.get("Dark Blade")));



            user.setActiveDeck(currDeck);
            DataBase.saveTheUserList(User.getUsers());
            DataBase.storeDecks(allDecks);
        }
        user.setActiveDeck(currDeck);
    }

    public static void initDeckList(){
        File tmpDir = new File("Decks.list");
        if(tmpDir.exists()) Deck.allDecks = DataBase.restoreDecks();
    }
}
