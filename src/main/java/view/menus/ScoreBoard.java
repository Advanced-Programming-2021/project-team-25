package view.menus;

import javafx.scene.control.Button;
import models.User;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.CreateGrid;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoard {
    public void start(Stage primaryStage) {

        GridPane grid = CreateGrid.createGridPane();

        Text welcomeText = new Text("ScoreBoard");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Button back = new Button("back");

        ArrayList<User> users = User.getUsers();
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getUsername);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank = 1 ,j = 1;
        //iterate users
        for (int i = users.size() - 1 ; i >= 0 ; i-- ) {
            Text iText = new Text((rank + "- " + users.get(i).getUsername() + " : " + users.get(i).getScore()) + "\n");
            iText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
            grid.add(iText,0 ,j);
            j++;
            if(i != 0 && users.get(i).getScore() > users.get(i-1).getScore() ) rank++;
        }
        grid.addRow(2,back);
        Scene scene = new Scene(grid);

        back.setOnMouseClicked(e-> new MainMenu().start());
        primaryStage.setScene(scene);
    }
}
