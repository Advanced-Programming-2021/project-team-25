package view;


import controllers.Constants.Initialize;

public class MainView {

    public static void main(String[] args) {
        Initialize.init();
        API api = API.getInstance();
        api.run();
    }

}
