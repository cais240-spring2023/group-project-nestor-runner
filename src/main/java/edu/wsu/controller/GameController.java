package edu.wsu.controller;

import edu.wsu.model.NestorRunner;
import edu.wsu.model.Settings;
import edu.wsu.model.UserAction;
import edu.wsu.view.GameView;
import javafx.animation.AnimationTimer;

public class GameController {

    private final NestorRunner nestorRunner;

    private final Settings settings;

    private final GameView gameView;

    private AnimationTimer timer;

    public GameController(GameView gameView) {
        nestorRunner = NestorRunner.getInstance();
        settings = Settings.getInstance();

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
            nestorRunner.unpause();
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

                        nestorRunner.tickForward(deltaTime);
                        gameView.update(nestorRunner);

                        gameView.setEventHandler(event -> {
                            UserAction userAction = settings.getBoundAction(event.getCode());

                            if (userAction == null) return;
                            switch (userAction) {
                                case JUMP:
                                    if (!nestorRunner.isJumping()) gameView.getSound().playJumpSound();
                                    nestorRunner.setJump();
                                    break;
                                case PAUSE:
                                    gameView.pause();
                                    nestorRunner.pause();
                                    break;
                                case FIRE:
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
