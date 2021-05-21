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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class SummonMonsterTest implements BackupDatabase {
    Battlefield battlefield;
    ArrayList<Card> expectedMonsterZone = new ArrayList<>();

    @BeforeEach
    public void initForSummon(){
        Initialize.init();
        Duelist duelist1 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        String data = "1\nselect 5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        battlefield = new Battlefield(duelist1, duelist2);
        //going to main_phase 1
        battlefield.nextPhase();
        battlefield.nextPhase();
        //cards added to monsterZone
        battlefield.getTurn().field.hand.add(Card.getCardByName("Wattaildragon"));
        battlefield.selectedCard = Card.getCardByName("Wattaildragon");
        battlefield.getTurn().field.monsterZone.set(0,Card.getCardByName("Wattaildragon"));
        battlefield.getTurn().field.monsterZone.set(1,Card.getCardByName("Warrior Dai Grepher"));
        battlefield.getTurn().field.monsterZone.set(3,new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
        battlefield.getTurn().field.monsterZone.set(4,new GateGuardian(Card.allCards.get("Gate Guardian")));
    }

    @Test
    public void summonMonsterTest() throws IOException {
        moveDatabase();

        initForSummon();
        Monster monster = (Monster) battlefield.getTurn().field.monsterZone.get(4);
        String data = "select 5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        battlefield.summon();
        assertEquals(battlefield.getTurn().field.graveYard.get(0), monster);

        backDatabase();
    }
}
