package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.NestorRunnerSingleton;
import edu.wsu.view.MenuViewImpl;

public class MenuController {

    public static MenuViewImpl getMenu() {
        return NestorRunnerSingleton.getInstance().getMenuView();
    }

    public static void startGame() {
        App.setRoot("game");
        GameController.initialize();
    }

    public void howToPlay() {

    }
}