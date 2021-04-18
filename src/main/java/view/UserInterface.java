package view;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private static Scanner scanner = new Scanner(System.in);

    public static void printResponse(Responses response) {
        System.out.println(response.getMessage());
    }
    public static void printResponse(String response) {
        System.out.println(response);
    }
    public static String getUserInput(){
        return scanner.nextLine();
    }
}
