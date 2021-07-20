package view;

import com.google.gson.Gson;
import com.sun.scenario.effect.impl.prism.PrImage;
import controllers.Battelfield.Battlefield;
import controllers.Battelfield.BattlefieldController;
import controllers.Constants.Initialize;
import controllers.Database.DataBase;
import controllers.Regex;
import controllers.menues.DeckMenu;
import controllers.menues.DuelMenu;
import javafx.scene.image.Image;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Location;
import models.CardStufs.Type;
import models.Deck;
import models.Duelist;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import models.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static HashMap<String, User> loggedInUsers = new HashMap<>();
    public static HashMap<User, BufferedImage> userImages = new HashMap<>();
    private static ArrayList<Duelist> availableUsers = new ArrayList<>();
    private static HashMap<Duelist, Duelist> playingUsers = new HashMap<>();
    public static HashMap<User, Socket> userConnected = new HashMap<>();
    private static Controller singleToneClass = null;

    public static Controller getInstance() {
        if (singleToneClass == null) singleToneClass = new Controller();
        return singleToneClass;

    }

    public User getUSerByToken(String token) {
        for (Map.Entry<String, User> set : loggedInUsers.entrySet()) {
            if (set.getKey().equals(token))
                return set.getValue();
        }
        return null;
    }

    public String changeNickname(String command) {
        Matcher matcher = Regex.getMatcher(command, Regex.changeNickname);
        if (matcher.find()) {
            String newNickname = matcher.group("nickname");
            Matcher matcherToken = Regex.getMatcher(command, "--token (.+)");
            if (matcherToken.find()) {
                User currUser = getUSerByToken(matcherToken.group(1));
                currUser.setNickName(newNickname);
                DataBase.saveTheUserList(User.getUsers());
                Initialize.initUserList();
                return UserInterface.printResponse("success", "User nickname changed successfully");
            } else
                return UserInterface.printResponse("error", "token not valid");

        } else return UserInterface.printResponse("error", Responses.INVALID_COMMAND.getMessage());
    }

    public String changPass(String command) {
        Matcher matcher = Regex.getMatcher(command, Regex.changePassword);
        if (matcher.find()) {
            String currentPass = matcher.group("curr");
            String newPass = matcher.group("new");
            Matcher matcherToken = Regex.getMatcher(command, "--token (.+)");
            if (matcherToken.find()) {
                User currUser = getUSerByToken(matcherToken.group(1));
                if (!currUser.getPassword().equals(currentPass))
                    return UserInterface.printResponse("error", "current password is invalid");
                else if (currUser.getPassword().equals(newPass))
                    return UserInterface.printResponse("error", "please enter a new password");
                else {
                    currUser.setPassword(newPass);
                    DataBase.saveTheUserList(User.getUsers());
                    Initialize.initUserList();
                    return UserInterface.printResponse("success", "password changed successfully!");
                }
            } else
                return UserInterface.printResponse("error", "token not valid");
        } else return UserInterface.printResponse("error", Responses.INVALID_COMMAND.getMessage());
    }

    public String createNewUser(String command) {
        Matcher matcher = Regex.getMatcher(command, Regex.userCreate);
        if (matcher.find()) {
            String username = matcher.group("username");
            String password = matcher.group("password");
            String nickname = matcher.group("nickname");

            if (isExistUsername(username))
                return UserInterface.printResponse("error", "user with username " + username + " already exist!");
            else if (isExistNickname(nickname))
                return UserInterface.printResponse("error", "user with nickname " + nickname + " already exist!");
            else {
                //creating new user
                User user = new User(username, password, nickname);
                getImageRandom(user);
                DataBase.saveTheUserList(User.getUsers());
                // by default user be logged in
                return "success description=\"" + Responses.USER_CREATE_SUCCESS.getMessage() + "\"";
            }
        } else
            return "error description=\"" + Responses.INVALID_COMMAND.getMessage() + "\"";
    }

    private void getImageRandom(User user) {
        Random random = new Random();
        int number = random.nextInt(29);
        number += 1;
        String from = Objects.requireNonNull(this.getClass().getResource("/models/UserImages/"+number + ".png")).getPath();
        try{
            BufferedImage image = ImageIO.read(new File(from));
            ImageIO.write(image, "png", new File(user.getUsername()+".png"));
            userImages.put(user,image);
            System.out.println("read Image!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized String loginUser(String command) {
        String username;
        String password;
        Matcher matcher = Pattern.compile(Regex.userLogin).matcher(command);

        if (matcher.find()) {
            //checking which group is been captured
            if (!Objects.isNull(matcher.group("group1"))) {
                password = matcher.group("password");
                username = matcher.group("username");
            } else {
                password = matcher.group("password2");
                username = matcher.group("username2");
            }
            //checking user exist
            User user = User.getUserByUsername(username);
            //checking correct password
            if (Objects.isNull(user) || !user.getPassword().equals(password))
                return "error description=\"" + Responses.USER_PASS_NOT_MATCHED_ERROR.getMessage() + "\"";
            else {
                //set the user logged in
                user.setIsLoggedIn(true);
                String token = UUID.randomUUID().toString();
                loggedInUsers.put(token, user);
                //update curr user
                return "success description=\"" + token + "\"";
            }
        } else
            return "error description=\"" + Responses.INVALID_COMMAND.getMessage() + "\"";
    }


    public String initUserList() {
        Initialize.initUserList();
        return "success description=\"successfully initiated users\"";
    }


    public String initDeckList() {
        Initialize.initDeckList();
        return "success description=\"successfully initiated decks\"";
    }

    public boolean checkToken(String command) {
        Matcher mather = Regex.getMatcher(command, "--token (.+)");
        if (mather.find()) {
            String token = mather.group(1);
            return loggedInUsers.containsKey(token);
        } else return false;
    }


    private static boolean isExistUsername(String username) {
        return !Objects.isNull(User.getUserByUsername(username));
    }

    private static boolean isExistNickname(String nickname) {
        return !Objects.isNull(User.getUserByNickName(nickname));
    }

    public Object getUserToJson(String command) {
        Matcher mather = Regex.getMatcher(command, "--token (.+)");
        if (mather.find()) {
            String token = mather.group(1);
            return getUSerByToken(token);
        } else return UserInterface.printResponse("error", "token not valid");
    }


    public Object getDecksOfUser(String command) {
        ArrayList<Deck> decks = new ArrayList<>();
        Matcher matcher = Regex.getMatcher(command, "--token (.+)");
        if (matcher.find()) {
            String token = matcher.group(1);
            User user = getUSerByToken(token);
            for (int i = 0; i < Deck.allDecks.size(); ++i) {
                if (Deck.allDecks.get(i).getOwnerName().equals(user.getUsername())) {
                    decks.add(Deck.allDecks.get(i));
                }
            }
            return decks;
        } else return "error description=\"token not valid\"";
    }

    public Object logoutUSer(String command) {
        Matcher matcher = Regex.getMatcher(command, "--token (.+)");
        if (matcher.find()) {
            String token = matcher.group(1);
            loggedInUsers.remove(token);
            return UserInterface.printResponse("success", "logout successfully");
        } else return "error description=\"token not valid\"";
    }

    public Object addCardToSideDeck(String command) {
        Matcher matcher = Regex.getMatcher(command, "addCardToSide --cardName (.+) --deckName (.+) --token (.+)");
        if (matcher.find()) {
            String token = matcher.group(3);
            String deckName = matcher.group(2);
            String cardName = matcher.group(1);
            if (loggedInUsers.containsKey(token)) {
                User user = getUSerByToken(token);
                Deck deck = Deck.getDeckByName(deckName);
                if (Objects.isNull(deck) || !deck.getOwnerName().equals(user.getUsername()))
                    return UserInterface.printResponse("error", "deck not found or Deck is not yours!");
                else {
                    String result = DeckMenu.getInstance(user).addCardToSide(cardName, deck.getDeckName());
                    if (result.contains("successfully")) {
                        DataBase.storeDecks(Deck.allDecks);
                        return UserInterface.printResponse("success", result);
                    } else
                        return UserInterface.printResponse("error", result);
                }
            } else
                return "error description=\"token not valid\"";
        } else
            return "error description=\"command not found\"";
    }

    public Object addCardToMainDeck(String command) {
        Matcher matcher = Regex.getMatcher(command, "addCardToMain --cardName (.+) --deckName (.+) --token (.+)");
        if (matcher.find()) {
            String token = matcher.group(3);
            String deckName = matcher.group(2);
            String cardName = matcher.group(1);
            if (loggedInUsers.containsKey(token)) {
                User user = getUSerByToken(token);
                Deck deck = Deck.getDeckByName(deckName);
                if (Objects.isNull(deck) || !deck.getOwnerName().equals(user.getUsername()))
                    return UserInterface.printResponse("error", "deck not found or Deck is not yours!");
                else {
                    String result = DeckMenu.getInstance(user).addCard(cardName, deck.getDeckName());
                    if (result.contains("successfully")) {
                        DataBase.storeDecks(Deck.allDecks);
                        return UserInterface.printResponse("success", result);
                    } else
                        return UserInterface.printResponse("error", result);
                }
            } else
                return "error description=\"token not valid\"";
        } else
            return "error description=\"command not found\"";
    }

    public Object addNewDeck(String command) {
        Matcher matcher = Regex.getMatcher(command, "addNewDeck --deckName (.+) --token (.+)");
        if (matcher.find()) {
            String token = matcher.group(2);
            String deckName = matcher.group(1);
            if (loggedInUsers.containsKey(token)) {
                User user = getUSerByToken(token);
                String result = DeckMenu.getInstance(user).createDeck(deckName);
                if (result.contains("successfully")) {
                    DataBase.storeDecks(Deck.allDecks);
                    return UserInterface.printResponse("success", result);
                } else
                    return UserInterface.printResponse("error", result);

            } else
                return "error description=\"token not valid\"";
        } else
            return "error description=\"command not found\"";
    }

    public Object deleteDeck(String command) {
        Matcher matcher = Regex.getMatcher(command, "deleteDeck --deckName (.+) --token (.+)");
        if (matcher.find()) {
            String token = matcher.group(2);
            String deckName = matcher.group(1);
            User user = getUSerByToken(token);
            Deck deck = Deck.getDeckByName(deckName);
            if (!Objects.isNull(user)) {
                if (Objects.isNull(deck) || !deck.getOwnerName().equals(user.getUsername()))
                    return UserInterface.printResponse("error", "deck not found or Deck is not yours!");
                else {
                    String result = DeckMenu.getInstance(user).deleteDeck(deckName);
                    if (result.contains("successfully")) {
                        DataBase.storeDecks(Deck.allDecks);
                        return UserInterface.printResponse("success", result);
                    } else
                        return UserInterface.printResponse("error", result);
                }
            } else
                return "error description=\"token not valid\"";
        } else
            return "error description=\"command not found\"";
    }

    public Object setActiveDeck(String command) {
        Matcher matcher = Regex.getMatcher(command, "setActive --deckName (.+) --token (.+)");
        if (matcher.find()) {
            String token = matcher.group(2);
            String deckName = matcher.group(1);
            User user = getUSerByToken(token);
            Deck deck = Deck.getDeckByName(deckName);
            if (!Objects.isNull(user)) {
                if (Objects.isNull(deck) || !deck.getOwnerName().equals(user.getUsername()))
                    return UserInterface.printResponse("error", "deck not found or Deck is not yours!");
                else {
                    String result = DeckMenu.getInstance(user).setActive(deckName);
                    if (result.contains("successfully")) {
                        DataBase.storeDecks(Deck.allDecks);
                        return UserInterface.printResponse("success", result);
                    } else
                        return UserInterface.printResponse("error", result);
                }
            } else
                return "error description=\"token not valid\"";
        } else
            return "error description=\"command not found\"";
    }

    public Object removeCardDeck(String command) {
        Matcher matcher = Regex.getMatcher(command, "(removeCardFromSide|removeCardFromMain) --cardName (.+) --deckName (.+) --token (.+)");
        if (matcher.find()) {
            String token = matcher.group(4);
            String deckName = matcher.group(3);
            String cardName = matcher.group(2);
            String type = matcher.group(1);
            User user = getUSerByToken(token);
            Deck deck = Deck.getDeckByName(deckName);
            if (!Objects.isNull(user)) {
                if (Objects.isNull(deck) || !deck.getOwnerName().equals(user.getUsername()))
                    return UserInterface.printResponse("error", "deck not found or Deck is not yours!");
                else if (Objects.isNull(Card.allCards.get(cardName)))
                    return UserInterface.printResponse("error", "card not found");
                else {
                    String result;
                    if (type.equals("removeCardFromSide"))
                        result = DeckMenu.getInstance(user).removeCardFromSide(cardName, deck.getDeckName());
                    else
                        result = DeckMenu.getInstance(user).removeCard(cardName, deck.getDeckName());

                    if (result.contains("successfully")) {
                        DataBase.storeDecks(Deck.allDecks);
                        return UserInterface.printResponse("success", result);
                    } else
                        return UserInterface.printResponse("error", result);
                }
            } else
                return "error description=\"token not valid\"";
        } else
            return "error description=\"command not found\"";
    }

    public String buyCard(String command) {
        Pattern pattern = Pattern.compile("card buy (.+)? --token (.+)?");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            String cardsName = matcher.group(1);
            String token = matcher.group(2);
            User user = getUSerByToken(token);
            user.cardsBought.add(cardsName);
            user.money -= Card.allCards.get(cardsName).getPrice();
            DataBase.saveTheUserList(User.getUsers());
            return "success description=\"card was bought successfully\"";
        }
        return "error description\"mission failed\"";
    }

    public Object getUserImage(String command) {
        Matcher matcher = Regex.getMatcher(command,"getUserImage (.+)");
        if(matcher.find()){
            String username = matcher.group(1);
            for(Map.Entry<User, BufferedImage> set : userImages.entrySet()){
                if(set.getKey().getUsername().equals(username)){
                    return set.getValue();
                }
            }
            return "error description=\"image not found!\"";
        }
        else
            return "error description=\"user not found!\"";
    }

    public Object getDuelistForStartingGame(String command){
        //availableUsers.add(new Duelist(User.getUserByUsername("admin")));
        Matcher mather = Regex.getMatcher(command, "--token (.+)");
        if (mather.find()) {
            String token = mather.group(1);
            User user =  getUSerByToken(token);
            Duelist duelistUser = new Duelist(user);
            if(availableUsers.size()>0 && checkIfUserFound(user)==null){
                playingUsers.put(duelistUser,availableUsers.get(0));
                availableUsers.remove(0);
                availableUsers.remove(duelistUser);
            }
            else{
                availableUsers.add(duelistUser);
                if(isUserFoundInPlayingUsers(user)!= null)
                    return isUserFoundInPlayingUsers(user);
                if (availableUsers.size()<2){
                    return "noUserFound";
                }
                else if(availableUsers.size()==2 && checkIfUserFound(user)!=null){
                    Duelist duelist = checkIfUserFound(user);
                    availableUsers.remove(duelist);
                    return "noUserFound";
                }
                else{
                    playingUsers.put(duelistUser,availableUsers.get(1));
                    availableUsers.remove(1);
                    availableUsers.remove(duelistUser);
                }
            }
            return playingUsers.get(duelistUser);
        } else
            return null;
    }

    private Duelist isUserFoundInPlayingUsers(User user) {
        for(Map.Entry<Duelist,Duelist> entry : playingUsers.entrySet()){
            if(entry.getKey().getUser().getUsername().equals(user.getUsername()))
                return entry.getValue();// always return rival!
            else if(entry.getValue().getUser().getUsername().equals(user.getUsername()))
                return entry.getKey();
        }
        return null;
    }

    private Duelist checkIfUserFound(User user) {
        for (Duelist availableUser : availableUsers) {
            if(availableUser.getUser().getUsername().equals(user.getUsername()))
                return availableUser;
        }
        return null;
    }

    public Object startNeGame(String command) {
        Matcher mather = Regex.getMatcher(command, "--token (.+)");
        if (mather.find()) {
            String token = mather.group(1);
            User user =  getUSerByToken(token);
            for (Map.Entry<Duelist, Duelist> entry : playingUsers.entrySet()) {
                if (entry.getKey().getUser().getUsername().equals(user.getUsername()) ||
                    entry.getValue().getUser().getUsername().equals(user.getUsername())){
                    if (user.activeDeck == null)
                        return "error description=\"you dont have active deck!\"";
                    else if ((entry.getValue().getUser()).activeDeck == null)
                        return "error description=\"your rival has no active deck\"";
                    else if (!Deck.isValid(user.activeDeck.getDeckName()))
                        return "error description=\"your deck is not valid\"";
                    else if (!Deck.isValid(entry.getValue().getUser().activeDeck.getDeckName()))
                        return "error description=\"your rival's deck is not valid\"";
                    else {
                        Battlefield currBattlefield = getBattlefield(entry.getKey(), entry.getValue());

                        if (currBattlefield == null) {
                            Matcher matherRound = Regex.getMatcher(command, "--rounds (\\d+)");
                            if (matherRound.find()) {
                                // checking use in key or in value
                                if(entry.getKey().getUser().getUsername().equals(user.getUsername()))
                                    currBattlefield = new Battlefield(entry.getKey(), null,userConnected.get(user),null);
                                else
                                    currBattlefield = new Battlefield(entry.getValue(), null,userConnected.get(user),null);
                                BattlefieldController.battlefields.put(user, currBattlefield);
                                currBattlefield.roundToPlay = Integer.parseInt(matherRound.group(1));
                            }
                        } else {
                            if((currBattlefield.getOpponent() == null || !currBattlefield.getOpponent().getUser().getUsername().equals(user.getUsername())) &&
                                    (currBattlefield.getTurn() == null || !currBattlefield.getTurn().getUser().getUsername().equals(user.getUsername()))){
                                Matcher matherRound = Regex.getMatcher(command, "--rounds (\\d+)");
                                if (matherRound.find()) {
                                    int newRounds = Integer.parseInt(matherRound.group(1));
                                    if (currBattlefield.roundToPlay != newRounds)
                                        return "error description=\"rounds not same\"";
                                    else {
                                        if(currBattlefield.getOpponent() == null)
                                            currBattlefield.setOpponent(new Duelist(user));
                                        else
                                            currBattlefield.setTurn(new Duelist(user));
                                        if(currBattlefield.duelist1 == null){
                                            currBattlefield.duelist1 = new Duelist(user);
                                            currBattlefield.connectedDuelists.put(currBattlefield.duelist1,userConnected.get(user));
                                        }else{
                                            currBattlefield.duelist2 = new Duelist(user);
                                            currBattlefield.connectedDuelists.put(currBattlefield.duelist2, userConnected.get(user));
                                        }
                                    }
                                }
                            }
                        }

                        assert currBattlefield != null;
                        if (currBattlefield.roundToPlay == 1) {
                            //need to play one round!
                            if(currBattlefield.getTurn()!=null && currBattlefield.getOpponent()!=null) {
                                DuelMenu.getInstance().oneRoundDuel(currBattlefield);
                                Thread thread = BattlefieldController.threads.get(currBattlefield);
                                if(!thread.isAlive()) thread.start();
                                if (currBattlefield.getTurn().getUser().getUsername().equals(user.getUsername())) {
                                    System.out.println(user.getUsername()+" -> turn");
                                    return "success description=\"turn\"";
                                }
                                else {
                                    System.out.println(user.getUsername()+" -> opponent");
                                    return "success description=\"opponent\"";
                                }
                            }
                        } else {
                            //3 round play
                            if(currBattlefield.getTurn()!=null && currBattlefield.getOpponent()!=null) {
                                DuelMenu.getInstance().threeRoundDuel(currBattlefield);
                                if (currBattlefield.getTurn().getUser().getUsername().equals(user.getUsername())) {
                                    System.out.println(user.getUsername()+" -> turn");
                                    return "success description=\"turn\"";
                                }
                                else {
                                    System.out.println(user.getUsername()+" -> opponent");
                                    return "success description=\"opponent\"";
                                }
                            }
                        }
                        return "wait description=\"wait for other user to log in\"";

                    }
                }
            }

            return "error description=\"user not found!\"";
        } else
            return "error description=\"token not valid\"";
    }
    public static Battlefield getBattlefield(Duelist duelist1, Duelist duelist2) {
        for(Map.Entry<User,Battlefield> entry : BattlefieldController.battlefields.entrySet()){
            if(entry.getKey().getUsername().equals(duelist1.getUser().getUsername()) ||
                    entry.getKey().getUsername().equals(duelist2.getUser().getUsername()) ||
                    (entry.getValue().getTurn() != null && entry.getValue().getOpponent() != null) &&
                            (entry.getValue().getOpponent().getUser().getUsername().equals(duelist2.getUser().getUsername()) ||
                    entry.getValue().getTurn().getUser().getUsername().equals(duelist2.getUser().getUsername()))){
                return entry.getValue();
            }
        }
        return null;
    }

    public Object battlefieldCommands(String command) {
        Matcher mather = Regex.getMatcher(command, "--token (.+)");
        if (mather.find()) {
            String token = mather.group(1);
            User user =  getUSerByToken(token);
            if(user == null)
                return "error description=\"user not found!\"";
            Battlefield battlefield = getBattlefieldFromUser(user);
            if(battlefield == null)
                return "error description=\"user not playing game yet!\"";
            if(command.contains("summon")){
                String result = summonMonster(command, user, battlefield);
                if (result != null) return result;
            }
            else if(command.contains("set")){
                String result = setCard(command, user, battlefield);
                if (result != null) return result;
            }else if(command.contains("removeCard")){
                removeCard(command, battlefield);
            }else if(command.contains("winner")){
                winner(command, battlefield);
            }else if(command.contains("addToHand")){
                addCardToHand(command, user, battlefield);
            }else if(command.contains("changeTurn")){
                battlefield.changeTurn();
            }else if(command.contains("getDuelist")){
                Matcher matherGetDuelist= Regex.getMatcher(command.split(" --token")[0], "--name (.+)");
                if(matherGetDuelist.find()){
                    String name = matherGetDuelist.group(1);
                    if(battlefield.getTurn().getName().equals(name))
                        return battlefield.getTurn();
                    else
                        return battlefield.getOpponent();
                }
            }else if(command.contains("whoIsTurn")){
                String your = getWhoIsTurn(command, battlefield);
                if (your != null) return your;
            }
            return "success description=\"process done\"";
        } else
            return "error description=\"token not valid\"";
    }

    private synchronized String getWhoIsTurn(String command, Battlefield battlefield) {
        Matcher matherGetDuelist= Regex.getMatcher(command.split(" --token")[0], "--name (.+)");
        if(matherGetDuelist.find()){
            String name = matherGetDuelist.group(1);
            if(battlefield.getTurn().getUser().getUsername().equals(name)) {
                System.out.println(name+"-> you are turn ");
                return "your";
            }
            else if(battlefield.getOpponent().getUser().getUsername().equals(name)) {
                System.out.println(name+"-> you are opponent ");
                return "opponent";
            }
        }
        return null;
    }

    private void addCardToHand(String command, User user, Battlefield battlefield) {
        Matcher matherAddToHand = Regex.getMatcher(command, "--name (.+) --index (\\d+)");
        if(matherAddToHand.find()){
            String name = matherAddToHand.group(1);
            int index = Integer.parseInt(matherAddToHand.group(2));
            if(battlefield.getTurn().getUser().getUsername().equals(user.getUsername()))
                battlefield.getTurn().field.hand.add(index,Card.allCards.get(name));
            else
                battlefield.getOpponent().field.hand.add(index,Card.allCards.get(name));
        }
    }

    private void winner(String command, Battlefield battlefield) {
        Matcher matherWinner = Regex.getMatcher(command, "--winner (.+)");
        if(matherWinner.find()){
            String name = matherWinner.group(1);
            if(battlefield.getTurn().getName().equals(name)){
                //turn is winner
                battlefield.winner = battlefield.getTurn();
                battlefield.isEndGame = true;
            }
            else{
                //opponent is winner
                battlefield.winner = battlefield.getOpponent();
                battlefield.isEndGame = true;
            }
            playingUsers.remove(battlefield.duelist1);
            playingUsers.remove(battlefield.duelist2);

        }
    }

    private String setCard(String command, User user, Battlefield battlefield) {
        Matcher matherSet = Regex.getMatcher(command, "--indexHand (\\d+) --index (\\d+)");
        if(matherSet.find()){
            int indexHand = Integer.parseInt(matherSet.group(1));
            int indexMonster = Integer.parseInt(matherSet.group(2));
            if(battlefield.getTurn().getUser().getUsername().equals(user.getUsername())){
                Card selectedCard = battlefield.getTurn().field.hand.get(indexHand);
                if(selectedCard.getCardsType().equals(Type.MONSTER)){
                    battlefield.getTurn().field.monsterZone.set(indexMonster, selectedCard);
//                            selectedCard.setIsSetThisTurn(true);
                    selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                    selectedCard.setCardsLocation(Location.MONSTER_AREA);
                }
                else{
                    battlefield.getTurn().field.spellTrapZone.set(indexMonster, selectedCard);
//                            selectedCard.setIsSetThisTurn(true);
                    selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                    selectedCard.setCardsLocation(Location.SPELL_AREA);
                }
                battlefield.getTurn().field.hand.remove(selectedCard);
            }else{
                Card selectedCard = battlefield.getOpponent().field.hand.get(indexHand);
                if(selectedCard.getCardsType().equals(Type.MONSTER)){
                    battlefield.getOpponent().field.monsterZone.set(indexMonster, selectedCard);
                    selectedCard.setIsSetThisTurn(true);
                    selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                    selectedCard.setCardsLocation(Location.MONSTER_AREA);
                }else{
                    battlefield.getOpponent().field.spellTrapZone.set(indexMonster, selectedCard);
                    selectedCard.setIsSetThisTurn(true);
                    selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                    selectedCard.setCardsLocation(Location.SPELL_AREA);
                }
                battlefield.getOpponent().field.hand.remove(selectedCard);
            }
        }else
            return "error description=\"card not found\"";
        return null;
    }

    private String summonMonster(String command, User user, Battlefield battlefield) {
        Matcher matherSummon = Regex.getMatcher(command, "--indexHand (\\d+) --index (\\d+)");
        if(matherSummon.find()){
            int indexHand = Integer.parseInt(matherSummon.group(1));
            int indexMonster = Integer.parseInt(matherSummon.group(2));
            if(battlefield.getTurn().equals(getDuelist(user))){
                Card selectedCard = battlefield.getTurn().field.hand.get(indexHand);
                battlefield.getTurn().field.monsterZone.set(indexMonster, selectedCard);
                battlefield.getTurn().field.hand.remove(indexHand);
                selectedCard.setCardsLocation(Location.MONSTER_AREA);
            }else{
                Card selectedCard = battlefield.getOpponent().field.hand.get(indexHand);
                battlefield.getOpponent().field.monsterZone.set(indexMonster, selectedCard);
                battlefield.getOpponent().field.hand.remove(indexHand);
                selectedCard.setCardsLocation(Location.MONSTER_AREA);
            }
        }else
            return "error description=\"card not found\"";
        return null;
    }

    private void removeCard(String command, Battlefield battlefield) {
        Matcher matherRmMonster = Regex.getMatcher(command, "--name (.+)");
        if(matherRmMonster.find()){
            String name = matherRmMonster.group(1);
            //in turn monster zone
            for (int i = 0; i<5; ++i){
                if (battlefield.getTurn().field.monsterZone.get(i)!= null &&
                        battlefield.getTurn().field.monsterZone.get(i).getName().equals(name)){
                   ((Monster) battlefield.getTurn().field.monsterZone.get(i)).removeMonster(battlefield);
                    break;
                }
            }
            //in opponent monster zone
            for (int i = 0; i<5; ++i){
                if (battlefield.getOpponent().field.monsterZone.get(i) != null &&
                        battlefield.getOpponent().field.monsterZone.get(i).getName().equals(name)){
                    ((Monster) battlefield.getOpponent().field.monsterZone.get(i)).removeMonster(battlefield);
                    break;
                }
            }
            //in turn spell zone
            for (int i = 0; i<5; ++i){
                if (battlefield.getTurn().field.spellTrapZone.get(i)!= null &&
                        battlefield.getTurn().field.spellTrapZone.get(i).getName().equals(name)){
                    ((SpellAndTrap) battlefield.getTurn().field.spellTrapZone.get(i)).removeSpellOrTrap(battlefield);
                    break;
                }
            }
            //in opponent spell zone
            for (int i = 0; i<5; ++i){
                if (battlefield.getOpponent().field.spellTrapZone.get(i) != null &&
                        battlefield.getOpponent().field.spellTrapZone.get(i).getName().equals(name)){
                    ((SpellAndTrap) battlefield.getOpponent().field.spellTrapZone.get(i)).removeSpellOrTrap(battlefield);
                    break;
                }
            }
        }
    }

    public static Battlefield getBattlefieldFromUser(User user) {
        Duelist duelist = getDuelist(user);
        Battlefield battlefield = getBattlefield(duelist,duelist);
        return battlefield;
    }

    public static Duelist getDuelist(User user) {
        Duelist duelist = null;
        for(Map.Entry<Duelist,Duelist> entry : playingUsers.entrySet()){
            if(entry.getKey().getUser().getUsername().equals(user.getUsername()))
                duelist = entry.getKey();
            else if(entry.getValue().getUser().getUsername().equals(user.getUsername()))
                duelist = entry.getValue();
        }
        return duelist;
    }
}
