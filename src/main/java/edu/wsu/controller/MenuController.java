package edu.wsu.controller;

import edu.wsu.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    public void startGame(ActionEvent actionEvent) throws Exception {
        FXMLLoader gameLoader = new FXMLLoader(App.class.getResource("game.fxml"));
        Parent gameRoot = gameLoader.load();
        GameController gameController = gameLoader.getController();
        gameController.start();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        Scene gameScene = new Scene(gameRoot, 640, 480);
        stage.setScene(gameScene);
    }
}
