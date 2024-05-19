package com.omurefeguclu.puzzlegamehw;

import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ImagePuzzle implements Puzzle {
    private final int puzzleSize;
    private final Image image;

    public ImagePuzzle(String imagePath, int puzzleSize)
    {
        this.image = new Image(imagePath);
        this.puzzleSize = puzzleSize;
    }

    public int getPuzzleSize(){
        return puzzleSize;
    }

    public PuzzlePieceButton getPuzzleButton(int index){
        int colIndex = index % puzzleSize;
        int rowIndex = index / puzzleSize;

        PixelReader reader = this.image.getPixelReader();
        int width = (int)(image.getWidth() / puzzleSize);
        int height = (int)(image.getHeight() / puzzleSize);

        WritableImage tileImage = new WritableImage(reader, colIndex * width, rowIndex * height, width, height);

        PuzzlePieceButton button = new PuzzlePieceButton(tileImage, colIndex, rowIndex);

        //button.setStyle("-fx-background-size: 100;");
        //button.setStyle("-fx-background-position: center, center, center, center, center, center;");

        return button;
    }
}
