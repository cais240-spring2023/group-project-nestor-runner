package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import edu.wsu.view.GameViewImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameController {

    public StackPane gameRoot;
    public Pane gameBackground;
    public VBox gameWindow;
    public StackPane playSpace;
    public Pane scrolling;
    public Rectangle enemy;
    public Rectangle shortObstacle;
    public Rectangle tallObstacle;
    public Pane playerPane;
    public GridPane scoreWindow;
    public Label scoreLabel;
    public Label score;
    public Rectangle player;
    public Rectangle proj;
    public Pane ground;
    public StackPane endScreen;
    public Pane grayFilter;
    public VBox gameEndMenu;
    public Button playAgain;
    public Label resultsTitle;
    public Button mainMenu;

    public static final Timeline t = new Timeline();

    public GameController() {

    }

    public static void initialize() {
        t.getKeyFrames().add(new KeyFrame(Duration.millis(10000)));
        t.setAutoReverse(false);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    public static GameViewImpl getGame() {
        return NestorRunnerSingleton.getInstance().getGameView();
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
//            try {
//                if(keyEvent.getCharacter().equals(" ")) {
//                    //NestorRunnerSingleton.getInstance().jump(x,y);
//                } else if(keyEvent.isAltDown()) {
//                    //NestorRunnerSingleton.getInstance().shoot(x,y);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    @FXML
    private void switchToMenuView() {
        App.setRoot("menu");
        t.stop();
    }


}
