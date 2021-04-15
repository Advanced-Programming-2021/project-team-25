package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static String menuEnter = "^menu enter (\\S+)$";
    public static String menuExit = "^menu exit$";
    public static String login = "(^login (\\S+) (\\S+)$)";
    public static String register = "^register (\\S+) (\\S+)$";
    public static String exit = "^exit$";
    public static String remove = "^remove (\\S+) (\\S+)$";
    public static String listUsers = "^list_users$";
    public static String newGame = "^new_game (\\S+)$";
    public static String logOut = "^logout$";
    public static String scoreBoard = "^scoreboard$";
    public static String shop = "^shop$";
    public static String help = "^help$";
    public static String putShip = "^put S(\\d+) (\\d+),(\\d+) -(\\S)$";
    public static String putMine = "^put-mine (\\d+),(\\d+)$";
    public static String putAntiAircraft = "^put-antiaircraft (\\d+) -(\\S)$";
    public static String putInvisible = "^invisible (\\d+),(\\d+)$";
    public static String finishArranging = "^finish-arranging$";
    public static String showMyBoard = "^show-my-board$";
    public static String showTurn = "^show-turn$";
    public static String bomb = "^bomb (\\d+),(\\d+)$";
    public static String putAirplane = "^put-airplane (\\d+),(\\d+) -(\\S)$";
    public static String scanner = "^scanner (\\d+),(\\d+)$";

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
