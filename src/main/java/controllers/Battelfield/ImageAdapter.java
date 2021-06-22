package controllers.Battelfield;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageAdapter {
    private final static int widthImg = 75;
    private final static int heightImg = 80;
    private final static int heightFromTopTurnImg = 222;
    private final static int heightFromTopOpponentImg = 138;
    private final static int widthFromLeftImg = 85;
    private final static int widthFromLastImg = 68;
    private final static int heightFromLastImg = 82;
    //5 | 3 | 1 | 2 | 4
    public static void setMonsterOn5(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg, heightFromTopTurnImg, widthImg, heightImg);
    }

    public static void setMonsterOn4(GraphicsContext mainGraphic, Image image) {
        final int width = widthFromLastImg;
        mainGraphic.drawImage(image, widthFromLeftImg + width * 4, heightFromTopTurnImg, widthImg, heightImg);
    }

    public static void setMonsterOn3(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg, heightFromTopTurnImg, widthImg, heightImg);
    }

    public static void setMonsterOn2(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 3, heightFromTopTurnImg, widthImg, heightImg);
    }

    public static void setMonsterOn1(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 2, heightFromTopTurnImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn5(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg, heightFromTopTurnImg + heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn4(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 4, heightFromTopTurnImg + heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn3(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg, heightFromTopTurnImg + heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn2(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 3, heightFromTopTurnImg + heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn1(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 2, heightFromTopTurnImg + heightFromLastImg, widthImg, heightImg);
    }

    public static void setMonsterOn4Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg, heightFromTopOpponentImg, widthImg, heightImg);
    }

    public static void setMonsterOn5Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 4, heightFromTopOpponentImg, widthImg, heightImg);
    }

    public static void setMonsterOn2Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg, heightFromTopOpponentImg, widthImg, heightImg);
    }

    public static void setMonsterOn3Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 3, heightFromTopOpponentImg, widthImg, heightImg);
    }

    public static void setMonsterOn1Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 2, heightFromTopOpponentImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn4Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg, heightFromTopOpponentImg - heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn5Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 4, heightFromTopOpponentImg - heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn2Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg, heightFromTopOpponentImg - heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn3Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 3, heightFromTopOpponentImg - heightFromLastImg, widthImg, heightImg);
    }

    public static void setSpellOrTrapOn1Rival(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, widthFromLeftImg + widthFromLastImg * 2, heightFromTopOpponentImg - heightFromLastImg, widthImg, heightImg);
    }

    public static void setCardOnTurnFieldZone(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 12, heightFromTopTurnImg, widthImg, heightImg);
    }

    public static void setCardOnOpponentFieldZone(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 16 + widthFromLastImg * 6, heightFromTopOpponentImg, widthImg, heightImg);
    }

    public static void setCardOnOpponentGraveYard(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 12, heightFromTopOpponentImg-5, widthImg, heightImg);
    }

    public static void setCardOnTurnGraveYard(GraphicsContext mainGraphic, Image image) {
        mainGraphic.drawImage(image, 18 + widthFromLastImg * 6, heightFromTopTurnImg+10, widthImg, heightImg);
    }
}
