package com.omurefeguclu.puzzlegamehw;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainMenuController {

    private int activePuzzleSliderIndex = 0;
    public int getSelectedPuzzleIndex() {
        return activePuzzleSliderIndex;
    }

    @FXML private void initialize(){
        Puzzle[] puzzles = GameManager.getInstance().getPuzzles();

        for (int x = 0; x < puzzles.length; x++) {
            Puzzle puzzle = puzzles[x];

            if(puzzle == null)
            {
                continue;
            }

            GridPane puzzleGrid = new GridPane();

            /*puzzleGrid.setHgap(1);
            puzzleGrid.setVgap(1);*/
            puzzleGrid.setPrefSize(puzzlesContainer.getPrefWidth(), puzzlesContainer.getPrefHeight());


            int pieceCount = (puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - 1;
            double pieceSize = puzzlesContainer.getPrefWidth() / puzzle.getPuzzleSize();

            for(int i = 0; i < pieceCount; i++) {
                Button button = puzzle.getPuzzleButton(i);

                int rowIndex = i / puzzle.getPuzzleSize();
                int columnIndex = i % puzzle.getPuzzleSize();

                button.setMinSize(pieceSize, pieceSize);

                puzzleGrid.add(button, columnIndex, rowIndex);
            }

            puzzlesContainer.getChildren().add(puzzleGrid);
        }

        refreshPuzzleSlider();
    }

    @FXML private void advancePuzzle(){
        activePuzzleSliderIndex++;

        if(activePuzzleSliderIndex >= puzzlesContainer.getChildren().size())
            activePuzzleSliderIndex = 0;

        refreshPuzzleSlider();
    }
    @FXML private void rollbackPuzzle(){
        activePuzzleSliderIndex--;

        if(activePuzzleSliderIndex < 0)
            activePuzzleSliderIndex = puzzlesContainer.getChildren().size() - 1;

        refreshPuzzleSlider();
    }
    private void refreshPuzzleSlider() {
        for(int i = 0; i < puzzlesContainer.getChildren().size(); i++) {

            puzzlesContainer.getChildren().get(i).setVisible(activePuzzleSliderIndex == i);
        }
    }

    @FXML private AnchorPane puzzlesContainer;
    @FXML public Button playButton;
}