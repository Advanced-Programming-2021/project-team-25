package view.menus;

import com.google.gson.Gson;
import controllers.ProgramController;
import controllers.Regex;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import models.Deck;
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
import view.SendReceiveData;

import java.util.Objects;
import java.util.regex.Matcher;

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
        String result = SendReceiveData.sendReceiveData("user login --username "+
                txtUsername.getText()+
                " --password "+txtPassword.getText());
        if(Objects.isNull(result) || result.isBlank() || result.isEmpty())
            showAlert(grid.getScene().getWindow(), "An Error occurred");
        else if(result.startsWith("error")){
            Matcher matcherDesc = Regex.getMatcher(result,"description=\"(.+)\"");
            if(matcherDesc.find())
                showAlert(grid.getScene().getWindow(), matcherDesc.group(1));
        }
        else if(result.startsWith("success")) {
            Matcher matcherDesc = Regex.getMatcher(result,"description=\"(.+)\"");
            if(matcherDesc.find()) {
                SendReceiveData.token = matcherDesc.group(1);
                ProgramController.currUser = User.getUserByUsername(txtUsername.getText());
                new MainMenu().start();
            }
            //get curr User
            ProgramController.currUser = SendReceiveData.getCurrUserFromServer();
            //get curr Decks
            parseDecks(Objects.requireNonNull(SendReceiveData.getDecksOfUser()));
        }
    }

    private void parseDecks (String decks){
        //
        System.out.println("^^^^^^^^^^^^^^" + decks);
        //
        Gson gson = new Gson();
        if (!decks.equals("")) {
            String[] myDecksArray = decks.split("&&&");
            for (String s : myDecksArray) {
                Deck deck = gson.fromJson(s, Deck.class);
                Deck.allDecks.add(deck);
            }
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
