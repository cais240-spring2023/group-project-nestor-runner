package edu.wsu.view;

import javafx.scene.Scene;

import java.io.IOException;

public interface GameView {

    /**
     * Disables the end screen if it isn't already.
     */
    void disableEndScreen();

    /**
     * Enables the end screen if it isn't already.
     */
    void enableEndScreen();
}
