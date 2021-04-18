package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static String menuEnter = "^menu enter (\\S+)$";
    public static String menuExit = "^menu exit$";
    public static String menuShowCurrent = "^menu Show Current$";
    public static String userCreate = "user create --username (?<username>\\S+) --nickname (?<nickname>\\S+) --password (?<password>\\S+)";
    public static String userLogin = "user login (?<group1>(--password|-p) (?<password>\\S+) (--username|-u) (?<username>\\S+))|(?<group2>(--username|-u) (?<username2>\\S+) (--password|-p) (?<password2>\\S+))";
    public static String userLogout = "^register (\\S+) (\\S+)$";
    public static String scoreBoardShow = "^scoreboard show$";
    public static String changeNickname = "^scoreboard show$";
    public static String changePassword = "^scoreboard show$";
    public static String cardShow = "^scoreboard show$";
    public static String deckCreate = "^deck create (\\S+)$";
    public static String deckDelete = "^^deck delete (\\S+)$";
    public static String deckSetActive = "^deck set-activate (\\S+)$";
    public static String deckAddCard = "^deck add-card --card (\\S+) --deck (\\S+)$";
    public static String deckRemoveCard = "^scoreboard show$";
    public static String deckShowAll = "^scoreboard show$";
    public static String deckShowDeckName = "^scoreboard show$";
    public static String deckShowCards = "^scoreboard show$";
    public static String shopBuy = "^scoreboard show$";
    public static String shopShowAll = "^scoreboard show$";

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
