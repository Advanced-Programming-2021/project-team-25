package controllers.menues;

import controllers.Battelfield.Battlefield;
import controllers.Database.DataBase;
import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.*;
import view.Main;
import view.Responses;
import view.UserInterface;
import view.menus.ChangeMainAndSideCards;
import view.menus.DuelMenu;

import javax.swing.*;
import java.util.Objects;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;

public class DuelMenuController {

    private static DuelMenuController singleToneClass = null;
    public static Duelist duelistRival;
    private User currUser;
    public String duelistName;
    public int currWins = 0 , notCurrWins = 0;
    int round1currLp , round1notCurrLp;
    int round2currLp , round2notCurrLp;
    int round3currLp , round3notCurrLp;

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
        else new Battlefield(new Duelist(currUser),new AI(User.getUserByUsername("admin")), 1);
    }

    public void newDuel(Matcher matcher){

        duelistName = matcher.group(1);
        String round = matcher.group(2);

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
        //round1
        Duelist duelist1 = new Duelist(currUser);
        if(duelistRival != null){
            Battlefield battlefield = new Battlefield(duelist1, duelistRival, 1);
            battlefield.isOneRound = true;
        }
    }

    public void finishround1( Duelist duelist1, Duelist duelist2, Battlefield battlefield) {
        int duelist1Wins = 0, duelist2Wins = 0;
        //round1Finish
        if(battlefield.getWinner().getName().equals(duelist1.getName())) duelist1Wins++;
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
        this.duelistName = duelistName;
        //round1
        Duelist duelist1 = new Duelist(currUser);
        if(duelistRival != null){
            Battlefield battlefield = new Battlefield(duelist1, duelistRival, 1);
            battlefield.isOneRound = false;
        }

    }

    public void finishRound1(Duelist duelist1, Duelist duelist2, Battlefield battlefield) {
        //round1Finish
        if (battlefield.getWinner().getName().equals(currUser.getNickName())) currWins++;
        else notCurrWins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + currWins + " - " + notCurrWins);
        if (duelist1.getName().equals(currUser.getNickName())) {
            round1currLp = duelist1.LP;
            round1notCurrLp = duelist2.LP;
        }
        else{
            round1currLp = duelist2.LP;
            round1notCurrLp = duelist1.LP;
        }

        //add card from side
        transferPermission(currUser, "oneToTwo");
    }

    public void finishRound2(Duelist duelist1, Duelist duelist2, Battlefield battlefield) {
        //round2Finish
        if (battlefield.getWinner().getName().equals(currUser.getNickName())) currWins++;
        else notCurrWins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + currWins + " - " + notCurrWins);
        if (duelist1.getName().equals(currUser.getNickName())) {
            round2currLp = duelist1.LP;
            round2notCurrLp = duelist2.LP;
        }
        else{
            round2currLp = duelist2.LP;
            round2notCurrLp = duelist1.LP;
        }
        if (round2currLp < round1currLp) round2currLp = round1currLp;
        if (round2notCurrLp < round1notCurrLp) round2notCurrLp = round1notCurrLp;

        //checkMatchIsFinished
        if (currWins == 2) {
            if (duelist1.getName().equals(currUser.getNickName())){
                finish2Round(duelist1, duelist2);
            }
            else{
                finish2Round(duelist2, duelist1);
            }
            DataBase.saveTheUserList(User.getUsers());
            DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
            return;
        } else if (notCurrWins == 2) {
            if (duelist1.getName().equals(currUser.getNickName())){
                finish2Round(duelist2, duelist1);
            }
            else{
                finish2Round(duelist1, duelist2);
            }
            DataBase.saveTheUserList(User.getUsers());
            DuelMenu.getInstance(ProgramController.currUser).run(Main.stage);
            return;
        }

        //add card from side
        transferPermission(currUser, "twoToThree");
    }

    private void finish2Round(Duelist duelist1, Duelist duelist2) {
        if (duelist1.getName().equals(currUser.getNickName())) {
            UserInterface.printResponse(duelist1.getName() + " won the whole match with score: " + currWins + " - " + notCurrWins);
            duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 3000 + 3 * round2currLp);
        }
        else{
            UserInterface.printResponse(duelist1.getName() + " won the whole match with score: " + notCurrWins + " - " + currWins);
            duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 3000 + 3 * round2notCurrLp);
        }
        duelist1.getUser().setScore(duelist1.getUser().getScore() + 3000);
        duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 300);
        DataBase.saveTheUserList(User.getUsers());
    }

    public void finishRound3(Duelist duelist1, Duelist duelist2, Battlefield battlefield) {
        //round3Finish
        if (battlefield.getWinner().getName().equals(currUser.getNickName())) currWins++;
        else notCurrWins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + currWins + " - " + notCurrWins);
        if (duelist1.getName().equals(currUser.getNickName())){
            round3currLp = duelist1.LP;
            round3notCurrLp = duelist2.LP;
        }
        else{
            round3currLp = duelist2.LP;
            round3notCurrLp = duelist1.LP;
        }
        if (round3currLp < round2currLp) round3currLp = round2currLp;
        if (round3notCurrLp < round2notCurrLp) round3notCurrLp = round2notCurrLp;
    }

    public void matchFinish(Duelist duelist1, Duelist duelist2){
        //matchFinish
        if(currWins == 2) {
            UserInterface.printResponse(currUser.getNickName() + " won the whole match with score: " + currWins + " - " + notCurrWins);
            currUser.setScore(currUser.getScore() + 3000);
            currUser.setMoney(currUser.getMoney() + 3000 + 3 * round3currLp);
            if (duelist1.getName().equals(currUser.getNickName()))
                duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 300);
            else
                duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 300);
        }
        else {
            if (duelist1.getName().equals(currUser.getNickName())) {
                UserInterface.printResponse(duelist2.getName() + " won the whole match with score: " + notCurrWins + " - " + currWins);
                duelist2.getUser().setScore(duelist2.getUser().getScore() + 3000);
                duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 3000 + 3 * round3notCurrLp);
                duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 300);
            }
        }
        DataBase.saveTheUserList(User.getUsers());
    }

    public void transferPermission(User user, String how) {
        Object[] options = {"Yes", "No"};
        int n1 = JOptionPane.showOptionDialog(null,
                "Hey " + user.getUsername() + "do you want to transfer card?",
                "Transfer Card Question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]);
        if(n1 == 0) transferCard(user, how);
        else{
            if (user == ProgramController.currUser){
                transferPermission(Objects.requireNonNull(User.getUserByUsername(duelistName)), how);
            }
            else{
                Duelist duelist1 = new Duelist(ProgramController.currUser);
                Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName)));
                Battlefield battlefield;
                if (how.equals("oneToTwo")) {
                    battlefield = new Battlefield(duelist1, duelist2, 2);
                    battlefield.isOneRound = false;
                }
                else{
                    battlefield = new Battlefield(duelist1, duelist2, 3);
                    battlefield.isOneRound = false;
                }
            }
        }
    }

    public void transferCard(User user, String how){
        new ChangeMainAndSideCards(user.activeDeck, user, this, how);
    }
}
