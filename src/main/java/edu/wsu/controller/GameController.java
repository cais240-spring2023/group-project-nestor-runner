package edu.wsu.controller;

import edu.wsu.model.GameState;
import edu.wsu.model.NestorRunner;
import edu.wsu.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class GameController {

    private final NestorRunner nestorRunner;
    private final GameView gameView;

    public GameController(GameView gameView) {
        nestorRunner = NestorRunner.getInstance();
        this.gameView = gameView;
    }

    public void start() {
        nestorRunner.start();
        gameView.getSound().startBackGroundTrack();
        gameView.getEndPane().setButton1Action(e -> {
            gameView.again();
            start();
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                if (nestorRunner.state != GameState.PLAYING) return;
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                // 1e9 = 10^-9
                double deltaTime = (now - lastTime) / 1e9;
                lastTime = now;

                nestorRunner.update(deltaTime);

                // this parameter is the only dependency between Model and View
                // in the entire program. It can be resolved by just passing the raw data that you need.
                gameView.update(nestorRunner);

                gameView.setEventHandler(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        if (!nestorRunner.isJumping()) {
                            gameView.getSound().playJumpSound();
                        }
                        nestorRunner.setJump();
                    }
                    if (event.getCode() == KeyCode.ESCAPE) {
                        if (nestorRunner.state == GameState.PLAYING) {
                            stop();
                            nestorRunner.state = GameState.PAUSED;
                            gameView.pause();
                        }
                    }
                });

                if (nestorRunner.state == GameState.OVER) {
                    stop();
                    gameView.end();
                }
            }
        };


        timer.start();
    }
}
