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
    ArrayList<Monster> listOfSpecialMonsters = new ArrayList<>();
    ArrayList<Monster> listOfNormalMonsters = new ArrayList<>();

    ArrayList<Integer> defenceReturn = new ArrayList<>();
    ArrayList<Integer> expectedReturn = new ArrayList<>();

    Battlefield battlefield;
    @BeforeEach
    public void initBattlefield(){
        Initialize.init();
        User user = User.getUserByUsername("admin");
        Initialize.makeAI();
        Duelist duelist1 = new Duelist(user);
        Duelist duelist2 = new Duelist(user);
        String data = "1\nnext phase";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        battlefield = new Battlefield(duelist1, duelist2);
    }

    private void initializeMonster() {
        initBattlefield();
        listOfSpecialMonsters.add(new YomiShip(Card.allCards.get("Yomi Ship")));
        listOfSpecialMonsters.add(new Suijin(Card.allCards.get("Suijin")));
        listOfSpecialMonsters.add(new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
        listOfSpecialMonsters.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
        listOfSpecialMonsters.add(new Scanner(Card.allCards.get("Scanner")));
        listOfSpecialMonsters.add(new Marshmallon(Card.allCards.get("Marshmallon")));
        listOfSpecialMonsters.add(new BeastKingBarbaros(Card.allCards.get("Beast King Barbaros")));
        listOfSpecialMonsters.add(new Texchanger(Card.allCards.get("Texchanger")));
        listOfSpecialMonsters.add(new TheCalculator(Card.allCards.get("The Calculator")));
        listOfSpecialMonsters.add(new HeraldOfCreation(Card.allCards.get("Herald of Creation")));
        listOfSpecialMonsters.add(new TerratigerTheEmpoweredWarrior(Card.allCards.get("Terratiger, the Empowered Warrior")));
        listOfSpecialMonsters.add(new TheTricky(Card.allCards.get("The Tricky")));
        //normal monsters with 14 monsters
        listOfNormalMonsters.add((Monster) Card.getCardByName("Feral Imp"));///def 1400 att 1300
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
    }

    @Test
    public void testMonsterAttack(){
        initializeMonster();
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
    @Test
    public void testMonsterDefence(){
        initializeMonster();

        defenceReturn.clear();expectedReturn.clear();

        battlefield.attackingMonster = listOfNormalMonsters.get(0);
        battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
        battlefield.attackedMonster = listOfNormalMonsters.get(11);
        battlefield.attackedMonster.setCardsFace(FaceUp.DEFENSE_FRONT);
        defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));

        battlefield.attackingMonster = listOfNormalMonsters.get(1);
        battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
        battlefield.attackedMonster = listOfNormalMonsters.get(10);
        battlefield.attackedMonster.setCardsFace(FaceUp.DEFENSE_BACK);
        defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));

        battlefield.attackingMonster = listOfNormalMonsters.get(2);
        battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
        battlefield.attackedMonster = listOfNormalMonsters.get(9);
        battlefield.attackedMonster.setCardsFace(FaceUp.DEFENSE_BACK);
        defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));

        battlefield.attackingMonster = listOfNormalMonsters.get(3);
        battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
        battlefield.attackedMonster = listOfNormalMonsters.get(8);
        battlefield.attackedMonster.setCardsFace(FaceUp.DEFENSE_BACK);
        defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));

        battlefield.attackingMonster = listOfNormalMonsters.get(4);
        battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
        battlefield.attackedMonster = listOfNormalMonsters.get(7);
        battlefield.attackedMonster.setCardsFace(FaceUp.DEFENSE_FRONT);
        defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));

        battlefield.attackingMonster = listOfNormalMonsters.get(5);
        battlefield.attackingMonster.setCardsFace(FaceUp.ATTACK);
        battlefield.attackedMonster = listOfNormalMonsters.get(6);
        battlefield.attackedMonster.setCardsFace(FaceUp.DEFENSE_BACK);
        defenceReturn.add(battlefield.attackedMonster.defenceFunc(battlefield));

        expectedReturn.add(1);
        expectedReturn.add(2);
        expectedReturn.add(2);
        expectedReturn.add(2);
        expectedReturn.add(-1);
        expectedReturn.add(2);

        assertEquals(defenceReturn.size(), expectedReturn.size());

        for(int i = 0; i < defenceReturn.size(); i++) {
            assertEquals(expectedReturn.get(i), defenceReturn.get(i));
        }

    }

}
