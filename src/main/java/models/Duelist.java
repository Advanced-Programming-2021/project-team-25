package models;

public class Duelist extends User{

    private Field field;
    private final int LP = 8000;
    private boolean hasPutMonster = false;
    public Duelist(String username, String password, String nickname) {
        super(username, password, nickname);
    }
}
