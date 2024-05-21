package com.omurefeguclu.puzzlegamehw;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class GameController {

    private Puzzle currentPuzzle;
    private int blankPuzzleSlotX, blankPuzzleSlotY;

    private PuzzleGrid puzzleGrid;

    private Timeline timerTimeline;
    private boolean gameActive = true;
    private int time = GameConfiguration.PUZZLE_TIME;

    @FXML private void initialize()
    {
        currentPuzzle = GameManager.getInstance().currentPuzzle;

        this.blankPuzzleSlotX = this.blankPuzzleSlotY = currentPuzzle.getPuzzleSize() - 1;
        puzzleGrid = new PuzzleGrid(currentPuzzle, puzzleContainer.getPrefWidth());

        //puzzleGrid.Shuffle();
        Shuffle();

        LayoutAnimator animator = new LayoutAnimator();
        animator.observe(puzzleGrid.getChildren());

        puzzleContainer.getChildren().add(puzzleGrid);


        PuzzleGrid completedPuzzleGrid = new PuzzleGrid(currentPuzzle, completedPuzzleContainer.getPrefWidth());
        completedPuzzleContainer.getChildren().add(completedPuzzleGrid);

        UpdateTimer();
        timerTimeline = new Timeline();
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            // KeyFrame event handler
                            public void handle(ActionEvent event) {
                                time--;
                                UpdateTimer();
                            }
                        }));
        timerTimeline.playFromStart();
    }

    private void Shuffle(){
        Random random = new Random();

        int lastMovement = -1, movement;
        int i = 0;
        while(i++ < 1000){
            movement = random.nextInt(4);
            if(movement > 1 && lastMovement > 1 || movement < 2 && lastMovement < 2)
            {
                if(lastMovement - movement != 0)
                    continue;
            }
            lastMovement = movement;

            switch(movement)
            {
                case 0:
                    OnHorizontalMoveInput(true);
                    break;
                case 1:
                    OnHorizontalMoveInput(false);
                    break;
                case 2:
                    OnVerticalMoveInput(true);
                    break;
                case 3:
                    OnVerticalMoveInput(false);
                    break;
            }
        }
    }

    public void OnHorizontalMoveInput(boolean right) {
        if(!gameActive)
            return;


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
        if(!gameActive)
            return;

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


    private void UpdateTimer(){
        timeText.setText(STR."TIME: \{time}");
        if (time <= 0) {
            timerTimeline.stop();
            TimerEnded();
        }
    }
    private void TimerEnded()
    {
        this.gameActive = false;

        System.out.println("Game Over! YOU LOST");
        timeText.setText("Game Over!\n YOU LOST");
    }
    private void ProMovement()
    {
        if(this.puzzleGrid.isCorrect()){
            this.gameActive = false;

            System.out.println("Game Over! YOU WIN");
            timeText.setText("Game Over!\n YOU WIN");
        }

    }

    @FXML
    private AnchorPane puzzleContainer;
    @FXML
    private Pane completedPuzzleContainer;
    @FXML
    public Pane closeButton;
    @FXML
    private Text timeText;
}
