package view;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static controllers.Regex.changeNickname;

public class API extends Thread{

    private static API singleToneClass = null;
    private Controller controller = Controller.getInstance();
    public static API getInstance (){
        if (singleToneClass == null) singleToneClass = new API();
        return singleToneClass;

    }
    @Override
    public void run() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(7185);
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (!serverSocket.isClosed()) {
                    String input = dataInputStream.readUTF();
                    String result = process(input);
                    dataOutputStream.writeUTF(result);
                    dataOutputStream.flush();
                }
                dataInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String process(String command) {
        if (command.startsWith("create")) {
            return controller.createNewUser(command);
        } else if (command.startsWith("login")) {
            return controller.loginUser(command);
        } else if (command.startsWith("profile change --nickname")) {
            return controller.changeNickname(command);
        } else if (command.startsWith("profile change --password")) {
            return controller.changPass(command);
        } else if (command.startsWith("getHelp")) {
        }
        return "";
    }
}
