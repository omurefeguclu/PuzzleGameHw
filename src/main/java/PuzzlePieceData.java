import javafx.scene.Node;

public class PuzzlePieceData {
    public Node puzzleButton;
    public int positionX, positionY;
    public int correctPositionX, correctPositionY;

    public PuzzlePieceData(Node puzzleButton, int correctPositionX, int correctPositionY) {
        this.puzzleButton = puzzleButton;
        this.correctPositionX = correctPositionX;
        this.correctPositionY = correctPositionY;
    }
}
