
import controllers.Menu;
import controllers.Regex;
import controllers.menues.DeckMenu;
import controllers.menues.ProfileMenu;
import models.Deck;
import models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;

import static controllers.ProgramController.currUser;
import static controllers.ProgramController.currentMenu;
import static controllers.menues.LoginMenu.createNewUser;

public class ProfileTest implements BackupDatabase{
    @Test
    public void profileTest() throws IOException {
        moveDatabase();
        new User("pouria1","123","Empire1");
        new User("pouria2","123","Empire2");
        new User("pouria3","123","Empire3");
        new User("pouria4","123","Empire4");
        new User("pouria5","123","Empire5");
        new User("pouria6","123","Empire6");
        new User("pouria7","123","Empire7");
        String userInputs = "profile change --nickname e1" + System.getProperty("line.separator")
                + "profile change --password --current 123 --new 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "profile change -n e2" + System.getProperty("line.separator")
                + "profile change -p -c 123 -n 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "profile change -p --current 123 --new 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "profile change --password -c 123 --new 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "profile change -p -c 123 --new 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "profile change --password --current 123 -n 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator")
                + "profile change -p -c 123 -n 321" + System.getProperty("line.separator")
                + "menu exit" + System.getProperty("line.separator");
        new Regex();
        System.setIn(new ByteArrayInputStream(userInputs.getBytes()));
        User user1 = User.getUserByUsername("pouria1");
        new ProfileMenu(user1).runProfileMenu(user1);
        assert user1 != null;
        Assert.assertEquals(user1.getPassword(),"321");
        Assert.assertEquals(user1.getNickName(),"e1");
        User user2 = User.getUserByUsername("pouria2");
        new ProfileMenu(user2).runProfileMenu(user2);
        assert user2 != null;
        Assert.assertEquals(user2.getPassword(),"321");
        Assert.assertEquals(user2.getNickName(),"e2");
        User user3 = User.getUserByUsername("pouria3");
        new ProfileMenu(user3).runProfileMenu(user3);
        assert user3 != null;
        Assert.assertEquals(user3.getPassword(),"321");
        User user4 = User.getUserByUsername("pouria4");
        new ProfileMenu(user4).runProfileMenu(user4);
        assert user4 != null;
        Assert.assertEquals(user4.getPassword(),"321");
        User user5 = User.getUserByUsername("pouria5");
        new ProfileMenu(user5).runProfileMenu(user5);
        assert user5 != null;
        Assert.assertEquals(user5.getPassword(),"321");
        User user6 = User.getUserByUsername("pouria6");
        new ProfileMenu(user6).runProfileMenu(user6);
        assert user6 != null;
        Assert.assertEquals(user6.getPassword(),"321");
        User user7 = User.getUserByUsername("pouria7");
        new ProfileMenu(user7).runProfileMenu(user7);
        assert user7 != null;
        Assert.assertEquals(user7.getPassword(),"321");
        backDatabase();
    }
}
