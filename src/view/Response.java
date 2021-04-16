package view;

import java.util.HashMap;

public class Response {
    public static String invalidFormat = "invalid command";
    private static HashMap<String,String> errors = new HashMap<>();
    private static HashMap<String,String> successes = new HashMap<>();

    public Response() {
        initializeErrorsAndSuccesses();

    }

    private void initializeErrorsAndSuccesses() {
        errors.put("LOGIN_FIRST_ERROR","please login first");
        errors.put("USER_PASS_NOT_MATCHED","Username and password didn't match!");

        successes.put("LOGIN_SUCCESS","user logged in successfully!");
        successes.put("LOGOUT_SUCCESS","user logged out successfully!");
    }
}
