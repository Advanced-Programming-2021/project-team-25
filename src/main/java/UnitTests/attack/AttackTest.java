package UnitTests.attack;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import controllers.menues.DuelMenu;
import models.Card;
import models.Duelist;
import models.Monster.*;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Objects;

public class AttackTest {
    Battlefield battlefield;
    @BeforeEach
    public void initBattlefield(){
        Duelist duelist1 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        battlefield = new Battlefield(duelist1, duelist2);
    }
    @Test
    public void testMonsterAttack(){

        ArrayList<Monster> listOfSpecialMonsters = new ArrayList<>();
        ArrayList<Monster> listOfNormalMonsters = new ArrayList<>();
        listOfSpecialMonsters.add(new YomiShip(Card.allCards.get("Yomi Ship")));
        listOfSpecialMonsters.add(new Suijin(Card.allCards.get("Suijin")));
        listOfSpecialMonsters.add(new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
        listOfSpecialMonsters.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
        listOfSpecialMonsters.add(new Scanner(Card.allCards.get("Scanner")));
        listOfSpecialMonsters.add(new Marshmallon(Card.allCards.get("Marshmallon")));
        listOfSpecialMonsters.add(new BeastKingBarbaros(Card.allCards.get("Beast King Barbaros")));
        listOfSpecialMonsters.add(new Texchanger(Card.allCards.get("Texchanger")));
        listOfSpecialMonsters.add(new TheCalculator(Card.allCards.get("The Calculator")));
        listOfSpecialMonsters.add(new MirageDragon(Card.allCards.get("Mirage Dragon")));
        listOfSpecialMonsters.add(new HeraldOfCreation(Card.allCards.get("Herald of Creation")));
        listOfSpecialMonsters.add(new ExploderDragon(Card.allCards.get("Exploder Dragon")));
        listOfSpecialMonsters.add(new TerratigerTheEmpoweredWarrior(Card.allCards.get("Terratiger, the Empowered Warrior")));
        listOfSpecialMonsters.add(new TheTricky(Card.allCards.get("The Tricky")));
        //normal monsters
        listOfNormalMonsters.add((Monster) Card.getCardByName("Feral Imp"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Dark Magician"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Feral Imp"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Wattkid"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Baby Dragon"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Hero of the East"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Battle Warrior"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Crawling dragon"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Flame Manipulator"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Blue-Eyes White Dragon"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Feral Imp"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Haniwa"));
        //ritual monsters
        listOfNormalMonsters.add((Monster) Card.getCardByName("Crab Turtle"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Skull Guardian"));

        for(int i=0;i<listOfNormalMonsters.size() - 1;i++) {
            battlefield.attackingMonster = listOfNormalMonsters.get(i);
            battlefield.attackedMonster = listOfNormalMonsters.get(i+1);

            battlefield.attackingMonster.action(battlefield);
        }

    }
}
