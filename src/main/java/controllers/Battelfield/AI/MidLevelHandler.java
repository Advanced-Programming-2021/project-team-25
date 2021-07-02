package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import view.UserInterface;

public class MidLevelHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(countOpponentMonsterInAttackPosition(battlefield) == 3 || (someOfAttacksOfOpponentMonster(battlefield) >= 1000 && someOfAttacksOfOpponentMonster(battlefield) < 1500)){
            if (howManyPlacesAreEmpty(battlefield) != 5)
                attack(battlefield);

            if (howManyPlacesAreEmpty(battlefield) != 0)
                setOrSummonMonsters(battlefield);

            activateSpells(battlefield);
        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }

    }




    public int howManyPlacesAreEmpty (Battlefield battlefield){
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) == null)
                counter += 1;
        }
        return counter;
    }


    public void attack (Battlefield battlefield) {
        for (int i = 0; i < 5; ++i) {
            if (battlefield.getOpponent().field.monsterZone.get(i) != null) {
                Monster temp = (Monster) battlefield.getOpponent().field.monsterZone.get(i);
                int attackToWho = attackToWhichMonster(battlefield, temp.getAttack());
                if (attackToWho == -1) {
                    battlefield.getTurn().LP -= temp.getAttack();
                } else if (attackToWho != -2) {
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


    public void setOrSummonMonsters (Battlefield battlefield){
        if (findMonster("yomi ship", battlefield) != -1){
            setAMonster(battlefield, "yomi ship");
        }
        else if (findMonster("suijin", battlefield) != -1 && howManyPlacesAreEmpty(battlefield) < 4){
            tributeXMonster(battlefield, 2);
            summonAMonster(battlefield, "suijin");
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
                tributeXMonster(battlefield, 3);
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
        else if (findMonsterInMonsterZone("herald of creation", battlefield) != -1 && isThereAnyMonsterUpper6InGraveYard(battlefield) != -1){
            int graveIndex = isThereAnyMonsterUpper6InGraveYard(battlefield);
            int index = -1;
            int money = 100000;
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
        else if (findMonster("exploder dragon", battlefield) != -1){
            setAMonster(battlefield, "exploder dragon");
        }
        else if (findMonster("terratiger, the empowered warrior", battlefield) != -1 && isThereAnyMonsterBelow5(battlefield)){
            summonAMonster(battlefield, "terratiger, the empowered warrior");
            for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i)
                if (battlefield.getOpponent().field.hand.get(i) != null &&
                        ((Monster)battlefield.getOpponent().field.hand.get(i)).getLevel() < 5)
                    setAMonster(battlefield, battlefield.getOpponent().field.hand.get(i).getName());
        }
        else if (findMonster("the tricky", battlefield) != -1){
            int where = findMonster("the tricky", battlefield);
            int index = -1;
            int money = 100000;
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
        else{
            for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
                if (battlefield.getOpponent().field.hand.get(i).getCardsType() == Type.MONSTER){
                    Card temp = battlefield.getOpponent().field.hand.get(i);
                    if (((Monster)temp).getLevel() < 5){
                        summonAMonster(battlefield, temp.getName());
                        break;
                    }
                    else if ( ((Monster)temp).getLevel() > 4 && ((Monster)temp).getLevel() < 7 && howManyPlacesAreEmpty(battlefield) < 5){
                        tributeXMonster(battlefield, 1);
                        summonAMonster(battlefield, temp.getName());
                        break;
                    }
                    else if (((Monster)temp).getLevel() > 6 && howManyPlacesAreEmpty(battlefield) < 4){
                        tributeXMonster(battlefield, 2);
                        summonAMonster(battlefield, temp.getName());
                        break;
                    }
                }
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


    public void tributeXMonster (Battlefield battlefield, int x){
        for (int i = 0; i<x; ++i){
            int index = -1;
            int price = 100000;
            for (int j = 0; j<5; ++j){
                if (battlefield.getOpponent().field.monsterZone.get(j) != null && battlefield.getOpponent().field.monsterZone.get(j).getPrice() < price){
                    index = j;
                    price = battlefield.getOpponent().field.monsterZone.get(j).getPrice();
                }
            }
            ((Monster)battlefield.getOpponent().field.monsterZone.get(index)).removeMonster(battlefield);
        }
    }


    public int findMonster (String name, Battlefield battlefield){
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i) != null && battlefield.getOpponent().field.hand.get(i).getName().equalsIgnoreCase(name)){
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


    public int findMonsterInMonsterZone (String name, Battlefield battlefield){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null && battlefield.getOpponent().field.monsterZone.get(i).getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }


    public int isThereAnyMonsterUpper6InGraveYard (Battlefield battlefield){
        for (int i = 0; i<battlefield.getOpponent().field.graveYard.size(); ++i){
            if (battlefield.getOpponent().field.graveYard.get(i).getCardsType() == Type.MONSTER &&
                    ((Monster)battlefield.getOpponent().field.graveYard.get(i)).getLevel() > 6)
                return i;
        }
        return -1;
    }


    public boolean isThereAnyMonsterBelow5 (Battlefield battlefield){
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i) != null &&
                    ((Monster)battlefield.getOpponent().field.hand.get(i)).getLevel() < 5)
                return true;
        }
        return false;
    }
























    public void activateSpells (Battlefield battlefield){
        if (whereIsSpellInHand(battlefield, "monster reborn") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 0 && howManyPlacesAreEmpty(battlefield) > 0) {
            activeMonsterReborn(battlefield, 1);
        }
        else if (whereIsSpellInSpellZone(battlefield, "monster reborn") != -1 && howManyPlacesAreEmpty(battlefield) > 0) {
            activeMonsterReborn(battlefield, 2);
        }
        else if (whereIsSpellInHand(battlefield, "harpie’s feather duster") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 0) {
            activeHarpiesFeatherDuster(battlefield, 1);
        }
        else if (whereIsSpellInSpellZone(battlefield, "harpie’s feather duster") != -1) {
            activeHarpiesFeatherDuster(battlefield, 2);
        }
        else if (whereIsSpellInHand(battlefield, "twin twisters") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 1 &&
                numberOfSpellsInHumanSpellZone(battlefield) > 1){
            activeTwinTwisters(battlefield);
            summonASpellOrTrap(battlefield, "twin twisters", "summon");
        }
        else if (whereIsSpellInSpellZone(battlefield, "twin twisters") != -1 && numberOfSpellsInHumanSpellZone(battlefield) > 1) {
            activeTwinTwisters(battlefield);
        }
        else if (whereIsSpellInHand(battlefield, "mystical space typhoon") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 1 &&
                numberOfSpellsInHumanSpellZone(battlefield) > 0){
            activeMysticalSpaceTyphoon(battlefield);
            summonASpellOrTrap(battlefield, "mystical space typhoon", "summon");
        }
        else if (whereIsSpellInSpellZone(battlefield, "mystical space typhoon") != -1 && numberOfSpellsInHumanSpellZone(battlefield) > 0) {
            activeMysticalSpaceTyphoon(battlefield);
        }
        else if (activateFieldZoneSpell(battlefield)){
            Card.allCards.get("Suijin");
        }
        else if (whereIsSpellInHand(battlefield, "sword of dark destruction") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 1 &&
                (isThereMonsterWithTypeX(battlefield, "fiend") || isThereMonsterWithTypeX(battlefield, "spellcaster"))){
            activatingSwordOfDarkDestruction(battlefield, battlefield.getOpponent().field.hand.get(whereIsSpellInHand(battlefield, "sword of dark destruction")));
            summonASpellOrTrap(battlefield, "sword of dark destruction", "summon");
        }
        else if (whereIsSpellInSpellZone(battlefield, "sword of dark destruction") != -1 &&
                (isThereMonsterWithTypeX(battlefield, "fiend") || isThereMonsterWithTypeX(battlefield, "spellcaster"))) {
            activatingSwordOfDarkDestruction(battlefield, battlefield.getOpponent().field.spellTrapZone.get(whereIsSpellInSpellZone(battlefield, "sword of dark destruction")));
        }
        else if (whereIsSpellInHand(battlefield, "black pendant") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 1){
            activatingBlackPendant(battlefield, battlefield.getOpponent().field.hand.get(whereIsSpellInHand(battlefield, "black pendant")));
            summonASpellOrTrap(battlefield, "black pendant", "summon");
        }
        else if (whereIsSpellInSpellZone(battlefield, "black pendant") != -1) {
            activatingBlackPendant(battlefield, battlefield.getOpponent().field.spellTrapZone.get(whereIsSpellInSpellZone(battlefield, "black pendant")));
        }
        else if (whereIsSpellInHand(battlefield, "united we stand") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 1){
            activatingUnitedWeStand(battlefield, battlefield.getOpponent().field.hand.get(whereIsSpellInHand(battlefield, "united we stand")));
            summonASpellOrTrap(battlefield, "united we stand", "summon");
        }
        else if (whereIsSpellInSpellZone(battlefield, "united we stand") != -1) {
            activatingUnitedWeStand(battlefield, battlefield.getOpponent().field.spellTrapZone.get(whereIsSpellInSpellZone(battlefield, "united we stand")));
        }
        else if (whereIsSpellInHand(battlefield, "magnum shield") != -1 && howManyPlacesAreEmptyInSpellZone(battlefield) > 1 &&
                isThereMonsterWithTypeX(battlefield, "warrior")){
            activatingMagnumShield(battlefield, battlefield.getOpponent().field.hand.get(whereIsSpellInHand(battlefield, "magnum shield")));
            summonASpellOrTrap(battlefield, "magnum shield", "summon");
        }
        else if (whereIsSpellInSpellZone(battlefield, "magnum shield") != -1 && isThereMonsterWithTypeX(battlefield, "magnum shield")) {
            activatingMagnumShield(battlefield, battlefield.getOpponent().field.spellTrapZone.get(whereIsSpellInSpellZone(battlefield, "magnum shield")));
        }
        else{
            for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
                if (battlefield.getOpponent().field.hand.get(i).getCardsType() != Type.MONSTER){
                    summonASpellOrTrap(battlefield, battlefield.getOpponent().field.hand.get(i).getName(), "set");
                    break;
                }
            }
        }
    }


    public int whereIsSpellInHand (Battlefield battlefield, String name){
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i) != null && battlefield.getOpponent().field.hand.get(i).getName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }


    public int howManyPlacesAreEmptyInSpellZone (Battlefield battlefield){
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.spellTrapZone.get(i) == null)
                counter += 1;
        }
        return counter;
    }


    public void activeMonsterReborn (Battlefield battlefield, int number){
        int indexOpponent = -1;
        int priceOpponent = -1;
        for (int i = 0; i<battlefield.getOpponent().field.graveYard.size(); ++i){
            if (battlefield.getOpponent().field.graveYard.get(i).getPrice() > priceOpponent){
                indexOpponent = i;
                priceOpponent = battlefield.getOpponent().field.graveYard.get(i).getPrice();
            }
        }

        int indexTurn = -1;
        int priceTurn = -1;
        for (int i = 0; i<battlefield.getTurn().field.graveYard.size(); ++i){
            if (battlefield.getTurn().field.graveYard.get(i).getPrice() > priceTurn){
                indexTurn = i;
                priceTurn = battlefield.getTurn().field.graveYard.get(i).getPrice();
            }
        }

        if (indexOpponent != -1 || indexTurn != -1){
            Monster temp;
            if (priceOpponent > priceTurn){
                temp = (Monster) battlefield.getOpponent().field.graveYard.get(indexOpponent);
                temp.setCardsFace(FaceUp.ATTACK);
                battlefield.getOpponent().field.graveYard.remove(indexOpponent);
            }
            else{
                temp = (Monster) battlefield.getTurn().field.graveYard.get(indexTurn);
                temp.setCardsFace(FaceUp.ATTACK);
                battlefield.getTurn().field.graveYard.remove(indexTurn);
            }
            for (int i = 0; i<5; ++i){
                if (battlefield.getOpponent().field.monsterZone.get(i) == null){
                    battlefield.getOpponent().field.monsterZone.set(i, temp);
                    break;
                }
            }
            if (number == 1) {
                battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(whereIsSpellInHand(battlefield, "monster reborn")));
                battlefield.getOpponent().field.hand.remove(whereIsSpellInHand(battlefield, "monster reborn"));
            }
            else {
                battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.spellTrapZone.get(whereIsSpellInSpellZone(battlefield, "monster reborn")));
                battlefield.getOpponent().field.spellTrapZone.remove(whereIsSpellInSpellZone(battlefield, "monster reborn"));
            }
        }
    }


    public int whereIsSpellInSpellZone (Battlefield battlefield, String name){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.spellTrapZone.get(i) != null && battlefield.getOpponent().field.spellTrapZone.get(i).getName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }


    public void activeHarpiesFeatherDuster (Battlefield battlefield, int number){
        for (int i = 0; i<5; ++i){
            if (battlefield.getTurn().field.spellTrapZone.get(i) != null)
                ((SpellAndTrap)(battlefield.getTurn().field.spellTrapZone.get(i))).removeSpellOrTrap(battlefield);
        }
        if (number == 1){
            for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
                if (battlefield.getOpponent().field.hand.get(i).getName().equalsIgnoreCase("harpie's feather duster")){
                    battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(i));
                    battlefield.getOpponent().field.hand.remove(i);
                    break;
                }
            }
        }
        else{
            for (int i = 0; i<5; ++i){
                if (battlefield.getOpponent().field.spellTrapZone.get(i) != null &&
                        battlefield.getOpponent().field.hand.get(i).getName().equalsIgnoreCase("harpie's feather duster")){
                    battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.spellTrapZone.get(i));
                    battlefield.getOpponent().field.spellTrapZone.remove(i);
                    break;
                }
            }
        }
    }


    public int numberOfSpellsInHumanSpellZone (Battlefield battlefield){
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.spellTrapZone.get(i) != null)
                counter += 1;
        }
        return counter;
    }


    public void activeTwinTwisters (Battlefield battlefield){
        int index = -1;
        int money = 100000;
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i).getPrice() < money){
                index = i;
                money = battlefield.getOpponent().field.hand.get(i).getPrice();
            }
        }
        battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.hand.get(index));
        battlefield.getOpponent().field.hand.remove(index);

        for (int i = 0; i<2; ++i){
            index = -1;
            money = -1;
            for (int j = 0; j<5; ++j){
                if (battlefield.getTurn().field.spellTrapZone.get(j) != null && battlefield.getTurn().field.spellTrapZone.get(j).getPrice() > money){
                    index = j;
                    money = battlefield.getTurn().field.spellTrapZone.get(j).getPrice();
                }
            }
            ((SpellAndTrap)(battlefield.getTurn().field.monsterZone.get(index))).removeSpellOrTrap(battlefield);
        }
    }


    public void summonASpellOrTrap (Battlefield battlefield, String name, String setOrSummon){
        int index = whereIsSpellInHand(battlefield, name);
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.spellTrapZone.get(i) == null){
                battlefield.getOpponent().field.spellTrapZone.set(i, battlefield.getOpponent().field.hand.get(index));
                if (setOrSummon.equalsIgnoreCase("summon"))
                    battlefield.getOpponent().field.hand.get(index).setCardsFace(FaceUp.ATTACK);
                else
                    battlefield.getOpponent().field.hand.get(index).setCardsFace(FaceUp.DEFENSE_BACK);
                battlefield.getOpponent().field.hand.remove(index);
                break;
            }
        }
    }


    public void activeMysticalSpaceTyphoon (Battlefield battlefield){
        int index = -1;
        int money = -1;
        for (int k = 0; k<5; ++k){
            if (battlefield.getTurn().field.spellTrapZone.get(k) != null && battlefield.getTurn().field.spellTrapZone.get(k).getPrice() > money){
                index = k;
                money = battlefield.getTurn().field.spellTrapZone.get(k).getPrice();
            }
        }
        ((SpellAndTrap)(battlefield.getTurn().field.monsterZone.get(index))).removeSpellOrTrap(battlefield);
    }


    public boolean activateFieldZoneSpell (Battlefield battlefield){
        boolean toReturn = false;
        int index = -1;
        for (int i = 0; i<battlefield.getOpponent().field.hand.size(); ++i){
            if (battlefield.getOpponent().field.hand.get(i).getCardsType() == Type.SPELL &&
                    ((SpellAndTrap)battlefield.getOpponent().field.hand.get(i)).getIcon().equalsIgnoreCase("field")){
                toReturn = true;
                index = i;
                break;
            }
        }

        if (!toReturn)
            return false;

        if (battlefield.getOpponent().field.fieldZone != null)
            ((SpellAndTrap)battlefield.getOpponent().field.fieldZone).removeSpellOrTrap(battlefield);
        if (battlefield.getTurn().field.fieldZone != null)
            ((SpellAndTrap)battlefield.getTurn().field.fieldZone).removeSpellOrTrap(battlefield);

        battlefield.getOpponent().field.hand.get(index).action(battlefield);
        return true;
    }


    public boolean isThereMonsterWithTypeX (Battlefield battlefield, String name){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null &&
                    ((Monster)battlefield.getOpponent().field.monsterZone.get(i)).getMonsterType().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }


    public void activatingSwordOfDarkDestruction (Battlefield battlefield, Card card){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null &&
                    (((Monster)battlefield.getOpponent().field.monsterZone.get(i)).getMonsterType().equalsIgnoreCase("fiend") ||
                            ((Monster)battlefield.getOpponent().field.monsterZone.get(i)).getMonsterType().equalsIgnoreCase("spellcaster"))){
                Monster monster = (Monster) battlefield.getOpponent().field.monsterZone.get(i);
                if (((SpellAndTrap)card).targetedMonsters.size() == 0) {
                    monster.setAttack(monster.getAttack() + 400);
                    monster.setDefence(monster.getDefence() - 200);
                    ((SpellAndTrap) card).targetedMonsters.add(monster);
                }
                break;
            }
        }
    }


    public void activatingBlackPendant (Battlefield battlefield, Card card){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null){
                Monster monster = (Monster) battlefield.getOpponent().field.monsterZone.get(i);
                if (((SpellAndTrap)card).targetedMonsters.size() == 0) {
                    monster.setAttack(monster.getAttack() + 500);
                    ((SpellAndTrap) card).targetedMonsters.add(monster);
                }
                break;
            }
        }
    }


    public void activatingUnitedWeStand (Battlefield battlefield, Card card){
        int x = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null && battlefield.getOpponent().field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK)
                x += 1;
        }
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null){
                Monster monster = (Monster) battlefield.getOpponent().field.monsterZone.get(i);
                if (((SpellAndTrap)card).targetedMonsters.size() == 0) {
                    monster.setAttack(monster.getAttack() + x*800);
                    monster.setDefence(monster.getDefence() + x*800);
                    ((SpellAndTrap) card).targetedMonsters.add(monster);
                }
                break;
            }
        }
    }


    public void activatingMagnumShield (Battlefield battlefield, Card card){
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null &&
                    ((Monster)battlefield.getOpponent().field.monsterZone.get(i)).getMonsterType().equalsIgnoreCase("warrior")){
                Monster monster = (Monster) battlefield.getOpponent().field.monsterZone.get(i);
                if (((SpellAndTrap)card).targetedMonsters.size() == 0) {
                    if (monster.getCardsFace() == FaceUp.ATTACK)
                        monster.setAttack(monster.getAttack() + monster.getDefence());
                    else
                        monster.setDefence(monster.getDefence() + monster.getAttack());
                    ((SpellAndTrap) card).targetedMonsters.add(monster);
                }
                break;
            }
        }
    }



}
