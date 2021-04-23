package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static String menuEnter = "^menu enter (\\S+)$";
    public static String menuExit = "^menu exit$";
    public static String menuShowCurrent = "^menu Show Current$";
    public static String userCreate = "^user create --username (?<username>\\S+) --nickname (?<nickname>\\S+) --password (?<password>\\S+)$";
    public static String userLogin = "^user login (?<group1>(--password|-p) (?<password>\\S+) (--username|-u) (?<username>\\S+))|(?<group2>(--username|-u) (?<username2>\\S+) (--password|-p) (?<password2>\\S+))$";
    public static String userLogout = "^register (\\S+) (\\S+)$";
    public static String scoreBoardShow = "^scoreboard show$";
    public static String changeNickname = "^scoreboard show$";
    public static String changePassword = "^scoreboard show$";
    public static String cardShow = "^card show (\\S+)$";
    public static String deckCreate = "^deck create (\\S+)$";
    public static String deckDelete = "^deck delete (\\S+)$";
    public static String deckSetActive = "^deck set-activate (\\S+)$";
    public static String deckAddCard = "^deck add-card --card (\\S+) --deck (\\S+)$";
    public static String deckRemoveCard = "^deck rm-card --card (\\S+) --deck (\\S+)$";
    public static String deckShowAll = "^deck show --all$";
    public static String deckShowDeckName = "^deck show --deck-name (\\S+)$";
    public static String deckShowCards = "^deck show --cards$";
    public static String shopBuy = "^shop buy (\\S+)$";
    public static String shopShowAll = "^shop show --all$";
    public static String select = "^select (\\S+)$";
    public static String selectOpponent = "^select (\\S+) --opponent$";
    public static String deselect = "^select -d$";
    public static String summon = "^summon$";
    public static String set = "^set$";
    public static String setPosition = "^set --position (attack|defence)$";
    public static String flipSummon = "^flip-summon$";
    public static String attack = "^attack (\\S+)$";
    public static String attackDirect = "^attack direct$";
    public static String activateEffect = "^activate effect$";
    public static String showGraveyard = "^show graveyard$";
    public static String showSelectedCard = "^card show --selected$";
    public static String surrender = "^surrender$";
    public static String cancel = "^cancel$";

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
