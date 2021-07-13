package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static String menuEnter = "^menu enter (\\S+)$";
    public static String menuExit = "^menu exit$";
    public static String menuShowCurrent = "^menu Show Current$";
    public static String userCreate = "user create (--username|-u) (?<username>\\S+) (--nickname|-n) (?<nickname>\\S+) (--password|-p) (?<password>\\S+)";
    public static String userLogin = "user login (?<group1>(--password|-p) (?<password>\\S+) (--username|-u) (?<username>\\S+))|(?<group2>(--username|-u) (?<username2>\\S+) (--password|-p) (?<password2>\\S+))";
    public static String userLogout = "^user logout$";
    public static String scoreBoardShow = "^scoreboard show$";
    public static String changeNickname = "^profile change (--nickname|-n) (?<nickname>\\S+)$";
    public static String changePassword = "^profile change (--password|-p) (--current|-c) (?<curr>\\S+) (--new|-n) (?<new>\\S+)$";
    public static String cardShow = "^card show (.+)$";
    public static String deckCreate = "^deck create (.+)$";
    public static String deckDelete = "^deck delete (.+)$";
    public static String deckSetActive = "^deck set-activate (.+)$";
    public static String deckAddCardToSide = "^deck add-card (--card|-c) (.+) (--deck|-d) (.+) (--side|-s)$";
    public static String deckAddCard = "^deck add-card (--card|-c) (.+) (--deck|-d) (.+)$";
    public static String deckRemoveCardFromSide = "^deck rm-card (--card|-c) (.+) (--deck|-d) (.+) --side$";
    public static String deckRemoveCard = "^deck rm-card (--card|-c) (.+) (--deck|-d) (.+)$";
    public static String deckShowAll = "^deck show (--all|-a)$";
    public static String deckShowDeckNameSide = "^deck show (--deck-name|-d) (.+) --side$";
    public static String deckShowDeckName = "^deck show (--deck-name|-d) (.+)$";
    public static String deckShowCards = "^deck show --cards$";
    public static String shopBuy = "^shop buy (.+)$";
    public static String shopShowAll = "^shop show --all$";
    public static String increaseMoney = "^increase --money (.+)$";
    public static String duelNew = "^duel --new --second-player (.+) --rounds (.+)$";
    public static String duelNewAi = "^duel --new --ai --rounds (.+)$";
    public static String select = "^select (.+)$";
    public static String selectOpponent = "^select (.+) --opponent$";
    public static String deselect = "^select -d$";
    public static String nextPhase = "^next phase$";
    public static String summon = "^summon$";
    public static String set = "^set$";
    public static String setPosition = "^set --position (attack|defence)$";
    public static String flipSummon = "^flip-summon$";
    public static String attack = "^attack (.+)$";
    public static String attackDirect = "^attack direct$";
    public static String activateEffect = "^activate effect$";
    public static String showGraveyard = "^show graveyard$";
    public static String showSelectedCard = "^card show --selected$";
    public static String surrender = "^surrender$";
    public static String cancel = "^cancel$";
    public static String selectCardFromGraveyard = "^select --(M|H) (\\d{0,50})$";
    public static String selectCardInAllFields = "^select --(G|D|H) (\\d{0,50})$";
    public static String selectFieldCard = "^select (\\d+)$";
    public static String exportCard = "^export (.+)$";
    public static String importCard = "^import (.+)$";
    public static String extractName = "^Name: (.+)$";
    public static String forceAddedCardToHand = "select --hand (.+)? --force";
    public static String duelWinCheat = "duel set-winner (.+)?";
    public static String increaseLPCheat = "increase --LP (.+)";

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}



