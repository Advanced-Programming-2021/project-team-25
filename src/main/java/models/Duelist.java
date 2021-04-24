package models;

public class Duelist {

    private User user;
    private Field field;
    private final int LP = 8000;
    private boolean hasPutMonster = false;
    public Duelist(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
