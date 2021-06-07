package view;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class WelcomeMenu{

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
        Text welcomeText = new Text("Welcome");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(actionEvent -> new LoginMenu().start(stage));
        grid.add(loginBtn,0 ,1);

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(actionEvent -> new SignUpMenu().start(stage));
        grid.add(signUpBtn,0 ,2);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(actionEvent -> System.exit(0));
        grid.add(exitBtn,0 ,3);
    }
}

