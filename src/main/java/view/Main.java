package view;

import controllers.Constants.Initialize;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.menus.WelcomeMenu;

import java.io.File;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Main.stage = new Stage();

        //Music
//        String path = "Nick-Cave-Red-Right-Hand.mp3";
//        Media media = new Media(new File(path).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);

        //load data from dataBase
        Initialize.init();
        //start from welcome menu
        new WelcomeMenu().start();
    }
}