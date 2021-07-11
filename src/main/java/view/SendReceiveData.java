package view;

import controllers.ProgramController;
import models.User;

import java.io.*;
import java.net.Socket;


public class SendReceiveData {
    public static String token = "";
    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static InputStream inputStream;
    public static ObjectInputStream objectInputStream;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 7184);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    public static String sendReceiveData(String command){
        try {
            dataOutputStream.writeUTF(command + " --token " + token);
            dataOutputStream.flush();
            String ss = (String) objectInputStream.readObject();
            return ss;
        } catch (IOException | ClassNotFoundException x) {
            x.printStackTrace();
            return null;
        }
    }
    public static void getCurrUserFromServer(){
        try {
            // get the input stream from the connected socket
            dataOutputStream.writeUTF("get currUser"+ " --token " + token);
            dataOutputStream.flush();
            ProgramController.currUser = (User) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Object getDecksOfUser(){
        try {
            dataOutputStream.writeUTF("get currDecks --token " + token);
            dataOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
