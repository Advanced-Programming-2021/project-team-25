package view;

import controllers.ProgramController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu {
    public static Scene mainMenuScene;
    public static Stage gameStage;
    public void start(Stage stage){
        GridPane grid = CreateGrid.createGridPane();

        Text welcomeText = new Text("Main Menu");
        welcomeText.setFont(Font.font("tahoma", FontWeight.LIGHT ,25));
        grid.add(welcomeText,0 ,0);

        Button DuelBtn = new Button("Duel Menu");
        DuelBtn.setOnAction(actionEvent -> {
            DuelMenu.getInstance(ProgramController.currUser).run(stage);
        });
        grid.add(DuelBtn,0,1);

        Button ShopBtn = new Button("Shop Menu");
        ShopBtn.setOnAction(actionEvent -> {
            try {
                new ShopMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        grid.add(ShopBtn,0,2);

        Button DeckBtn = new Button("Deck Menu");
        DeckBtn.setOnAction(actionEvent -> {
            //new DuelMenu().runDuelMenu();
        });
        grid.add(DeckBtn,0,3);

        Button ProfileBtn = new Button("Profile Menu");
        ProfileBtn.setOnAction(actionEvent -> {
            ProfileMenu.getInstance(ProgramController.currUser).runProfileMenu(stage);
        });
        grid.add(ProfileBtn,0,4);

        Button ScoreBoardBtn = new Button("ScoreBoard Menu");
        ScoreBoardBtn.setOnAction(actionEvent -> new ScoreBoard().start(stage));
        grid.add(ScoreBoardBtn,0,5);

        Button ImportExportBtn = new Button("Import/Export Menu");
        ImportExportBtn.setOnAction(actionEvent -> {
           //
        });
        grid.add(ImportExportBtn,0,6);

        Button LogoutBtn = new Button("Logout");
        LogoutBtn.setOnAction(actionEvent -> new WelcomeMenu().start(stage));
        grid.add(LogoutBtn,0,7);

        Scene scene = new Scene(grid ,500 ,500);
        mainMenuScene = scene;
        stage.setScene(scene);
        gameStage = stage;
    }

}
