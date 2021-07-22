package view.menus;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
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
import view.UserInterface;

import java.util.ArrayList;
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
    ArrayList<DBObject> dbObjects = new ArrayList<>();
    public static Chat getInstance() {
        if (singleToneClass == null) singleToneClass = new Chat();
        return singleToneClass;
    }

    public void run(Stage stage) {
        GridPane gridPane = CreateGrid.createGridPane();
        TextArea textAreaAllChats = new TextArea();
        Text welcomeText = new Text("Chat Menu");
        Text onlineUsers = new Text("Online gamers: "+SendReceiveData.sendReceiveData("showAllOnline"));
        welcomeText.getStyleClass().add("welcome-text");
        onlineUsers.getStyleClass().add("welcome-text");
        gridPane.addRow(1, welcomeText);
        gridPane.addRow(2, onlineUsers);

        Label lblName = new Label("send message");
        Label lblName2 = new Label("your message");
        Label lblChats = new Label("all chats");
        txtMessage.setPromptText("your message...");
        gridPane.addRow(3, lblName2, txtMessage);

        Button back = new Button("back");
        back.setOnMouseClicked(e -> {
            Main.stage.setScene(MainMenu.mainMenuScene);
        });
        Button refresh = new Button("refresh");
        refresh.setOnMouseClicked(e -> {
            textAreaAllChats.clear();
            dbObjects.clear();
            printChats(textAreaAllChats);
        });
        Button delete = new Button("delete");
        delete.setOnMouseClicked(e -> {
            deleteMessage(textAreaAllChats);
        });

        Button update = new Button("Edit Message");
        update.setOnMouseClicked(e -> {
            updateMessage(textAreaAllChats);
        });
        Button sendBtn = new Button("Send");
        gridPane.addRow(4, lblName, sendBtn);

        gridPane.addRow(5, lblChats, textAreaAllChats);
        gridPane.addRow(6, back, refresh);
        gridPane.addRow(7, delete, update);
        sendBtn.setOnMouseClicked(e -> {
            DBObject messages = new BasicDBObject("_id", SendReceiveData.token)
                    .append("name", ProgramController.currUser.getNickName()).append("message", txtMessage.getText());
            collection.insert(messages);
            printChats(textAreaAllChats);
            txtMessage.clear();
        });

        Scene scene = new Scene(gridPane, 800, 650);
        impExpScene = scene;
        String style = Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
        stage.centerOnScreen();
        stage.setScene(scene);
    }

    private void updateMessage(TextArea textAreaAllChats) {
        UserInterface.printResponse("please select the message you want to Edit");
        String selected = textAreaAllChats.getSelectedText();
        Matcher matcher = Regex.getMatcher(selected,"ID: (\\d+)");
        if(matcher.find()){
            UserInterface.printResponse("please enter your message id");
            String id = UserInterface.getUserInput();
            UserInterface.printResponse("please enter your new message");
            String newMessage = UserInterface.getUserInput();
            DBObject dbObject = dbObjects.get(Integer.parseInt(id));
            String message = dbObject.toString();
            Matcher matcherName = Regex.getMatcher(message,"\"name\": \"(.+)\",");
            Matcher matcherMessage = Regex.getMatcher(message,"\"message\": \"(.+)\"");
            if(matcherName.find() && matcherMessage.find()){
                String name = matcherName.group(1);
                String message2 = matcherMessage.group(1);
                if(!name.equals(ProgramController.currUser.getNickName()))
                    UserInterface.printResponse("you only can delete your own message");
                else{
                    BasicDBObject query = new BasicDBObject();
                    query.append("message", message2); // (1)

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put("message", newMessage); // (2)

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument); // (3)

                    collection.update(query,updateObject);
                    System.out.println("Document updated successfully...");
                    textAreaAllChats.setText(textAreaAllChats.getText().replace(message2,newMessage));
                }
            }
            textAreaAllChats.clear();
            dbObjects.clear();
            printChats(textAreaAllChats);
        }
        textAreaAllChats.clear();
        printALLChats(textAreaAllChats);
    }

    private void deleteMessage(TextArea textAreaAllChats) {
        UserInterface.printResponse("please select the message you want to delete");
        String selected = textAreaAllChats.getSelectedText();
        Matcher matcher = Regex.getMatcher(selected,"ID: (\\d+)");
        if(matcher.find()){
            String id = UserInterface.getUserInput();
            DBObject dbObject = dbObjects.get(Integer.parseInt(id));
            String message = dbObject.toString();
            Matcher matcherName = Regex.getMatcher(message,"\"name\": \"(.+)\",");
            Matcher matcherMessage = Regex.getMatcher(message,"\"message\": \"(.+)\"");
            if(matcherName.find() && matcherMessage.find()){
                String name = matcherName.group(1);
                String message2 = matcherMessage.group(1);
                if(!name.equals(ProgramController.currUser.getNickName()))
                    UserInterface.printResponse("you only can delete your own message");
                else{
                    BasicDBObject query = new BasicDBObject();
                    query.append("message", message2);
                    collection.remove(query);
                    System.out.println("Document deleted successfully...");
                    textAreaAllChats.setText(textAreaAllChats.getText().replace(selected,""));
                }
            }
        }
        textAreaAllChats.clear();
        dbObjects.clear();
        printChats(textAreaAllChats);
    }

    private void printChats(TextArea textAreaAllChats) {
        for (DBObject dbObject : collection.find()) {
            if(dbObjects.contains(dbObject))
                continue;
            else
                dbObjects.add(dbObject);
            print(textAreaAllChats, dbObject);
        }
    }
    private void printALLChats(TextArea textAreaAllChats) {
        for (DBObject dbObject : collection.find()) {
            print(textAreaAllChats, dbObject);
        }
    }

    private void print(TextArea textAreaAllChats, DBObject dbObject) {
        String message = dbObject.toString();
        System.out.println(message);
        Matcher matcherName = Regex.getMatcher(message,"\"name\": \"(.+)\",");
        Matcher matcherMessage = Regex.getMatcher(message,"\"message\": \"(.+)\"");
        if(matcherMessage.find() && matcherName.find()){
            textAreaAllChats.appendText("ID: "+dbObjects.indexOf(dbObject)+" nickName: " + matcherName.group(1).replace("\"",""));
            System.out.println(matcherMessage.group(1));
            textAreaAllChats.appendText(" message: " + matcherMessage.group(1)+"\n");
        }
    }
}
