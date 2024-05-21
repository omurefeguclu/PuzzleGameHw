package com.omurefeguclu.puzzlegamehw;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.media.Media;
import javafx.util.Duration;

public class PuzzleGameApplication extends Application {
    private static Stage primaryStage;
    private static Scene mainMenuScene;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setResizable(false);

        Media gameMusicMedia = new Media(getClass().getResource("game_music.mp3").toExternalForm());

        MediaPlayer mediaPlayer = new MediaPlayer(gameMusicMedia);

        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });

        GameManager.getInstance().createPuzzles();

        OpenMainMenu();

        primaryStage.show();
    }

    public void OpenMainMenu() {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(PuzzleGameApplication.class.getResource("views/main-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MainMenuController controller = fxmlLoader.getController();

            controller.playButton.setOnMouseClicked(e -> {
                this.StartNewGame(controller.getSelectedPuzzleIndex());
            });

            primaryStage.setScene(scene);
        }
        catch(IOException ioException) {
            System.out.println("Open Main Menu: ioexception");
        }

    }

    private void OpenGameScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(PuzzleGameApplication.class.getResource("views/game-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            GameController controller = fxmlLoader.getController();

            controller.closeButton.setOnMouseClicked(e -> {
                this.OpenMainMenu();
            });
            controller.backToMainMenuButton.setOnMouseClicked(e->{
                this.OpenMainMenu();
            });
            controller.replayButton.setOnMouseClicked(e -> {
                this.OpenGameScene();
            });
            scene.setOnKeyReleased(event -> {
                System.out.println(event.getCode());

                switch (event.getCode()) {
                    case UP:    controller.OnVerticalMoveInput(true); break;
                    case DOWN:  controller.OnVerticalMoveInput(false); break;
                    case LEFT:  controller.OnHorizontalMoveInput(false); break;
                    case RIGHT: controller.OnHorizontalMoveInput(true); break;
                }
            });

            primaryStage.setScene(scene);
        }
        catch(IOException exception) {
            System.out.println("Start New Game: ioexception");
        }
    }
    public void StartNewGame(int puzzleIndex){
        Puzzle puzzle = GameManager.getInstance().getPuzzles()[puzzleIndex];

        GameManager.getInstance().currentPuzzle = puzzle;

        OpenGameScene();
    }

    public static void main(String[] args) {
        launch();
    }
}