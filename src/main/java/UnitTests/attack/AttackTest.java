package UnitTests.attack;

import controllers.Battelfield.Battlefield;
import controllers.Constants.Initialize;
import controllers.Regex;
import controllers.menues.DuelMenu;
import models.Card;
import models.CardStufs.FaceUp;
import models.Duelist;
import models.Monster.*;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class AttackTest {
    Battlefield battlefield;
    @BeforeEach
    public void initBattlefield(){
        Initialize.init();
        Duelist duelist1 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        String data = "1\nnext phase";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        battlefield = new Battlefield(duelist1, duelist2);
    }
    @Test
    public void testMonsterAttack(){
        initBattlefield();
        ArrayList<Monster> listOfSpecialMonsters = new ArrayList<>();
        ArrayList<Monster> listOfNormalMonsters = new ArrayList<>();

        ArrayList<Integer> defenceReturn = new ArrayList<>();
        ArrayList<Integer> expectedReturn = new ArrayList<>();

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
        //normal monsters with 14 monsters
        listOfNormalMonsters.add((Monster) Card.getCardByName("Feral Imp"));///att 1300 def 1400
        listOfNormalMonsters.add((Monster) Card.getCardByName("Dark magician"));//def 2100 att 2500
        listOfNormalMonsters.add((Monster) Card.getCardByName("Fireyarou"));//def 1000 att 1300
        listOfNormalMonsters.add((Monster) Card.getCardByName("Wattkid"));//def 500 att 1000
        listOfNormalMonsters.add((Monster) Card.getCardByName("Baby dragon"));// def 700 att 1200
        listOfNormalMonsters.add((Monster) Card.getCardByName("Hero of the east"));// def 1000 att 1100
        listOfNormalMonsters.add((Monster) Card.getCardByName("Battle warrior"));// def 1000 att 700
        listOfNormalMonsters.add((Monster) Card.getCardByName("Crawling dragon"));//def 1400 att 1600
        listOfNormalMonsters.add((Monster) Card.getCardByName("Flame manipulator"));// def 1000 att 900
        listOfNormalMonsters.add((Monster) Card.getCardByName("Blue-Eyes white dragon"));//def 2500 att 3000
        listOfNormalMonsters.add((Monster) Card.getCardByName("Haniwa"));//def 500 att 500
        listOfNormalMonsters.add((Monster) Card.getCardByName("Wattaildragon"));//def 1000 att 2500
        listOfNormalMonsters.add((Monster) Card.getCardByName("Warrior Dai Grepher"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Dark Blade"));
        //ritual monsters
        listOfNormalMonsters.add((Monster) Card.getCardByName("Crab Turtle"));
        listOfNormalMonsters.add((Monster) Card.getCardByName("Skull Guardian"));

        for(int i=0;i<6;i++) {
            //0 until 5
            battlefield.attackingMonster = listOfNormalMonsters.get(i);
            battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
            //6 until 12
            battlefield.attackedMonster = listOfNormalMonsters.get(listOfNormalMonsters.size()-i-5);
            battlefield.attackedMonster.setCardsFace(FaceUp.ATTACK);

            defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));
        }
        expectedReturn.add(-1);
        expectedReturn.add(1);
        expectedReturn.add(-1);
        expectedReturn.add(1);
        expectedReturn.add(-1);
        expectedReturn.add(1);

        assertEquals(defenceReturn.size(), expectedReturn.size());

        for(int i = 0; i < defenceReturn.size(); i++) {
            assertEquals(defenceReturn.get(i), expectedReturn.get(i));
        }

    }
}
