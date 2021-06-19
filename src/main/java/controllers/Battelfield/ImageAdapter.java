package controllers.Battelfield;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageAdapter {
    //5 | 3 | 1 | 2 | 4
    public static void setMonsterOn5(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85, 230, 75, 80);
    }

    public static void setMonsterOn4(GraphicsContext mainGraphic, Image image) {
        final int width = 68;
        mainGraphic.drawImage(image, 85 + width * 4, 230, 75, 80);
    }

    public static void setMonsterOn3(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68, 230, 75, 80);
    }

    public static void setMonsterOn2(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68 * 3, 230, 75, 80);
    }

    public static void setMonsterOn1(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68 * 2, 230, 75, 80);
    }

    public static void setSpellOrTrapOn5(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85, 230 + 82, 75, 80);
    }

    public static void setSpellOrTrapOn4(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68 * 4, 230 + 82, 75, 80);
    }

    public static void setSpellOrTrapOn3(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68, 230 + 82, 75, 80);
    }

    public static void setSpellOrTrapOn2(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68 * 3, 230 + 82, 75, 80);
    }

    public static void setSpellOrTrapOn1(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 85 + 68 * 2, 230 + 82, 75, 80);
    }
}
