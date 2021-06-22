package controllers.menues;

import controllers.Battelfield.Battlefield;
import controllers.Database.DataBase;
import controllers.Menu;
import controllers.Regex;
import models.*;
import view.Responses;
import view.UserInterface;

import javax.swing.*;
import java.util.Objects;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;

public class DuelMenuController {

    private static DuelMenuController singleToneClass = null;
    private User currUser;
    public static DuelMenuController getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DuelMenuController(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }

    public DuelMenuController(User currUser){
        this.currUser=currUser;
    }

    public void runDuelMenu(){
        while (currentMenu == Menu.DUEL_MENU) {

            String command = UserInterface.getUserInput();
            Matcher matcher;

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if ((matcher = Regex.getMatcher(command, Regex.duelNewAi)).matches()) newDuelAi(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.duelNew)).matches()) newDuel(matcher);
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    private void newDuelAi(Matcher matcher){
        String round = matcher.group(1);
        if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
        else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
        else if(!(round.equals("1") || round.equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
        else new Battlefield(new Duelist(currUser),new AI(User.getUserByUsername("admin")));
    }

    public void newDuel(Matcher matcher){

        String duelistName = matcher.group(1) , round = matcher.group(2);

        if(User.getUserByUsername(duelistName) == null) UserInterface.printResponse("there is no player with this username");
        else if(duelistName.equals(currUser.getUsername())) UserInterface.printResponse("you can't play with yourself");
        else if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
        else if(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck == null) System.out.println(duelistName + " has no active deck");
        else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
        else if(!Deck.isValid(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck.getDeckName())) UserInterface.printResponse(duelistName + "'s deck is not valid");
        else if(!(round.equals("1") || round.equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
        else if(round.equals("1")) oneRoundDuel(duelistName);
        else threeRoundDuel(duelistName);
    }

    public void oneRoundDuel(String duelistName) {
        int duelist1Wins = 0 , duelist2Wins = 0;
        //round1
        Duelist duelist1 = new Duelist(currUser);
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName)));
        Battlefield battlefield = new Battlefield(duelist1, duelist2);

        //finishround1(duelist1Wins, duelist2Wins, duelist1, duelist2, battlefield);
    }

    private void finishround1(int duelist1Wins, int duelist2Wins, Duelist duelist1, Duelist duelist2, Battlefield battlefield) {
        //round1Finish
        if(battlefield.getWinner().getName().equals(currUser.getUsername())) duelist1Wins++;
        else duelist2Wins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + duelist1Wins + " - " + duelist2Wins);

        //matchFinish
        if(duelist1Wins == 1){
            UserInterface.printResponse(duelist1.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
            duelist1.getUser().setScore(duelist1.getUser().getScore() + 1000);
            duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 1000 + duelist1.LP);
            duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 100 );
        }
        else {
            UserInterface.printResponse(duelist2.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
            duelist2.getUser().setScore(duelist2.getUser().getScore() + 1000);
            duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 1000 + duelist2.LP);
            duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 100 );
        }
        DataBase.saveTheUserList(User.getUsers());
    }

    public void threeRoundDuel(String duelistName) {
        int duelist1Wins = 0 , duelist2Wins = 0;

        //round1
        Duelist duelist1 = new Duelist(currUser);
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName)));
        Battlefield battlefield = new Battlefield(duelist1, duelist2);

        //round1Finish
        if(battlefield.getWinner().getName().equals(currUser.getUsername())) duelist1Wins++;
        else duelist2Wins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + duelist1Wins + " - " +  duelist2Wins);
        int round1Duelist1Lp = duelist1.LP, round1Duelist2Lp = duelist2.LP;

        //add card from side
        transferPermission(duelistName);

        //round2
        duelist1 = new Duelist(currUser);
        duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName)));
        battlefield = new Battlefield(duelist1, duelist2);

        //round2Finish
        if(battlefield.getWinner().getName().equals(currUser.getUsername())) duelist1Wins++;
        else duelist2Wins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + duelist1Wins + " - " +  duelist2Wins);
        int round2Duelist1Lp = duelist1.LP, round2Duelist2Lp = duelist2.LP;
        if(round2Duelist1Lp < round1Duelist1Lp ) round2Duelist1Lp = round1Duelist1Lp;
        if(round2Duelist2Lp < round1Duelist2Lp ) round2Duelist2Lp = round1Duelist2Lp;

        //checkMatchIsFinished
        if(duelist1Wins == 2) {
            finish2Round(duelist1Wins, duelist2Wins, duelist2, duelist1, round2Duelist1Lp);
            return;
        }
        else if(duelist2Wins == 2){
            finish2Round(duelist1Wins, duelist2Wins, duelist1, duelist2, round2Duelist2Lp);
            return;
        }

        //add card from side
        transferPermission(duelistName);

        //round3
        duelist1 = new Duelist(currUser);
        duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName)));
        battlefield = new Battlefield(duelist1, duelist2);

        //round3Finish
        if(battlefield.getWinner().getName().equals(currUser.getUsername())) duelist1Wins++;
        else duelist2Wins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + duelist1Wins + " - " +  duelist2Wins);
        int round3Duelist1Lp = duelist1.LP, round3Duelist2Lp = duelist2.LP;
        if(round3Duelist1Lp < round2Duelist1Lp ) round3Duelist1Lp = round2Duelist1Lp;
        if(round3Duelist2Lp < round2Duelist2Lp ) round3Duelist2Lp = round2Duelist2Lp;

        //matchFinish
        if(duelist1Wins == 2) {
            UserInterface.printResponse(duelist1.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
            duelist1.getUser().setScore(duelist1.getUser().getScore() + 3000);
            duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 3000 + 3 * round3Duelist1Lp);
            duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 300 );
        }
        else {
            UserInterface.printResponse(duelist2.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
            duelist2.getUser().setScore(duelist2.getUser().getScore() + 3000);
            duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 3000 + 3 * round3Duelist2Lp);
            duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 300 );
        }
        DataBase.saveTheUserList(User.getUsers());
    }

    private void transferPermission(String duelistName) {
        UserInterface.printResponse("Hey " + currUser.getUsername() + "do you want to transfer card?");
        Object[] options = {"Yes",
                "No"};
        int n1 = JOptionPane.showOptionDialog(null,
                "Are you sure to Delete account?",
                "delete account question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]);
        if(n1 == 0) transferCard(currUser);
        UserInterface.printResponse("Hey " + duelistName + "do you want to transfer card?");
        int n2 = JOptionPane.showOptionDialog(null,
                "Are you sure to Delete account?",
                "delete account question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]);
        if(n2 == 0) transferCard(Objects.requireNonNull(User.getUserByUsername(duelistName)));
    }

    private void finish2Round(int duelist1Wins, int duelist2Wins, Duelist duelist1, Duelist duelist2, int round2Duelist2Lp) {
        UserInterface.printResponse(duelist2.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
        duelist2.getUser().setScore(duelist2.getUser().getScore() + 3000);
        duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 3000 + 3 * round2Duelist2Lp);
        duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 300);
        DataBase.saveTheUserList(User.getUsers());
    }

    private void transferCard(User user){
        String sideName = JOptionPane.showInputDialog("enter name of the card from side deck : ");
        boolean sideExist = false;
        Card tempSide = null;
        for (Card card: user.activeDeck.sideDeck) {
            if(card.getName().equals(sideName)){
                tempSide = card;
                user.activeDeck.sideDeck.remove(card);
                sideExist = true;
            }
        }
        if(!sideExist){
            UserInterface.printResponse("you don't have this card in your side deck");
            return;
        }

        String mainName = JOptionPane.showInputDialog("enter name of the card from main deck : ");
        boolean mainExist = false;
        Card tempMain = null;
        for (Card card: user.activeDeck.mainDeck) {
            if(card.getName().equals(mainName)){
                tempMain = card;
                user.activeDeck.mainDeck.remove(card);
                mainExist = true;
            }
        }
        if(!mainExist){
            UserInterface.printResponse("you don't have this card in your main deck");
            return;
        }
        user.activeDeck.mainDeck.add(tempSide);
        user.activeDeck.sideDeck.add(tempMain);
    }
}
