package view;

import controllers.Constants.Initialize;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.menus.WelcomeMenu;

import java.io.File;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) {
        Main.stage = new Stage();

        //Music
        AudioClip audioClip = new AudioClip(getClass().getResource("/music/firstMusic.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
        audioClip.play();

        //load data from dataBase
        Initialize.init();

        //start from welcome menu
        new WelcomeMenu().start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}