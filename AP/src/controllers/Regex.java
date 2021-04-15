package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static String menuEnter = "^menu enter (\\S+)$";
    public static String menuExit = "^menu exit$";
    public static String menuShowCurrent = "^menu Show Current$";
    public static String login = "(^login (\\S+) (\\S+)$)";
    public static String register = "^register (\\S+) (\\S+)$";
    public static String userLogout = "^register (\\S+) (\\S+)$";

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
