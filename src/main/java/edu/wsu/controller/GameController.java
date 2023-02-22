package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerImpl;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {

    private static Timeline t;
    public GameController() {

    }

    /*
    Called when the Start Game button is pressed, called from GameViewImpl.
    When called, this will start the timeline and will handle keyboard presses.
     */
    public static void initialize() {
        t = new Timeline();
        t.setAutoReverse(false);
        t.setCycleCount(Timeline.INDEFINITE);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(10000),
            event -> handleEvent()));
        t.play();

        /*
        Above shows the timeline initializing and starting. Below, we have our keyboard handler.
        We have to access a StackPane that is already loaded into the Application from GameViewImpl,
        So we access gameRoot from our gameView that is already loaded into the model. This allows us
        to actually make movements to the model and the view at the same time.
         */

        NestorRunnerImpl.getGameRoot().setOnKeyPressed(event -> {
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
        NestorRunnerImpl.getGameRoot().requestFocus();
    }

    private static void handleEvent() {

    }

    /*
    This function is called from GameViewImpl whenever the "Main Menu" button is pressed.
    This will switch the Application to the menu view and stop the timeline that is being tracked
    within this controller.
     */
    public static void switchToMenuView() {
        App.setRoot("menu");
        t.stop();
    }
}
