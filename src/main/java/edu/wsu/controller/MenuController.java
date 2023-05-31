package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Difficulty;
import edu.wsu.model.NestorRunner;
import edu.wsu.model.Settings;
import edu.wsu.view.GameView;
import edu.wsu.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class MenuController {

    @FXML
    public Button howToPlayButton;

    @FXML
    public Button startGameButton;

    @FXML
    public Button settingsButton;

    @FXML
    public ComboBox<String> selectDifficulty;

    public CheckBox enableSpectate;

    @FXML
    public void initialize() {
        selectDifficulty.getItems().add("Easy");
        selectDifficulty.getItems().add("Medium");
        selectDifficulty.getItems().add("Hard");
        selectDifficulty.setValue("Easy");
    }

    public void handleStartGameAction(ActionEvent actionEvent) {
        NestorRunner nestorRunner = NestorRunner.getInstance();

        switch (selectDifficulty.getValue()) {
            case "Easy":
                nestorRunner.setDifficulty(Difficulty.EASY);
            case "Medium":
                nestorRunner.setDifficulty(Difficulty.MEDIUM);
            case "Hard":
                nestorRunner.setDifficulty(Difficulty.HARD);
        }

        Settings settingsInstance = Settings.getInstance();
        GameView gameView = new GameView(settingsInstance.getMusicVolPercent(), settingsInstance.getSfxVolPercent());

        GameController gameController = new GameController(gameView);
        View.getStage(actionEvent).setScene(new Scene(gameView));
        gameController.start();
    }

    public void handleHowToPlayAction(ActionEvent event) throws IOException {
        FXMLLoader howToPlayLoader = new FXMLLoader(App.class.getResource("fxml/howToPlay.fxml"));
        Parent root = howToPlayLoader.load();
        View.getStage(event).setScene(new Scene(root));
    }

    public void handleOptionsAction(ActionEvent event) throws IOException {
        FXMLLoader optionsLoader = new FXMLLoader(App.class.getResource("fxml/options.fxml"));
        Parent root = optionsLoader.load();
        View.getStage(event).setScene(new Scene(root));
    }

}
