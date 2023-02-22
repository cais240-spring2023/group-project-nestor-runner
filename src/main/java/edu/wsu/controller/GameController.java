package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import edu.wsu.view.GameViewImpl;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public static StackPane playSpace;
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

    private static Timeline t;

    public GameController() {

    }

    public static void initialize() {
        t = new Timeline();
        t.setAutoReverse(false);
        t.setCycleCount(Timeline.INDEFINITE);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(10000),
            event -> handleEvent()));
        t.play();

        /* This will not work because gameRoot is not what was loaded. It is throwing a
         * NullPointerException stating that cannot invoke method because gameRoot is null.
         * The gamePane was loaded in App.java as a StackPane but gameRoot was not.
         * I'm kind of stuck here xD But we have a timeline! lol
        */
        NestorRunnerSingleton.getInstance().getGameRoot().setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case SPACE:
                    //if timeline is not paused
                    if(t.getStatus() != Status.PAUSED) {
                        //jump
                        break;
                    }
                case CONTROL:
                    if(t.getStatus() != Status.PAUSED) {
                        //shoot
                        break;
                    }
                case ESCAPE:
                    if(t.getStatus() == Status.PAUSED) {
                        //if paused, start game
                        t.play();
                    } else {
                        //pause game on press of escape
                        t.pause();
                    }
                default:
                    break;
            }
        });
        playSpace.requestFocus();
    }

    private static void handleEvent() {

    }

    public static GameViewImpl getGame() {
        return NestorRunnerSingleton.getInstance().getGameView();
    }

//    public static class KeyboardPressHandler implements EventHandler<KeyEvent> {
//        int x;
//        int y;
//
//        public KeyboardPressHandler(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        @Override
//        public void handle(KeyEvent keyEvent) {
////            try {
////                if(keyEvent.getCharacter().equals(" ")) {
////                    //NestorRunnerSingleton.getInstance().jump(x,y);
////                } else if(keyEvent.isAltDown()) {
////                    //NestorRunnerSingleton.getInstance().shoot(x,y);
////                }
////            } catch (IOException e) {
////                throw new RuntimeException(e);
////            }
//        }
//    }

    @FXML
    private void switchToMenuView() {
        App.setRoot("menu");
        t.stop();
    }


}
