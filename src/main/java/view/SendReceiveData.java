package view;
import javafx.embed.swing.SwingFXUtils;
import controllers.ProgramController;
import javafx.scene.image.Image;
import models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;


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
            User.getUsers().add(ProgramController.currUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void getUserIMage(User user){
        try {
            // get the input stream from the connected socket
            dataOutputStream.writeUTF("getUserImage "+user.getUsername());
            dataOutputStream.flush();
            System.out.println("Reading: " + System.currentTimeMillis());

            byte[] sizeAr = new byte[4];
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

            System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
            ImageIO.write(image, "png", new File(user.getUsername()+".png"));
        } catch (IOException e) {
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


    public static Object getAllUsersForScoreboard (){
        try {
            dataOutputStream.writeUTF("get allUsers --token " + token);
            dataOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

}
