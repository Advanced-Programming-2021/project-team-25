package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javax.swing.*;
import javax.swing.JOptionPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;

import java.util.Objects;

import static view.MainMenu.mainMenuScene;

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

        gridPane.add(new Label("Play with AI:"),1,1);
        CheckBox gameWithAi = new CheckBox("AI");
        gridPane.add(gameWithAi,1,2);

        Label lblUsername = new Label("Username of your rival!");
        gridPane.add(lblUsername,3,1);

        TextField txtRival = new TextField();
        txtRival.setPromptText("username");
        gridPane.add(txtRival,3,2);

        Button back = new Button("back");
        gridPane.add(back,0,3);

        Button btnStartGame = new Button("Start Game!");
        gridPane.add(btnStartGame,3,3);

        back.setOnMouseClicked(e->{
            stage.setScene(mainMenuScene);
            stage.show();
        });

        btnStartGame.setOnMouseClicked(e->{

        });
        checkBoxEvent(gameWithAi, lblUsername, txtRival);

        Scene scene = new Scene(gridPane ,600 ,600);
        String style= Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
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
