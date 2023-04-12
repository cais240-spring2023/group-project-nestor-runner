package edu.wsu.controller;

import edu.wsu.model.GameState;
import edu.wsu.model.NestorRunner;
import edu.wsu.view.GameView;
import edu.wsu.view.View;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.scene.input.KeyCode;

public class GameController{

    private final NestorRunner nestorRunner;
    private final GameView gameView;

    AnimationTimer timer;

    public GameController(GameView gameView) {
        nestorRunner = NestorRunner.getInstance();
        this.gameView = gameView;
        //gameView.getPausePane().setButton1Action();

    }

    public void togglePause() {
        gameView.togglePause();
        nestorRunner.togglePause();
    }

    private void animationTimerContent(long now) {

    }

    public void start() {
        nestorRunner.startNewGame();
        gameView.getSound().startBackGroundTrack();
        gameView.getSound().startDoomSoundTrack();
        gameView.getSound().pauseDoomSoundTrack();
        gameView.getEndPane().setButton1Action(e -> {
            gameView.again();
            start();
        });
        gameView.getPausePane().setButton1Action(e -> {
            togglePause();
        });
        gameView.getPausePane().setButton2Action(e -> {
            nestorRunner.togglePause();
            timer.stop();
            gameView.swapToMainMenu(e);
            gameView.togglePause();
        });


        this.timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                // 1e9 = 10^-9
                double deltaTime = (now - lastTime) / 1e9;
                lastTime = now;

                nestorRunner.update(deltaTime);
                gameView.update(nestorRunner);

                gameView.setEventHandler(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        if (!nestorRunner.isJumping()) {
                            gameView.getSound().playJumpSound();
                        }
                        nestorRunner.setJump();
                    }
                    // escape to toggle pause/unpause
                    if (event.getCode() == KeyCode.ESCAPE) {
                        togglePause();
                    }
                    if (event.getCode() == KeyCode.D) {
                        if (nestorRunner.getCannonTimer() > 0) {
                            nestorRunner.fireCannon();
                        }
                    }
                });

                if (nestorRunner.state == GameState.OVER) {
                    stop();
                    gameView.end();
                } else if (nestorRunner.state == GameState.MAIN_MENU){
                    stop();
                }
            }
        };
        timer.start();
    }
}
