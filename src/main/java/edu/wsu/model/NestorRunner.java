package edu.wsu.model;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.Random;


public class NestorRunner {

    private static final int OBSTACLE_HEIGHT = 50;
    private static final int canvasHeight = 400;
    private static final int canvasWidth = 600;

    private int obstacleSpeed;
    Nestor nestor;
    ArrayList<Obstacle> obstacles;
    int obstacleSpacing;
    int obstacleCounter;
    Random rand;
    Difficulty difficulty;


    public NestorRunner(Difficulty difficulty) {
        this.difficulty = difficulty;
        difficultySetter();
        this.nestor = new Nestor(50, 500);
        this.obstacles = new ArrayList<>();
        this.obstacleSpacing = 200;
        this.obstacleCounter = 0;
        this.rand = new Random();
    }

    private void difficultySetter(){
        if (this.difficulty == Difficulty.EASY){
            obstacleSpeed = 75;
        } else if (this.difficulty == Difficulty.MEDIUM){
            obstacleSpeed = 100;
        } else {
            obstacleSpeed = 125;
        }
    }

    public boolean update(double deltaTime){
        nestor.update(deltaTime);
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update(deltaTime);
            if (obstacle.leftCollidesWith(nestor)){
                return true;
            }
            if (obstacle.getX() <= 0 - obstacle.getWidth()){
                obstacles.remove(0);
            }
        }
        obstacleCounter -= obstacleSpeed * deltaTime;
        if (obstacleCounter <= 0) {
            obstacleCounter = obstacleSpacing;
            obstacles.add(new Obstacle(canvasWidth, canvasHeight - OBSTACLE_HEIGHT));
        }
        return false;
    }
    public double getNestorX(){
        return nestor.getX();
    }
    public double getNestorY(){
        return nestor.getY();
    }

    public ArrayList<Double> getObstaclePositions(){
        ArrayList<Double> positions = new ArrayList<>();
        for (int i = 0; i < obstacles.size(); i++) {
            positions.add(obstacles.get(i).getX());
        }
        return positions;
    }
}
