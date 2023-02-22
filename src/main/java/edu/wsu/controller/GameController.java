package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import edu.wsu.view.GameViewImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {

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
//        NestorRunnerSingleton.getInstance().getGameRoot().setOnKeyPressed(event -> {
//            switch(event.getCode()) {
//                case SPACE:
//                    //if timeline is not paused
//                    if(t.getStatus() != Status.PAUSED) {
//                        //jump
//                        break;
//                    }
//                case CONTROL:
//                    if(t.getStatus() != Status.PAUSED) {
//                        //shoot
//                        break;
//                    }
//                case ESCAPE:
//                    if(t.getStatus() == Status.PAUSED) {
//                        //if paused, start game
//                        t.play();
//                    } else {
//                        //pause game on press of escape
//                        t.pause();
//                    }
//                default:
//                    break;
//            }
//        });
//        playSpace.requestFocus();
    }

    private static void handleEvent() {

    }

    public static GameViewImpl getGame() {
        return NestorRunnerSingleton.getInstance().getGameView();
    }
    public static void switchToMenuView() {
        App.setRoot("menu");
        t.stop();
    }


}
