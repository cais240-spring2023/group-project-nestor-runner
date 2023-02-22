package edu.wsu.view;

import edu.wsu.controller.MenuController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuViewImpl {

    public StackPane menuRoot;
    public Pane menuBackground;
    public Label title;
    public VBox buttons;
    public Button startGame;
    public Button howToPlay;

    /*
    When in the main menu, if the start button is pressed, this function is called.
    This function will call the startGame method within the MenuController.
     */
    public void startGame() {
        MenuController.startGame();
    }

    public void howToPlay() {

    }
}
