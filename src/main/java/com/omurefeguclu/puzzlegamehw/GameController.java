package com.omurefeguclu.puzzlegamehw;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class GameController {

    private Puzzle currentPuzzle;
    private int blankPuzzleSlotX, blankPuzzleSlotY;


    private PuzzleGrid puzzleGrid;

    @FXML private void initialize()
    {
        currentPuzzle = GameManager.getInstance().currentPuzzle;

        this.blankPuzzleSlotX = this.blankPuzzleSlotY = currentPuzzle.getPuzzleSize() - 1;
        puzzleGrid = new PuzzleGrid(currentPuzzle, puzzleContainer.getPrefWidth());

        //puzzleGrid.Shuffle();

        LayoutAnimator animator = new LayoutAnimator();
        animator.observe(puzzleGrid.getChildren());

        puzzleContainer.getChildren().add(puzzleGrid);
    }

    public void OnHorizontalMoveInput(boolean right) {
        System.out.println("horizontal move" + right);


        if(right) {
            if(blankPuzzleSlotX==0)
                return;

            PuzzlePieceButton currentPiece = puzzleGrid.getButton(blankPuzzleSlotX - 1, blankPuzzleSlotY);

            currentPiece.SetPosition(blankPuzzleSlotX, blankPuzzleSlotY);

            blankPuzzleSlotX--;
        }
        else {
            if(blankPuzzleSlotX == currentPuzzle.getPuzzleSize() - 1)
                return;

            PuzzlePieceButton currentPiece = puzzleGrid.getButton(blankPuzzleSlotX + 1, blankPuzzleSlotY);

            currentPiece.SetPosition(blankPuzzleSlotX, blankPuzzleSlotY);

            blankPuzzleSlotX++;
        }

        ProMovement();
    }

    public void OnVerticalMoveInput(boolean up) {
        System.out.println("vertical move" + up);


        if(!up) {
            if(blankPuzzleSlotY==0)
                return;

            PuzzlePieceButton currentPiece = puzzleGrid.getButton(blankPuzzleSlotX, blankPuzzleSlotY - 1);

            currentPiece.SetPosition(blankPuzzleSlotX, blankPuzzleSlotY);

            blankPuzzleSlotY--;
        }
        else {
            if(blankPuzzleSlotY == currentPuzzle.getPuzzleSize() - 1)
                return;


            PuzzlePieceButton currentPiece = puzzleGrid.getButton(blankPuzzleSlotX, blankPuzzleSlotY + 1);

            currentPiece.SetPosition(blankPuzzleSlotX, blankPuzzleSlotY);

            blankPuzzleSlotY++;
        }

        ProMovement();
    }

    private void ProMovement()
    {
        if(this.puzzleGrid.isCorrect()){
            System.out.println("Game Over! YOU WIN");
        }
    }

    @FXML
    private AnchorPane puzzleContainer;
}
