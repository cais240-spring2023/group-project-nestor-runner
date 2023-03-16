package edu.wsu.model;

import edu.wsu.model.enums.Difficulty;
import edu.wsu.model.enums.EntityType;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class NestorRunner {
    private static final int canvasHeight = 400;
    private static final int nestorStartX = 50;
    private static final int nestorStartY = canvasHeight;

    private ArrayList<EntityType> obstacleTypes;
    private int score;
    private int obstacleSpeed;
    Nestor nestor;
    ArrayList<Obstacle> obstacles;
    int obstacleSpacing;
    int obstacleCountdown;
    Random rand;
    Difficulty difficulty;
    private double speedCounter = 0;
    private double deltaTimeModifier = 1;

    private final String deathSound = "src/main/resources/edu/wsu/deathSound.wav";
    private final String jumpSound = "src/main/resources/edu/wsu/jumpSound.mp3";
    private final Media deathMedia = new Media(new File(deathSound).toURI().toString());
    private final Media jumpMedia = new Media(new File(jumpSound).toURI().toString());
    private final MediaPlayer deathPlayer = new MediaPlayer(deathMedia);
    private final MediaPlayer jumpPlayer = new MediaPlayer(jumpMedia);

    public NestorRunner(Difficulty difficulty) {
        this.score = 0;
        this.difficulty = difficulty;
        difficultySetter();
        this.nestor = new Nestor(nestorStartX, nestorStartY);
        this.obstacles = new ArrayList<>();
        this.obstacleSpacing = 500;
        this.obstacleCountdown = 0;
        this.rand = new Random();
        initializeObstacleTypes();
    }

    private void initializeObstacleTypes(){
        this.obstacleTypes = new ArrayList<>();
        obstacleTypes.add(EntityType.SMALL_BUILDING);
        obstacleTypes.add(EntityType.BIG_BUILDING);
        // omitted because these obstacles have not been implemented yet
        obstacleTypes.add(EntityType.PROJECTILE);
        obstacleTypes.add(EntityType.HOLE);
    }

    private void difficultySetter(){
        if (this.difficulty == Difficulty.EASY){
            obstacleSpeed = 200;
        } else if (this.difficulty == Difficulty.MEDIUM){
            obstacleSpeed = 300;
        } else {
            obstacleSpeed = 400;
        }
    }

    public void jump(){
        if(!nestor.getJumpingStatus()) {
            //only play the jump sound if the player is currently mid-jump (isJumping)
            jumpPlayer.seek(jumpPlayer.getStartTime());
            jumpPlayer.play();
        }
        nestor.jump();
    }

    public boolean update(double deltaTime){
        // nestor.update updates nestors current position
        double deltaTimeMod = deltaTime * deltaTimeModifier;
        nestor.update(deltaTimeMod);
        this.speedCounter += deltaTime;
        if (this.speedCounter >= 5){
            this.deltaTimeModifier += .1;
            this.speedCounter = 0;
        }

        // for each obstacle, update them and check for collision
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update(deltaTimeMod);
            if (obstacle.getX() < 110){
                if (obstacle.leftCollidesWith(nestor)){
                    // play death sound upon collision
                    deathPlayer.seek(deathPlayer.getStartTime());
                    deathPlayer.play();
                    return true;
                }
                // if obstacle is past nestor and outside the screen, delete it.
                if (obstacle.getX() <= 0 - obstacle.getWidth()){
                    obstacles.remove(0);
                    score++;
                }
            }
        }
        // update obstacle countdown based on change in time
        obstacleCountdown -= obstacleSpeed * deltaTimeMod;

        // if the obstacle countdown reaches zero, its time to spawn an obstacle and reset the countdown
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
        for (Obstacle obstacle : obstacles) {
            positions.add(obstacle.getX());
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
        int obstacleSelector = rand.nextInt(4); // set bound equal to # of available obstacles
        return new Obstacle(obstacleTypes.get(obstacleSelector), this.obstacleSpeed);
     }
}
