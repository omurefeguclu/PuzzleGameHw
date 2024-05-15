package com.omurefeguclu.puzzlegamehw;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class PuzzlePieceButton extends Button {

    public int positionX, positionY;
    public int correctPositionX, correctPositionY;


    public PuzzlePieceButton(String num, int correctPositionX, int correctPositionY) {

        this.setStyle("-fx-background-color: #1D4ED8; -fx-background-radius: 0; -fx-border-radius: 0; -fx-font-family: SansSerif; -fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #fff;");
        this.setText(num);

        InitPosition(correctPositionX, correctPositionY);
    }
    public PuzzlePieceButton(Image image, int correctPositionX, int correctPositionY) {

        BackgroundImage tileBackgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));

        this.setBackground(new Background(tileBackgroundImage));

        InitPosition(correctPositionX, correctPositionY);
    }

    private void InitPosition(int correctPositionX, int correctPositionY) {
        this.correctPositionX = correctPositionX;
        this.correctPositionY = correctPositionY;

        this.positionX = correctPositionX;
        this.positionY = correctPositionY;
    }

    public void SetPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;

        GridPane.setConstraints(this, x, y);
    }
}
