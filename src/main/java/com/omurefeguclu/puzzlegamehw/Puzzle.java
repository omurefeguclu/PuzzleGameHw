package com.omurefeguclu.puzzlegamehw;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface Puzzle {
    int getPuzzleSize();
    Button getPuzzleButton(int index);
}