package view;


import controllers.Regex;
import models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;

public class MyThread extends Thread{

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;
    ServerSocket serverSocket;
    // get the output stream from the socket.
    OutputStream outputStream;
    // create an object output stream from the output stream so we can send an object through it
    ObjectOutputStream objectOutputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    public static boolean wantToSendObj = false;
    public static User user;

    public MyThread(ServerSocket serverSocket, Socket socket) {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            this.socket = socket;
            this.serverSocket = serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            processInputData(dataInputStream, dataOutputStream);
            dataInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processInputData(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        while (true) {
            Object input = null;
            try {
                //download means: serve download image and client upload that!
                //as upload image by server
                try {
                    input = objectInputStream.readObject();
                }catch (Exception e){
                    DownloadImage(input);
                }

                Object result = null;
                if(input instanceof String)
                    result = API.getInstance().process(input);
                else
                    result = DownloadImage(input);
                if(result instanceof BufferedImage){
                    uploadImage((BufferedImage) result);
                }else{
                    objectOutputStream.writeObject(result);
                    objectOutputStream.flush();
                }

            }catch (EOFException eofException){
                return;
            }catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void uploadImage(BufferedImage result) throws IOException {
        BufferedImage image = result;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());
    }

    private Object DownloadImage(Object input){

        System.out.println("Reading: " + System.currentTimeMillis());

        byte[] sizeAr = new byte[4];
        try {
            inputStream = socket.getInputStream();
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

            System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
            UUID imageName = UUID.randomUUID();
            ImageIO.write(image, "png", new File(imageName+".png"));
            return "success description=\""+imageName+"\"";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }



    }
}