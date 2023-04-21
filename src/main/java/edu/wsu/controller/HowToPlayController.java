package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class HowToPlayController {

    @FXML
    public Button mainMenu;

    public void handleMainMenuAction(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        Parent root = menuLoader.load();
        View.getStage(event).setScene(new Scene(root));
    }

}
