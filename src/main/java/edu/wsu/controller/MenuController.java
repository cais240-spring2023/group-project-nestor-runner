package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuController {
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

    public void startGame() {
        // NestorRunnerSingleton.getInstance().initialize();
    }

    @FXML
    private void switchToGameView() throws IOException {
        App.setRoot("game");
    }
}