package view;


import controllers.Constants.Initialize;
import models.Deck;
import models.User;

public class MainView {

    public static void main(String[] args) {
        Initialize.init();
        //
        System.out.println(User.getUsers());
        System.out.println(Deck.allDecks);
        //
        API api = API.getInstance();
        api.run();
    }

}
