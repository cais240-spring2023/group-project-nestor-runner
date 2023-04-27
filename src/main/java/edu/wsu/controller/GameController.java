package edu.wsu.controller;

import edu.wsu.model.GameState;
import edu.wsu.model.NestorRunner;
import edu.wsu.view.GameView;
import javafx.animation.AnimationTimer;

public class GameController{

    private final NestorRunner nestorRunner;
    private final GameView gameView;

    AnimationTimer timer;

    public GameController(GameView gameView) {
        nestorRunner = NestorRunner.getInstance();
        this.gameView = gameView;
    }

    public void start() {
        nestorRunner.startNewGame();
        gameView.getSound().startBackGroundTrack();
        gameView.getSound().startDoomSoundTrack();
        gameView.getSound().pauseDoomSoundTrack();
        gameView.getEndPane().setButton1Action(e -> {
            gameView.restart();
            start();
        });
        gameView.getPausePane().setButton1Action(e -> {
            gameView.unPause();
            nestorRunner.unPause();
        });
        gameView.getPausePane().setButton2Action(e -> {
            timer.stop();
            gameView.getSound().stopBackGroundTrack();
            gameView.getSound().stopDoomSoundTrack();
            gameView.swapToMainMenu(e);
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

                switch (nestorRunner.state) {
                    case PLAYING:
                        nestorRunner.update(deltaTime);
                        gameView.update(nestorRunner);

                        gameView.setEventHandler(event -> {
                            switch (event.getCode()) {
                                case SPACE:
                                    if (!nestorRunner.isJumping()) gameView.getSound().playJumpSound();
                                    nestorRunner.setJump();
                                    break;
                                case ESCAPE:
                                    gameView.pause();
                                    nestorRunner.pause();
                                    break;
                                case D:
                                    if (nestorRunner.getCannonTimer() > 0) nestorRunner.fireCannon();
                                    break;
                            }
                        });
                        break;

                    case OVER:
                        stop();
                        gameView.end();
                        break;

                    case MAIN_MENU:
                        stop();
                        break;

                }
            }
        };
        timer.start();
    }
}
