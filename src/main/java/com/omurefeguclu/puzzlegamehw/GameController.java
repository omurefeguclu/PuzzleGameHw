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
import javafx.scene.layout.StackPane;
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
                    MakeMovement(1, 0);
                    break;
                case 1:
                    MakeMovement(-1, 0);
                    break;
                case 2:
                    MakeMovement(0, 1);
                    break;
                case 3:
                    MakeMovement(0, -1);
                    break;
            }
        }
    }

    private void MakeMovement(int x, int y){

        if(x != 0){
            if(x == -1 && blankPuzzleSlotX == 0)
                return;
            if(x == 1 && blankPuzzleSlotX == currentPuzzle.getPuzzleSize() - 1)
                return;
        }
        if(y != 0){
            if(y == -1 && blankPuzzleSlotY==0)
                return;
            if(y == 1 && blankPuzzleSlotY == currentPuzzle.getPuzzleSize() - 1)
                return;
        }

        PuzzlePieceButton currentPiece = puzzleGrid.getButton(blankPuzzleSlotX + x, blankPuzzleSlotY + y);

        currentPiece.SetPosition(blankPuzzleSlotX, blankPuzzleSlotY);


        blankPuzzleSlotX += x;
        blankPuzzleSlotY += y;

    }

    public void OnHorizontalMoveInput(boolean right) {
        if(!gameActive)
            return;


        if(right) {
            MakeMovement(-1, 0);
        }
        else {
            MakeMovement(1, 0);
        }

        ProMovement();
    }
    public void OnVerticalMoveInput(boolean up) {
        if(!gameActive)
            return;

        if(!up) {
            MakeMovement(0, -1);
        }
        else {
            MakeMovement(0, 1);
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

        gameOverOverlay.setVisible(true);

        gameOverText.setText("Game Over!\n YOU LOST");

        timeText.setText("Game Over!\n YOU LOST");
    }
    private void ProMovement()
    {
        if(this.puzzleGrid.isCorrect()){
            this.gameActive = false;

            gameOverOverlay.setVisible(true);

            timerTimeline.stop();
            gameOverText.setText("YAYY!\n YOU WIN");

            timeText.setText("YAYY!\n YOU WIN");
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
    @FXML
    private Text gameOverText;

    @FXML
    private StackPane gameOverOverlay;
    @FXML
    public StackPane replayButton;
    @FXML
    public StackPane backToMainMenuButton;
}
