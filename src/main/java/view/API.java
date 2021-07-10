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
            ServerSocket serverSocket = new ServerSocket(7185);
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

    String process(String command) {
        if (command.startsWith("create")) {
            return controller.createNewUser(command);
        } else if (command.startsWith("login")) {
            return controller.loginUser(command);
        } else if (command.startsWith("profile change --nickname")) {
            return controller.changeNickname(command);
        } else if (command.startsWith("profile change --password")) {
            return controller.changPass(command);
        } else if (command.startsWith("initUserList")){
            return controller.initUserList();
        } else if (command.startsWith("initDeckList")){
            return controller.initDeckList();
        } else if (command.startsWith("getHelp")) {
        }
        return "";
    }

    public Socket getSocket() {
        return socket;
    }
}
