package controllers;

import models.Card;
import models.CardStufs.Type;
import models.Monster.*;
import models.SpellAndTrap.*;
import view.UserInterface;

public class ShowCard {

    public static void showCard(String cardsName){
        if (!Card.allCards.containsKey(cardsName)) UserInterface.printResponse("there is no card with this name");
        else{
            if (Card.allCards.get(cardsName).getCardsType() == Type.MONSTER){
                Monster monster = (Monster) Card.allCards.get(cardsName);
                UserInterface.printResponse("Name: " + cardsName);
                UserInterface.printResponse("Level: " + monster.getLevel());
                UserInterface.printResponse("Type: " + monster.getCardTypeInExel());
                UserInterface.printResponse("ATK: " + monster.getAttack());
                UserInterface.printResponse("DEF: " + monster.getDefence());
                UserInterface.printResponse("Description: " + monster.getDescription());
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

    public static Card Cards(String name) {
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
        else if (name.equals("Herald of Creation")) temp = new HeraldOfCreation(Card.allCards.get(name));
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
        else if (name.equals("Magic Jammer")) temp = new MagicJammer(Card.allCards.get(name));
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
        else if (name.equals("Advanced Ritual Art")) temp = new AdvancedRitualArt(Card.allCards.get(name));
        else temp = new Monster(Card.allCards.get(name));
        return temp;
    }

}
