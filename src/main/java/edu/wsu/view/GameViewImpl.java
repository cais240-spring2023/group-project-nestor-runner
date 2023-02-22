package edu.wsu.view;

import edu.wsu.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class GameViewImpl implements GameView {

    public StackPane gameRoot;
    public Pane gameBackground;
    public VBox gameWindow;
    public StackPane playSpace;
    public Pane scrolling;
    public Rectangle enemy;
    public Rectangle shortObstacle;
    public Rectangle tallObstacle;
    public Pane playerPane;
    public GridPane scoreWindow;
    public Label scoreLabel;
    public Label score;
    public Rectangle player;
    public Rectangle proj;
    public Pane ground;
    public StackPane endScreen;
    public Pane grayFilter;
    public VBox gameEndMenu;
    public Label resultsTitle;
    public Button playAgain;
    public Button mainMenu;

    @Override
    public void disableEndScreen() {

    }

    @Override
    public void enableEndScreen() {

    }

    /*
     * When in the game and the game ends, if you press the main menu button, this
     * function will call the GameController function to switch to the menu view.
     */
    @FXML
    private void switchToMenuView() {
        GameController.switchToMenuView();
    }
}
