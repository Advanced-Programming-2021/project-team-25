package controllers.menues;

import controllers.Battelfield.Battlefield;
import controllers.Database.DataBase;
import controllers.Menu;
import controllers.Regex;
import models.*;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;

public class DuelMenu {

    private static DuelMenu singleToneClass = null;
    private User currUser;
    public static DuelMenu getInstance (){
        if (singleToneClass == null) singleToneClass = new DuelMenu();
        return singleToneClass;
    }

    public DuelMenu(){
    }


    private void newDuelAi(Matcher matcher){
        String round = matcher.group(1);
        if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
        else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
        else if(!(round.equals("1") || round.equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
        else new Battlefield(new Duelist(currUser),new AI(User.getUserByUsername("admin")));
    }


    public void oneRoundDuel(Battlefield battlefield) {
        int duelist1Wins = 0 , duelist2Wins = 0;
        //round1
        Duelist duelist1 = battlefield.getOpponent();
        Duelist duelist2 = battlefield.getTurn();
        battlefield.start();

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

    public void threeRoundDuel(Battlefield battlefield) {
        int duelist1Wins = 0 , duelist2Wins = 0;
        Duelist turn = battlefield.getTurn();
        //round1
        Duelist duelist1 = battlefield.getOpponent();
        Duelist duelist2 = battlefield.getTurn();
        battlefield.start();

        //round1Finish
        if(battlefield.getWinner().getName().equals(currUser.getUsername())) duelist1Wins++;
        else duelist2Wins++;
        UserInterface.printResponse(battlefield.getWinner().getName() + " won the game and the score is: " + duelist1Wins + " - " +  duelist2Wins);
        int round1Duelist1Lp = duelist1.LP, round1Duelist2Lp = duelist2.LP;

        //add card from side
        transferPermission(duelist1.getName());

        //round2
        duelist1 = battlefield.getOpponent();
        if(turn.equals(duelist1)) {
            battlefield.setTurn(duelist2);
            battlefield.setOpponent(duelist1);
            turn = duelist2;
        }
        else{
            battlefield.setTurn(duelist1);
            battlefield.setOpponent(duelist2);
            turn = duelist1;
        }

        battlefield.start();
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
        transferPermission(duelist2.getName());

        //round3
        if(turn.equals(duelist1)) {
            battlefield.setTurn(duelist2);
            battlefield.setOpponent(duelist1);
        }
        else{
            battlefield.setTurn(duelist1);
            battlefield.setOpponent(duelist2);
        }

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
        String yesOrNo = UserInterface.getUserInput();
        if(yesOrNo.equals("yes")) transferCard(currUser);
        UserInterface.printResponse("Hey " + currUser.getUsername() + "do you want to transfer card?");
        yesOrNo = UserInterface.getUserInput();
        if(yesOrNo.equals("yes")) transferCard(Objects.requireNonNull(User.getUserByUsername(duelistName)));
    }

    private void finish2Round(int duelist1Wins, int duelist2Wins, Duelist duelist1, Duelist duelist2, int round2Duelist2Lp) {
        UserInterface.printResponse(duelist2.getName() + " won the whole match with score: " + duelist1Wins + " - " + duelist2Wins);
        duelist2.getUser().setScore(duelist2.getUser().getScore() + 3000);
        duelist2.getUser().setMoney(duelist2.getUser().getMoney() + 3000 + 3 * round2Duelist2Lp);
        duelist1.getUser().setMoney(duelist1.getUser().getMoney() + 300);
        DataBase.saveTheUserList(User.getUsers());
    }

    private void transferCard(User user){
        UserInterface.printResponse("enter name of the card from side deck : ");
        String sideName = UserInterface.getUserInput();
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

        UserInterface.printResponse("enter name of the card from main deck : ");
        String mainName = UserInterface.getUserInput();
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
