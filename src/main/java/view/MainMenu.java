package view;

import controllers.menues.DuelMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;

public class MainMenu {
    public void start(Stage stage){
        GridPane grid = CreateGrid.createGridPane();

        Text welcomeText = new Text("Main Menu");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Button DuelBtn = new Button("Duel Menu");
        DuelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //new DuelMenu().runDuelMenu();
            }
        });


        Label lblUsername = new Label("Username");
        grid.add(lblUsername,0,1);

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("username");
        grid.add(txtUsername,1,1);

        Label lblNickname = new Label("Nickname");
        grid.add(lblNickname,0,2);

        TextField txtNickname = new TextField();
        txtNickname.setPromptText("nickname");
        grid.add(txtNickname,1,2);

        Label lblPassword = new Label("Password");
        grid.add(lblPassword,0,3);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("password");
        grid.add(txtPassword,1,3);

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(actionEvent -> {
            if(User.getUserByUsername(txtUsername.getText()) != null)
                showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "There is a user with this username");
            else if(User.getUserByNickName(txtNickname.getText()) != null)
                showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "There is a user with this Nickname");
            else if(txtPassword.getText().isEmpty())
                showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "Please enter a password");
            else{
                new User(txtUsername.getText(),txtNickname.getText(),txtPassword.getText());
                showAlert(Alert.AlertType.CONFIRMATION, grid.getScene().getWindow(), "Registration Successful!", "Welcome " + txtUsername.getText());
                new WelcomeMenu().start(primaryStage);
            }
        });
        grid.add(signUpBtn,1 ,4);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, 4);
        exitButton.setOnAction(event -> new WelcomeMenu().start(primaryStage));

        Scene scene = new Scene(grid ,500 ,500);
        primaryStage.setScene(scene);
    }
}
