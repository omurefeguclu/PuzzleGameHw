package com.omurefeguclu.puzzlegamehw;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class NumberPuzzle implements Puzzle {
    private int puzzleSize;

    public NumberPuzzle(int puzzleSize)
    {
        this.puzzleSize = puzzleSize;
    }

    public int getPuzzleSize(){
        return puzzleSize;
    }
    public Button getPuzzleButton(int index){
        Button button = new Button();


        button.setStyle("-fx-background-color: #F1F5F9;");
        button.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
        button.setTextOverrun(OverrunStyle.CLIP);
        button.setText(STR."\{index + 1}");

        System.out.println("puzzleSize : " + this.puzzleSize + ", number: " + (index + 1));

        return button;
    }

}
