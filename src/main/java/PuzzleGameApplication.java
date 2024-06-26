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
        primaryStage.setTitle("Puzzle Game");

        //INITIALIZE SETTINGS
        SettingsManager.getInstance().Initialize();

        StartGameMusic();

        //INITIALIZE PUZZLES
        GameManager.getInstance().createPuzzles();

        //SHOW INITIAL SCENE
        OpenMainMenu();

        primaryStage.show();
    }

    //THIS METHOD INITIALIZES GAME MUSIC
    private void StartGameMusic(){
        Media gameMusicMedia = new Media(getClass().getResource("game_music.mp3").toExternalForm());

        MediaPlayer mediaPlayer = new MediaPlayer(gameMusicMedia);

        mediaPlayer.setVolume(SettingsManager.getInstance().getMusicVolume() / 100.0);
        SettingsManager.getInstance().getMusicVolumeProperty()
                        .addListener((observable, oldValue, newValue) -> {
                            mediaPlayer.setVolume((double)newValue.intValue() / 100.0);
                        });

        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
    }

    public void OpenMainMenu() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/main-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MainMenuController controller = fxmlLoader.getController();

            //BIND BUTTON CLICK EVENTS
            controller.playButton.setOnMouseClicked(e -> {
                this.StartNewGame(controller.getSelectedPuzzleIndex());
            });
            controller.settingsButton.setOnMouseClicked(e -> {
                System.out.println("settings clicked");
                this.OpenSettings();
            });
            controller.creditsButton.setOnMouseClicked(e -> {
                this.OpenCredits();
            });

            primaryStage.setScene(scene);
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
            System.out.println("Open Main Menu: ioexception");
        }

    }
    public void OpenSettings(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/settings-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            SettingsController controller = fxmlLoader.getController();

            controller.backToMainMenuButton.setOnMouseClicked(e->{
                this.OpenMainMenu();
            });
            primaryStage.setScene(scene);
        }
        catch(IOException exception) {
            System.out.println("Start New Game: ioexception");
        }
    }
    public void OpenCredits(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/credits-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            CreditsController controller = fxmlLoader.getController();

            //BIND BUTTON CLICK EVENTS
            controller.backToMainMenuButton.setOnMouseClicked(e->{
                this.OpenMainMenu();
            });

            primaryStage.setScene(scene);
        }
        catch(IOException exception) {
            System.out.println("Start New Game: ioexception");
        }
    }
    private void OpenGameScene(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/game-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            GameController controller = fxmlLoader.getController();

            //BIND BUTTON CLICK EVENTS
            controller.closeButton.setOnMouseClicked(e -> {
                this.OpenMainMenu();
            });
            controller.backToMainMenuButton.setOnMouseClicked(e->{
                this.OpenMainMenu();
            });
            controller.replayButton.setOnMouseClicked(e -> {
                this.OpenGameScene();
            });


            //BIND KEYBOARD EVENTS
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
        //PASSES THE SELECTED PUZZLE TO GAME SCENE
        Puzzle puzzle = GameManager.getInstance().getPuzzles()[puzzleIndex];
        GameManager.getInstance().currentPuzzle = puzzle;

        OpenGameScene();
    }

    public static void main(String[] args) {
        launch();
    }
}