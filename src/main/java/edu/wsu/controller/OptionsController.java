package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Settings;
import edu.wsu.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.io.IOException;

public class OptionsController {

    Settings settingsInstance;

    @FXML
    public Button mainMenuButton;

    @FXML
    public Button saveButton;

    @FXML
    public Slider musicVolumeSlider;

    @FXML
    public Slider soundEffectsSlider;

    @FXML
    public void initialize() {
        settingsInstance = Settings.getInstance();
        musicVolumeSlider.setValue(settingsInstance.getMusicVolPercent() * 100);
        soundEffectsSlider.setValue(settingsInstance.getSfxVolPercent() * 100);
    }

    public void handleMainMenuAction(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        Parent root = menuLoader.load();
        View.getStage(event).setScene(new Scene(root));
    }

    public void handleSaveAction(ActionEvent event) {
        settingsInstance.setMusicVolPercent(musicVolumeSlider.getValue() / 100);
        settingsInstance.setSfxVolPercent(soundEffectsSlider.getValue() / 100);
        settingsInstance.writeToDat();
    }

}
