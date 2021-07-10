package view;

import javax.swing.*;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printResponse(Responses response) {
        System.out.println(response.getMessage());
    }
    public static String printResponse(String type,String response) {
        return type+" description=\""+response+"\"";
    }
    public static String getUserInput(){
        return JOptionPane.showInputDialog("input");
    }

    public static void printResponse(int input) {
        JOptionPane.showMessageDialog(null,input);
    }
}
