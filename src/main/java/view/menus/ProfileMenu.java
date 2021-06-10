package view.menus;


import controllers.ProgramController;
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
import view.UserInterface;

public class ProfileMenu {
    public User currUser;
    private static ProfileMenu singleToneClass = null;
    public static Scene profileScene;
    public ProfileMenu(User currUser) {
        this.currUser = currUser;
    }

    public static ProfileMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new ProfileMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }

    public void runProfileMenu(Stage stage){
        GridPane gridPane = CreateGrid.createGridPane();

        Image userImageProfile = new Image("file:admin.png");

        ImageView imageView = new ImageView(userImageProfile);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

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
        txtNickname.setPromptText("old password...");

        Label lblNewPassword = new Label("new Password");
        TextField txtNewPassword = new TextField();
        txtNickname.setPromptText("new password...");

        gridPane.addRow(0,imageView,btnUploadImage);
        gridPane.addRow(1,lblUser);
        gridPane.addRow(2,lblChangeNickname,txtNickname);
        gridPane.addRow(3,lblOldPassword,txtOldPassword);
        gridPane.addRow(4,lblNewPassword,txtNewPassword);
        gridPane.addRow(5,btnBack,btnChangeNickname,btnChangePassword);

        btnBack.setOnMouseClicked(e->{
            new MainMenu().start();
        });

        btnChangeNickname.setOnMouseClicked(e->{
            currUser.setNickName(txtNickname.getText());
            UserInterface.printResponse("User nickname changed successfully");
        });

        btnChangePassword.setOnMouseClicked(e->{
            String currentPass = txtOldPassword.getText();
            String newPass = txtNewPassword.getText();
            if(currentPass.isEmpty() || currentPass.isBlank() || newPass.isEmpty() || currentPass.isBlank())
                UserInterface.printResponse("please fill all textFields");
            else if(!currUser.getPassword().equals(currentPass))
                UserInterface.printResponse("current password is invalid");
            else if(currUser.getPassword().equals(newPass))
                UserInterface.printResponse("new password and old password are same!");
            else{
                currUser.setPassword(txtNewPassword.getText());
                UserInterface.printResponse("password changed successfully!");
            }
        });

        btnUploadImage.setOnMouseClicked(e->{
            new FileChooserSample().start(stage);
        });

        Scene scene = new Scene(gridPane ,900 ,600);
        String style= Objects.requireNonNull(this.getClass().getResource("profile/profile.css")).toExternalForm();
        scene.getStylesheets().add(style);
        profileScene = scene;
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static class FileChooserSample extends Application {

        @Override
        public void start(final Stage stage) {
            stage.setTitle("File Chooser Sample");

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

            GridPane.setConstraints(openButton, 0, 1);
            inputGridPane.setHgap(6);
            inputGridPane.setVgap(6);
            inputGridPane.getChildren().addAll(openButton);

            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(inputGridPane);
            rootGroup.setPadding(new Insets(12, 12, 12, 12));

            stage.setScene(new Scene(rootGroup));
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
                UserInterface.printResponse("your image uploaded successfully!");
                Files.copy(from, Path.of(ProgramController.currUser.getUsername() + "png"), StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
