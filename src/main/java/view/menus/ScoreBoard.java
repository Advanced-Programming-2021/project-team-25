package view.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import models.User;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.CreateGrid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ScoreBoard {
    public void start(Stage primaryStage) {
        StackPane stackPane = new StackPane();

        //adding video
        String path = Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/vv.mp4")).toExternalForm();

        //Instantiating Media class
        Media media = new Media(path);

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //Instantiating MediaView class
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(800);
        mediaView.setFitWidth(1000);

        //by setting this property to true, the Video will be played
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);



        stackPane.getChildren().add(mediaView);



        ArrayList<Integer> ranks = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();



        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Text welcomeText = new Text("ScoreBoard");
        welcomeText.setFont(Font.font("tahoma", FontWeight.BOLD ,30));
        welcomeText.setFill(Paint.valueOf("orange"));
        vBox.getChildren().add(welcomeText);

        Button back = new Button("Back");
        back.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                "linear-gradient(#020b02, #3a3a3a), linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%)," +
                "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%)," +
                "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);" +
                "-fx-background-insets: 0,1,4,5,6; -fx-background-radius: 9,8,5,4,3;" +
                "-fx-padding: 15 30 15 30; -fx-font-family: \"Helvetica\";" +
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");


        ArrayList<User> users = User.getUsers();
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getUsername);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank = 1 ,j = 1;
        //iterate users
        for (int i = users.size() - 1 ; i >= 0 ; i-- ) {
            ranks.add(rank);
            names.add(users.get(i).getUsername());
            scores.add(users.get(i).getScore());
            if(i != 0 && users.get(i).getScore() > users.get(i-1).getScore() ) rank++;
        }


        PieChart pieChart = new PieChart();

        int sum = 0;
        int max = -1;

        for (int i = 0; i<names.size(); ++i){
            if (i == 10) break;
            sum += ranks.get(i);
            if (ranks.get(i) > max)
                max = ranks.get(i);
        }

        max += 1;

        for (int i = 0; i<names.size(); ++i){
            if (i == 10)
                break;
            pieChart.getData().add(new PieChart.Data(names.get(i), (360/sum)*(max-ranks.get(i))));
        }





        vBox.getChildren().add(pieChart);
        vBox.getChildren().add(back);

        stackPane.getChildren().add(vBox);

        Scene scene = new Scene(stackPane);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mediaPlayer.stop();
                new MainMenu().start();
            }
        });
        primaryStage.setScene(scene);
    }
}
