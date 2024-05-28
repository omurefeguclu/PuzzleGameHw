import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface Puzzle {
    int getPuzzleSize();
    PuzzlePieceButton getPuzzleButton(int index);
}
