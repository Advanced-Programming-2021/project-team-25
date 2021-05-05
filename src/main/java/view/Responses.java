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
    SUCCESS_CARD_BUY("card bought successfully"),
    NOT_SUPPORTED_ROUNDS("number of rounds is not supported"),
    INVALID_CARD_SELECTION_ADDRESS("invalid selection"),
    NO_CARD_SELECTED_ERROR("no card is selected yet"),
    NOT_ENOUGH_SPACE_IN_HAND("no enough space in hand"),
    NOT_ENOUGH_SPACE_IN_MONSTER_ZONE("no enough space in monster zone");
    private final String message;

    Responses(String errorMessage) {
        message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
