package com.omurefeguclu.puzzlegamehw;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

public class SettingsController {

    @FXML private void initialize(){
        musicVolumeSlider.setValue(SettingsManager.getInstance().getMusicVolume());
        sfxVolumeSlider.setValue(SettingsManager.getInstance().getSFXVolume());

        musicVolumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> SettingsManager.getInstance().setMusicVolume(newValue.intValue()));
        sfxVolumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> SettingsManager.getInstance().setSFXVolume(newValue.intValue()));
    }

    @FXML private Slider musicVolumeSlider;
    @FXML private Slider sfxVolumeSlider;

    @FXML public StackPane backToMainMenuButton;
}
