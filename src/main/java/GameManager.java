public class GameManager {
    private static GameManager instance;

    public static GameManager getInstance() {
        if(instance == null){
            instance = new GameManager();
        }

        return instance;
    }

    public Puzzle currentPuzzle;

    private Puzzle[] puzzles;

    public Puzzle[] getPuzzles() {
        return puzzles;
    }

    public void createPuzzles() {
        int numberPuzzleCount = GameConfiguration.NUMBER_PUZZLE_SIZES.length;
        int puzzleCount =  numberPuzzleCount + GameConfiguration.IMAGE_PUZZLE_FILES.length;

        this.puzzles = new Puzzle[puzzleCount];

        for(int i = 0; i < GameConfiguration.NUMBER_PUZZLE_SIZES.length; i++) {
            int puzzleSize = GameConfiguration.NUMBER_PUZZLE_SIZES[i];

            puzzles[i] = new NumberPuzzle(puzzleSize);
        }

        for(int i = 0; i < GameConfiguration.IMAGE_PUZZLE_FILES.length; i++) {
            String imagePath = GameConfiguration.IMAGE_PUZZLE_FILES[i];

            puzzles[i + numberPuzzleCount] = new ImagePuzzle(getClass().getResource(imagePath).toString(), 3);
        }

    }
}
