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

public class SettingsController {

    @FXML
    public Button mainMenuButton;
    @FXML
    public Slider musicVolumeSlider;
    @FXML
    public Slider soundEffectsSlider;

    public void handleMainMenuAction(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        Parent root = menuLoader.load();

        Settings settingsInstance = Settings.getInstance();
        settingsInstance.setMusicVolPercent(musicVolumeSlider.getValue() / 100);
        settingsInstance.setSfxVolPercent(soundEffectsSlider.getValue() / 100);

        View.getStage(event).setScene(new Scene(root));
    }

}
