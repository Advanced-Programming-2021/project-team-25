package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeMenu{

    public void start(Stage primaryStage) {
        GridPane gridPane = CreateGrid.createGridPane();
        addUIControls(gridPane, primaryStage);

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
    }

    private void addUIControls(GridPane grid, Stage stage) {
        Text welcomeText = new Text("Welcome");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(actionEvent -> new LoginMenu().start(stage));
        grid.add(loginBtn,0 ,1);

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(actionEvent -> new SignUpMenu().start(stage));
        signUpBtn.setDefaultButton(true);
        grid.add(signUpBtn,0 ,2);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(actionEvent -> System.exit(0));
        grid.add(exitBtn,0 ,3);
    }
}

