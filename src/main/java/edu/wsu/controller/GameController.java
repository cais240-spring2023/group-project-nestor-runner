package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class GameController {

    public GameController() {

    }

    public static class KeyboardPressHandler implements EventHandler<KeyEvent> {
        int x;
        int y;

        public KeyboardPressHandler(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            try {
                if(keyEvent.getCharacter().equals(" ")) {
                    NestorRunnerSingleton.getInstance().jump(x,y);
                } else if(keyEvent.isAltDown()) {
                    NestorRunnerSingleton.getInstance().shoot(x,y);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void switchToMenuView() throws IOException {
        App.setRoot("menu");
    }


}
