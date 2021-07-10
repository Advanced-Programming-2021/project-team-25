package Server.view;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class API extends Thread{

    private static API singleToneClass = null;

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
        String[] parts = command.split(" ");
        if (command.startsWith("register")) {

        } else if (command.startsWith("login")) {

        } else if (command.startsWith("getTasks")) {

        } else if (command.startsWith("addTask")) {

        } else if (command.startsWith("getHelp")) {
        }
        return "";
    }
}
