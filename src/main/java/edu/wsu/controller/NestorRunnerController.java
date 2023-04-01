package edu.wsu.controller;

import edu.wsu.model.NestorRunner;
import edu.wsu.model.NestorRunnerSingleton;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class NestorRunnerController implements EventHandler<KeyEvent> {

    private final NestorRunner nestorRunner = NestorRunnerSingleton.getInstance();

    public void start() {
        AnimationTimer timer = new AnimationTimer() {
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

                if (nestorRunner.isDead() || nestorRunner.isPaused()) {
                    stop();
                }
            }
        };

        timer.start();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case SPACE:
                nestorRunner.setJump();
                break;
            case ESCAPE:
                nestorRunner.togglePause();
                break;
        }
    }
}
