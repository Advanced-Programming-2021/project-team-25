import controllers.Constants.Initialize;
import controllers.Database.DataBase;
import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import controllers.ShowCard;
import controllers.menues.ProfileMenu;
import controllers.menues.ScoreBoardMenu;
import controllers.menues.ShopMenu;
import models.*;
import models.CardStufs.FaceUp;
import models.CardStufs.Location;
import models.CardStufs.Type;
import models.Monster.Suijin;
import org.junit.Test;
import view.Responses;
import view.UserInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TestClass {


    @Test
    public void test1 (){
        ByteArrayInputStream in2 = new ByteArrayInputStream("xxx\nuser create --username abcd5 --nickname abcd5 --password abcd5\nmenu exit\nmenu exit".getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        String[] args = new String[1];
        args[0] = "ali";
        Main.main(args);
    }

    @Test
    public void test2 (){
        Responses responses = Responses.DECK_CREATE_SUCCESS;
        String message = Responses.DECK_ACTIVE_SUCCESS.getMessage();
        UserInterface.printResponse(Responses.DECK_ACTIVE_SUCCESS);
        UserInterface.printResponse(2);
        UserInterface.printResponse("25");
        ArrayList<User> allUsers = new ArrayList<>();
        User.setUsers(allUsers);
    }

    @Test
    public void test3 (){
        User user = new User("qwe", "1234", "qwe");
        ArrayList<String> cardsBought = user.cardsBought;
        int money = user.money;

        User user1 = new User("qwe2", "12342", 10);
        String password = user1.getPassword();
        ArrayList<User> allUsers = User.getUsers();
        user1.toString();
        user1.getScore();
        user1.setScore(10);
        user1.setNickName("ali");
        user1.setPassword("1234");
        Deck deck = new Deck("MyDeck", "ali");
        user1.setActiveDeck(deck);
        ArrayList<String> cardsBought2 = new ArrayList<>();
        cardsBought2.add("Suijin");
        user1.setCardsBought(cardsBought2);
        user1.setMoney(200);
        user1.getCardsBought();
        user1.getActiveDeck();
        user1.getMoney();
        user1.isLoggedIn();
        user1.setIsLoggedIn(true);

        User user2 = User.getUserByUsername("123456789");

        User user3 = User.getUserByNickName("123456789");
    }

    @Test
    public void test4 (){
        Field field = new Field();
    }

    @Test
    public void test5 (){
        User user = new User("qwe3", "1234", "qwe3");
        Deck deck = new Deck("MyDeck", "ali");
        user.setActiveDeck(deck);
        Duelist duelist = new Duelist(user);

        duelist.getUser();
        duelist.getName();
    }

    @Test
    public void test6 (){
        Suijin suijin = new Suijin("Suijin", Type.MONSTER, "nothing", 2000, 4, "fire", "monster", "fire", 3000, 2000);

        Deck deck = new Deck("MyDeck1234", "ali");
        deck.mainDeck.add(suijin);
        deck.sideDeck.add(suijin);
        String x = deck.getDeckName();
        String y = deck.getOwnerName();
        Deck deck1 = Deck.getDeckByName("ali1234");
        int z = Deck.getNumberOfCardsInWholeDeck("MyDeck1234", "Suijin");
        int z1 = Deck.getNumberOfCardsInMainDeck("MyDeck1234", "Suijin");
        int z2 = Deck.getNumberOfCardsInSideDeck("MyDeck1234", "Suijin");
        boolean q = Deck.isValid("MyDeck1234");
    }

    @Test
    public void test7 (){
        Suijin suijin = new Suijin("Suijin", Type.MONSTER, "nothing", 2000, 4, "fire", "monster", "fire", 3000, 2000);
        Type type = suijin.getCardsType();
        FaceUp faceUp = suijin.getCardsFace();
        suijin.setCardsFace(FaceUp.ATTACK);
        Location location = suijin.getCardsLocation();
        suijin.setCardsLocation(Location.HAND);
        boolean x = suijin.getSetChanged();
        suijin.setSetChanged(true);
        boolean x2 = suijin.getIsSetThisTurn();
        suijin.setIsSetThisTurn(true);
        String y = suijin.getDescription();
        Card.allCards.put("Suijin", suijin);
        Card card = Card.getCardByName("Suijin21");
        Card card1 = Card.getCardByName("Suijin");
        int p = suijin.getPrice();
        boolean x3 = suijin.getIsAttackedThisTurn();
        suijin.setISAttackedThisTurn(true);
        boolean x4 = suijin.isExpired();
        suijin.setExpired(true);
        suijin.toString();
    }

    @Test
    public void test8 (){
        User user = new User("ggg", "ggg", "ggg");
        ProgramController.setLoggedInUsers(user);
        ProgramController.setLoggedOutUsers();
    }

    @Test
    public void test9 (){
        User user = new User("qqq1", "qqq1", "qqq1");
        ShopMenu shopMenu = ShopMenu.getInstance(user);
        ProgramController.currentMenu = Menu.SHOP_MENU;

        ByteArrayInputStream in2 = new ByteArrayInputStream("xxx\nmenu exit".getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        shopMenu.runShopMenu(user);
    }

    @Test
    public void test10 (){
        try {
            DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User("qqq2", "qqq2", "qqq2");
        ShopMenu shopMenu = ShopMenu.getInstance(user);
        ProgramController.currentMenu = Menu.SHOP_MENU;

        Matcher matcher;
        String command = "shop buy Suijinnn";
        if ((matcher = Regex.getMatcher(command, Regex.shopBuy)).matches()){
            shopMenu.buyCard(matcher);
        }

        user.setMoney(0);
        Matcher matcher1;
        String command1 = "shop buy Suijin";
        if ((matcher1 = Regex.getMatcher(command1, Regex.shopBuy)).matches()){
            shopMenu.buyCard(matcher1);
        }


        user.setMoney(100000);
        Matcher matcher2;
        String command2 = "shop buy Suijin";
        if ((matcher2 = Regex.getMatcher(command2, Regex.shopBuy)).matches()){
            shopMenu.buyCard(matcher2);
        }


        Matcher matcher3;
        String command3 = "increase --money 10000";
        if ((matcher3 = Regex.getMatcher(command3, Regex.increaseMoney)).matches()){
            shopMenu.increaseMoney(matcher3);
        }

        shopMenu.showAllCards();

    }

    @Test
    public void test11 (){
        ScoreBoardMenu scoreBoardMenu = ScoreBoardMenu.getInstance();

        scoreBoardMenu.setUp();
        scoreBoardMenu.addition();
        scoreBoardMenu.tearDown();


        ProgramController.currentMenu = Menu.SCOREBOARD_MENU;

        ByteArrayInputStream in2 = new ByteArrayInputStream("xxx\nmenu exit".getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        scoreBoardMenu.runScoreBoardMenu();
    }

    @Test
    public void test12 (){
        User user = new User("qqq3", "qqq3", "qqq3");
        ProfileMenu profileMenu = ProfileMenu.getInstance(user);

        ByteArrayInputStream in2 = new ByteArrayInputStream("xxx\nmenu exit".getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        profileMenu.runProfileMenu(user);
    }


    @Test
    public void test13 (){
        User user = new User("qqq4", "qqq4", "qqq4");
        ProfileMenu profileMenu = ProfileMenu.getInstance(user);

        profileMenu.changeNickname(Regex.getMatcher("profile change --nicknameee qqq4", Regex.changeNickname));

        profileMenu.changeNickname(Regex.getMatcher("profile change --nickname qqq4", Regex.changeNickname));

        profileMenu.changPass(Regex.getMatcher("profile change --password --current qqq6 --new qqq5", Regex.changePassword));
        profileMenu.changPass(Regex.getMatcher("profile change --password --current qqq4 --new qqq4", Regex.changePassword));
        profileMenu.changPass(Regex.getMatcher("profile change --password --current qqq4 --new qqq5", Regex.changePassword));
        profileMenu.changPass(Regex.getMatcher("profile change --passworddd --current qqq6 --new qqq5", Regex.changePassword));
    }


    @Test
    public void test14 (){
        ByteArrayInputStream in2 = new ByteArrayInputStream("xxx\nuser create --username abcd6 --nickname abcd6 --password abcd6\nmenu Show Current\nmenu enter Duel\nmenu exit\nmenu enter Shop\nmenu exit\nmenu enter Deck\nmenu exit\nmenu enter Profile\nmenu exit\nmenu enter Scoreboard\nmenu exit\nmenu enter Import/Export\nmenu exit\nmenu enterr Shop\nxxx\nuser logout\nmenu exit".getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        String[] args = new String[1];
        args[0] = "ali";
        Main.main(args);
    }


    @Test
    public void test15 (){
        ByteArrayInputStream in2 = new ByteArrayInputStream(("xxx\nuser create --username abcd7 --nickname abcd7 --password abcd7\nmenu exit\nuser create --username abcd7 --nickname abcd8 --password abcd7\nuser create --username abcd8 --nickname abcd7 --password abcd7\nuser createee --username abcd8 --nickname abcd7 --password abcd7\nxxx\n" +
                "user loginn --password abcd7 --username abcd7\nuser login --password abcd10 --username abcd7\nuser login --username abcd7 --password abcd7\nmenu exit\nmenu exit").getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        String[] args = new String[1];
        args[0] = "ali";
        Main.main(args);
    }


//    @Test
//    public void test16 (){
//        try {
//            DataBase.loadCards();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteArrayInputStream in2 = new ByteArrayInputStream(("user login --password abcd7 --username abcd7\nmenu enter Import/Export\nxxx\nexport Suijin\nexport Forest\nexport Time Seal\nmenu exit\nmenu exit\nmenu exit").getBytes());
//        System.setIn(in2);
//        UserInterface.scanner = new Scanner(System.in);
//
//        String[] args = new String[1];
//        args[0] = "ali";
//        Main.main(args);
//    }


    @Test
    public void test17 (){
        try {
            DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream in2 = new ByteArrayInputStream(("user create --username ccc1 --nickname ccc1 --password ccc1\nmenu enter Shop\n" +
                "shop buy Yami\nshop buy Raigeki\nshop buy Suijin\nshop buy Scanner\nmenu exit\nmenu enter Deck\n" +
                "xxx\ncard show Suijin\ncard show Time Seal\ndeck create MyDeck\ndeck create MyDeck10\ndeck create MyDeck\ndeck create MyDeck2\n" +
                "deck delete MyDeck3\ndeck delete MyDeck2\ndeck set-activate MyDeck2\ndeck set-activate MyDeck\n" +
                "deck add-card --card Suijin --deck MyDeck --side\ndeck add-card --card Scanner --deck MyDeck\n" +
                "deck rm-card --card Scanner --deck MyDeck --side\ndeck rm-card --card Suijin --deck MyDeck --side\n" +
                "deck rm-card --card Suijin --deck MyDeck\ndeck rm-card --card Scanner --deck MyDeck\ndeck show --all\n" +
                "deck add-card --card Raigeki --deck MyDeck --side\ndeck add-card --card Suijin --deck MyDeck --side\ndeck show --deck-name MyDeck8 --side\ndeck show --deck-name MyDeck --side\n" +
                "deck add-card --card Yami --deck MyDeck\ndeck add-card --card Scanner --deck MyDeck\ndeck show --deck-name MyDeck8\ndeck show --deck-name MyDeck\n" +
                "deck show --cards\nmenu exit\nmenu exit\nmenu exit").getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        String[] args = new String[1];
        args[0] = "ali";
        Main.main(args);
    }


    @Test
    public void test18 (){
        try {
            DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Initialize.makeAI();
    }


    @Test
    public void test19 (){
        try {
            DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShowCard.Cards("Battle OX");
    }


    @Test
    public void test20 (){
        ByteArrayInputStream in2 = new ByteArrayInputStream(("user create --username aaallliii --nickname aallii --password ali\n" +
                "menu enter Shop\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Mirror Force\n" +
                "shop buy Mirror Force\n" +
                "shop buy Mirror Force\n" +
                "shop buy Trap Hole\n" +
                "shop buy Trap Hole\n" +
                "shop buy Trap Hole\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Negate Attack\n" +
                "shop buy Negate Attack\n" +
                "shop buy Negate Attack\n" +
                "shop buy Solemn Warning\n" +
                "shop buy Solemn Warning\n" +
                "shop buy Solemn Warning\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Time Seal\n" +
                "shop buy Time Seal\n" +
                "shop buy Time Seal\n" +
                "shop buy Mind Crush\n" +
                "shop buy Mind Crush\n" +
                "shop buy Mind Crush\n" +
                "shop buy Battle OX\n" +
                "shop buy Battle OX\n" +
                "shop buy Battle OX\n" +
                "shop buy Axe Raider\n" +
                "shop buy Axe Raider\n" +
                "shop buy Axe Raider\n" +
                "shop buy Horn Imp\n" +
                "shop buy Horn Imp\n" +
                "shop buy Horn Imp\n" +
                "shop buy Baby dragon\n" +
                "shop buy Baby dragon\n" +
                "shop buy Baby dragon\n" +
                "shop buy Battle warrior\n" +
                "shop buy Battle warrior\n" +
                "shop buy Battle warrior\n" +
                "shop buy Crab Turtle\n" +
                "shop buy Crab Turtle\n" +
                "shop buy Crab Turtle\n" +
                "menu exit\n" +
                "menu enter Deck\n" +
                "deck create alireza1\n" +
                "deck add-card --card Battle warrior --deck alireza1\n" +
                "deck add-card --card Battle warrior --deck alireza1\n" +
                "deck add-card --card Battle warrior --deck alireza1\n" +
                "deck add-card --card Axe Raider --deck alireza1\n" +
                "deck add-card --card Axe Raider --deck alireza1\n" +
                "deck add-card --card Axe Raider --deck alireza1\n" +
                "deck add-card --card Baby dragon --deck alireza1\n" +
                "deck add-card --card Baby dragon --deck alireza1\n" +
                "deck add-card --card Baby dragon --deck alireza1\n" +
                "deck add-card --card Battle OX --deck alireza1\n" +
                "deck add-card --card Battle OX --deck alireza1\n" +
                "deck add-card --card Battle OX --deck alireza1\n" +
                "deck add-card --card Call of The Haunted --deck alireza1\n" +
                "deck add-card --card Call of The Haunted --deck alireza1\n" +
                "deck add-card --card Call of The Haunted --deck alireza1\n" +
                "deck add-card --card Horn Imp --deck alireza1\n" +
                "deck add-card --card Horn Imp --deck alireza1\n" +
                "deck add-card --card Horn Imp --deck alireza1\n" +
                "deck add-card --card Magic Cylinder --deck alireza1\n" +
                "deck add-card --card Magic Cylinder --deck alireza1\n" +
                "deck add-card --card Magic Cylinder --deck alireza1\n" +
                "deck add-card --card Mind Crush --deck alireza1\n" +
                "deck add-card --card Mind Crush --deck alireza1\n" +
                "deck add-card --card Mind Crush --deck alireza1\n" +
                "deck add-card --card Mirror Force --deck alireza1\n" +
                "deck add-card --card Mirror Force --deck alireza1\n" +
                "deck add-card --card Mirror Force --deck alireza1\n" +
                "deck add-card --card Negate Attack --deck alireza1\n" +
                "deck add-card --card Negate Attack --deck alireza1\n" +
                "deck add-card --card Negate Attack --deck alireza1\n" +
                "deck add-card --card Solemn Warning --deck alireza1\n" +
                "deck add-card --card Solemn Warning --deck alireza1\n" +
                "deck add-card --card Solemn Warning --deck alireza1\n" +
                "deck add-card --card Time Seal --deck alireza1\n" +
                "deck add-card --card Time Seal --deck alireza1\n" +
                "deck add-card --card Time Seal --deck alireza1\n" +
                "deck add-card --card Torrential Tribute --deck alireza1\n" +
                "deck add-card --card Torrential Tribute --deck alireza1\n" +
                "deck add-card --card Torrential Tribute --deck alireza1\n" +
                "deck add-card --card Trap Hole --deck alireza1\n" +
                "deck add-card --card Trap Hole --deck alireza1\n" +
                "deck add-card --card Trap Hole --deck alireza1\n" +
                "deck set-activate alireza1\n" +
                "menu exit\n" +
                "user logout\n" +
                "user create --username aaallliii1 --nickname aallii1 --password ali1\n" +
                "menu enter Shop\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Magic Cylinder\n" +
                "shop buy Mirror Force\n" +
                "shop buy Mirror Force\n" +
                "shop buy Mirror Force\n" +
                "shop buy Trap Hole\n" +
                "shop buy Trap Hole\n" +
                "shop buy Trap Hole\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Torrential Tribute\n" +
                "shop buy Negate Attack\n" +
                "shop buy Negate Attack\n" +
                "shop buy Negate Attack\n" +
                "shop buy Solemn Warning\n" +
                "shop buy Solemn Warning\n" +
                "shop buy Solemn Warning\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Call of The Haunted\n" +
                "shop buy Time Seal\n" +
                "shop buy Time Seal\n" +
                "shop buy Time Seal\n" +
                "shop buy Mind Crush\n" +
                "shop buy Mind Crush\n" +
                "shop buy Mind Crush\n" +
                "shop buy Battle OX\n" +
                "shop buy Battle OX\n" +
                "shop buy Battle OX\n" +
                "shop buy Axe Raider\n" +
                "shop buy Axe Raider\n" +
                "shop buy Axe Raider\n" +
                "shop buy Horn Imp\n" +
                "shop buy Horn Imp\n" +
                "shop buy Horn Imp\n" +
                "shop buy Baby dragon\n" +
                "shop buy Baby dragon\n" +
                "shop buy Baby dragon\n" +
                "shop buy Battle warrior\n" +
                "shop buy Battle warrior\n" +
                "shop buy Battle warrior\n" +
                "shop buy Crab Turtle\n" +
                "shop buy Crab Turtle\n" +
                "shop buy Crab Turtle\n" +
                "menu exit\n" +
                "menu enter Deck\n" +
                "deck create alireza2\n" +
                "deck add-card --card Battle warrior --deck alireza2\n" +
                "deck add-card --card Battle warrior --deck alireza2\n" +
                "deck add-card --card Battle warrior --deck alireza2\n" +
                "deck add-card --card Axe Raider --deck alireza2\n" +
                "deck add-card --card Axe Raider --deck alireza2\n" +
                "deck add-card --card Axe Raider --deck alireza2\n" +
                "deck add-card --card Baby dragon --deck alireza2\n" +
                "deck add-card --card Baby dragon --deck alireza2\n" +
                "deck add-card --card Baby dragon --deck alireza2\n" +
                "deck add-card --card Battle OX --deck alireza2\n" +
                "deck add-card --card Battle OX --deck alireza2\n" +
                "deck add-card --card Battle OX --deck alireza2\n" +
                "deck add-card --card Call of The Haunted --deck alireza2\n" +
                "deck add-card --card Call of The Haunted --deck alireza2\n" +
                "deck add-card --card Call of The Haunted --deck alireza2\n" +
                "deck add-card --card Horn Imp --deck alireza2\n" +
                "deck add-card --card Horn Imp --deck alireza2\n" +
                "deck add-card --card Horn Imp --deck alireza2\n" +
                "deck add-card --card Magic Cylinder --deck alireza2\n" +
                "deck add-card --card Magic Cylinder --deck alireza2\n" +
                "deck add-card --card Magic Cylinder --deck alireza2\n" +
                "deck add-card --card Mind Crush --deck alireza2\n" +
                "deck add-card --card Mind Crush --deck alireza2\n" +
                "deck add-card --card Mind Crush --deck alireza2\n" +
                "deck add-card --card Mirror Force --deck alireza2\n" +
                "deck add-card --card Mirror Force --deck alireza2\n" +
                "deck add-card --card Mirror Force --deck alireza2\n" +
                "deck add-card --card Negate Attack --deck alireza2\n" +
                "deck add-card --card Negate Attack --deck alireza2\n" +
                "deck add-card --card Negate Attack --deck alireza2\n" +
                "deck add-card --card Solemn Warning --deck alireza2\n" +
                "deck add-card --card Solemn Warning --deck alireza2\n" +
                "deck add-card --card Solemn Warning --deck alireza2\n" +
                "deck add-card --card Time Seal --deck alireza2\n" +
                "deck add-card --card Time Seal --deck alireza2\n" +
                "deck add-card --card Time Seal --deck alireza2\n" +
                "deck add-card --card Torrential Tribute --deck alireza2\n" +
                "deck add-card --card Torrential Tribute --deck alireza2\n" +
                "deck add-card --card Torrential Tribute --deck alireza2\n" +
                "deck add-card --card Trap Hole --deck alireza2\n" +
                "deck add-card --card Trap Hole --deck alireza2\n" +
                "deck add-card --card Trap Hole --deck alireza2\n" +
                "deck set-activate alireza2\n" +
                "menu exit\n" +
                "user logout\n" +
                "user login -u aaallliii -p ali\n" +
                "menu enter Duel\n" +
                "duel --new --second-player aaallliii1 --rounds 1\n" +
                "2\n" +
                "xxx\n" +
                "next phase\nnext phase\n" +
                "select --hand 1\n" +
                "summon\n" +
                "select --hand 2\n" +
                "summon\n" +
                "select --hand 3\n" +
                "summon\n" +
                "next phase\nnext phase\nnext phase\nnext phase\n" +
                "surrender\n" +
                "menu exit\n" +
                "menu exit\n" +
                "menu exit").getBytes());
        System.setIn(in2);
        UserInterface.scanner = new Scanner(System.in);

        String[] args = new String[1];
        args[0] = "ali";
        Main.main(args);
    }
}
