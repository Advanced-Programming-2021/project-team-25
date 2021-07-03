package view.menus;

import controllers.ProgramController;
import controllers.Regex;
import controllers.ShowCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import view.CreateGrid;
import view.Main;
import view.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ImportExport {
    private static ImportExport singleToneClass = null;
    private File fileForSave = null;
    private Scene impExpScene;
    private TextArea textArea = new TextArea();

    public static ImportExport getInstance() {
        if (singleToneClass == null) singleToneClass = new ImportExport();
        return singleToneClass;
    }

    public void run(Stage stage) {
        GridPane gridPane = CreateGrid.createGridPane();

        Text welcomeText = new Text("Import Export Menu");
        welcomeText.getStyleClass().add("welcome-text");
        gridPane.addRow(1, welcomeText);
        Label lblName = new Label("name of card");
        Label showCard = new Label("Selected Card is: ");
        TextField txtName = new TextField();
        txtName.setPromptText("card Name...");
        gridPane.addRow(2, lblName, txtName);

        Button back = new Button("back");
        back.setOnMouseClicked(e -> {
            Main.stage.setScene(MainMenu.mainMenuScene);
        });

        Button importBtn = new Button("import");
        Button exportBtn = new Button("export");
        gridPane.addRow(3, importBtn, exportBtn);

        gridPane.addRow(4, showCard, textArea);
        gridPane.addRow(5, back);
        importBtn.setOnMouseClicked(e -> {
            importFile(stage);
        });
        exportBtn.setOnMouseClicked(e -> {
            if (txtName.getText().isEmpty() || txtName.getText().isBlank()) {
                UserInterface.printResponse("please enter a name");
            } else
                export(txtName.getText(), stage);
        });
        Scene scene = new Scene(gridPane, 800, 650);
        impExpScene = scene;
        String style = Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
        stage.centerOnScreen();
        stage.setScene(scene);
    }

    public void export(String name, Stage stage) {
        if (!Card.allCards.containsKey(name)) UserInterface.printResponse("there is no card with this name");
        else {
            getFile(stage);

            if (Card.allCards.get(name).getCardsType() == Type.MONSTER) {
                Monster monster = (Monster) Card.allCards.get(name);
                try {
                    FileWriter myWriter = new FileWriter(name + ".txt");
                    myWriter.write("Name: " + name + "\n" +
                            "Level: " + monster.getLevel() + "\n" +
                            "Attribute: " + monster.getAttribute() + "\n" +
                            "Monster Type: " + monster.getMonsterType() + "\n" +
                            "Type: " + monster.getCardTypeInExel() + "\n" +
                            "ATK: " + monster.getAttack() + "\n" +
                            "DEF: " + monster.getDefence() + "\n" +
                            "Description: " + monster.getDescription() + "\n" +
                            "Price: " + monster.getPrice());
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else {
                SpellAndTrap spellAndTrap = (SpellAndTrap) Card.allCards.get(name);
                if (spellAndTrap.getCardsType() == Type.SPELL) {
                    try {
                        FileWriter myWriter = new FileWriter(name + ".txt");
                        myWriter.write("Name: " + name + "\n" +
                                "Type: Spell\n" +
                                "Icon (Property): " + spellAndTrap.getIcon() + "\n" +
                                "Description: " + spellAndTrap.getDescription() + "\n" +
                                "Status: " + spellAndTrap.getStatus() + "\n" +
                                "Price: " + spellAndTrap.getPrice());
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FileWriter myWriter = new FileWriter(name + ".txt");
                        myWriter.write("Name: " + name + "\n" +
                                "Type: Trap\n" +
                                "Icon (Property): " + spellAndTrap.getIcon() + "\n" +
                                "Description: " + spellAndTrap.getDescription() + "\n" +
                                "Status: " + spellAndTrap.getStatus() + "\n" +
                                "Price: " + spellAndTrap.getPrice());
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getFile(Stage stage) {
        stage.setTitle("Export");
        Group root = new Group();

        Button buttonSave = new Button("Save");
        Button buttonBack = new Button("Back");
        buttonBack.setOnMouseClicked(e -> {
            stage.setScene(impExpScene);
        });
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);

                if (file != null) {
                    fileForSave = file;
                }
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttonBack, buttonSave);

        root.getChildren().add(vBox);
        String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
        Scene scene = new Scene(root, 300, 300);
        scene.getStylesheets().add(style);
        stage.setScene(scene);

        stage.show();

    }

    public void importFile(final Stage stage) {
        stage.setTitle("Import");

        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Open a txt file..");

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            openFile(file);
                        }
                        stage.setScene(impExpScene);
                    }
                });

        final GridPane inputGridPane = new GridPane();
        Button btnBack = new Button("back");

        btnBack.setOnMouseClicked(e -> {
            stage.setScene(impExpScene);
        });

        GridPane.setConstraints(openButton, 0, 1);

        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane, btnBack);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
        String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();

        Scene scene = new Scene(rootGroup, 300, 300);
        scene.getStylesheets().add(style);
        stage.setScene(scene);
        stage.show();
    }

    private void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }

    private void openFile(File file) {
        try {
            Scanner myReader = new Scanner(file);
            String data = myReader.nextLine();
            Matcher nameMatcher = Regex.getMatcher(data, Regex.extractName);
            String nameInFile = nameMatcher.group(1);
            if (!file.getName().equals(nameInFile + ".txt")) UserInterface.printResponse("Bad File chossen");
            else ShowCard.Cards(file.getName().replace(".txt", ""));
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public TextArea getTextArea() {
        return textArea;
    }
}
