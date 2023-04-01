package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunner;
import edu.wsu.model.NestorRunnerSingleton;
import edu.wsu.view.NestorRunnerView;
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

public class MenuController {

    public Button howToPlay;
    public Button startGame;
    @FXML
    ComboBox<String> selectDifficulty;

    public void initialize() {
        selectDifficulty.getItems().add("Easy");
        selectDifficulty.getItems().add("Normal");
        selectDifficulty.getItems().add("Hard");
    }

    public void handleStartGameAction(ActionEvent actionEvent) throws Exception {

        NestorRunner nestorRunner = NestorRunnerSingleton.getInstance();
        nestorRunner.setDifficulty(selectDifficulty.getValue());

        NestorRunnerView nestorRunnerView = new NestorRunnerView();
        nestorRunner.addObserver(nestorRunnerView);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(nestorRunnerView);
        stage.setScene(scene);
        stage.show();

        nestorRunner.startGame();
    }

    public void handleHowToPlayAction(ActionEvent actionEvent) throws Exception {
        // TODO: load some How to Play instruction pane here
    }
}
