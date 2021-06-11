package view.menus;

import controllers.ProgramController;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import models.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;
import view.CreateGrid;
import view.Main;

import java.util.Objects;

public class LoginMenu{

    public void start() {
        GridPane grid = CreateGrid.createGridPane();
        addUIControl(grid);

        Scene scene = new Scene(grid,800,650);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);
        scene.setCursor(cursor);

        String style= Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
        scene.getStylesheets().add(style);

        Main.stage.setScene(scene);
    }

    private void addUIControl(GridPane grid) {
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
        loginBtn.setOnAction(actionEvent -> loginFunc(grid, txtUsername, txtPassword));
        grid.add(loginBtn,1 ,3);

        grid.setOnKeyPressed(ev ->{
            if (ev.getCode() == KeyCode.ENTER) {
                loginBtn.fire();
                ev.consume();
            }
        });
        Button exitButton = new Button("Exit");
        grid.add(exitButton, 0, 3);
        exitButton.setOnAction(event -> new WelcomeMenu().start());
    }

    private void loginFunc(GridPane grid, TextField txtUsername, PasswordField txtPassword) {
        if(User.getUserByUsername(txtUsername.getText()) == null)
            showAlert(grid.getScene().getWindow(), "There is not user with this username");
        else if(!txtPassword.getText().equals(Objects.requireNonNull(User.getUserByUsername(txtUsername.getText())).getPassword()))
            showAlert(grid.getScene().getWindow(), "Wrong Password!");
        else {
            ProgramController.currUser = User.getUserByUsername(txtUsername.getText());
            new MainMenu().start();
        }
    }

    private void showAlert(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }

}
