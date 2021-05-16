package controllers.menues;

import controllers.Battelfield.Battlefield;
import controllers.Database.DataBase;
import controllers.Menu;
import controllers.Regex;
import models.AI;
import models.Deck;
import models.Duelist;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;

public class DuelMenu {

    private static DuelMenu singleToneClass = null;
    private User currUser;
    private User AiUser = User.getUserByUsername("admin");
    public static DuelMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DuelMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }

    public DuelMenu(User currUser){
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
        else new Battlefield(new Duelist(currUser),new AI(AiUser));
    }

    public void newDuel(Matcher matcher){

        String duelistName = matcher.group(1) , round = matcher.group(2);

        if(User.getUserByUsername(duelistName) == null) UserInterface.printResponse("there is no player with this username");
        else if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
        else if(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck == null) System.out.println(duelistName + " has no active deck");
        else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
        else if(!Deck.isValid(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck.getDeckName())) UserInterface.printResponse(duelistName + "'s deck is not valid");
        else if(!(round.equals("1") || round.equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
        else if(round.equals("1")) oneRoundDuel(duelistName);
        else threeRoundDuel(duelistName);

    }

    private void oneRoundDuel(String duelistName) {
        int duelist1Wins = 0 , duelist2Wins = 0;
        //round1
        Duelist duelist1 = new Duelist(currUser);
        Duelist duelist2 = new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName)));
        Battlefield battlefield = new Battlefield(duelist1, duelist2);

        //round1Finish
        if(battlefield.getWinner().getName().equals(currUser.getUsername())) duelist1Wins++;
        else duelist2Wins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + duelist1Wins + " - " +  duelist2Wins);

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

    private void threeRoundDuel(String duelistName) {
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

    private void finish2Round(int duelist1Wins, int duelist2Wins, Duelist duelist1, Duelist duelist2, int round2Duelist2Lp) {
        UserInterface.printResponse(duelist2.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
        duelist2.getUser().setScore(duelist2.getUser().getScore() + 3000);
        duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 3000 + 3 * round2Duelist2Lp);
        duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 300);
        DataBase.saveTheUserList(User.getUsers());
    }
}
