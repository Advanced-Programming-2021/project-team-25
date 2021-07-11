package view;


import models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyThread extends Thread{

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;
    ServerSocket serverSocket;
    // get the output stream from the socket.
    OutputStream outputStream;
    // create an object output stream from the output stream so we can send an object through it
    ObjectOutputStream objectOutputStream;
    public static boolean wantToSendObj = false;
    public static User user;
    public MyThread(ServerSocket serverSocket, Socket socket) {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
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
            String input = null;
            try {
                input = dataInputStream.readUTF();
                Object result = API.getInstance().process(input);
                objectOutputStream.writeObject(result);
                objectOutputStream.flush();

            }catch (EOFException eofException){
                return;
            }catch (IOException e) {
                System.out.println("Client disconnected");
                break;
            }
        }
    }
}