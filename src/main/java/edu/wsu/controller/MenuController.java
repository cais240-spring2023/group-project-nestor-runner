package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Difficulty;
import edu.wsu.model.NestorRunner;
import edu.wsu.view.GameView;
import edu.wsu.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public Button howToPlay;
    @FXML
    public Button startGame;
    @FXML
    public ComboBox<String> selectDifficulty;

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
        GameView gameView = new GameView(NestorRunner.GROUND_Y);
        GameController gameController = new GameController(gameView);
        View.getStage(actionEvent).setScene(new Scene(gameView));
        gameController.start();
    }

    public void handleHowToPlayAction(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/howToPlay.fxml"));
        Parent root = menuLoader.load();
        View.getStage(event).setScene(new Scene(root));
    }
}
