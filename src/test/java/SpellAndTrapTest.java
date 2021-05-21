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

public class SpellAndTrapTest implements BackupDatabase {
    Battlefield battlefield;
    ArrayList<Card> expectedMonsterZone = new ArrayList<>();
    ArrayList<Card> expectedSpellAndTrapZone = new ArrayList<>();
    @BeforeEach
    public void initForSpell(){
        Initialize.init();
        Duelist duelist1 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername("admin")));
        String data = "1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        battlefield = new Battlefield(duelist1, duelist2);
        //cards added to monsterZone
        battlefield.getOpponent().field.monsterZone.add(Card.getCardByName("Wattaildragon"));
        battlefield.getOpponent().field.monsterZone.add(Card.getCardByName("Warrior Dai Grepher"));
        battlefield.getOpponent().field.monsterZone.add(Card.getCardByName("Dark Blade"));
        battlefield.getOpponent().field.monsterZone.add(new ManEaterBug(Card.allCards.get("Man-Eater Bug")));
        battlefield.getOpponent().field.monsterZone.add(new GateGuardian(Card.allCards.get("Gate Guardian")));
        //cards added to spellAndTrap zone
        battlefield.getOpponent().field.monsterZone.add(new TrapHole(Card.allCards.get("Trap Hole")));
        battlefield.getOpponent().field.monsterZone.add(new MirrorForce(Card.allCards.get("Mirror Force")));
        battlefield.getOpponent().field.monsterZone.add(new SupplySquad(Card.allCards.get("Supply Squad")));
        battlefield.getOpponent().field.monsterZone.add(new MonsterReborn(Card.allCards.get("Monster Reborn")));
        battlefield.getOpponent().field.monsterZone.add(new ChangeOfHeart(Card.allCards.get("Change of Heart")));
    }
    @Test
    public void spellAndTrapTest() throws IOException {
        moveDatabase();

        initForSpell();
        //just one card added to turn and activate it
        //first test :
        battlefield.getTurn().field.spellTrapZone.add(0,new Raigeki(Card.allCards.get("Raigeki")));
        battlefield.getTurn().field.spellTrapZone.get(0).action(battlefield);
        expectedMonsterZone.add(null);
        expectedMonsterZone.add(null);
        expectedMonsterZone.add(null);
        expectedMonsterZone.add(null);
        expectedMonsterZone.add(null);

        for(int i = 0; i < 5; i++) {
            assertEquals(expectedMonsterZone.get(i), battlefield.getOpponent().field.monsterZone.get(i));
        }
        backDatabase();
    }
}
