package edu.wsu.model;

import edu.wsu.view.GameViewImpl;
import edu.wsu.view.MenuViewImpl;
import javafx.scene.layout.StackPane;

public class NestorRunnerImpl implements NestorRunner {
  GameViewImpl gameView;
  StackPane gameRoot;
  MenuViewImpl menuView;
  StackPane menuRoot;
  public StackPane getGameRoot() {
    return this.gameRoot;
  }

  public StackPane getMenuRoot() {
    return this.menuRoot;
  }
  public void setMenuView(MenuViewImpl view) {
    menuView = view;
  }

  public MenuViewImpl getMenuView() {
    return this.menuView;
  }

  public void setGameView(GameViewImpl view) {
    gameView = view;
  }

  public GameViewImpl getGameView() {
    return this.gameView;
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
