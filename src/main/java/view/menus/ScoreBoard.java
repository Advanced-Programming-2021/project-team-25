package view.menus;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import models.User;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.SendReceiveData;

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
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mediaPlayer.stop();
                new MainMenu().start();
            }
        });


        Button showAllBtn = new Button("Show everyone");
        showAllBtn.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                "linear-gradient(#020b02, #3a3a3a), linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%)," +
                "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%)," +
                "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);" +
                "-fx-background-insets: 0,1,4,5,6; -fx-background-radius: 9,8,5,4,3;" +
                "-fx-padding: 15 30 15 30; -fx-font-family: \"Helvetica\";" +
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
        showAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mediaPlayer.stop();
                new ShowAllUsers().start(primaryStage);
            }
        });









        ArrayList<User> users = (ArrayList<User>) SendReceiveData.getAllUsersForScoreboard();
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getUsername);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank = 1;
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
            pieChart.getData().add(new PieChart.Data(names.get(i) + ":" + scores.get(i), (360/sum)*(max-ranks.get(i))));
        }





        vBox.getChildren().add(pieChart);


        vBox.getChildren().add(back);
        vBox.getChildren().add(showAllBtn);

        stackPane.getChildren().add(vBox);

        Scene scene = new Scene(stackPane);

        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
    }
}
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
class ShowAllUsers {
    public void start (Stage stage){
        BorderPane borderPane = new BorderPane();

        HBox hBoxTop = new HBox();
        Image up = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/up.png")).toExternalForm(), 300, 150, false, false);
        ImageView upView = new ImageView(up);
        hBoxTop.getChildren().add(upView);
        hBoxTop.setAlignment(Pos.CENTER);


        Image left = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/left.png")).toExternalForm(), 170, 500, false, false);
        ImageView leftView = new ImageView(left);


        Image right = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/right.png")).toExternalForm(), 170, 500, false, false);
        ImageView rightView = new ImageView(right);

        borderPane.setLeft(leftView);
        borderPane.setRight(rightView);
        borderPane.setTop(hBoxTop);



        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().add(gridPane);
        vBox.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane(vBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(stackPane);


        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));


        ArrayList<Integer> ranks = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        ArrayList<User> users = (ArrayList<User>) SendReceiveData.getAllUsersForScoreboard();
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getUsername);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank = 1;
        //iterate users
        for (int i = users.size() - 1 ; i >= 0 ; i-- ) {
            ranks.add(rank);
            names.add(users.get(i).getUsername());
            scores.add(users.get(i).getScore());
            if(i != 0 && users.get(i).getScore() > users.get(i-1).getScore() ) rank++;
        }

        for (int i = 0; i<names.size(); ++i){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(4);
            Image image = null;
            ImageView imageView = null;
            if (ranks.get(i) == 1){
                image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/gold.png")).toExternalForm());
                imageView = new ImageView(image);
            }
            else if (ranks.get(i) == 2){
                image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/silver.png")).toExternalForm());
                imageView = new ImageView(image);
            }
            else if (ranks.get(i) == 3){
                image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/scoreboard/bronze.png")).toExternalForm());
                imageView = new ImageView(image);
            }
            hBox.getChildren().add(imageView);

            Button buttonRank = new Button(String.valueOf(ranks.get(i)));
            if (i >= 20) {
                buttonRank.setStyle("-fx-background-color: #090a0c," +
                        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                        "linear-gradient(#20262b, #191d22)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
            }
            else{
                buttonRank.setStyle("-fx-background-color: #FFA500," +
                        "linear-gradient(#FFA500 0%, #FFA500 20%, #FFA500 100%)," +
                        "linear-gradient(#FFA500, #FFA500)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
            }
            hBox.getChildren().add(buttonRank);


            Button buttonName = new Button(names.get(i));
            if (i >= 20) {
                buttonName.setStyle("-fx-background-color: #090a0c," +
                        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                        "linear-gradient(#20262b, #191d22)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
            }
            else {
                buttonName.setStyle("-fx-background-color: #FFA500," +
                        "linear-gradient(#FFA500 0%, #FFA500 20%, #FFA500 100%)," +
                        "linear-gradient(#FFA500, #FFA500)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
            }
            hBox.getChildren().add(buttonName);


            Button buttonScore = new Button(String.valueOf(scores.get(i)));
            if (i >= 20) {
                buttonScore.setStyle("-fx-background-color: #090a0c," +
                        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                        "linear-gradient(#20262b, #191d22)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
            }
            else{
                buttonScore.setStyle("-fx-background-color: #FFA500," +
                        "linear-gradient(#FFA500 0%, #FFA500 20%, #FFA500 100%)," +
                        "linear-gradient(#FFA500, #FFA500)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5; -fx-background-insets: 0,1,2,0; -fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: \"Arial\"; -fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px; -fx-padding: 10 20 10 20;");
            }
            hBox.getChildren().add(buttonScore);

            gridPane.add(hBox, 0, i);
        }
        borderPane.setCenter(scrollPane);



        HBox hBoxExit = new HBox();
        Button exit = new Button("Back");
        exit.setStyle("-fx-background-color: #ecebe9," +
                "rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740)," +
                "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%)," +
                "linear-gradient(#f6ebbe, #e6c34d);" +
                "-fx-background-insets: 0,9 9 8 9,9,10,11;" +
                "-fx-background-radius: 50; -fx-padding: 15 30 15 30;" +
                "-fx-font-family: \"Helvetica\"; -fx-font-size: 18px;" +
                "-fx-text-fill: #311c09;" +
                "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ScoreBoard().start(stage);
            }
        });
        hBoxExit.getChildren().add(exit);
        hBoxExit.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBoxExit);




        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(800);


    }
}

