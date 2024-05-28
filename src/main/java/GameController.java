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
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class GameController {

    private AudioClip buzzSound;

    private Puzzle currentPuzzle;
    private int blankPuzzleSlotX, blankPuzzleSlotY;

    private PuzzleGrid puzzleGrid;

    private Timeline timerTimeline;
    private boolean gameActive = true;
    private int time = GameConfiguration.PUZZLE_TIME;

    @FXML private void initialize()
    {
        //RECEIVE SELECTED PUZZLE FROM TRANSPORTER
        currentPuzzle = GameManager.getInstance().currentPuzzle;

        InitializeSFX();

        InitializePuzzle();

        InitializeCompletedPuzzle();

        InitializeTimer();
    }

    private void InitializeSFX(){
        buzzSound = new AudioClip(getClass().getResource("sfx/buzz-beep-sound.mp3").toExternalForm());
        buzzSound.setVolume((double)SettingsManager.getInstance().getSFXVolume() / 100.0);
    }
    private void InitializePuzzle()
    {
        //SET BLANK SPOTS FOR FUNCTIONALITY
        this.blankPuzzleSlotX = this.blankPuzzleSlotY = currentPuzzle.getPuzzleSize() - 1;

        puzzleGrid = new PuzzleGrid(currentPuzzle, puzzleContainer.getPrefWidth());
        Shuffle();

        //PUZZLE PIECE SHIFTING ANIMATION
        LayoutAnimator animator = new LayoutAnimator();
        animator.observe(puzzleGrid.getChildren());

        puzzleContainer.getChildren().add(puzzleGrid);
    }
    private void InitializeCompletedPuzzle()
    {
        PuzzleGrid completedPuzzleGrid = new PuzzleGrid(currentPuzzle, completedPuzzleContainer.getPrefWidth());
        completedPuzzleContainer.getChildren().add(completedPuzzleGrid);
    }
    private void InitializeTimer(){
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

    //SHUFFLE METHOD: MAKES RANDOM MOVEMENT 1000 TIMES
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
                    TryMakeMovement(1, 0);
                    break;
                case 1:
                    TryMakeMovement(-1, 0);
                    break;
                case 2:
                    TryMakeMovement(0, 1);
                    break;
                case 3:
                    TryMakeMovement(0, -1);
                    break;
            }
        }
    }

    private boolean TryMakeMovement(int x, int y){
        //MOVEMENT VALIDATION RETURNS FALSE IF INVALID
        if(x != 0){
            if(x == -1 && blankPuzzleSlotX == 0)
                return false;
            if(x == 1 && blankPuzzleSlotX == currentPuzzle.getPuzzleSize() - 1)
                return false;
        }
        if(y != 0){
            if(y == -1 && blankPuzzleSlotY==0)
                return false;
            if(y == 1 && blankPuzzleSlotY == currentPuzzle.getPuzzleSize() - 1)
                return false;
        }

        //MOVE PIECE
        PuzzlePieceButton currentPiece = puzzleGrid.getButton(blankPuzzleSlotX + x, blankPuzzleSlotY + y);
        currentPiece.SetPosition(blankPuzzleSlotX, blankPuzzleSlotY);

        //ADJUST BLANK SLOT
        blankPuzzleSlotX += x;
        blankPuzzleSlotY += y;

        //MOVEMENT SUCCEEDED RETURNS TRUE
        return true;
    }

    public void OnHorizontalMoveInput(boolean right) {
        if(!gameActive)
            return;

        boolean success = right ? TryMakeMovement(-1, 0): TryMakeMovement(1, 0);

        if(!success){
            //ERROR
            buzzSound.play();
        }

        ProMovement();
    }
    public void OnVerticalMoveInput(boolean up) {
        if(!gameActive)
            return;

        boolean success = up ? TryMakeMovement(0, 1) : TryMakeMovement(0, -1);

        if(!success){
            //ERROR
            buzzSound.play();
        }

        ProMovement();
    }


    private void UpdateTimer(){
        timeText.setText("TIME: " + time);
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


    //SCENE COMPONENTS

    @FXML
    private AnchorPane puzzleContainer;
    @FXML
    private Pane completedPuzzleContainer;

    @FXML
    private Text timeText;

    @FXML
    private Text gameOverText;
    @FXML
    private StackPane gameOverOverlay;

    @FXML
    public Pane closeButton;
    @FXML
    public StackPane replayButton;
    @FXML
    public StackPane backToMainMenuButton;
}
