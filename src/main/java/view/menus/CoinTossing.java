package view.menus;

import controllers.ProgramController;
import controllers.menues.DuelMenuController;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.Main;
import view.UserInterface;

import java.util.Objects;
import java.util.Random;

public class CoinTossing{

    private int tossSide = -1;
    private final ImageView coinView = new ImageView();
    private int round;
    private String duelistName;
    private MediaView mediaView;

    public void start(int round, String duelistName) {
        this.round = round;
        this.duelistName = duelistName;


        //adding video
        String path = Objects.requireNonNull(getClass().getResource("/music/coin.mp4")).toExternalForm();

        //Instantiating Media class
        Media media = new Media(path);

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //Instantiating MediaView class
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(500);
        mediaView.setFitWidth(700);

        //by setting this property to true, the Video will be played
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);



        VBox vBox = new VBox();
        addUIControls(vBox);

        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox,800,650);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);
        scene.setCursor(cursor);

        String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
        scene.getStylesheets().add(style);

        Main.stage.setScene(scene);
    }

    private void addUIControls(VBox vbox) {
        Text welcomeText = new Text("Let's Duel");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));

        tossButtonAction();

//        Button loginBtn = new Button("toss a coin");
//        loginBtn.setOnAction(actionEvent -> {
//            AudioClip audioClip = new AudioClip(getClass().getResource("/music/COIN.mp3").toExternalForm());
//            audioClip.play();
//            tossButtonAction();
//        });

        Button duelBtn = new Button("Start");
        duelBtn.setOnAction(actionEvent -> duel());

        vbox.getChildren().addAll(welcomeText, mediaView, duelBtn);
    }

    public void tossButtonAction() {
        tossSide = generateRandomNumber();
        Image image;

        // if tossSide is 0, coin has landed on HEADS
        switch (tossSide) {
            case 0 -> {
                image = new Image(Objects.requireNonNull(this.getClass().getResource("tail.jpg")).toExternalForm());
                coinView.setImage(image);
            }
            case 1 -> {
                image = new Image(Objects.requireNonNull(this.getClass().getResource("head.jpg")).toExternalForm());
                coinView.setImage(image);
            }
            default -> tossSide = generateRandomNumber();
        }
    }

    public int generateRandomNumber() {
        Random rand = new Random();
        return(rand.nextInt(2));
    }

    private void duel(){
        if(tossSide == -1) UserInterface.printResponse("You have to Toss First");

        else{
//            DuelMenuController duelMenuController = DuelMenuController.getInstance(ProgramController.currUser);
//            if (round == 1) duelMenuController.oneRoundDuel(duelistName);
//            else duelMenuController.threeRoundDuel(duelistName);
        }
    }
}


