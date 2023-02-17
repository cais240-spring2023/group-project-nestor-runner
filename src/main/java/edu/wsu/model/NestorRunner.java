package edu.wsu.model;

public interface NestorRunner {

  void createPlayer();
  int getPlayerYPos();
  void playerJump();
  void spawnObstacle();
  int getObstacleXPos();
  boolean collision();

}
