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
import javafx.stage.Window;
import models.Card;
import models.CardStufs.Type;
import models.Deck;
import models.User;
import view.CreateGrid;
import view.Main;
import view.SendReceiveData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

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

    public void start() {
        GridPane gridPane = CreateGrid.createGridPane();
        addUIControls(gridPane);

        Scene scene = new Scene(gridPane,800,650);

        Image img = new Image(Objects.requireNonNull(getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);
        scene.setCursor(cursor);

        String style = Objects.requireNonNull(getClass().getResource("deck/Deck.css")).toExternalForm();
        scene.getStylesheets().add(style);

        Main.stage.setScene(scene);
    }

    private void addUIControls(GridPane grid) {
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
            deckInRow(grid,currUser.getActiveDeck(),1);
            activeDeckName = currUser.getActiveDeck().getDeckName();
        }

        Text otherDeckText = new Text("َOther Deck(s)");
        otherDeckText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(otherDeckText,0 ,2);

        int i;
        for (i = 0; i < Deck.allDecks.size(); i++) {
            if(!Deck.allDecks.get(i).getDeckName().equals(activeDeckName) && Deck.allDecks.get(i).getOwnerName().equals(currUser.getUsername()))
                deckInRow(grid,Deck.allDecks.get(i),i + 3);
        }

        Text deckNameText = new Text("Enter new Deck Name");
        deckNameText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(deckNameText,0,i + 3);

        Label lblDeck = new Label("Deck Name");

        TextField deckName = new TextField();
        deckName.setPromptText("deckName");

        Button addDeckButton = new Button("Add New Deck");
        addDeckButton.setOnAction(event -> {
            showAlert(grid.getScene().getWindow(), "Add New Deck",
                    SendReceiveData.sendReceiveData("addNewDeck --deckName " + deckName.getText()));
            DataBase.storeDecks(allDecks);
            DataBase.saveTheUserList(User.getUsers());
            start();
        });

        grid.addRow(i + 4,lblDeck, deckName, addDeckButton);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, i + 5);
        exitButton.setOnAction(event -> {
            new MainMenu().start();
            DataBase.storeDecks(allDecks);
            DataBase.saveTheUserList(User.getUsers());
        });


        Button showCardButton = new Button("Show Card");
        grid.add(showCardButton, 2, i + 5);
        showCardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showCard();
            }
        });
    }

    private void deckInRow(GridPane grid, Deck deck, int i) {
        Text deckNameText = new Text(deck.getDeckName() + "," + deck.mainDeck.size() +"," + deck.sideDeck.size());
        deckNameText.setFont(Font.font("tahoma", FontWeight.LIGHT ,20));

        HBox hbox = new HBox();

        Image starImage = new Image(Objects.requireNonNull(getClass().getResource("deck/star.png")).toExternalForm());
        ImageView star = new ImageView(starImage);
        star.setFitHeight(50);
        star.setFitWidth(50);
        star.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showAlert(grid.getScene().getWindow(), "Activate this Deck", DeckMenu.getInstance(currUser).setActive(deck.getDeckName()));
            DataBase.storeDecks(allDecks);
            DataBase.saveTheUserList(User.getUsers());
            start();
        });

        Image pencilImage = new Image(Objects.requireNonNull(getClass().getResource("deck/pencil.png")).toExternalForm());
        ImageView pencil = new ImageView(pencilImage);
        pencil.setFitHeight(50);
        pencil.setFitWidth(50);
        pencil.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> new inDeckMenu(deck,currUser).start());

        Image eyeImage = new Image(Objects.requireNonNull(getClass().getResource("deck/eye.png")).toExternalForm());
        ImageView eye = new ImageView(eyeImage);
        eye.setFitHeight(50);
        eye.setFitWidth(50);
        eye.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> new ShowDeck(deck,currUser).start());
        //adding tool tip
        String toolTip = "";
        ArrayList<String> cardsName = new ArrayList<>();
        for (int j = 0; j<deck.mainDeck.size(); ++j){
            if (!cardsName.contains(deck.mainDeck.get(j).getName())){
                cardsName.add(deck.mainDeck.get(j).getName());
                int counter = 0;
                for (int k = 0; k<deck.mainDeck.size(); ++k){
                    if (deck.mainDeck.get(k).getName().equals(deck.mainDeck.get(j).getName()))
                        counter += 1;
                }
                for (int k = 0; k<deck.sideDeck.size(); ++k){
                    if (deck.sideDeck.get(k).getName().equals(deck.mainDeck.get(j).getName()))
                        counter += 1;
                }
                toolTip += deck.mainDeck.get(j).getName() + " X" + counter + "\n";
            }
        }
        Tooltip.install(eye, new Tooltip(toolTip));
        //end of adding tool tip

        Image deleteImage = new Image(Objects.requireNonNull(getClass().getResource("deck/delete.png")).toExternalForm());
        ImageView delete = new ImageView(deleteImage);
        delete.setFitHeight(50);
        delete.setFitWidth(50);
        delete.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showAlert(grid.getScene().getWindow(), "Delete this Deck", DeckMenu.getInstance(currUser).deleteDeck(deck.getDeckName()));
            DataBase.storeDecks(allDecks);
            DataBase.saveTheUserList(User.getUsers());
            start();
        });

        hbox.getChildren().addAll(star,pencil,eye,delete);

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


    public void showAlert2(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        //else if(result.get() == ButtonType.OK) new app().start(this.stage);
    }


    public void showCard (){
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

                        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/Monsters/" + cardsName + ".jpg")).toExternalForm());
                        ImageView imageView = new ImageView(image2);

                        vBox.getChildren().addAll(imageView);

                        HBox hBox1 = new HBox();
                        hBox1.setAlignment(Pos.CENTER);

                        vBox.getChildren().add(hBox1);

                        // create a image
                        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/background.png")).toExternalForm());
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
                        new subStage("Show Card", scene);
                    }
                    else{
                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER);

                        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/SpellTrap/" + cardsName + ".jpg")).toExternalForm());
                        ImageView imageView = new ImageView(image2);

                        vBox.getChildren().addAll(imageView);

                        HBox hBox1 = new HBox();
                        hBox1.setAlignment(Pos.CENTER);

                        vBox.getChildren().add(hBox1);

                        // create a image
                        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/background.png")).toExternalForm());
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
                        new subStage("Show Stage", scene);
                    }
                }
                else {
                    showAlert2(Alert.AlertType.INFORMATION, vBox.getScene().getWindow(), "Invalid Name", "Please enter a valid name.");
                }
            }
        });


        hBox1.getChildren().addAll(button);



        vBox.getChildren().addAll(label, hBox, hBox1);


        // create a image
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/view/menus/shop/background.png")).toExternalForm());
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
        new subStage("Show Card", scene);
    }
}

