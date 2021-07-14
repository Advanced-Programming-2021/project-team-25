package view;
import controllers.menues.DuelMenuController;
import javafx.embed.swing.SwingFXUtils;
import controllers.ProgramController;
import javafx.scene.image.Image;
import models.Duelist;
import models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class SendReceiveData {
    public static String token = "";
    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static InputStream inputStream;
    public static ObjectInputStream objectInputStream;
    public static OutputStream outputStream;
    // create an object output stream from the output stream so we can send an object through it
    public static ObjectOutputStream objectOutputStream;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 7184);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            objectInputStream = new ObjectInputStream(inputStream);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    public static String sendReceiveData(String command){
        try {
            objectOutputStream.writeObject(command + " --token " + token);
            objectOutputStream.flush();
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
            objectOutputStream.writeObject("get currUser"+ " --token " + token);
            objectOutputStream.flush();
            ProgramController.currUser = (User) objectInputStream.readObject();
            User.getUsers().add(ProgramController.currUser);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String getCurrDuelistFromServer(String command){
        try {
            // get the input stream from the connected socket
            objectOutputStream.writeObject(command+ " --token " + token);
            objectOutputStream.flush();
            DuelMenuController.duelistRival = (Duelist) objectInputStream.readObject();
            return "success";
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void getUserIMage(User user){
        try {
            // get the input stream from the connected socket
            objectOutputStream.writeObject("getUserImage "+user.getUsername());
            dataOutputStream.flush();
            System.out.println("Reading: " + System.currentTimeMillis());

            byte[] sizeAr = new byte[4];
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

            //System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
            ImageIO.write(image, "png", new File(user.getUsername()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String sendImGToServer(BufferedImage image) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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


    public static Object getAllDecks (){
        try {
            dataOutputStream.writeUTF("get allDecks --token " + token);
            dataOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

}
