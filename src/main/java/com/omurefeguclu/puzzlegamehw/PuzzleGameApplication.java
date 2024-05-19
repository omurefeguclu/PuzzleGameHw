package com.omurefeguclu.puzzlegamehw;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PuzzleGameApplication extends Application {
    private static Stage primaryStage;
    private static Scene mainMenuScene;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        GameManager.getInstance().createPuzzles();

        OpenMainMenu();

        primaryStage.show();
    }

    public void OpenMainMenu() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(PuzzleGameApplication.class.getResource("views/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MainMenuController controller = fxmlLoader.getController();

        controller.playButton.setOnMouseClicked(e -> {
            this.StartNewGame(controller.getSelectedPuzzleIndex());
        });

        primaryStage.setScene(scene);
    }

    public void StartNewGame(int puzzleIndex){
        Puzzle puzzle = GameManager.getInstance().getPuzzles()[puzzleIndex];

        GameManager.getInstance().currentPuzzle = puzzle;

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(PuzzleGameApplication.class.getResource("views/game-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            GameController controller = fxmlLoader.getController();

            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    System.out.println(event.getCode());

                    switch (event.getCode()) {
                        case UP:    controller.OnVerticalMoveInput(true); break;
                        case DOWN:  controller.OnVerticalMoveInput(false); break;
                        case LEFT:  controller.OnHorizontalMoveInput(false); break;
                        case RIGHT: controller.OnHorizontalMoveInput(true); break;
                    }
                }
            });

            primaryStage.setScene(scene);
        }
        catch(IOException exception) {
            System.out.println("Start New Game: ioexception");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}