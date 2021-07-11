package view;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class API{

    private static API singleToneClass = null;
    private Controller controller = Controller.getInstance();
    Socket socket;
    public static API getInstance (){
        if (singleToneClass == null) singleToneClass = new API();
        return singleToneClass;
    }
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(7184);
            while (true) {
                Socket socket = serverSocket.accept();
                MyThread myThread = new MyThread(serverSocket, socket);
                myThread.start();
            }
        }catch (SocketException socketException){
            System.out.println("socket closed!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    Object process(String command) {
        if (command.startsWith("user create")) {
            return controller.createNewUser(command);
        } else if (command.startsWith("user login")) {
            return controller.loginUser(command);
        } else if (command.startsWith("profile change --nickname")) {
            return controller.changeNickname(command);
        } else if (command.startsWith("profile change --password")) {
            return controller.changPass(command);
        } else if (command.startsWith("initUserList")){
            return controller.initUserList();
        } else if (command.startsWith("initDeckList")){
            return controller.initDeckList();
        } else if (command.startsWith("get currUser")) {
            return controller.getUserToJson(command);
        } else if (command.startsWith("get currDecks")) {
            return controller.getDecksOfUser(command);
        } else if(command.startsWith("logout")) {
            return controller.logoutUSer(command);
        }
        //in deck menu
        else if(command.startsWith("addCardToSide")){
            return controller.addCardToSideDeck(command);
        } else if(command.startsWith("addCardToMain")){
            return controller.addCardToMainDeck(command);
        } else if(command.startsWith("addNewDeck")){
            return controller.addNewDeck(command);
        }
        return "";
    }


    public Socket getSocket() {
        return socket;
    }
}
