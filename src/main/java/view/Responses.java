package view;

public enum Responses {

    INVALID_COMMAND("invalid command"),
    LOGIN_FIRST_ERROR("please login first"),
    USER_PASS_NOT_MATCHED_ERROR("Username and password didn't match!"),
    USER_CREATE_SUCCESS("user created successfully"),
    LOGIN_SUCCESS("user logged in successfully!"),
    LOGOUT_SUCCESS("user logged out successfully!"),
    DECK_CREATE_SUCCESS("deck created successfully!"),
    DECK_DELETE_SUCCESS("deck deleted successfully!"),
    DECK_ACTIVE_SUCCESS("deck activated successfully!");

    private final String message;

    Responses(String errorMessage) {
        message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

}
