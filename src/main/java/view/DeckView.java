package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeckView {
    public void start(Stage primaryStage) {
        GridPane grid = CreateGrid.createGridPane();

        Text activeDeckText = new Text("َActive Deck");
        activeDeckText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(activeDeckText,0 ,0);

        Text deckNameText = new Text("َActive Deck,0,0");
        deckNameText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(deckNameText,0 ,1);

        HBox hbox = new HBox();
        Rectangle rect = new Rectangle();
        rect.setFill(Color.GREEN);
        hbox.getChildren().add(rect);
//        Rectangle rect2 = new Rectangle();
//        rect.setFill(Color.RED);
//        hbox.getChildren().add(rect2);
        grid.add(hbox,1,1);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, 4);
        exitButton.setOnAction(event -> new WelcomeMenu().start(primaryStage));

        Scene scene = new Scene(grid ,500 ,500);
        primaryStage.setScene(scene);
    }
}
