package view.menus;

import controllers.Battelfield.Battlefield;
import controllers.ProgramController;
import controllers.menues.DuelMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Window;
import models.Card;
import models.CardStufs.Type;
import models.Deck;
import models.Duelist;
import models.User;
import view.SendReceiveData;

import java.util.Objects;
import java.util.Optional;

public class ChangeMainAndSideCards {
    User user;
    Deck deck;
    private String loc = "Main";
    subStage myStage;
    DuelMenuController duelMenuController;
    String how;

    public ChangeMainAndSideCards (Deck deck, User user, DuelMenuController duelMenuController, String how){
        this.duelMenuController = duelMenuController;
        this.how = how;
        this.user = user;
        this.deck = deck;
        this.start();
    }

    public void start (){
        //Left and Right Pics!
        BorderPane borderPane = new BorderPane();
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/right.png")).toExternalForm(), 170, 650, false, false);
        ImageView imageView = new ImageView(image);
        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/left.png")).toExternalForm(), 170, 650, false, false);
        ImageView imageView1 = new ImageView(image1);
        borderPane.setRight(imageView);
        borderPane.setLeft(imageView1);

        borderPane.setTop(topBar());

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);

        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER);

        if(loc.equals("Main")) {
            for (int i = 0; i < deck.mainDeck.size(); i++)
                addCard(borderPane, gridPane, i, deck.mainDeck.get(i).getName());
        }
        else{
            for (int i = 0; i < deck.sideDeck.size(); i++)
                addCard(borderPane, gridPane, i, deck.sideDeck.get(i).getName());
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);
        Scene scene = new Scene(borderPane,800,650);

        Image img = new Image(Objects.requireNonNull(getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);
        scene.setCursor(cursor);
        myStage = new subStage("Transfer Card", scene);
    }


    private HBox topBar() {
        HBox hBox1 = new HBox();

        Label label = new Label(loc + "Deck");
        label.setFont(Font.font(20));
        label.setTextFill(Color.web("Black"));

        Button button1 = new Button("Main");
        button1.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button1.setOnAction(actionEvent -> {
            loc = "Main";
            myStage.stage.close();
            start();
        });

        Button button2 = new Button("Side");
        button2.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button2.setOnAction(actionEvent -> {
            loc = "Side";
            myStage.stage.close();
            start();
        });

        //Back Button
        Button button = new Button("Back");
        button.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (user == ProgramController.currUser){
                    myStage.stage.close();
                    duelMenuController.transferPermission(Objects.requireNonNull(DuelMenuController.duelistRival.getUser()), how);
                }
                else{
                    myStage.stage.close();
                    Duelist duelist1 = new Duelist(ProgramController.currUser);
                    Duelist duelist2 = new Duelist(Objects.requireNonNull(DuelMenuController.duelistRival.getUser()));
                    Battlefield battlefield;
                    if (how.equals("oneToTwo")) {
                        battlefield = new Battlefield(duelist1, duelist2, 2);
                        battlefield.isOneRound = false;
                    }
                    else{
                        battlefield = new Battlefield(duelist1, duelist2, 3);
                        battlefield.isOneRound = false;
                    }
                }
            }
        });

        hBox1.getChildren().addAll(label, button1, button2, button);
        hBox1.setSpacing(10);
        return hBox1;
    }


    private void addCard(BorderPane borderPane, GridPane gridPane, int i, String cardName) {
        if(loc.equals("Main")) {
            if (Card.allCards.get(cardName).getCardsType() == Type.MONSTER) {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + deck.mainDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        changeCardFunc(borderPane, cardName);
                    }
                });
                gridPane.add(imageView2, i % 3, i / 3);
            } else {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + deck.mainDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        changeCardFunc(borderPane, cardName);
                    }
                });
                gridPane.add(imageView2, i % 3, i / 3);
            }
        }
        else{
            if (Card.allCards.get(cardName).getCardsType() == Type.MONSTER) {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + deck.sideDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        changeCardFunc(borderPane, cardName);
                    }
                });
                gridPane.add(imageView2, i % 3, i / 3);
            } else {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + deck.sideDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        changeCardFunc(borderPane, cardName);
                    }
                });
                gridPane.add(imageView2, i % 3, i / 3);
            }
        }
    }


    public void changeCardFunc (BorderPane borderPane,  String cardName){
        HBox hBox1 = new HBox();
        Label label = new Label();
        if (loc.equals("Main")){
            label.setText("Are you Sure You Want To Transfer " + cardName + " from Main to Side?");
        }
        else{
            label.setText("Are you Sure You Want To Transfer " + cardName + " from Side to Main?");
        }
        label.setFont(Font.font(20));
        label.setTextFill(Color.web("Black"));

        Button button1 = new Button("Yes");
        button1.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (loc.equals("Main")){
                    Card card = null;
                    for (int i = 0; i<deck.mainDeck.size(); ++i){
                        if (deck.mainDeck.get(i).getName().equalsIgnoreCase(cardName)){
                            card = deck.mainDeck.get(i);
                            deck.mainDeck.remove(i);
                            //for server
                            SendReceiveData.sendReceiveData("removeCardFromMain --cardName "+ cardName +" --deckName " + deck.getDeckName());
                            break;
                        }
                    }
                    deck.sideDeck.add(card);
                    //for server
                    SendReceiveData.sendReceiveData("addCardToSide --cardName "+cardName+" --deckName "+deck.getDeckName());
                    showAlert(Alert.AlertType.INFORMATION, hBox1.getScene().getWindow(), "change card response", "Card was transferred from main to side successfully.");
                    myStage.stage.close();
                    start();
                }
                else{
                    Card card = null;
                    for (int i = 0; i<deck.sideDeck.size(); ++i){
                        if (deck.sideDeck.get(i).getName().equalsIgnoreCase(cardName)){
                            card = deck.sideDeck.get(i);
                            deck.sideDeck.remove(i);
                            //for server
                            SendReceiveData.sendReceiveData("removeCardFromSide --cardName "+ cardName +" --deckName " + deck.getDeckName());
                            break;
                        }
                    }
                    deck.mainDeck.add(card);
                    //for server
                    SendReceiveData.sendReceiveData("addCardToMain --cardName "+cardName+" --deckName "+deck.getDeckName());
                    showAlert(Alert.AlertType.INFORMATION, hBox1.getScene().getWindow(), "change card response", "Card was transferred from side to main successfully.");
                    myStage.stage.close();
                    start();
                }
            }
        });

        hBox1.getChildren().addAll(label,button1);
        hBox1.setSpacing(10);
        borderPane.setTop(hBox1);
    }



    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
    }
}
