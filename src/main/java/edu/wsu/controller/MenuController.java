package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.view.View;
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
        FXMLLoader gameLoader = new FXMLLoader(App.class.getResource("fxml/game.fxml"));
        Parent gameRoot = gameLoader.load();
        GameController gameController = gameLoader.getController();
        gameController.start();

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        Scene gameScene = new Scene(gameRoot, View.SCENE_WIDTH, View.SCENE_HEIGHT);
        stage.setScene(gameScene);
    }
}
