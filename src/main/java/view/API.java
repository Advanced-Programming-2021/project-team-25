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
    Socket socket;
    public static API getInstance (){
        if (singleToneClass == null) singleToneClass = new API();
        return singleToneClass;

    }
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7185);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                this.socket = socket;
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

    public Socket getSocket() {
        return socket;
    }
}
