package controllers.Battelfield;

import controllers.Regex;
import controllers.ShowCard;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Location;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;

import static controllers.ShowCard.showCard;

public class Battlefield {

    private Duelist turn;
    private Duelist opponent;
    private Duelist winner;
    private Card selectedCard;
    private Phase phase;
    private boolean isRitualSummoned = false;

    public Battlefield(Duelist duelist1, Duelist duelist2) {
        whoStart(duelist1, duelist2);
        startGame();
        runBattleField();
    }

    public void runBattleField(){
        while (winner == null) {
            String command = UserInterface.getUserInput();
            Matcher matcher;
            if(isRitualSummoned) UserInterface.printResponse("you should ritual summon right now");
            else if ((matcher = Regex.getMatcher(command, Regex.selectOpponent)).matches()) selectOpponentCard(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.select)).matches()) selectCard(matcher);
            else if (Regex.getMatcher(command, Regex.deselect).matches()) deselectCard();
            else if (Regex.getMatcher(command, Regex.nextPhase).matches()) nextPhase();
            else if (Regex.getMatcher(command, Regex.summon).matches()) summon();
            else if (Regex.getMatcher(command, Regex.set).matches()) set();
            else if ((matcher = Regex.getMatcher(command, Regex.setPosition)).matches()) setPosition(matcher);
            else if (Regex.getMatcher(command, Regex.flipSummon).matches()) flipSummon();
            else if ((matcher = Regex.getMatcher(command, Regex.attack)).matches()) attack(matcher);
            else if (Regex.getMatcher(command, Regex.attackDirect).matches()) directAttack();
            else if (Regex.getMatcher(command, Regex.activateEffect).matches()) activeSpell();
            else if (Regex.getMatcher(command, Regex.showGraveyard).matches()) showGraveyard();
            else if ((matcher=Regex.getMatcher(command, Regex.cardShow)).matches()) showCard(matcher.group(1));
            else if (Regex.getMatcher(command, Regex.showSelectedCard).matches()) showSelectedCard();
            else if (Regex.getMatcher(command, Regex.surrender).matches()) surrender();
            //else if (Regex.getMatcher(command, Regex.cancel).matches())
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
            showBattleField();
        }
        endGame();
    }

    private void whoStart(Duelist duelist1, Duelist duelist2) {
        Random ran = new Random();
        if(ran.nextInt(2) == 0) chooseStarter(duelist2, duelist1);
        else chooseStarter(duelist1, duelist2);
    }

    private void chooseStarter(Duelist duelist1, Duelist duelist2) {
        UserInterface.printResponse("I flipped a coin and " + duelist2.getName() + " can decide who start’s\n1." + duelist2.getName() + "\n2." + duelist1.getName());
        String num = UserInterface.getUserInput();
        if (num.equals("1")) {
            turn = duelist2;
            opponent = duelist1;
        } else {
            turn = duelist1;
            opponent = duelist2;
        }
    }

    public void startGame(){
        if(opponent.field.hand.isEmpty() || turn.field.hand.isEmpty()){
            //shuffling the cards
            Collections.shuffle(opponent.field.deck);
            Collections.shuffle(turn.field.deck);
            //draw 6 cards for opponent and turn
            for(int i=0;i<6;i++){

                addCardToPlayersDeck(opponent);

                addCardToPlayersDeck(turn);
            }
        }
    }

    private void addCardToPlayersDeck(Duelist turn) {
        turn.field.hand.add(turn.field.deck.get(0));
        UserInterface.printResponse("new card added to the hand: "+turn.field.deck.get(0).getName());
        turn.field.deck.remove(0);
    }

    public void showBattleField(){
        UserInterface.printResponse(opponent.getName() + " : " + opponent.LP);
        for (Card card: opponent.field.hand) System.out.print("c\t");
        System.out.println("");
        UserInterface.printResponse(opponent.field.deck.size() + "");

        System.out.print("\t");
        showSpellAndTrapsZone(3 , opponent);
        showSpellAndTrapsZone(1 , opponent);
        showSpellAndTrapsZone(0 , opponent);
        showSpellAndTrapsZone(2 , opponent);
        showSpellAndTrapsZone(4 , opponent);

        System.out.print("\t");
        showMonsterZone(3 , opponent);
        showMonsterZone(1 , opponent);
        showMonsterZone(0 , opponent);
        showMonsterZone(2 , opponent);
        showMonsterZone(4 , opponent);

        System.out.print(opponent.field.graveYard.size() + "\t\t\t\t\t\t");
        if(opponent.field.fieldZone == null ) System.out.print("O\n");
        else System.out.print("E\n");

        UserInterface.printResponse("____________________________________________");

        if(turn.field.fieldZone == null ) System.out.print("O\n");
        else System.out.print("E\n");
        System.out.print("\t\t\t\t\t\t" + turn.field.graveYard.size());

        System.out.print("\t");
        showMonsterZone(4 , turn);
        showMonsterZone(2 , turn);
        showMonsterZone(0 , turn);
        showMonsterZone(1 , turn);
        showMonsterZone(3 , turn);

        System.out.print("\t");
        showSpellAndTrapsZone(4 , turn);
        showSpellAndTrapsZone(2 , turn);
        showSpellAndTrapsZone(0 , turn);
        showSpellAndTrapsZone(1 , turn);
        showSpellAndTrapsZone(3 , turn);

        UserInterface.printResponse( "\t\t\t\t\t\t" + turn.field.deck.size());
        for (Card card: turn.field.hand) System.out.print("c\t");
        System.out.println("");

        UserInterface.printResponse(turn.getName() + " : " + turn.LP);
    }

    private void showSpellAndTrapsZone(int i , Duelist duelist) {
        if (duelist.field.spellTrapZone.get(i) == null) System.out.print("E\t");
        else if (duelist.field.spellTrapZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) System.out.print("H\t");
        else if (duelist.field.spellTrapZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT) System.out.print("O\t");
    }

    private void showMonsterZone(int i, Duelist duelist) {
        if (duelist.field.monsterZone.get(i) == null) System.out.print("E\t");
        else if (duelist.field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_BACK) System.out.print("DH\t");
        else if (duelist.field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT) System.out.print("DO\t");
        else if (duelist.field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK) System.out.print("OO\t");
    }

    public void selectCard(Matcher matcher){
    	String restOfCommand = matcher.group(1);
        String[] temp = restOfCommand.split(" ");
        String[] breakedCommand = new String[2];
        int counter = 0;
        for (int i = 0; i<temp.length; ++i){
            if (temp[i].length() > 0){
                breakedCommand[counter] = temp[i];
                counter += 1;
            }
        }
        if (breakedCommand[0].equals("--monster")){
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (turn.field.monsterZone.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = turn.field.monsterZone.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        }
        else if (breakedCommand[0].equals("--spell")){
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (turn.field.spellTrapZone.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = turn.field.spellTrapZone.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        }
        else if (breakedCommand[0].equals("--field")){
            selectedCard = turn.field.fieldZone;
        }
        else if (breakedCommand[0].equals("--hand")){
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 6)
                UserInterface.printResponse("invalid selection");
            else if (turn.field.hand.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = turn.field.hand.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        }
        else{
            UserInterface.printResponse("invalid selection");
        }
    }

    public void selectOpponentCard(Matcher matcher){
    	String restOfCommand = matcher.group(1);
        String[] temp = restOfCommand.split(" ");
        String[] breakedCommand = new String[2];
        int counter = 0;
        for (int i = 0; i<temp.length; ++i){
            if (temp[i].length() > 0){
                breakedCommand[counter] = temp[i];
                counter += 1;
            }
        }
        if (breakedCommand[0].equals("--monster")){
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (opponent.field.monsterZone.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = opponent.field.monsterZone.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        }
        else if (breakedCommand[0].equals("--spell")){
            if (Integer.parseInt(breakedCommand[1]) < 1 || Integer.parseInt(breakedCommand[1]) > 5)
                UserInterface.printResponse("invalid selection");
            else if (opponent.field.spellTrapZone.get(Integer.parseInt(breakedCommand[1]) - 1) == null)
                UserInterface.printResponse("no card found in the given position");
            else {
                selectedCard = opponent.field.spellTrapZone.get(Integer.parseInt(breakedCommand[1]) - 1);
                UserInterface.printResponse("card selected");
            }
        }
        else if (breakedCommand[0].equals("--field")){
            selectedCard = opponent.field.fieldZone;
        }
        else{
            UserInterface.printResponse("invalid selection");
        }
    }

    public void deselectCard(){
        if (Objects.isNull(selectedCard)) UserInterface.printResponse("no card is selected yet");
        else{
            UserInterface.printResponse("card deselected");
            selectedCard = null;
        }
    }

    public void nextPhase(){
        if( phase == Phase.DRAW_PHASE ) phase = Phase.STANDBY_PHASE;
        else if( phase == Phase.STANDBY_PHASE ) phase = Phase.MAIN1_PHASE;
        else if( phase == Phase.MAIN1_PHASE ) phase = Phase.BATTLE_PHASE;
        else if( phase == Phase.BATTLE_PHASE ) phase = Phase.MAIN2_PHASE;
        else if( phase == Phase.MAIN2_PHASE ) phase = Phase.END_TURN;
        else if( phase == Phase.END_TURN ){

            changeTurn();

            cleanTurn();
            phase = Phase.DRAW_PHASE;
            //drawing card for Turn.
            drawCard();

            UserInterface.printResponse("its " + turn.getName() + "’s turn");
        }
        UserInterface.printResponse("phase: " + phase);
        if (phase == Phase.DRAW_PHASE ) drawCard();
    }

    public void summon(){

        //loading the monster from selected card
        Monster monster = (Monster)selectedCard;
        //checking is a card selected or not
        if (Objects.isNull(selectedCard)) UserInterface.printResponse("no card is selected yet");
        //checking that if we have monster
        else if(!turn.field.hand.contains(selectedCard) || !(selectedCard.getCardsType() == Type.MONSTER) || !monster.getCardTypeInExel().equals("normal"))
            UserInterface.printResponse("you cant summon this card");
        //checking the correct phase
        else if( !(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
            UserInterface.printResponse("action not allowed in this phase");
        //checking is the zone filled
        else if(getSizeOfMonsterZone()==5)
            UserInterface.printResponse("monster card zone is full");
        //checking if turn can summon
        else if(!turn.hasPutMonster)
            UserInterface.printResponse("you already summoned/set on this turn");
        //summon level 5 or 6 monsters
        else if(monster.getLevel()==5 || monster.getLevel()==6){
            summonLevel6Or5();
            selectedCard = null;
        }
        //summon level 7 , 8 monsters
        else if(monster.getLevel()==7 || monster.getLevel()==8){
            summonLevel8Or7(monster);
            selectedCard = null;
        }
        //normal summon
        else if(monster.getLevel()<=4){
            summonedMonster();
            //check that monster put
            turn.hasPutMonster = true;
            selectedCard = null;
        }
    }

    private void summonLevel8Or7(Monster monster) {
        //checking if can tribute happened
        if(getSizeOfMonsterZone()<2) UserInterface.printResponse("there are not enough cards for tribute");
        else {
            Monster monsterForTribute1 , monsterForTribute2;
            UserInterface.printResponse("please select two card to tribute!");
            UserInterface.printResponse("please select the first one");
            monsterForTribute1 = tributeOneMonster();
            UserInterface.printResponse("please select the next one");
            monsterForTribute2 = tributeOneMonster();
            //checking is error happened or not
            if(Objects.isNull(monsterForTribute1) || Objects.isNull(monsterForTribute2))
                return;
            //checking the levels is enough or not
            assert false;
            if(monsterForTribute1.getLevel()+monsterForTribute2.getLevel()<monster.getLevel())
                UserInterface.printResponse("selected monster levels don`t match with ritual monster");
            else{
                moveMonsterToGraveYard(monsterForTribute1);
                moveMonsterToGraveYard(monsterForTribute2);
                //summon
                summonedMonster();
                //check that monster put
                turn.hasPutMonster = true;
            }
        }
    }

    private void moveMonsterToGraveYard(Monster monsterForTribute1) {
        turn.field.monsterZone.remove(monsterForTribute1);
        turn.field.graveYard.add(monsterForTribute1);
    }

    private void summonLevel6Or5() {
        //checking if can tribute happened
        if(turn.field.monsterZone.isEmpty()) UserInterface.printResponse("there are not enough cards for tribute");
        else {
            UserInterface.printResponse("please select one card to tribute!");
            tributeOneMonster();
            //summon
            summonedMonster();
            //check monster put
            turn.hasPutMonster = true;
        }
    }

    private void summonedMonster() {
        turn.hasPutMonster = true;
        selectedCard.setSetChanged(true);
        selectedCard.setCardsFace(FaceUp.ATTACK);
        //putting card in last monster zone
        turn.field.monsterZone.set(getSizeOfMonsterZone()+1,selectedCard);

        UserInterface.printResponse("summoned successfully");
    }

    private Monster tributeOneMonster() {
        //selecting card to tribute
        String command = UserInterface.getUserInput();
        //getting card address
        Matcher matcher = Regex.getMatcher(command, Regex.select);

        if (matcher.find()) {
            //get monster
            Monster monsterForTribute = (Monster) turn.field.monsterZone.get(Integer.parseInt(matcher.group(1)));
            //checking not empty
            if (Objects.isNull(monsterForTribute))
                UserInterface.printResponse("no card found in the given position");
            //send tribute monster back
            return monsterForTribute;
        } else{
            UserInterface.printResponse(Responses.INVALID_CARD_SELECTION_ADDRESS);
            return null;
        }
    }

    public int getSizeOfMonsterZone(){
        int count=0;
        for (int i = 0; i<5; ++i)
            if (turn.field.monsterZone.get(i) != null) count += 1;
        return count;
    }
    public void set(){
        if (selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if (!turn.field.hand.contains(selectedCard))
            UserInterface.printResponse("you can't set this card");
        else if( !(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
            UserInterface.printResponse("you can't do this action in this phase");
        else if (selectedCard.getCardsType() == Type.MONSTER){
            int counter = getSizeOfMonsterZone();
            if (counter == 5)
                UserInterface.printResponse("monster card zone is full");
            else if (turn.hasPutMonster)
                UserInterface.printResponse("you already summoned/set on this turn");
            else{
                UserInterface.printResponse("set successfully");
                for (int i = 0; i<5; ++i){
                    if (turn.field.monsterZone.get(i) == null){
                        turn.field.monsterZone.set(i, selectedCard);
                        selectedCard.setIsSetThisTurn(true);
                        selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                        selectedCard.setCardsLocation(Location.MONSTER_AREA);
                        selectedCard = null;
                        break;
                    }
                }
                turn.hasPutMonster = true;
            }
        }
        else if (selectedCard.getCardsType() == Type.SPELL || selectedCard.getCardsType() == Type.TRAP){
            int counter = 0;
            for (int i = 0; i<5; ++i)
                if (turn.field.spellTrapZone.get(i) != null) counter += 1;
            if (counter == 5)
                UserInterface.printResponse("spell card zone is full");
            else{
                UserInterface.printResponse("set successfully");
                for (int i = 0; i<5; ++i){
                    if (turn.field.spellTrapZone.get(i) == null){
                        turn.field.spellTrapZone.set(i, selectedCard);
                        selectedCard.setIsSetThisTurn(true);
                        selectedCard.setCardsFace(FaceUp.DEFENSE_BACK);
                        selectedCard.setCardsLocation(Location.SPELL_AREA);
                        selectedCard = null;
                        break;
                    }
                }
            }
        }
    }

    public void setPosition(Matcher matcher){
        if(matcher.group(1).equals("attack")){
            if(selectedCard == null) UserInterface.printResponse("no card is selected yet");
            else if(!turn.field.monsterZone.contains(selectedCard)) UserInterface.printResponse("you can’t change this card position");
            else if(!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE)) UserInterface.printResponse("you can’t do this action in this phase");
            else if(selectedCard.getCardsFace() == FaceUp.ATTACK) UserInterface.printResponse("this card is already in the wanted position");
            else if(selectedCard.getSetChanged()) UserInterface.printResponse("you already changed this card position in this turn");
            else{
                selectedCard.setSetChanged(true);
                selectedCard.setCardsFace(FaceUp.ATTACK);
                UserInterface.printResponse("monster card position changed successfully");
            }
        }
        else if(matcher.group(1).equals("defence")){
            if(selectedCard == null) UserInterface.printResponse("no card is selected yet");
            else if(!turn.field.monsterZone.contains(selectedCard)) UserInterface.printResponse("you can’t change this card position");
            else if(!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE)) UserInterface.printResponse("you can’t do this action in this phase");
            else if(selectedCard.getCardsFace() != FaceUp.ATTACK) UserInterface.printResponse("this card is already in the wanted position");
            else if(selectedCard.getSetChanged()) UserInterface.printResponse("you already changed this card position in this turn");
            else{
                selectedCard.setSetChanged(true);
                selectedCard.setCardsFace(FaceUp.DEFENSE_FRONT);
                UserInterface.printResponse("monster card position changed successfully");
            }
        }
    }

    public void flipSummon(){
        Monster monster = (Monster) selectedCard;
        if(Objects.isNull(selectedCard)) UserInterface.printResponse(Responses.NO_CARD_SELECTED_ERROR);
        else if(!turn.field.monsterZone.contains(monster))
            UserInterface.printResponse("you can`t change this card position");
        else if(!(phase == Phase.MAIN1_PHASE || phase == Phase.MAIN2_PHASE))
            UserInterface.printResponse("you can’t do this action in this phase");
        else if(monster.getSetChanged() || selectedCard.getCardsFace() != FaceUp.DEFENSE_BACK)
            UserInterface.printResponse("you can’t flip summon this card");
        else{
            selectedCard.setCardsFace(FaceUp.ATTACK);
            UserInterface.printResponse("flip summoned successfully");
        }

    }

    public void attack(Matcher matcher){
        int monsterNum = Integer.parseInt(matcher.group(1));
        if(selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if(!turn.field.monsterZone.contains(selectedCard)) UserInterface.printResponse("you can’t attack with this card");
        else if(phase != Phase.BATTLE_PHASE) UserInterface.printResponse("you can’t do this action in this phase");
        else if(selectedCard.getIsAttackedThisTurn()) UserInterface.printResponse("this card already attacked");
        else if(getIndex(monsterNum) == -1) UserInterface.printResponse("invalid command");
        else if(opponent.field.monsterZone.get(getIndex(monsterNum)) == null) UserInterface.printResponse("there is no card to attack here");
        else{
            Monster attackedMonster = (Monster) opponent.field.monsterZone.get(getIndex(monsterNum));
            Monster attackingMonster = (Monster) selectedCard;

            if(attackedMonster.getCardsFace() == FaceUp.ATTACK){

                if(attackingMonster.getAttack() > attackedMonster.getAttack()){
                    selectedCard.setISAttackedThisTurn(true);
                    opponent.field.graveYard.add(opponent.field.monsterZone.get(getIndex(monsterNum)));
                    opponent.field.monsterZone.set(getIndex(monsterNum) , null);
                    int damage = attackingMonster.getAttack() - attackedMonster.getAttack();
                    opponent.LP = opponent.LP - damage;
                    UserInterface.printResponse("your opponent’s monster is destroyed and your opponent receives" + damage + "battle damage");
                }

                else if(attackingMonster.getAttack() == attackedMonster.getAttack()){
                    opponent.field.graveYard.add(opponent.field.monsterZone.get(getIndex(monsterNum)));
                    opponent.field.monsterZone.set(getIndex(monsterNum) , null);
                    turn.field.graveYard.add(selectedCard);
                    turn.field.monsterZone.set(getIndex(getIndexOfCard()) , null);
                    UserInterface.printResponse("both you and your opponent monster cards are destroyed and no one receives damage");
                }

                else{
                    turn.field.graveYard.add(selectedCard);
                    turn.field.monsterZone.set(getIndex(getIndexOfCard()) , null);
                    int damage = attackedMonster.getAttack() - attackingMonster.getAttack();
                    turn.LP = turn.LP - damage;
                    UserInterface.printResponse("Your monster card is destroyed and you received " + damage + " battle damage");
                }

            }

            else {

                if(attackingMonster.getAttack() > attackedMonster.getDefence()){
                    selectedCard.setISAttackedThisTurn(true);
                    opponent.field.graveYard.add(opponent.field.monsterZone.get(getIndex(monsterNum)));
                    opponent.field.monsterZone.set(getIndex(monsterNum) , null);
                    UserInterface.printResponse("the defense position monster is destroyed");
                }

                else if(attackingMonster.getAttack() == attackedMonster.getDefence()){
                    selectedCard.setISAttackedThisTurn(true);
                    if(attackedMonster.getCardsFace() == FaceUp.DEFENSE_BACK){
                        UserInterface.printResponse("opponent’s monster card was " + attackedMonster.getName() + " and no card is destroyed");
                        opponent.field.monsterZone.get(getIndex(monsterNum)).setCardsFace(FaceUp.DEFENSE_FRONT);
                    }
                    UserInterface.printResponse("no card is destroyed");
                }

                else{
                    turn.field.graveYard.add(selectedCard);
                    turn.field.monsterZone.set(getIndex(getIndexOfCard()) , null);
                    int damage = attackedMonster.getDefence() - attackingMonster.getAttack();
                    turn.LP = turn.LP - damage;
                    UserInterface.printResponse("no card is destroyed and you received " + damage + " battle damage");
                }

            }
        }
    }

    public void directAttack(){
        if(selectedCard == null) UserInterface.printResponse("no card is selected yet");
        else if(!turn.field.monsterZone.contains(selectedCard)) UserInterface.printResponse("you can’t attack with this card");
        else if(phase != Phase.BATTLE_PHASE) UserInterface.printResponse("you can’t do this action in this phase");
        else if(selectedCard.getIsAttackedThisTurn()) UserInterface.printResponse("this card already attacked");
        else if(!isOpponentEmptyOfMonsters()) UserInterface.printResponse("you can’t attack the opponent directly");
        else{
            selectedCard.setISAttackedThisTurn(true);
            Monster monster = (Monster) selectedCard;
            opponent.LP = opponent.LP -  monster.getAttack();
            System.out.println("your opponent receives " + monster.getAttack() + " battle damage");
        }
    }

    public void activeSpell(){

    }

    public void ritualSummon(){
        String command;
        //getting the ritual monster in hand if exist
        Monster ritualMonster = getRitualMonsterInHand();
        //getting the sum of levels in monster zone
        int sumOfLevels = getSumOfLevelsInZone();
        if (Objects.isNull(ritualMonster) || sumOfLevels < 7)
            UserInterface.printResponse("there is no way you could ritual summon a monster");
        else{
            //checking not input another command
            isRitualSummoned = true;
            //get input command
            command = UserInterface.getUserInput();
            //force user to say summon
            while(!command.equals("summon"))
                UserInterface.printResponse("you should ritual summon right now");

            summonLevel8Or7(ritualMonster);
        }
    }

    private Monster getRitualMonsterInHand () {
        for (Card card : turn.field.hand) {
            if (card.getCardsType().equals(Type.MONSTER) && ((Monster) card).getCardTypeInExel().equals("Ritual")) {
                return (Monster) card;
            }
        }
        return null;
    }

    private int getSumOfLevelsInZone () {
        int sum = 0;
        for (Card card : turn.field.monsterZone) {
            sum += ((Monster) card).getLevel();
        }
        return sum;
    }

    public void showGraveyard(){
        if(turn.field.graveYard.isEmpty()) UserInterface.printResponse("graveyard empty");
        else{
            int i=1;
            for (Card card: turn.field.graveYard) {
                UserInterface.printResponse(i + ". " + card.getName() + " : " + card.getDescription());
            }
        }
        UserInterface.getUserInput();
    }

    public void showSelectedCard(){
        if(selectedCard==null) UserInterface.printResponse("no card is selected yet");
        else if(selectedCard.getCardsFace() == FaceUp.DEFENSE_BACK) UserInterface.printResponse("card is not visible");
        else ShowCard.showCard(selectedCard.getName());
    }

    public void surrender(){
        winner = opponent;
    }

    public void changeTurn(){
        Duelist temp;
        temp = turn;
        turn = opponent;
        opponent = temp;
    }

    public void endGame(){
        UserInterface.printResponse(winner.getName() + " won the game and the score is: " + "1" + "-" +"0");
        UserInterface.printResponse(winner.getName() + " won the whole match with score: " + "1" + "-" + "2");
        int pevScore = winner.getUser().getScore();
        winner.getUser().setScore(pevScore + 1000);
        int pevMoney = winner.getUser().getMoney();
        winner.getUser().setMoney(pevMoney + winner.LP);
    }

    public void cleanTurn(){
        turn.hasPutMonster = false;
        opponent.hasPutMonster = false;
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                turn.field.monsterZone.get(i).setSetChanged(false);
                turn.field.monsterZone.get(i).setIsSetThisTurn(false);
                turn.field.monsterZone.get(i).setISAttackedThisTurn(false);
            }
            if (opponent.field.monsterZone.get(i) != null){
                opponent.field.monsterZone.get(i).setSetChanged(false);
                opponent.field.monsterZone.get(i).setIsSetThisTurn(false);
                opponent.field.monsterZone.get(i).setISAttackedThisTurn(false);
            }
            if (turn.field.spellTrapZone.get(i) != null){
                turn.field.spellTrapZone.get(i).setSetChanged(false);
                turn.field.spellTrapZone.get(i).setIsSetThisTurn(false);
            }
            if (opponent.field.spellTrapZone.get(i) != null){
                opponent.field.spellTrapZone.get(i).setSetChanged(false);
                opponent.field.spellTrapZone.get(i).setIsSetThisTurn(false);
            }
        }
        if (turn.field.fieldZone != null) {
            turn.field.fieldZone.setSetChanged(false);
            turn.field.fieldZone.setIsSetThisTurn(false);
        }
        if (opponent.field.fieldZone != null) {
            opponent.field.fieldZone.setSetChanged(false);
            opponent.field.fieldZone.setIsSetThisTurn(false);
        }
    }

    public void drawCard(){

        if(turn.field.deck.size()>0){
            if(turn.field.hand.size()<6){
                addCardToPlayersDeck(turn);

            }
        }
        else {
            winner = opponent;
            endGame();
            // calling the function that must be told to done when someone wins.
        }
    }

    public void removeElementFromArrayList(ArrayList<Card> cards,Card element){
        cards.set(cards.indexOf(element),null);
    }

    public int getIndex(int num){
        if(num == 1) return 2;
        else if(num == 2) return 1;
        else if(num == 3) return 3;
        else if(num == 4) return 0;
        else if(num == 5) return 4;
        else return -1;
    }

    public int getIndexOfCard(){
        for (int i = 0 ; i < 5 ; i++ ) {
            if(selectedCard == turn.field.monsterZone.get(i)) return i;
        }
        return -1;
    }

    public boolean isOpponentEmptyOfMonsters(){
        for (Card card: opponent.field.monsterZone) {
            if(card != null) return false;
        }
        return true;
    }

}
