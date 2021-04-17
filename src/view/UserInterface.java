package view;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    public static String invalidFormat = "invalid command";
    private static final Scanner scanner = new Scanner(System.in);

    public static void printResponse(Responses response) {
        System.out.println(response.getMessage());
    }
    public static String getUserInput(){
        return scanner.nextLine();
    }
}
