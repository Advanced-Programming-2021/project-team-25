package view;

import java.util.Scanner;

public class UserInterface {
    public static Scanner scanner = new Scanner(System.in);

    public static void printResponse(Responses response) {
        System.out.println(response.getMessage());
    }
    public static void printResponse(String response) { System.out.println(response); }
    public static void printResponse(int input) {
        System.out.println(input);
    }
    public static String getUserInput(){
        return scanner.nextLine();
    }
}
