package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import edu.wsu.view.MenuViewImpl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuController {

    public StackPane menuRoot;
    public Pane menuBackground;
    public Label title;
    public VBox buttons;

    public static MenuViewImpl getMenu() {
        return NestorRunnerSingleton.getInstance().getMenuView();
    }

    public static class ClickHandler implements EventHandler<MouseEvent> {
        int x;
        int y;
        public ClickHandler(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
//            try {
//                 NestorRunnerSingleton.getInstance().select(x,y);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    @FXML
    public void startGame() {
        App.setRoot("game");
        GameController.initialize();
    }

    public void howToPlay() {

    }
}