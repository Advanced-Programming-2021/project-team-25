package view.menus;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import controllers.ProgramController;
import controllers.Regex;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bson.Document;
import view.CreateGrid;
import view.Main;
import view.SendReceiveData;

import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;

public class Chat {
    private static Chat singleToneClass = null;
    private Scene impExpScene;
    TextField txtMessage = new TextField();
    private Stage currStage;
    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    DB database = mongoClient.getDB("Chat");
    DBCollection collection = database.getCollection("chats");
    public static Chat getInstance() {
        if (singleToneClass == null) singleToneClass = new Chat();
        return singleToneClass;
    }

    public void run(Stage stage) {
        GridPane gridPane = CreateGrid.createGridPane();
        TextArea textAreaAllChats = new TextArea();
        Text welcomeText = new Text("Chat Menu");
        welcomeText.getStyleClass().add("welcome-text");
        gridPane.addRow(1, welcomeText);
        Label lblName = new Label("send message");
        Label lblName2 = new Label("your message");
        Label lblChats = new Label("all chats");
        txtMessage.setPromptText("your message...");
        gridPane.addRow(2, lblName2, txtMessage);

        Button back = new Button("back");
        back.setOnMouseClicked(e -> {
            Main.stage.setScene(MainMenu.mainMenuScene);
        });
        Button refresh = new Button("refresh");
        refresh.setOnMouseClicked(e -> {
            printChats(textAreaAllChats);
        });
        Button sendBtn = new Button("Send");
        gridPane.addRow(3, lblName, sendBtn);

        gridPane.addRow(4, lblChats, textAreaAllChats);
        gridPane.addRow(5, back, refresh);
        sendBtn.setOnMouseClicked(e -> {
            DBObject messages = new BasicDBObject("_id", SendReceiveData.token)
                    .append("name", ProgramController.currUser.getNickName()).append("message", txtMessage.getText());
            collection.insert(messages);
            printChats(textAreaAllChats);
        });

        Scene scene = new Scene(gridPane, 800, 650);
        impExpScene = scene;
        String style = Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
        stage.centerOnScreen();
        stage.setScene(scene);
    }

    private void printChats(TextArea textAreaAllChats) {
        for (DBObject dbObject : collection.find()) {
            String message = dbObject.toString();
            System.out.println(message);
            Matcher matcherName = Regex.getMatcher(message,"\"name\": \"(.+)\"");
            Matcher matcherMessage = Regex.getMatcher(message,"\"message\": \"(.+)\"");
            if(matcherMessage.find() && matcherName.find()){
                textAreaAllChats.appendText("nickName: " + matcherName.group(1).replace("\"",""));
                textAreaAllChats.appendText(matcherMessage.group(1).replace("\"",""));
                textAreaAllChats.appendText("\n");
            }
        }
    }
}
