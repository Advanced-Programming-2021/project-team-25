package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Monster.Monster;
import view.UserInterface;

public class HighLevelHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(countOpponentMonsterInAttackPosition(battlefield)>=4 || someOfAttacksOfOpponentMonster(battlefield)>=2000){

            if (howManyPlacesAreEmpty(battlefield) != 0)
                attack(battlefield);

            if (howManyPlacesAreEmpty(battlefield) != 0)
                setOrSummonMonsters(battlefield);
        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }
    }




    public void setOrSummonMonsters (Battlefield battlefield){
        if (findMonster("the tricky", battlefield) != -1){
            int where = findMonster("the tricky", battlefield);
            int index = -1;
            int money = 10000;
            for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i) {
                if (i != where && battlefield.getOpponent().field.hand.get(i) != null && battlefield.getOpponent().field.hand.get(i).getPrice() < money) {
                    index = i;
                    money = battlefield.getOpponent().field.hand.get(i).getPrice();
                }
            }
            battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(index));
            battlefield.getOpponent().field.hand.remove(index);
            summonAMonster(battlefield, "the tricky");
        }
        if (findMonsterInMonsterZone("herald of creation", battlefield) != -1){
            if (isThereAnyMonsterUpper6InGraveYard(battlefield) != -1){
                int graveIndex = isThereAnyMonsterUpper6InGraveYard(battlefield);
                int index = -1;
                int money = 10000;
                for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i) {
                    if (battlefield.getOpponent().field.hand.get(i).getPrice() < money) {
                        index = i;
                        money = battlefield.getOpponent().field.hand.get(i).getPrice();
                    }
                }
                battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(index));
                battlefield.getOpponent().field.hand.remove(index);
                battlefield.getOpponent().field.hand.add(battlefield.getOpponent().field.graveYard.get(graveIndex));
                battlefield.getOpponent().field.graveYard.remove(graveIndex);
            }
        }
        if (findMonster("yomi ship", battlefield) != -1){
            setAMonster(battlefield, "yomi ship");
        }
        else if (findMonster("man-eater bug", battlefield) != -1){
            setAMonster(battlefield, "man-eater bug");
            int index = -1;
            int max = -1;
            for (int i = 0; i<5; ++i){
                if (battlefield.getTurn().field.monsterZone.get(i) != null &&
                        ((Monster)(battlefield.getTurn().field.monsterZone.get(i))).getAttack() > max) {
                    index = i;
                    max = ((Monster) (battlefield.getTurn().field.monsterZone.get(i))).getAttack();
                }
            }
            ((Monster)battlefield.getTurn().field.monsterZone.get(index)).removeMonster(battlefield);
        }
        else if (findMonster("marshmallon", battlefield) != -1){
            setAMonster(battlefield, "marshmallon");
        }
        else if (findMonster("beast king barbaros", battlefield) != -1){
            int counter = 0;
            for (int i = 0; i<5; ++i)
                if (battlefield.getOpponent().field.monsterZone.get(i) != null)
                    counter += 1;

            if (counter >= 3){
                int counter1 = 0;
                for (int i = 0; i<5; ++i){
                    if (battlefield.getOpponent().field.monsterZone.get(i) != null){
                        ((Monster)battlefield.getOpponent().field.monsterZone.get(i)).removeMonster(battlefield);
                        counter1 += 1;
                    }
                    if (counter1 == 3)
                        break;
                }
                for (int i = 0; i<5; ++i)
                    if (battlefield.getTurn().field.monsterZone.get(i) != null)
                        ((Monster)battlefield.getTurn().field.monsterZone.get(i)).removeMonster(battlefield);
                summonAMonster(battlefield, "beast king barbaros");
            }
            else{
                int where = findMonster("beast king barbaros", battlefield);
                ((Monster)battlefield.getOpponent().field.hand.get(where)).setAttack(1900);
                summonAMonster(battlefield, "beast king barbaros");
            }
        }
        else if (findMonster("exploder dragon", battlefield) != -1){
            setAMonster(battlefield, "exploder dragon");
        }
        else if (findMonster("terratiger, the empowered warrior", battlefield) != -1 &&
                isThereAnyMonsterBelow5(battlefield)){
            summonAMonster(battlefield, "terratiger, the empowered warrior");
            for (int i = 0; i<5; ++i)
                if (battlefield.getTurn().field.hand.get(i) != null &&
                        ((Monster)battlefield.getOpponent().field.hand.get(i)).getLevel() < 5)
                    setAMonster(battlefield, battlefield.getOpponent().field.hand.get(i).getName());
        }
        else if (findMonster("herald of creation", battlefield) != -1){
            summonAMonster(battlefield, "herald of creation");
        }
        else if (findMonster("suijin", battlefield) != -1 && howManyPlacesAreEmpty(battlefield) < 4){
            tributeXMonster(battlefield, 2);
            summonAMonster(battlefield, "suijin");
        }
        else if (findMonster("crab turtle",battlefield) != -1 && howManyPlacesAreEmpty(battlefield) < 4 && findMonster("advanced ritual art",battlefield) != -1){
            tributeXMonster(battlefield, 2);
            int where = findMonster("advanced ritual art", battlefield);
            battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(where));
            battlefield.getOpponent().field.hand.remove(where);
            summonAMonster(battlefield, "crab turtle");
        }
        else if (findMonster("skull guardian",battlefield) != -1 && howManyPlacesAreEmpty(battlefield) < 4 && findMonster("advanced ritual art",battlefield) != -1){
            tributeXMonster(battlefield, 2);
            int where = findMonster("advanced ritual art", battlefield);
            battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(where));
            battlefield.getOpponent().field.hand.remove(where);
            setAMonster(battlefield, "skull guardian");
        }
        //you must check the others which has effect
        else{
            for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
                if (battlefield.getOpponent().field.hand.get(i).getCardsType() == Type.MONSTER){
                    Card temp = battlefield.getOpponent().field.hand.get(i);
                    if (((Monster)temp).getLevel() < 5){
                        summonAMonster(battlefield, temp.getName());
                    }
                    else if ( ((Monster)temp).getLevel() > 4 && ((Monster)temp).getLevel() < 7 && howManyPlacesAreEmpty(battlefield) < 5){
                        tributeXMonster(battlefield, 1);
                        summonAMonster(battlefield, temp.getName());
                    }
                    else if (((Monster)temp).getLevel() > 6 && howManyPlacesAreEmpty(battlefield) < 4){
                        tributeXMonster(battlefield, 2);
                        summonAMonster(battlefield, temp.getName());
                    }
                }
            }
        }


    }








    public void attack (Battlefield battlefield){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null){
                Monster temp = (Monster) battlefield.getOpponent().field.monsterZone.get(i);
                int attackToWho = attackToWhichMonster(battlefield, temp.getAttack());
                if (attackToWho == -1){
                    battlefield.getTurn().LP -= temp.getAttack();
                }
                else if (attackToWho == -2){
                    //nothing
                }
                else{
                    battlefield.attackingMonster = temp;
                    battlefield.attackedMonster = (Monster) battlefield.getTurn().field.monsterZone.get(attackToWho);
                    battlefield.attackedMonsterNum = attackToWho;
                    temp.action(battlefield);
                }
            }
        }


    }

    public int attackToWhichMonster (Battlefield battlefield, int attack){
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getTurn().field.monsterZone.get(i) != null)
                counter += 1;
        }

        int indexToAttack = -2;
        int mostHighAttackOrDef = -1;
        if (counter == 0) return -1;
        else{
            for (int i = 0; i<5; ++i){
                if (battlefield.getTurn().field.monsterZone.get(i) != null){
                    Monster temp = (Monster) battlefield.getTurn().field.monsterZone.get(i);
                    if (temp.getCardsFace() == FaceUp.ATTACK){
                        if (temp.getAttack() < attack && temp.getAttack() > mostHighAttackOrDef) {
                            indexToAttack = i;
                            mostHighAttackOrDef = temp.getAttack();
                        }
                    }
                    else{
                        if (temp.getDefence() < attack && temp.getDefence() > mostHighAttackOrDef){
                            indexToAttack = i;
                            mostHighAttackOrDef = temp.getDefence();
                            temp.setCardsFace(FaceUp.DEFENSE_FRONT);
                        }
                    }
                }
            }
        }
        return indexToAttack;

    }


















    public int findMonster (String name, Battlefield battlefield){
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i) != null && battlefield.getOpponent().field.hand.get(i).getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }

    public int findMonsterInMonsterZone (String name, Battlefield battlefield){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null && battlefield.getOpponent().field.monsterZone.get(i).getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }

    public void setAMonster (Battlefield battlefield, String name){
        int where = findMonster(name, battlefield);
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) == null){
                battlefield.getOpponent().field.monsterZone.set(i, battlefield.getOpponent().field.hand.get(where));
                battlefield.getOpponent().field.hand.get(where).setCardsFace(FaceUp.DEFENSE_BACK);
                battlefield.getOpponent().field.hand.remove(where);
                break;
            }
        }
    }


    public void summonAMonster (Battlefield battlefield, String name){
        int where = findMonster(name, battlefield);
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) == null){
                battlefield.getOpponent().field.monsterZone.set(i, battlefield.getOpponent().field.hand.get(where));
                battlefield.getOpponent().field.hand.get(where).setCardsFace(FaceUp.ATTACK);
                battlefield.getOpponent().field.hand.remove(where);
                break;
            }
        }
    }

    public boolean isThereAnyMonsterBelow5 (Battlefield battlefield){
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i) != null &&
                    ((Monster)battlefield.getOpponent().field.hand.get(i)).getLevel() < 5)
                return true;
        }
        return false;
    }

    public int isThereAnyMonsterUpper6InGraveYard (Battlefield battlefield){
        for (int i = 0; i<battlefield.getOpponent().field.graveYard.size(); ++i){
            if (battlefield.getOpponent().field.graveYard.get(i).getCardsType() == Type.MONSTER &&
                    ((Monster)battlefield.getOpponent().field.graveYard.get(i)).getLevel() > 6)
                return i;
        }
        return -1;
    }

    public int howManyPlacesAreEmpty (Battlefield battlefield){
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) == null)
                counter += 1;
        }
        return counter;
    }

    public void tributeXMonster (Battlefield battlefield, int x){
        for (int i = 0; i<x; ++i){
            int index = -1;
            int price = 10000;
            for (int j = 0; j<5; ++j){
                if (battlefield.getOpponent().field.monsterZone.get(j) != null && battlefield.getOpponent().field.monsterZone.get(j).getPrice() < price){
                    index = j;
                    price = battlefield.getOpponent().field.monsterZone.get(j).getPrice();
                }
            }
            ((Monster)battlefield.getOpponent().field.monsterZone.get(index)).removeMonster(battlefield);
        }
    }


}
