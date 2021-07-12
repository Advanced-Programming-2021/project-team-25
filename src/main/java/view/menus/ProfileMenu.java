package view.menus;


import com.google.gson.Gson;
import controllers.ProgramController;
import controllers.Regex;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.User;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import view.CreateGrid;
import view.Main;
import view.SendReceiveData;
import view.UserInterface;

public class ProfileMenu {
    private static ProfileMenu singleToneClass = null;
    public User currUser;
    ImageView imageViewProfile;
    Image userImageProfile;
    public static Scene profileScene;
    public ProfileMenu(User currUser) {
        this.currUser = currUser;
    }
    Canvas canvas = new Canvas(300,300);
    GraphicsContext graphic = canvas.getGraphicsContext2D();

    public static ProfileMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new ProfileMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }

    public void runProfileMenu(Stage stage){
        GridPane gridPane = CreateGrid.createGridPane();

        userImageProfile = new Image("file:"+currUser.getUsername()+".png");

        imageViewProfile = new ImageView(userImageProfile);
        imageViewProfile.setFitHeight(100);
        imageViewProfile.setFitWidth(100);

        Label lblUser = new Label("User LoggedIn - username:"+currUser.getUsername()+" Nickname :"+currUser.getNickName());

        Button btnUploadImage = new Button("new Image");
        Button btnChangePassword = new Button("change Password");
        Button btnChangeNickname = new Button("change Nickname");
        Button btnBack = new Button("back");

        Label lblChangeNickname = new Label("change nickname");
        TextField txtNickname = new TextField();
        txtNickname.setPromptText("new nickname...");

        Label lblOldPassword = new Label("Old Password");
        TextField txtOldPassword = new TextField();
        txtOldPassword.setPromptText("old password...");

        Label lblNewPassword = new Label("new Password");
        TextField txtNewPassword = new TextField();
        txtNewPassword.setPromptText("new password...");

        gridPane.addRow(0,imageViewProfile,btnUploadImage);
        gridPane.addRow(1,lblUser);
        gridPane.addRow(2,lblChangeNickname,txtNickname);
        gridPane.addRow(3,lblOldPassword,txtOldPassword);
        gridPane.addRow(4,lblNewPassword,txtNewPassword);
        gridPane.addRow(5,btnBack,btnChangeNickname,btnChangePassword);

        btnBack.setOnMouseClicked(e->{
            Main.stage.setScene(MainMenu.mainMenuScene);
        });

        btnChangeNickname.setOnMouseClicked(e->{
            String nickname = txtNickname.getText();
            if(nickname.isEmpty() || nickname.isBlank())
                UserInterface.printResponse("please fill all textFields");
            else {
                String result = SendReceiveData.sendReceiveData("profile change --nickname "+
                        nickname);
                currUser.setNickName(txtNickname.getText());
                checkResult(result);
            }
            runProfileMenu(stage);
        });

        btnChangePassword.setOnMouseClicked(e->{
            String currentPass = txtOldPassword.getText();
            String newPass = txtNewPassword.getText();
            if(currentPass.isEmpty() || currentPass.isBlank() || newPass.isEmpty() || currentPass.isBlank())
                UserInterface.printResponse("please fill all textFields");
            else{
                String result = SendReceiveData.sendReceiveData("profile change --password --current "+
                        currentPass+
                        " --new "+newPass);
                if(result!= null && result.contains("success")){
                    currUser.setPassword(txtNewPassword.getText());
                }
                checkResult(result);
            }
            runProfileMenu(stage);
        });

        btnUploadImage.setOnMouseClicked(e->{
            new FileChooserSample().start(stage);
        });

        Scene scene = new Scene(gridPane,800,650);
        String style= Objects.requireNonNull(this.getClass().getResource("profile/profile.css")).toExternalForm();
        scene.getStylesheets().add(style);
        profileScene = scene;
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void checkResult(String result) {
        if (Objects.isNull(result) || result.isBlank() || result.isEmpty())
            LoginMenu.showAlert(null, "An Error occurred");
        else if (result.startsWith("error")) {
            Matcher matcherDesc = Regex.getMatcher(result, "description=\"(.+)\"");
            if (matcherDesc.find())
                LoginMenu.showAlert(null, matcherDesc.group(1));
        } else if (result.startsWith("success")) {
            Matcher matcherDesc = Regex.getMatcher(result, "description=\"(.+)\"");
            if (matcherDesc.find()) {
                LoginMenu.showAlert(null, matcherDesc.group(1));
            }
        }
    }

    public class FileChooserSample {

        public void start(final Stage stage) {
            stage.setTitle("update image");

            final FileChooser fileChooser = new FileChooser();
            final Button openButton = new Button("Open a Picture...");
            final Button openMultipleButton = new Button("Open Pictures...");

            openButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            configureFileChooser(fileChooser);
                            File file = fileChooser.showOpenDialog(stage);
                            if (file != null) {
                                openFile(file);
                            }
                            //refresh image!
                            userImageProfile = new Image("file:"+currUser.getUsername()+".png");
                            imageViewProfile.setImage(userImageProfile);
                            stage.setScene(profileScene);
                        }
                    });

            openMultipleButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            configureFileChooser(fileChooser);
                            List<File> list =
                                    fileChooser.showOpenMultipleDialog(stage);
                            if (list != null) {
                                for (File file : list) {
                                    openFile(file);
                                }
                            }
                        }
                    });


            final GridPane inputGridPane = new GridPane();
            Button btnBack = new Button("back");

            btnBack.setOnMouseClicked(e->{
                Main.stage.setScene(MainMenu.mainMenuScene);
            });

            GridPane.setConstraints(openButton, 0, 1);

            inputGridPane.setHgap(6);
            inputGridPane.setVgap(6);
            inputGridPane.getChildren().addAll(openButton);

            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(inputGridPane,btnBack);
            rootGroup.setPadding(new Insets(12, 12, 12, 12));
            String style= Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();

            Scene scene = new Scene(rootGroup,300,300);
            scene.getStylesheets().add(style);
            stage.setScene(scene);
            stage.show();
        }

        private void configureFileChooser(
                final FileChooser fileChooser) {
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        }

        private void openFile(File file) {
            System.out.println(file.getName());
            Path from = Paths.get(file.toURI());
            try {
                String backGroundPath = ProgramController.currUser.getUsername() + ".png";
                Files.copy(from, Path.of(backGroundPath), StandardCopyOption.REPLACE_EXISTING);
                //Image backGroundIMG = new Image("admin.png");
                UserInterface.printResponse("your image uploaded successfully!");
                //graphic.drawImage(backGroundIMG,0,0,100,100);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
