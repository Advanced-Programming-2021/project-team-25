package view;

import java.util.HashMap;

public class Response {
    public static String invalidFormat = "invalid command";
    public static HashMap<String,String> errors = new HashMap<>();
    public static HashMap<String,String> successes = new HashMap<>();

    public static void printResponse(Responses response)
    {
        System.out.println(response.getMessage());
    }
}
