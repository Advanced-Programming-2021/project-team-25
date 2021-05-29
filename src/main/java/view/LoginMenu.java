package view;

import models.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Objects;
import java.util.Optional;

public class LoginMenu{

    public void start(Stage stage) {
        GridPane grid = CreateGrid.createGridPane();

        Text welcomeText = new Text("Login Menu");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Label lblUsername = new Label("Username");
        grid.add(lblUsername,0,1);

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("username");
        grid.add(txtUsername,1,1);

        Label lblPassword = new Label("Password");
        grid.add(lblPassword,0,2);

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("password");
        grid.add(txtPassword,1,2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(actionEvent -> {
            if(User.getUserByUsername(txtUsername.getText()) == null)
                showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "There is not user with this username");
            else if(!txtPassword.getText().equals(Objects.requireNonNull(User.getUserByUsername(txtUsername.getText())).getPassword()))
                showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "Wrong Password!");
            else
                new MainMenu().start(stage);
        });
        grid.add(loginBtn,1 ,3);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, 3);
        exitButton.setOnAction(event -> new WelcomeMenu().start(stage));

        Scene scene = new Scene(grid ,500 ,500);
        stage.setScene(scene);
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()) System.exit(0);
        //else if(result.get() == ButtonType.OK) new app().start(this.stage);
    }
}
