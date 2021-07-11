package view.menus;

import controllers.Regex;
import controllers.ShowCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import org.json.simple.JSONObject;

public class ImportExport {
    private static ImportExport singleToneClass = null;
    private File fileForSave = null;
    private Scene impExpScene;
    private TextArea textArea = new TextArea();
    TextField txtName = new TextField();
    private Stage currStage;
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
                getFile(stage);
        });
        Scene scene = new Scene(gridPane, 800, 650);
        impExpScene = scene;
        String style = Objects.requireNonNull(this.getClass().getResource("duel/duel.css")).toExternalForm();
        scene.getStylesheets().add(style);
        stage.centerOnScreen();
        stage.setScene(scene);
    }

    public void export(String name, Stage stage) {

    }
    private void saveJson(String name) {
        if (Card.allCards.get(name).getCardsType() == Type.MONSTER) {
            Monster monster = (Monster) Card.allCards.get(name);
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            //Inserting key-value pairs into the json object
            jsonObject.put("Name:", name);
            jsonObject.put("Level:", monster.getLevel());
            jsonObject.put("Attribute:", monster.getAttribute());
            jsonObject.put("Monster Type:", monster.getMonsterType());
            jsonObject.put("Type:", monster.getCardTypeInExel());
            jsonObject.put("ATK:", monster.getAttack());
            jsonObject.put("DEF:", monster.getDefence());
            jsonObject.put("Description:", monster.getDescription());
            jsonObject.put("Price:", monster.getPrice());
            try {
                FileWriter file = new FileWriter(name + ".json");
                file.write(jsonObject.toJSONString());
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("JSON file created: "+jsonObject);

        } else {
            SpellAndTrap spellAndTrap = (SpellAndTrap) Card.allCards.get(name);
            if (spellAndTrap.getCardsType() == Type.SPELL) {
                //Creating a JSONObject object
                JSONObject jsonObject = new JSONObject();
                //Inserting key-value pairs into the json object
                jsonObject.put("Name:", name);
                jsonObject.put("Type:", "Trap");
                SimilarPartInSpellAndTrapJson(name, spellAndTrap, jsonObject);
            } else {
                //Creating a JSONObject object
                JSONObject jsonObject = new JSONObject();
                //Inserting key-value pairs into the json object
                jsonObject.put("Name:", name);
                jsonObject.put("Type:", "Spell");
                SimilarPartInSpellAndTrapJson(name, spellAndTrap, jsonObject);
            }
        }
    }

    private void SimilarPartInSpellAndTrapJson(String name, SpellAndTrap spellAndTrap, JSONObject jsonObject) {
        jsonObject.put("Icon (Property):", spellAndTrap.getIcon());
        jsonObject.put("Status:", spellAndTrap.getStatus());
        jsonObject.put("Description:", spellAndTrap.getDescription());
        jsonObject.put("Price:", spellAndTrap.getPrice());
        try {
            FileWriter file = new FileWriter(name + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: "+jsonObject);
    }

    private void saveCsv(String name) {
        if (Card.allCards.get(name).getCardsType() == Type.MONSTER) {
            Monster monster = (Monster) Card.allCards.get(name);
            try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {

                StringBuilder sb = new StringBuilder();
                sb.append("Name,");
                sb.append(',');
                sb.append("name");
                sb.append('\n');

                sb.append("Level");
                sb.append(',');
                sb.append(monster.getLevel());
                sb.append('\n');

                sb.append("Attribute");
                sb.append(',');
                sb.append(monster.getAttribute());
                sb.append('\n');

                sb.append("Monster Type");
                sb.append(',');
                sb.append(monster.getMonsterType());
                sb.append('\n');

                sb.append("Type");
                sb.append(',');
                sb.append(monster.getCardTypeInExel());
                sb.append('\n');

                sb.append("ATK");
                sb.append(',');
                sb.append(monster.getAttack());
                sb.append('\n');

                sb.append("DEF");
                sb.append(',');
                sb.append(monster.getDefence());
                sb.append('\n');

                sb.append("Description");
                sb.append(',');
                sb.append(monster.getDescription());
                sb.append('\n');

                sb.append("Price");
                sb.append(',');
                sb.append(monster.getPrice());
                sb.append('\n');
                writer.write(sb.toString());

                System.out.println("done!");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else {
            SpellAndTrap spellAndTrap = (SpellAndTrap) Card.allCards.get(name);
            if (spellAndTrap.getCardsType() == Type.SPELL) {
                try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {

                    StringBuilder sb = new StringBuilder();
                    sb.append("Name,");
                    sb.append(',');
                    sb.append("name");
                    sb.append('\n');

                    sb.append("Type");
                    sb.append(',');
                    sb.append("Trap");
                    similarPartInSpellAndTrap(spellAndTrap, writer, sb);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {

                    StringBuilder sb = new StringBuilder();
                    sb.append("Name,");
                    sb.append(',');
                    sb.append("name");
                    sb.append('\n');

                    sb.append("Type");
                    sb.append(',');
                    sb.append("Spell");
                    similarPartInSpellAndTrap(spellAndTrap, writer, sb);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void similarPartInSpellAndTrap(SpellAndTrap spellAndTrap, PrintWriter writer, StringBuilder sb) {
        sb.append('\n');

        sb.append("Icon (Property)");
        sb.append(',');
        sb.append(spellAndTrap.getIcon());
        sb.append('\n');

        sb.append("Description");
        sb.append(',');
        sb.append(spellAndTrap.getDescription());
        sb.append('\n');

        sb.append("Status");
        sb.append(',');
        sb.append(spellAndTrap.getStatus());
        sb.append('\n');

        sb.append("Price");
        sb.append(',');
        sb.append(spellAndTrap.getPrice());
        sb.append('\n');
        writer.write(sb.toString());

        System.out.println("done!");
    }

    private void saveTxt(String name) {
        try {
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
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
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
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("TXT", "*.txt"),
                        new FileChooser.ExtensionFilter("Csv", "*.csv"),
                        new FileChooser.ExtensionFilter("Json", "*.json")
                );

                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);
                fileForSave = file;
                if (file != null) {
                    if(fileForSave.getName().contains(".txt")){
                        saveTxt(txtName.getText());
                    }
                    else if(fileForSave.getName().contains(".csv")){
                        saveCsv(txtName.getText());
                    }
                    else if(fileForSave.getName().contains(".json")){
                        saveJson(txtName.getText());
                    }

                }
                currStage.close();
                txtName.clear();
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttonBack, buttonSave);

        root.getChildren().add(vBox);
        String style = Objects.requireNonNull(this.getClass().getResource("login/Login.css")).toExternalForm();
        Scene scene = new Scene(root, 300, 300);
        scene.getStylesheets().add(style);
        subStage stage1 = new subStage("Import/Export",scene);
        currStage = stage1.getStage();
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
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("Csv", "*.csv"),
                new FileChooser.ExtensionFilter("Json", "*.json")
        );
    }

    private void openFile(File file) {
        try {
            Scanner myReader = new Scanner(file);
            String data = myReader.nextLine();
            Matcher nameMatcher = Regex.getMatcher(data, Regex.extractName);
            String nameInFile = nameMatcher.group(1);
            if (!file.getName().equals(nameInFile + ".txt")) UserInterface.printResponse("Bad File chossen");
            else {
                if(file.getName().contains(".txt"))
                    ShowCard.Cards(file.getName().replace(".txt", ""));
                else if(file.getName().contains(".csv"))
                    ShowCard.Cards(file.getName().replace(".csv", ""));
                else if(file.getName().contains(".json"))
                    ShowCard.Cards(file.getName().replace(".json", ""));
            }
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