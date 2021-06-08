package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Card;
import models.CardStufs.Type;
import models.User;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenu {
    public String username;

    public void start(Stage stage) throws Exception {
        //initialize nodes
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        //add buttons
        Button button = new Button("Show Card");
        button.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400)," +
                "linear-gradient(#ffef84, #f2ba44)," +
                "linear-gradient(#ffea6a, #efaa22)," +
                "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%)," +
                "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ShowCard showCard = new ShowCard();
                    showCard.username = username;
                    showCard.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.add(button, 0, 0);





        Button button2 = new Button("Show All Cards");
        button2.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400)," +
                "linear-gradient(#ffef84, #f2ba44)," +
                "linear-gradient(#ffea6a, #efaa22)," +
                "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%)," +
                "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ShowAllCards showAllCards = new ShowAllCards();
                showAllCards.username = username;
                try {
                    showAllCards.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.add(button2, 0, 2);


        Button button3 = new Button("Back");
        button3.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400)," +
                "linear-gradient(#ffef84, #f2ba44)," +
                "linear-gradient(#ffea6a, #efaa22)," +
                "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%)," +
                "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new MainMenu().start(stage);
            }
        });
        gridPane.add(button3, 0, 3);



        //add nodes to vbox
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().add(gridPane);


        // create a image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/background.png")).toExternalForm());
        double width = image.getWidth();
        double height = image.getHeight();
        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        // create Background
        Background background = new Background(backgroundimage);
        vBox.setBackground(background);


        //show stage
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Shop Menu");
        stage.centerOnScreen();
        stage.show();


    }
    //for graphic i should write buyCard function again
    public static String buyCardForGraphic (String command){
        Pattern pattern = Pattern.compile("^buy card --username (.+)? --card (.+)?$");
        Matcher matcher = pattern.matcher(command);

        String username = "";
        String cardsName = "";

        if (matcher.find()){
            username = matcher.group(1);
            cardsName = matcher.group(2);
        }

        User user = User.getUserByUsername(username);
        if (Card.allCards.get(cardsName).getPrice() > user.getMoney())
            return "not enough money";
        else{
            ArrayList<String> temp = user.getCardsBought();
            temp.add(cardsName);
            user.setCardsBought(temp);
            int money = user.getMoney();
            money -= Card.allCards.get(cardsName).getPrice();
            user.setMoney(money);
            return "card bought successfully";
        }
    }

    //for graphic i should write showAllCards function again
    public static  ArrayList<String> showAllCardsForGraphic (){
        ArrayList<String> allCards = new ArrayList<>();
        for (Map.Entry<String, Card> entry: Card.allCards.entrySet()){
            String key = entry.getKey();
            allCards.add(key);
        }

        Collections.sort(allCards);

        return allCards;
    }
}
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
class ShowCard{
    public String username;

    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);


        Label label = new Label("Enter the cards name:");
        label.setFont(Font.font(20));
        label.setTextFill(Color.web("white"));


        TextField textField = new TextField();
        HBox hBox = new HBox(textField);
        hBox.setAlignment(Pos.CENTER);
        textField.setPrefColumnCount(15);


        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);


        Button button = new Button("Search");
        button.setStyle("-fx-background-color: #000000," +
                "linear-gradient(#7ebcea, #2f4b8f)," +
                "linear-gradient(#426ab7, #263e75)," +
                "linear-gradient(#395cab, #223768);" +
                "-fx-background-insets: 0,1,2,3;" +
                "-fx-background-radius: 3,2,2,2;" +
                "-fx-padding: 12 30 12 30; -fx-text-fill: white;" +
                "-fx-font-size: 12px;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String cardsName = textField.getText();
                if (Card.allCards.containsKey(cardsName)){
                    if (Card.allCards.get(cardsName).getCardsType() == Type.MONSTER){
                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER);

                        Label label1 = new Label("Price:" + Card.allCards.get(cardsName).getPrice());
                        label1.setFont(Font.font(20));
                        label1.setTextFill(Color.web("white"));

                        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/Monsters/" + cardsName + ".jpg")).toExternalForm());
                        ImageView imageView = new ImageView(image2);

                        vBox.getChildren().addAll(label1, imageView);

                        HBox hBox1 = new HBox();
                        hBox1.setAlignment(Pos.CENTER);

                        Button button1 = new Button("Buy");
                        button1.setStyle("-fx-background-color: #ecebe9," +
                                "rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740)," +
                                "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%)," +
                                "linear-gradient(#f6ebbe, #e6c34d);" +
                                "-fx-background-insets: 0,9 9 8 9,9,10,11;" +
                                "-fx-background-radius: 50; -fx-padding: 15 30 15 30;" +
                                "-fx-font-family: \"Helvetica\"; -fx-font-size: 18px;" +
                                "-fx-text-fill: #311c09;" +
                                "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
                        button1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + cardsName);
                                showAlert(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "buy card response", response);
                            }
                        });
                        hBox1.getChildren().add(button1);


                        Button button2 = new Button("Back");
                        button2.setStyle("-fx-background-color: #ecebe9," +
                                "rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740)," +
                                "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%)," +
                                "linear-gradient(#f6ebbe, #e6c34d);" +
                                "-fx-background-insets: 0,9 9 8 9,9,10,11;" +
                                "-fx-background-radius: 50; -fx-padding: 15 30 15 30;" +
                                "-fx-font-family: \"Helvetica\"; -fx-font-size: 18px;" +
                                "-fx-text-fill: #311c09;" +
                                "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
                        button2.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                ShowCard showCard = new ShowCard();
                                showCard.username = username;
                                try {
                                    showCard.start(stage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        hBox1.getChildren().add(button2);

                        vBox.getChildren().add(hBox1);

                        // create a image
                        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/background.png")).toExternalForm());
                        // create a background image
                        BackgroundImage backgroundimage = new BackgroundImage(image,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        // create Background
                        Background background = new Background(backgroundimage);
                        vBox.setBackground(background);

                        Scene scene = new Scene(vBox);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else{
                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER);

                        Label label1 = new Label("Price:" + Card.allCards.get(cardsName).getPrice());
                        label1.setFont(Font.font(20));
                        label1.setTextFill(Color.web("white"));

                        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/SpellTrap/" + cardsName + ".jpg")).toExternalForm());
                        ImageView imageView = new ImageView(image2);

                        vBox.getChildren().addAll(label1, imageView);

                        HBox hBox1 = new HBox();
                        hBox1.setAlignment(Pos.CENTER);

                        Button button1 = new Button("Buy");
                        button1.setStyle("-fx-background-color: #ecebe9," +
                                "rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740)," +
                                "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%)," +
                                "linear-gradient(#f6ebbe, #e6c34d);" +
                                "-fx-background-insets: 0,9 9 8 9,9,10,11;" +
                                "-fx-background-radius: 50; -fx-padding: 15 30 15 30;" +
                                "-fx-font-family: \"Helvetica\"; -fx-font-size: 18px;" +
                                "-fx-text-fill: #311c09;" +
                                "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
                        button1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + cardsName);
                                showAlert(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "buy card response", response);
                            }
                        });
                        hBox1.getChildren().add(button1);


                        Button button2 = new Button("Back");
                        button2.setStyle("-fx-background-color: #ecebe9," +
                                "rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740)," +
                                "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%)," +
                                "linear-gradient(#f6ebbe, #e6c34d);" +
                                "-fx-background-insets: 0,9 9 8 9,9,10,11;" +
                                "-fx-background-radius: 50; -fx-padding: 15 30 15 30;" +
                                "-fx-font-family: \"Helvetica\"; -fx-font-size: 18px;" +
                                "-fx-text-fill: #311c09;" +
                                "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
                        button2.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                ShowCard showCard = new ShowCard();
                                showCard.username = username;
                                try {
                                    showCard.start(stage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        hBox1.getChildren().add(button2);

                        vBox.getChildren().add(hBox1);

                        // create a image
                        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/background.png")).toExternalForm());
                        // create a background image
                        BackgroundImage backgroundimage = new BackgroundImage(image,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        // create Background
                        Background background = new Background(backgroundimage);
                        vBox.setBackground(background);

                        Scene scene = new Scene(vBox);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
                else {
                    showAlert(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "Invalid Name", "Please enter a valid name.");
                }
            }
        });


        Button button1 = new Button("Back");
        button1.setStyle("-fx-background-color: #000000," +
                "linear-gradient(#7ebcea, #2f4b8f)," +
                "linear-gradient(#426ab7, #263e75)," +
                "linear-gradient(#395cab, #223768);" +
                "-fx-background-insets: 0,1,2,3;" +
                "-fx-background-radius: 3,2,2,2;" +
                "-fx-padding: 12 30 12 30; -fx-text-fill: white;" +
                "-fx-font-size: 12px;");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ShopMenu shopMenu = new ShopMenu();
                shopMenu.username = username;
                try {
                    shopMenu.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        hBox1.getChildren().addAll(button, button1);



        vBox.getChildren().addAll(label, hBox, hBox1);


        // create a image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/background.png")).toExternalForm());
        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        // create Background
        Background background = new Background(backgroundimage);
        vBox.setBackground(background);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        //else if(result.get() == ButtonType.OK) new app().start(this.stage);
    }
}
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
class ShowAllCards{
    public String username;

    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/right.png")).toExternalForm(), 170, 700, false, false);
        ImageView imageView = new ImageView(image);

        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/left.png")).toExternalForm(), 170, 700, false, false);
        ImageView imageView1 = new ImageView(image1);


        borderPane.setRight(imageView);
        borderPane.setLeft(imageView1);


        Button button = new Button("Back");
        button.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                "-fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ShopMenu shopMenu = new ShopMenu();
                shopMenu.username = username;
                try {
                    shopMenu.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        button.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(button);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);





        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER);

        ArrayList<String> allCards = ShopMenu.showAllCardsForGraphic();
        for (int i = 0; i<allCards.size(); ++i){
            String name = allCards.get(i);
            if (Card.allCards.get(allCards.get(i)).getCardsType() == Type.MONSTER){
                Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/Monsters/" + allCards.get(i) + ".jpg")).toExternalForm(), 275, 275, false, false);
                ImageView imageView2 = new ImageView(image2);
                imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        HBox hBox1 = new HBox();
                        Label label = new Label("You have " + Objects.requireNonNull(User.getUserByUsername(username)).getMoney() + "$ and the " +
                                "price of this card is " + Card.allCards.get(name).getPrice() + "$");
                        label.setFont(Font.font(20));
                        label.setTextFill(Color.web("Black"));
                        hBox1.getChildren().add(label);

                        Button button1 = new Button("Buy");
                        button1.setStyle("-fx-background-color: #c3c4c4," +
                                "linear-gradient(#d6d6d6 50%, white 100%)," +
                                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
                        button1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + name);
                                showAlert(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "buy card response", response);
                                ShowAllCards showAllCards = new ShowAllCards();
                                showAllCards.username = username;
                                try {
                                    showAllCards.start(stage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        hBox1.getChildren().add(button1);

                        hBox1.setSpacing(10);

                        borderPane.setTop(hBox1);
                    }
                });
                gridPane.add(imageView2, i%3, i/3);
            }
            else{
                if (allCards.get(i).equals("Magic Jammer")){
                    Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/SpellTrap/" + allCards.get(i) + ".png")).toExternalForm(), 275, 275, false, false);
                    ImageView imageView2 = new ImageView(image2);
                    imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            HBox hBox1 = new HBox();
                            Label label = new Label("You have " + Objects.requireNonNull(User.getUserByUsername(username)).getMoney() + "$ and the " +
                                    "price of this card is " + Card.allCards.get(name).getPrice() + "$");
                            label.setFont(Font.font(20));
                            label.setTextFill(Color.web("Black"));
                            hBox1.getChildren().add(label);

                            Button button1 = new Button("Buy");
                            button1.setStyle("-fx-background-color: #c3c4c4," +
                                    "linear-gradient(#d6d6d6 50%, white 100%)," +
                                    "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                                    "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                                    "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
                            button1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + name);
                                    showAlert(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "buy card response", response);
                                    ShowAllCards showAllCards = new ShowAllCards();
                                    showAllCards.username = username;
                                    try {
                                        showAllCards.start(stage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            hBox1.getChildren().add(button1);

                            hBox1.setSpacing(10);

                            borderPane.setTop(hBox1);
                        }
                    });
                    gridPane.add(imageView2, i%3, i/3);
                }
                else{
                    Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/shop/SpellTrap/" + allCards.get(i) + ".jpg")).toExternalForm(), 275, 275, false, false);
                    ImageView imageView2 = new ImageView(image2);
                    imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            HBox hBox1 = new HBox();
                            Label label = new Label("You have " + Objects.requireNonNull(User.getUserByUsername(username)).getMoney() + "$ and the " +
                                    "price of this card is " + Card.allCards.get(name).getPrice() + "$");
                            label.setFont(Font.font(20));
                            label.setTextFill(Color.web("Black"));
                            hBox1.getChildren().add(label);

                            Button button1 = new Button("Buy");
                            button1.setStyle("-fx-background-color: #c3c4c4," +
                                    "linear-gradient(#d6d6d6 50%, white 100%)," +
                                    "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                                    "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                                    "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
                            button1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + name);
                                    showAlert(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "buy card response", response);
                                    ShowAllCards showAllCards = new ShowAllCards();
                                    showAllCards.username = username;
                                    try {
                                        showAllCards.start(stage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            hBox1.getChildren().add(button1);

                            hBox1.setSpacing(10);

                            borderPane.setTop(hBox1);
                        }
                    });
                    gridPane.add(imageView2, i%3, i/3);
                }
            }
        }



        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);






        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(1200);
        stage.centerOnScreen();
        stage.show();
    }


    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        //else if(result.get() == ButtonType.OK) new app().start(this.stage);
    }
}
