package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreateGrid {

    static public GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

        return gridPane;
    }

    private void ImageDeveloper(Rectangle rectangle) {
        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\noroo\\IdeaProjects\\Pacman\\src\\heart.png");
            Image image = new Image(inputStream);
            ImagePattern backGround = new ImagePattern(image);
            rectangle.setFill(backGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
