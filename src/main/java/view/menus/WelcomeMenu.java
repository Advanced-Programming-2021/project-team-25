package view.menus;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.CreateGrid;
import view.Main;

import java.util.Objects;

public class WelcomeMenu{

    public void start() {
        GridPane gridPane = CreateGrid.createGridPane();
        addUIControls(gridPane);

        Scene scene = new Scene(gridPane);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("cursor.png")).toExternalForm());
        ImageCursor cursor = new ImageCursor(img, 10, 10);
        scene.setCursor(cursor);

        String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
        scene.getStylesheets().add(style);

        Main.stage.setTitle("YU-GI-UH!");
        Main.stage.resizableProperty().set(false);
        Main.stage.setScene(scene);
        Main.stage.setHeight(800);
        Main.stage.setWidth(1000);
        Main.stage.show();
    }

    private void addUIControls(GridPane grid) {
        Text welcomeText = new Text("Welcome");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(actionEvent -> new LoginMenu().start());
        grid.add(loginBtn,0 ,1);

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(actionEvent -> new SignUpMenu().start());
        grid.add(signUpBtn,0 ,2);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(actionEvent -> System.exit(0));
        grid.add(exitBtn,0 ,3);
    }
}

