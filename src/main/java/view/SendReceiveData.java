package view;

import controllers.ProgramController;
import models.User;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import view.menus.MainMenu;
import view.menus.ProfileMenu;

public class SendReceiveData {
    public static String token = "";
    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

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
            dataOutputStream.writeUTF(command + " --token " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }
    public static void getCurrUserFromServer(){
        try {
            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ProgramController.currUser = (User) objectInputStream.readObject();
//            dataOutputStream.writeUTF("get currUser --token " + token);
//            dataOutputStream.flush();
//            return new Gson().fromJson(dataInputStream.readUTF(),User.class);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String getDecksOfUser(){
        try {
            dataOutputStream.writeUTF("get currDecks --token " + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
