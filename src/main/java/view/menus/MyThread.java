package view.menus;

import controllers.Battelfield.Battlefield;
import controllers.ProgramController;
import javafx.scene.layout.Background;
import models.Duelist;
import models.User;
import view.SendReceiveData;

public class MyThread extends Thread {
    Battlefield battlefield = null;
    Game game;
    public MyThread(Battlefield battlefield ,Game game){
        this.battlefield = battlefield;
        this.game = game;
    }
    public void run(){
        while(battlefield.winner == null){
            if(battlefield.turn != null && battlefield.opponent != null){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("refresh the board");
                String whoIsTurn;
                System.out.println("Battlefield Turn -> "+battlefield.getTurn().getUser().getUsername());
                System.out.println("Battlefield Opponent -> "+battlefield.getOpponent().getUser().getUsername());
                whoIsTurn = SendReceiveData.sendReceiveData("battlefield whoIsTurn --name "+battlefield.getTurn().getUser().getUsername());
                System.out.println(whoIsTurn);
                if(whoIsTurn != null && whoIsTurn.equals("opponent")){
                    battlefield.isOpponentsTurn = true;
                    refresh();
                    System.out.println("Please notice you are opponent so you must wait until turn changed");
                }
            }
        }

    }
    public void refresh(){
        User curr = ProgramController.currUser;
//        battlefield.getTurn().setField((Field) SendReceiveData.getDuelist("battlefield getDuelist --name " + battlefield.getTurn().getUser().getUsername()));
//        battlefield.getTurn().setLP((Integer) SendReceiveData.getDuelist("battlefield getLp --name " + battlefield.getTurn().getUser().getUsername()));
//        battlefield.getTurn().setHasPutMonster((Boolean) SendReceiveData.getDuelist("battlefield getHasPutMonster --name " + battlefield.getTurn().getUser().getUsername()));
//        String duelistServerTurnMonsters = (String) SendReceiveData.getDuelist("battlefield getDuelist --name " + battlefield.getTurn().getUser().getUsername());
//        String duelistServerTurnSpells = (String) SendReceiveData.getDuelist("battlefield getDuelist --name " + battlefield.getTurn().getUser().getUsername());
        Duelist duelistTurn = SendReceiveData.getDuelist("battlefield getDuelist --name " + battlefield.getTurn().getUser().getUsername());
        battlefield.setTurn(duelistTurn);
        Duelist duelistServerOpponent =  SendReceiveData.getDuelist("battlefield getDuelist --name " + battlefield.getOpponent().getUser().getUsername());
        Duelist duelistClient2 = battlefield.getTurn();
        battlefield.setOpponent(duelistServerOpponent);
    }
}
