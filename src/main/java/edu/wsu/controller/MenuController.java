package edu.wsu.controller;

import edu.wsu.model.Difficulty;
import edu.wsu.model.NestorRunner;
import edu.wsu.view.GameView;
import edu.wsu.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

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

    public void handleHowToPlayAction(ActionEvent actionEvent) throws Exception {
        // TODO: load some How to Play instruction pane here
    }
}
