package view.menus;

import controllers.Database.DataBase;
import controllers.menues.DeckMenu;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import models.User;
import view.Main;
import view.SendReceiveData;

import java.util.Objects;

import static models.Deck.allDecks;

public class ShowDeck {
    private final User user;
    private final Deck deck;
    private String loc = "Main";

    public ShowDeck(Deck deck, User user){
        this.user = user;
        this.deck = deck;
    }

    public void start() {
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
                addCard(borderPane, gridPane, vBox, i, deck.mainDeck.get(i).getName());
        }
        else{
            for (int i = 0; i < deck.sideDeck.size(); i++)
                addCard(borderPane, gridPane, vBox, i, deck.sideDeck.get(i).getName());
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);
        Scene scene = new Scene(borderPane,800,650);

        Image img = new Image(Objects.requireNonNull(getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);
        scene.setCursor(cursor);
        Main.stage.setScene(scene);
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
            start();
        });

        //Back Button
        Button button = new Button("Back");
        button.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button.setOnAction(actionEvent -> DeckView.getInstance(user).start());

        hBox1.getChildren().addAll(label, button1, button2, button);
        hBox1.setSpacing(10);
        return hBox1;
    }

    private void addCard(BorderPane borderPane, GridPane gridPane, VBox vBox, int i, String cardName) {
        if(loc.equals("Main")) {
            if (Card.allCards.get(cardName).getCardsType() == Type.MONSTER) {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + deck.mainDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(mouseEvent -> deleteFunc(borderPane, vBox, cardName));
                gridPane.add(imageView2, i % 3, i / 3);
            } else {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + deck.mainDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(mouseEvent -> deleteFunc(borderPane, vBox, cardName));
                gridPane.add(imageView2, i % 3, i / 3);
            }
        }
        else{
            if (Card.allCards.get(cardName).getCardsType() == Type.MONSTER) {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + deck.sideDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(mouseEvent -> deleteFunc(borderPane, vBox, cardName));
                gridPane.add(imageView2, i % 3, i / 3);
            } else {
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + deck.sideDeck.get(i).getName() + ".jpg")).toExternalForm(), 140, 200, false, false));
                imageView2.setOnMouseClicked(mouseEvent -> deleteFunc(borderPane, vBox, cardName));
                gridPane.add(imageView2, i % 3, i / 3);
            }
        }
    }

    private void deleteFunc(BorderPane borderPane, VBox vBox, String cardName) {
        HBox hBox1 = new HBox();

        Label label = new Label("Are you Sure You Want To Delete " + cardName + " From Deck " + deck.getDeckName());
        label.setFont(Font.font(20));
        label.setTextFill(Color.web("Black"));

        Button button1 = new Button("Yes");
        button1.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button1.setOnAction(actionEvent -> {
            if(loc.equals("Main")) {
                String resultServer =  SendReceiveData.sendReceiveData("removeCardFromMain --cardName "+ cardName +" --deckName " + deck.getDeckName());
                String resultClient = DeckMenu.getInstance(user).removeCard(cardName, deck.getDeckName());
                if(resultClient.contains("success") && resultServer!=null && resultServer.contains("success"))
                    showAlert(vBox.getScene().getWindow(), "Delete Card From Main", resultClient);
                else
                    showAlert(vBox.getScene().getWindow(), "Delete Card From Main", "failed! please try again");
            }
            else {
                String resultServer =  SendReceiveData.sendReceiveData("removeCardFromSide --cardName "+ cardName +" --deckName " + deck.getDeckName());
                String resultClient = DeckMenu.getInstance(user).removeCardFromSide(cardName,deck.getDeckName());
                if(resultClient.contains("success") && resultServer!=null && resultServer.contains("success"))
                    showAlert(vBox.getScene().getWindow(), "Delete Card From Side", resultClient);
                else
                    showAlert(vBox.getScene().getWindow(), "Delete Card From Side", "failed! please try again");
            }
            DataBase.storeDecks(allDecks);
            DataBase.saveTheUserList(User.getUsers());
            new DeckView(user).start();
        });

        hBox1.getChildren().addAll(label,button1);
        hBox1.setSpacing(10);
        borderPane.setTop(hBox1);
    }

    private void showAlert(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}
