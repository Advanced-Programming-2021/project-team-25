package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class BlackPendant extends SpellAndTrap  implements Serializable {
    private static Duelist turn;

    public BlackPendant (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public BlackPendant (Object object){
        super(((BlackPendant)object).getName(), ((BlackPendant)object).getCardsType(),
                ((BlackPendant)object).getDescription(), ((BlackPendant)object).getPrice(),
                ((BlackPendant)object).getIcon(), ((BlackPendant)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();
        String[] monsters = new String[6];
        int counter = 0;
        ArrayList<Monster> trueMonsters = new ArrayList<>();
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster sample = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(sample) && targetedMonsters.size() == 0) {
                    trueMonsters.add(sample);
                    counter += 1;
                }
            }
        }

        if (targetedMonsters.size() > 0)
            UserInterface.printResponse("This spell has already equiped a monster.");
        else if (counter == 0)
            UserInterface.printResponse("You don't have any monster in your monster zone.");
        else{
            UserInterface.printResponse("Now select one of these monsters to equip it");
            int i=0;
            for (Monster trueMonster : trueMonsters) {
                monsters[i] = trueMonster.getName();
                i++;
            }
            monsters[5] = "Cancel";
            JList list = new JList(monsters);
            JOptionPane.showMessageDialog(
                    null, list, "Select Monster", JOptionPane.PLAIN_MESSAGE);
            System.out.println(Arrays.toString(list.getSelectedIndices()));
            String name = " ";
            while (true){
                String command = (String) list.getSelectedValue();
                for (Monster trueMonster : trueMonsters) {
                    if (trueMonster.getName().equals(command)) {
                        name = command;
                        equipMonster(trueMonster);
                        break;
                    }
                }
                if (name.equalsIgnoreCase("cancel"))
                    return;
                if (name.equals(" "))
                    UserInterface.printResponse("Insert a valid name please.");
                else
                    break;
            }
        }
    }


    public void equipMonster (Monster monster){
        targetedMonsters.add(monster);
        monster.setAttack(monster.getAttack() + 500);
    }

    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        if (targetedMonsters.get(0) != null) {
            targetedMonsters.get(0).setAttack(targetedMonsters.get(0).getAttack() - 500);
        }
        targetedMonsters = new ArrayList<>();
        turn.field.graveYard.add(this);
        for (int i = 0; i<5; ++i){
            if (turn.field.spellTrapZone.get(i) == this){
                turn.field.spellTrapZone.set(i, null);
                break;
            }
        }
    }
}
