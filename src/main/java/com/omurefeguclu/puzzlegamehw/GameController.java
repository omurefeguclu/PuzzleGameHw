package com.omurefeguclu.puzzlegamehw;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GameController {

    private Puzzle currentPuzzle;
    private int blankPuzzleSlotX, blankPuzzleSlotY;

    private GridPane puzzleGrid;

    @FXML private void initialize()
    {
        currentPuzzle = GameManager.getInstance().currentPuzzle;

        puzzleGrid = new GridPane();

            /*puzzleGrid.setHgap(1);
            puzzleGrid.setVgap(1);*/
        puzzleGrid.setPrefSize(puzzleContainer.getPrefWidth(), puzzleContainer.getPrefHeight());



        int pieceCount = (currentPuzzle.getPuzzleSize() * currentPuzzle.getPuzzleSize()) - 1;
        blankPuzzleSlotX = blankPuzzleSlotY = currentPuzzle.getPuzzleSize() - 1;
        double pieceSize = puzzleContainer.getPrefWidth() / currentPuzzle.getPuzzleSize();


        for(int i = 0; i < pieceCount; i++) {
            Button button = currentPuzzle.getPuzzleButton(i);

            int rowIndex = i / currentPuzzle.getPuzzleSize();
            int columnIndex = i % currentPuzzle.getPuzzleSize();

            button.setMinSize(pieceSize, pieceSize);

            puzzleGrid.add(button, columnIndex, rowIndex);
        }

        LayoutAnimator animator = new LayoutAnimator();
        animator.observe(puzzleGrid.getChildren());

        puzzleContainer.getChildren().add(puzzleGrid);
    }

    public void OnHorizontalMoveInput(boolean right) {
        System.out.println("horizontal move" + right);


        if(right) {
            if(blankPuzzleSlotX==0)
                return;

            Node currentPiece = getNodeByRowColumnIndex(blankPuzzleSlotX - 1, blankPuzzleSlotY, puzzleGrid);

            GridPane.setConstraints(currentPiece, blankPuzzleSlotX, blankPuzzleSlotY);
            blankPuzzleSlotX--;
        }
        else {
            if(blankPuzzleSlotX == currentPuzzle.getPuzzleSize() - 1)
                return;


            Node currentPiece = getNodeByRowColumnIndex(blankPuzzleSlotX + 1, blankPuzzleSlotY, puzzleGrid);

            GridPane.setConstraints(currentPiece, blankPuzzleSlotX, blankPuzzleSlotY);
            blankPuzzleSlotX++;
        }


    }

    public void OnVerticalMoveInput(boolean up) {
        System.out.println("vertical move" + up);


        if(!up) {
            if(blankPuzzleSlotY==0)
                return;

            Node currentPiece = getNodeByRowColumnIndex(blankPuzzleSlotX, blankPuzzleSlotY - 1, puzzleGrid);

            GridPane.setConstraints(currentPiece, blankPuzzleSlotX, blankPuzzleSlotY);
            blankPuzzleSlotY--;
        }
        else {
            if(blankPuzzleSlotY == currentPuzzle.getPuzzleSize() - 1)
                return;


            Node currentPiece = getNodeByRowColumnIndex(blankPuzzleSlotX, blankPuzzleSlotY + 1, puzzleGrid);

            GridPane.setConstraints(currentPiece, blankPuzzleSlotX, blankPuzzleSlotY);
            blankPuzzleSlotY++;
        }
    }
    public Node getNodeByRowColumnIndex (final int column, final int row, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


    @FXML
    private AnchorPane puzzleContainer;
}
