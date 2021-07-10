package view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class CreateGrid {
    public static GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }
}

