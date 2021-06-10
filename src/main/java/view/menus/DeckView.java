package view.menus;

import controllers.Database.DataBase;
import controllers.menues.DeckMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Card;
import models.CardStufs.Type;
import models.Deck;
import models.User;
import view.CreateGrid;

import java.util.Objects;

import static models.Deck.allDecks;

public class DeckView {

    public User currUser;
    private static DeckView singleToneClass = null;

    public DeckView(User currUser) {
        this.currUser = currUser;
    }

    public static DeckView getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DeckView(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }

    public void start(Stage stage) {
        GridPane gridPane = CreateGrid.createGridPane();
        addUIControls(gridPane, stage);

        Scene scene = new Scene(gridPane, 800, 703);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);

        String style = Objects.requireNonNull(this.getClass().getResource("login/login.css")).toExternalForm();
        scene.getStylesheets().add(style);
        scene.setCursor(cursor);

        System.out.println("Data Saved!");
        if(allDecks!=null) DataBase.storeDecks(allDecks);
        DataBase.saveTheUserList(User.getUsers());

        stage.setTitle("YU-GI-UH!");
        stage.resizableProperty().set(false);
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(1200);
        stage.show();
    }

    private void addUIControls(GridPane grid, Stage stage) {
        Text activeDeckText = new Text("َActive Deck");
        activeDeckText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(activeDeckText,0 ,0);

        String activeDeckName = null;
        if(currUser.getActiveDeck() == null || Deck.getDeckByName(currUser.getActiveDeck().getDeckName()) == null){
            Text noActiveDeckText = new Text("َNo Active Deck");
            noActiveDeckText.setFont(Font.font("tahoma", FontWeight.LIGHT ,20));
            grid.add(noActiveDeckText,0 ,1);
        }
        else{
            deckInRow(grid,currUser.getActiveDeck(),1, stage);
            activeDeckName = currUser.getActiveDeck().getDeckName();
        }

        Text otherDeckText = new Text("َOther Deck(s)");
        otherDeckText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(otherDeckText,0 ,2);

        int i;
        for (i = 0; i < Deck.allDecks.size(); i++) {
            if(!Deck.allDecks.get(i).getDeckName().equals(activeDeckName) && Deck.allDecks.get(i).getOwnerName().equals(currUser.getUsername()))
                deckInRow(grid,Deck.allDecks.get(i),i + 3, stage);
        }

        Text deckNameText = new Text("Enter new Deck Name");
        deckNameText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(deckNameText,0,i + 3);

        Label lblDeck = new Label("Deck Name");

        TextField deckName = new TextField();
        deckName.setPromptText("deckName");

        Button addDeckButton = new Button("Add New Deck");
        addDeckButton.setOnAction(event -> {
            showAlert(grid.getScene().getWindow(), "Add New Deck", DeckMenu.getInstance(currUser).createDeck(deckName.getText()));
            start(stage);
        });

        grid.addRow(i + 4,lblDeck, deckName, addDeckButton);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, i + 5);
        exitButton.setOnAction(event -> new MainMenu().start());
    }

    private void deckInRow(GridPane grid, Deck deck, int i, Stage stage) {
        Text deckNameText = new Text(deck.getDeckName() + "," + deck.mainDeck.size() +"," + deck.sideDeck.size());
        deckNameText.setFont(Font.font("tahoma", FontWeight.LIGHT ,20));

        HBox hbox = new HBox();

        ImageView star = new ImageView(new Image("file:download (2).png"));
        star.setFitHeight(50);
        star.setFitWidth(50);
        star.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showAlert(grid.getScene().getWindow(), "Activate this Deck", DeckMenu.getInstance(currUser).setActive(deck.getDeckName()));
            start(stage);
            event.consume();
        });

        ImageView pencil = new ImageView(new Image("file:pencil.png"));
        pencil.setFitHeight(50);
        pencil.setFitWidth(50);
        pencil.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            new inDeckMenu().start(stage,deck,currUser);
            event.consume();
        });

        ImageView delete = new ImageView(new Image("file:download.png"));
        delete.setFitHeight(50);
        delete.setFitWidth(50);
        delete.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showAlert(grid.getScene().getWindow(), "Delete this Deck", DeckMenu.getInstance(currUser).deleteDeck(deck.getDeckName()));
            start(stage);
            event.consume();
        });

        hbox.getChildren().addAll(star,pencil,delete);

        grid.addRow(i,deckNameText,hbox);
    }

    private void showAlert(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }

    public static class inDeckMenu {

        public String username;

        public void start(Stage stage, Deck deck, User user) {
            BorderPane borderPane = new BorderPane();
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/right.png")).toExternalForm(), 170, 700, false, false);
            ImageView imageView = new ImageView(image);
            Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/left.png")).toExternalForm(), 170, 700, false, false);
            ImageView imageView1 = new ImageView(image1);
            borderPane.setRight(imageView);
            borderPane.setLeft(imageView1);

            Button button = new Button("Back");
            button.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                    "-fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white;");
            button.setOnAction(actionEvent -> getInstance(user).start(stage));
            button.setAlignment(Pos.CENTER);

            HBox hBox = new HBox(button);
            hBox.setAlignment(Pos.CENTER);
            borderPane.setBottom(hBox);

            ScrollPane scrollPane = new ScrollPane();

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.TOP_CENTER);

            VBox vBox = new VBox(gridPane);
            vBox.setAlignment(Pos.CENTER);

            for (int i = 0; i < user.cardsBought.size(); i++) {
                String cardName = user.cardsBought.get(i);

                if (Card.allCards.get(cardName).getCardsType() == Type.MONSTER){
                    ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + user.cardsBought.get(i) + ".jpg")).toExternalForm(), 275, 275, false, false));
                    imageView2.setOnMouseClicked(mouseEvent -> {
                        HBox hBox1 = new HBox();

                        Label label = new Label("Where Do You Want To Add " + cardName + "To Deck " + deck.getDeckName());
                        label.setFont(Font.font(20));
                        label.setTextFill(Color.web("Black"));
                        hBox1.getChildren().add(label);

                        Button button1 = new Button("view.Main");
                        button1.setStyle("-fx-background-color: #c3c4c4," +
                                "linear-gradient(#d6d6d6 50%, white 100%)," +
                                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                                "-fx-background-radius: 30; -fx-background-insets: 0,1,1;" +
                                "-fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );");
                        button1.setOnAction(actionEvent -> {
                            String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + cardName);
                            showAlert(vBox.getScene().getWindow(), "add Card To Deck", response);
                            ShowAllCards showAllCards = new ShowAllCards();
                            showAllCards.username = username;
                            try {
                                showAllCards.start(stage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        hBox1.getChildren().add(button1);

                        hBox1.setSpacing(10);
                        borderPane.setTop(hBox1);
                    });
                    gridPane.add(imageView2, i%3, i/3);
                }
                else{
                    if (user.cardsBought.get(i).equals("Magic Jammer")){
                        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + user.cardsBought.get(i) + ".png")).toExternalForm(), 275, 275, false, false);
                        ImageView imageView2 = new ImageView(image2);
                        imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                HBox hBox1 = new HBox();
                                Label label = new Label("You have " + Objects.requireNonNull(User.getUserByUsername(username)).getMoney() + "$ and the " +
                                        "price of this card is " + Card.allCards.get(cardName).getPrice() + "$");
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
                                        String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + cardName);
                                        showAlert(vBox.getScene().getWindow(), "buy card response", response);
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
                        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + user.cardsBought.get(i) + ".jpg")).toExternalForm(), 275, 275, false, false);
                        ImageView imageView2 = new ImageView(image2);
                        imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                HBox hBox1 = new HBox();
                                Label label = new Label("You have " + Objects.requireNonNull(User.getUserByUsername(username)).getMoney() + "$ and the " +
                                        "price of this card is " + Card.allCards.get(cardName).getPrice() + "$");
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
                                        String response = ShopMenu.buyCardForGraphic("buy card --username " + username + " --card " + cardName);
                                        showAlert(vBox.getScene().getWindow(), "buy card response", response);
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

        private void showAlert(Window owner, String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(owner);
            alert.showAndWait();
        }
    }
}
