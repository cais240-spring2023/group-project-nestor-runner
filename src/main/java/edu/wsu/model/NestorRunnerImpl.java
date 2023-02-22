package edu.wsu.model;

import edu.wsu.view.GameViewImpl;
import edu.wsu.view.MenuViewImpl;
import javafx.scene.layout.StackPane;

public class NestorRunnerImpl implements NestorRunner {
  static GameViewImpl gameView;
  MenuViewImpl menuView;

  public void setMenuView(MenuViewImpl view) {
    menuView = view;
  }

  public static StackPane getGameRoot() {
      return gameView.gameRoot;
  }

  public void setGameView(GameViewImpl view) {
    gameView = view;
  }

  @Override
  public void createPlayer() {

  }

  @Override
  public int getPlayerYPos() {
    return 0;
  }

  @Override
  public void playerJump() {

  }

  @Override
  public void spawnObstacle() {

  }

  @Override
  public int getObstacleXPos() {
    return 0;
  }

  @Override
  public boolean collision() {
    return false;
  }
}
