package view;

import controllers.menues.DeckMenu;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Deck;
import models.User;

import java.util.Objects;

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

        stage.setTitle("YU-GI-UH!");
        stage.resizableProperty().set(false);
        stage.setScene(scene);
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
            if(!Deck.allDecks.get(i).getDeckName().equals(activeDeckName)) deckInRow(grid,Deck.allDecks.get(i),i + 3, stage);
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
        exitButton.setOnAction(event -> new MainMenu().start(stage));
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
}
