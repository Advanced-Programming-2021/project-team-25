package view;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
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

public class SignUpMenu {

    public void start(Stage stage) {
        GridPane gridPane = CreateGrid.createGridPane();
        addUIControls(gridPane, stage);

        Scene scene = new Scene(gridPane, 600, 600);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);

        String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
        scene.getStylesheets().add(style);
        scene.setCursor(cursor);

        stage.setTitle("YU-GI-UH!");
        stage.resizableProperty().set(false);
        stage.setScene(scene);
        stage.show();
    }

    private void addUIControls(GridPane grid, Stage stage) {
        Text welcomeText = new Text("Sign Up Menu");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

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
        signUpBtn.setOnAction(actionEvent -> signupFunc(stage, grid, txtUsername, txtNickname, txtPassword));
        grid.add(signUpBtn,1 ,4);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, 4);
        exitButton.setOnAction(event -> new WelcomeMenu().start(stage));
    }

    private void signupFunc(Stage primaryStage, GridPane grid, TextField txtUsername, TextField txtNickname, PasswordField txtPassword) {
        if(User.getUserByUsername(txtUsername.getText()) != null)
            showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "There is a user with this username");
        else if(User.getUserByNickName(txtNickname.getText()) != null)
            showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "There is a user with this Nickname");
        else if(txtPassword.getText().isEmpty())
            showAlert(Alert.AlertType.INFORMATION, grid.getScene().getWindow(), "Form Error!", "Please enter a password");
        else{
            new User(txtUsername.getText(), txtPassword.getText(), txtNickname.getText());
            showAlert(Alert.AlertType.CONFIRMATION, grid.getScene().getWindow(), "Registration Successful!", "Welcome " + txtUsername.getText());
            new WelcomeMenu().start(primaryStage);
        }
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
