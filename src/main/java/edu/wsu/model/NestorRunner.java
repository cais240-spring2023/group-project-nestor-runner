package edu.wsu.model;

import java.util.ArrayList;
import java.util.Random;


public class NestorRunner {
    private static final int OBSTACLE_HEIGHT = 50;
    private static final int canvasHeight = 400;
    private static final int canvasWidth = 600;

    private static final int nestorStartX = 50;
    private static final int nestorStartY = canvasHeight;

    private int score;
    private int obstacleSpeed;
    Nestor nestor;
    ArrayList<Obstacle> obstacles;
    int obstacleSpacing;
    int obstacleCountdown;
    Random rand;
    Difficulty difficulty;
    int maxJumpHeight;


    public NestorRunner(Difficulty difficulty) {
        this.score = 0;
        this.difficulty = difficulty;
        difficultySetter();
        this.nestor = new Nestor(nestorStartX, nestorStartY);
        this.obstacles = new ArrayList<>();
        this.obstacleSpacing = 200;
        this.obstacleCountdown = 0;
        this.rand = new Random();
        this.maxJumpHeight = (int) nestor.getMaxJumpHeight();
    }

    // helper
    private void difficultySetter(){
        if (this.difficulty == Difficulty.EASY){
            obstacleSpeed = 75;
        } else if (this.difficulty == Difficulty.MEDIUM){
            obstacleSpeed = 100;
        } else {
            obstacleSpeed = 125;
        }
    }

    public void jump(){
        nestor.jump();
    }

    public boolean update(double deltaTime){
        nestor.update(deltaTime);
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update(deltaTime);
            if (obstacle.getX() < 110){
                if (obstacle.leftCollidesWith(nestor)){
                    return true;
                }
                if (obstacle.getX() <= 0 - obstacle.getWidth()){
                    obstacles.remove(0);
                    score++;
                }
            }
        }
        obstacleCountdown -= obstacleSpeed * deltaTime;
        if (obstacleCountdown <= 0) {
            obstacleCountdown = obstacleSpacing;
            obstacles.add(randObstacleGenerator());
            //obstacles.add(new Obstacle(canvasWidth, canvasHeight - OBSTACLE_HEIGHT));
        }
        return false;
    }
    public double getNestorX(){
        return nestor.getX();
    }
    public double getNestorY(){
        return nestor.getY();
    }

    public int getScore(){
        return score;
    }

    public ArrayList<Double> getObstaclePositions(){
        ArrayList<Double> positions = new ArrayList<>();
        for (int i = 0; i < obstacles.size(); i++) {
            positions.add(obstacles.get(i).getX());
        }
        return positions;
    }
     public ArrayList<Entity> getEntities(){
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(nestor);
        entities.addAll(obstacles);
        return entities;
     }

     private Obstacle randObstacleGenerator(){
        int maxObstacleHeight = (int) (maxJumpHeight * .75);
        int randHeight = (rand.nextInt(maxObstacleHeight) + 1);
        int randWidth = (rand.nextInt(8) + 1) * 10;
        return new Obstacle(canvasWidth, canvasHeight - randHeight, randWidth, randHeight);
     }
}
