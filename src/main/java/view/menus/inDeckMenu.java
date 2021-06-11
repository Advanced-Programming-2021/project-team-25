package view.menus;

import javafx.geometry.Pos;
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

import java.util.Objects;

public class inDeckMenu {
    private final User user;
    private final Deck deck;

    public inDeckMenu(Deck deck, User user){
        this.user = user;
        this.deck = deck;
    }

    public void start() {

        //Left and Right Pics!
        BorderPane borderPane = new BorderPane();
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/right.png")).toExternalForm(), 200, 800, false, false);
        ImageView imageView = new ImageView(image);
        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/left.png")).toExternalForm(), 200, 800, false, false);
        ImageView imageView1 = new ImageView(image1);
        borderPane.setRight(imageView);
        borderPane.setLeft(imageView1);

        //Back Button
        Button button = new Button("Back");
        button.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                "-fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white;");
        button.setOnAction(actionEvent -> DeckView.getInstance(user).start());
        button.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(button);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);

        VBox vBox = new VBox(gridPane);
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < user.cardsBought.size(); i++)
            addCard(borderPane, gridPane, vBox, i, user.cardsBought.get(i));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        borderPane.setCenter(scrollPane);
        Scene scene = new Scene(borderPane);

        Main.stage.setScene(scene);
    }

    private void addCard(BorderPane borderPane, GridPane gridPane, VBox vBox, int i, String cardName) {
        if (Card.allCards.get(cardName).getCardsType() == Type.MONSTER) {
            ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + user.cardsBought.get(i) + ".jpg")).toExternalForm(), 275, 275, false, false));
            imageView2.setOnMouseClicked(mouseEvent -> addFunc(borderPane, vBox, cardName));
            gridPane.add(imageView2, i % 3, i / 3);
        } else {
            ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + user.cardsBought.get(i) + ".jpg")).toExternalForm(), 275, 275, false, false));
            imageView2.setOnMouseClicked(mouseEvent -> addFunc(borderPane, vBox, cardName));
            gridPane.add(imageView2, i % 3, i / 3);
        }
    }

    private void addFunc(BorderPane borderPane, VBox vBox, String cardName) {
        HBox hBox1 = new HBox();

        Label label = new Label("Where Do You Want To Add " + cardName + "To Deck " + deck.getDeckName());
        label.setFont(Font.font(20));
        label.setTextFill(Color.web("Black"));

        Button button1 = new Button("view.Main");
        button1.setStyle("-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
        button1.setOnAction(actionEvent -> {
            String response = ShopMenu.buyCardForGraphic("buy card --username " + user.getUsername() + " --card " + cardName);
            showAlert(vBox.getScene().getWindow(), "add Card To Deck", response);
            ShowAllCards showAllCards = new ShowAllCards();
            showAllCards.username = user.getUsername();
            try {
                showAllCards.start(Main.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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