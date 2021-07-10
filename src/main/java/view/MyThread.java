package view;

import controllers.menues.MainMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyThread extends Thread{

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;
    ServerSocket serverSocket;

    public MyThread(ServerSocket serverSocket, Socket socket) {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
                String result = API.getInstance().process(input);
                if (result.equals("")) break;
                dataOutputStream.writeUTF(result);
                dataOutputStream.flush();
            }catch (EOFException eofException){
                return;
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            processInputData(dataInputStream, dataOutputStream);
            dataInputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}