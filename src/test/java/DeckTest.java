import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import controllers.menues.DeckMenu;
import models.Deck;
import models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static controllers.ProgramController.currUser;
import static controllers.ProgramController.currentMenu;

public class DeckTest implements BackupDatabase {
    @Test
    public void deckTest() throws IOException {
        moveDatabase();
        String userInputs = "deck create alire" + System.getProperty("line.separator")
                + "deck create aaa" + System.getProperty("line.separator")
                + "deck create aaaaa" + System.getProperty("line.separator")
                + "deck set-activate alire" + System.getProperty("line.separator")
                + "deck show -a" + System.getProperty("line.separator")
                + "deck show -d alire" + System.getProperty("line.separator")
                + "deck show -d alire --side" + System.getProperty("line.separator")
                + "deck show --cards" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator");
        new Regex();
        System.setIn(new ByteArrayInputStream(userInputs.getBytes()));
        currentMenu = Menu.DECK_MENU;
        currUser = new User("alireza","123","game");
        DeckMenu.getInstance(currUser).runDeckMenu();
        Assert.assertNotNull(Deck.getDeckByName("alire"));
        Assert.assertNotNull(Deck.getDeckByName("aaa"));
        Assert.assertNotNull(Deck.getDeckByName("aaaaa"));
        Assert.assertNotNull(Deck.getDeckByName("alire"));
        User user = User.getUserByUsername("alireza");
        assert user != null;
        Assert.assertEquals(user.getActiveDeck(),Deck.getDeckByName("alire"));
        backDatabase();
    }

}
