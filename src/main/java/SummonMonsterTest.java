import controllers.Battelfield.Battlefield;
import controllers.Constants.Initialize;
import models.Card;
import models.Duelist;
import models.Monster.GateGuardian;
import models.Monster.ManEaterBug;
import models.Monster.Monster;
import models.SpellAndTrap.*;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class SummonMonsterTest {
    Battlefield battlefield;
    ArrayList<Card> expectedMonsterZone = new ArrayList<>();
    @BeforeEach
    public void initForSummon(){
        Initialize.init();
        Duelist duelist1 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        String data = "1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        battlefield = new Battlefield(duelist1, duelist2);
        //cards added to monsterZone
        battlefield.selectedCard = Card.getCardByName("Wattaildragon");
        battlefield.getOpponent().field.monsterZone.add(Card.getCardByName("Wattaildragon"));
        battlefield.getOpponent().field.monsterZone.add(Card.getCardByName("Warrior Dai Grepher"));
        battlefield.getOpponent().field.monsterZone.add(Card.getCardByName("Dark Blade"));
        battlefield.getOpponent().field.monsterZone.add(new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
        battlefield.getOpponent().field.monsterZone.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
    }

    @Test
    public void summonMonsterTest(){
        initForSummon();
        Monster monster = (Monster) battlefield.getTurn().field.monsterZone.get(4);
        battlefield.summon();
        String data = "select 5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        String data2 = "surrender$";
        System.setIn(new ByteArrayInputStream(data2.getBytes()));

        assertEquals(battlefield.getTurn().field.graveYard.get(0), monster);
    }
}
