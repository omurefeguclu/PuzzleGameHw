package com.omurefeguclu.puzzlegamehw;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class NumberPuzzle implements Puzzle {
    private final int puzzleSize;

    public NumberPuzzle(int puzzleSize)
    {
        this.puzzleSize = puzzleSize;
    }

    public int getPuzzleSize(){
        return puzzleSize;
    }
    public PuzzlePieceButton getPuzzleButton(int index){
        PuzzlePieceButton button = new PuzzlePieceButton(STR."\{index + 1}", index % puzzleSize, index / puzzleSize);

        return button;
    }

}
