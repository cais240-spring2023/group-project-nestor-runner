package edu.wsu.controller;

import edu.wsu.App;

public class MenuController {

    /*
    When the start game button is pressed in the main menu, this function will be called from MenuViewImpl.
    When this function is called, it will change the application to view the game, and it will call
    GameController.initialize which will start the timeline.
     */
    public static void startGame() {
        App.setRoot("game");
        GameController.initialize();
    }

    public void howToPlay() {

    }
}