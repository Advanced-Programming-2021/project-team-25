package view;

public enum Responses {

    INVALID_COMMAND("invalid command"),
    LOGIN_FIRST_ERROR("please login first"),
    NOT_POSSIBLE_NAVIGATION("menu navigation is not possible"),
    USER_PASS_NOT_MATCHED_ERROR("Username and password didn't match!"),
    USER_CREATE_SUCCESS("user created successfully"),
    LOGIN_SUCCESS("user logged in successfully!"),
    LOGOUT_SUCCESS("user logged out successfully!"),
    DECK_CREATE_SUCCESS("deck created successfully!"),
    DECK_DELETE_SUCCESS("deck deleted successfully!"),
    DECK_ACTIVE_SUCCESS("deck activated successfully!"),
    NO_CARD_WITH_THIS_NAME("there is no card with this name"),
    NOT_ENOUGH_MONEY("not enough money"),
    SUCCESS_CARD_BUY("card bought successfully");

    private final String message;

    Responses(String errorMessage) {
        message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
