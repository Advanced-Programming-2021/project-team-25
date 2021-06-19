package view.menus;

import controllers.menues.DuelMenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.swing.JOptionPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Deck;
import models.User;
import view.CreateGrid;

import java.util.Objects;

public class DuelMenu {

    private static DuelMenu singleToneClass = null;
    private User currUser;

    public DuelMenu(User currUser) {
        this.currUser = currUser;
    }

    public static DuelMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DuelMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }
    public void run(Stage stage){
        GridPane gridPane = CreateGrid.createGridPane();

        Text welcomeText = new Text("Duel Menu");
        welcomeText.getStyleClass().add("welcome-text");
        gridPane.add(welcomeText,0 ,0);
        Text currUserName = new Text("User : "+currUser.getUsername()+" "+currUser.getNickName());
        currUserName.getStyleClass().add("user-text");

        gridPane.add(currUserName,1,0);
        Label lblPlayWithAI = new Label("Play with AI:");
        CheckBox gameWithAi = new CheckBox("AI");
        gridPane.addRow(1,lblPlayWithAI,gameWithAi);

        Label lblUsername = new Label("Username of your rival!");

        TextField txtRival = new TextField();
        txtRival.setPromptText("username");
        gridPane.addRow(2,lblUsername,txtRival);

        Button back = new Button("back");

        Button btnStartGame = new Button("Start Game!");

        Label lblRounds= new Label("round to play");

        ChoiceBox<String> rounds = new ChoiceBox<>();
        rounds.setValue("1");
        // Add the items to the ChoiceBox
        rounds.getItems().addAll("1", "3");
        // Create the Selection Message Label
        Label result = new Label("Your selection is:");
        // Create the Selection Value Label
        Label selectedValueLbl = new Label();
        // Bind the value property to the text property of the Label
        selectedValueLbl.textProperty().bind(rounds.valueProperty());

        gridPane.addRow(3,lblRounds,rounds);
        gridPane.addRow(4,result,selectedValueLbl);
        gridPane.addRow(5,back,btnStartGame);

        back.setOnMouseClicked(e-> new MainMenu().start());

        btnStartGame.setOnMouseClicked(e->{
            DuelMenuController duelMenuController = DuelMenuController.getInstance(currUser);

            if(User.getUserByUsername(txtRival.getText()) == null) JOptionPane.showMessageDialog(null,
                    "there is no player with this username");
            else if(currUser.getUsername().equals(txtRival.getText())) JOptionPane.showMessageDialog(null,
                    "you can't play with yourself");
            else if(currUser.activeDeck == null) JOptionPane.showMessageDialog(null,
                    currUser.getUsername() + " has no active deck");
            else if(Objects.requireNonNull(User.getUserByUsername(txtRival.getText())).activeDeck == null)
                JOptionPane.showMessageDialog(null,txtRival.getText() + " has no active deck");
            else if(!Deck.isValid(currUser.activeDeck.getDeckName())) JOptionPane.showMessageDialog(null,
                    currUser.getUsername() + "'s deck is not valid");
            else if(!Deck.isValid(Objects.requireNonNull(User.getUserByUsername(txtRival.getText())).activeDeck.getDeckName()))
                JOptionPane.showMessageDialog(null,txtRival.getText() + "'s deck is not valid");
            else if(rounds.getValue().equals("1")) duelMenuController.oneRoundDuel(txtRival.getText());
            else duelMenuController.threeRoundDuel(txtRival.getText());
        });
        checkBoxEvent(gameWithAi, lblUsername, txtRival);

        Scene scene = new Scene(gridPane ,800 ,650);
        String style= Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
        stage.centerOnScreen();
        stage.setScene(scene);
    }


    private void checkBoxEvent(CheckBox gameWithAi, Label lblUsername, TextField txtRival) {
        gameWithAi.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> ov, final Boolean value, final Boolean newValue)
            {
                if(newValue != null && newValue)
                {
                    System.out.println("your selection is : game with AI");
                    txtRival.visibleProperty().set(false);
                    lblUsername.visibleProperty().set(false);
                }
                else if(value){
                    System.out.println("you not select AI");
                    txtRival.visibleProperty().set(true);
                    lblUsername.visibleProperty().set(true);
                }
            }
        });
    }
}
