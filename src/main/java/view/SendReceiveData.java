package view;

import models.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class SendReceiveData {
    public static String token = "";

    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 7185);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    public static String sendReceiveData(String command){
        try {
            dataOutputStream.writeUTF(command + "--token" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }
    public static User getCurrUserFromServer(){
        try {
            dataOutputStream.writeUTF("get currUser --token " + token);
            dataOutputStream.flush();
            Gson gson = new Gson();
            return gson.fromJson(dataInputStream.readUTF(),User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
