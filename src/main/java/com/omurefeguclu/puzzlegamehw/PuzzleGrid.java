package com.omurefeguclu.puzzlegamehw;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.List;

public class PuzzleGrid extends GridPane {

    private final int PUZZLE_SIZE;

    public PuzzleGrid(Puzzle puzzle, double layoutSize)
    {
        this.PUZZLE_SIZE = puzzle.getPuzzleSize();

        this.setPrefSize(layoutSize, layoutSize);

        int pieceCount = (puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - 1;
        double pieceSize = layoutSize / puzzle.getPuzzleSize();

        for(int i = 0; i < pieceCount; i++) {
            PuzzlePieceButton button = puzzle.getPuzzleButton(i);

            button.setPrefSize(pieceSize, pieceSize);

            this.add(button, button.correctPositionX, button.correctPositionY);
        }
    }

    public PuzzlePieceButton getButton(int x, int y) {
        PuzzlePieceButton result = null;
        ObservableList<Node> children = this.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x) {
                result = (PuzzlePieceButton) node;
                break;


            }
        }

        return result;
    }

    public boolean isCorrect(){
        ObservableList<Node> children = this.getChildrenUnmodifiable();

        for (Node node : children) {
            PuzzlePieceButton button = (PuzzlePieceButton) node;

            if (GridPane.getRowIndex(node) != button.correctPositionY || GridPane.getColumnIndex(node) != button.correctPositionX) {
                return false;
            }
        }

        return true;
    }
}
