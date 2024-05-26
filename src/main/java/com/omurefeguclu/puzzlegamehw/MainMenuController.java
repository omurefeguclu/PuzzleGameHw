package com.omurefeguclu.puzzlegamehw;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainMenuController {

    private int activePuzzleSliderIndex = 0;
    public int getSelectedPuzzleIndex() {
        return activePuzzleSliderIndex;
    }

    @FXML private void initialize(){
        Puzzle[] puzzles = GameManager.getInstance().getPuzzles();

        //BUILD PUZZLE SLIDER UI
        for (Puzzle puzzle : puzzles) {
            PuzzleGrid puzzleGrid = new PuzzleGrid(puzzle, puzzlesContainer.getPrefWidth());

            puzzlesContainer.getChildren().add(puzzleGrid);
        }

        refreshPuzzleSlider();
    }

    //START REGION: PUZZLE SLIDER
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
    //END REGION: PUZZLE SLIDER

    //SCENE COMPONENTS
    @FXML private AnchorPane puzzlesContainer;
    @FXML public StackPane playButton;
    @FXML public Pane settingsButton;
    @FXML public Pane creditsButton;
}