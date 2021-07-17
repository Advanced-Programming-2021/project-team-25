package view.menus;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import controllers.menues.DuelMenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.swing.JOptionPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.AI;
import models.Deck;
import models.Duelist;
import models.User;
import view.*;

import java.util.Objects;
import java.util.regex.Matcher;

public class DuelMenu {

    private static DuelMenu singleToneClass = null;
    private User currUser;

    public DuelMenu(User currUser) {
        this.currUser = currUser;
    }

    public static DuelMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DuelMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }
    public void run(Stage stage){
        GridPane gridPane = CreateGrid.createGridPane();

        Text welcomeText = new Text("Duel Menu");
        welcomeText.getStyleClass().add("welcome-text");
        gridPane.add(welcomeText,0 ,0);
        Text currUserName = new Text("User : "+currUser.getUsername()+" "+currUser.getNickName());
        currUserName.getStyleClass().add("user-text");

        gridPane.add(currUserName,1,0);
        Label lblPlayWithAI = new Label("Play with AI:");
        CheckBox gameWithAi = new CheckBox("AI");
        gridPane.addRow(1,lblPlayWithAI,gameWithAi);

        Label lblUsername = new Label("Username of your rival!");

        TextField txtRival = new TextField();
        txtRival.setPromptText("username");
        gridPane.addRow(2,lblUsername,txtRival);

        Button back = new Button("back");

        Button btnStartGame = new Button("Start Game!");

        Label lblRounds= new Label("round to play");

        ChoiceBox<String> rounds = new ChoiceBox<>();
        rounds.setValue("1");
        // Add the items to the ChoiceBox
        rounds.getItems().addAll("1", "3");
        // Create the Selection Message Label
        Label result = new Label("Your selection is:");
        // Create the Selection Value Label
        Label selectedValueLbl = new Label();
        // Bind the value property to the text property of the Label
        selectedValueLbl.textProperty().bind(rounds.valueProperty());

        gridPane.addRow(3,lblRounds,rounds);
        gridPane.addRow(4,result,selectedValueLbl);
        gridPane.addRow(5,back,btnStartGame);

        back.setOnMouseClicked(e-> new MainMenu().start());

        btnStartGame.setOnMouseClicked(e->{
            DuelMenuController duelMenuController = DuelMenuController.getInstance(currUser);
            if(gameWithAi.isSelected()){
                if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
                else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
                else if(!(rounds.getValue().equals("1") || rounds.getValue().equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
                else{
                    Main.audioClip.stop();
                    new Battlefield(new Duelist(currUser),new AI(User.getUserByUsername("admin")), 1);
                }
            }
            else{
                startNewDuel(rounds);
            }
        });
        checkBoxEvent(gameWithAi, lblUsername, txtRival);

        Scene scene = new Scene(gridPane ,800 ,650);
        String style= Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
        stage.centerOnScreen();
        stage.setScene(scene);
    }

    private void startNewDuel(ChoiceBox<String> rounds) {
        String resultOfServer = SendReceiveData.getCurrDuelistFromServer("startDuel");
        if(Objects.isNull(resultOfServer) || resultOfServer.isBlank() || resultOfServer.isEmpty())
            LoginMenu.showAlert(null, "An Error occurred");
        else if(resultOfServer.startsWith("error")){
            Matcher matcherDesc = Regex.getMatcher(resultOfServer,"description=\"(.+)\"");
            if(matcherDesc.find())
                LoginMenu.showAlert(null, matcherDesc.group(1));
        }
        else if(resultOfServer.startsWith("noUserFound")){
            int dialogResult = JOptionPane.showConfirmDialog (null, "No user found would you like to try again in five second?","information",JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startNewDuel(rounds);
            }
        }
        else if(resultOfServer.startsWith("success")) {
                resultOfServerStartGame(rounds);
        }
    }

    private void resultOfServerStartGame(ChoiceBox<String> rounds) {
        String resultOfServeInStartGame = SendReceiveData.sendReceiveData("startGame --rounds " + rounds.getValue());
        if(Objects.isNull(resultOfServeInStartGame) || resultOfServeInStartGame.isBlank() || resultOfServeInStartGame.isEmpty()) {
            LoginMenu.showAlert(null, "An Error occurred");
            resultOfServerStartGame(rounds);
        }
        else if(resultOfServeInStartGame.startsWith("error")){
            Matcher matcherDesc = Regex.getMatcher(resultOfServeInStartGame,"description=\"(.+)\"");
            if(matcherDesc.find())
                LoginMenu.showAlert(null, matcherDesc.group(1));
        }else if(resultOfServeInStartGame.startsWith("wait")){
            try {
                Matcher matcherDesc = Regex.getMatcher(resultOfServeInStartGame, "description=\"(.+)\"");
                if (matcherDesc.find())
                    LoginMenu.showAlert(null, matcherDesc.group(1));
                Thread.sleep(100);
                resultOfServerStartGame(rounds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(resultOfServeInStartGame.contains("success")){
            //success
            Matcher matcherSuccess = Regex.getMatcher(resultOfServeInStartGame,"description=\"(.+)\"");
            if(matcherSuccess.find()){
                String whoTurn  = matcherSuccess.group(1);
                if(whoTurn.equals("turn")){
                    System.out.println("turn");
                    if(rounds.getValue().equals("1"))
                        DuelMenuController.getInstance(currUser).oneRoundDuel(new Duelist(currUser),new Duelist(currUser));
                    else
                        DuelMenuController.getInstance(currUser).threeRoundDuel(new Duelist(currUser),new Duelist(currUser));
                }else{
                    System.out.println("opponent");

                    if(rounds.getValue().equals("1"))
                        DuelMenuController.getInstance(currUser).oneRoundDuel(DuelMenuController.duelistRival,new Duelist(currUser));
                    else
                        DuelMenuController.getInstance(currUser).threeRoundDuel(DuelMenuController.duelistRival,new Duelist(currUser));
                }
            }
        }
        else
            resultOfServerStartGame(rounds);
    }


    private void checkBoxEvent(CheckBox gameWithAi, Label lblUsername, TextField txtRival) {
        gameWithAi.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> ov, final Boolean value, final Boolean newValue)
            {
                if(newValue != null && newValue)
                {
                    System.out.println("your selection is : game with AI");
                    txtRival.visibleProperty().set(false);
                    lblUsername.visibleProperty().set(false);
                }
                else if(value){
                    System.out.println("you not select AI");
                    txtRival.visibleProperty().set(true);
                    lblUsername.visibleProperty().set(true);
                }
            }
        });
    }
}
