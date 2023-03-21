package edu.wsu.model;

import edu.wsu.model.enums.Difficulty;
import edu.wsu.model.enums.EntityType;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class NestorRunner {
    public boolean paused;

    private ArrayList<EntityType> obstacleTypes;
    private int score;
    private int obstacleSpeed;
    Nestor nestor;
    ArrayList<Obstacle> obstacles;
    int obstacleSpacing;
    int obstacleCountdown;
    Random rand;
    Difficulty difficulty;

    private final String deathSound = "src/main/resources/edu/wsu/sound/deathSound.wav";
    private final String jumpSound = "src/main/resources/edu/wsu/sound/jumpSound.mp3";
    private final Media deathMedia = new Media(new File(deathSound).toURI().toString());
    private final Media jumpMedia = new Media(new File(jumpSound).toURI().toString());
    private final MediaPlayer deathPlayer = new MediaPlayer(deathMedia);
    private final MediaPlayer jumpPlayer = new MediaPlayer(jumpMedia);

    public NestorRunner(Difficulty difficulty) {
        this.paused = false;
        this.score = 0;
        this.difficulty = difficulty;
        difficultySetter();
        this.nestor = new Nestor();
        this.obstacles = new ArrayList<>();
        this.obstacleSpacing = 200;
        this.obstacleCountdown = 0;
        this.rand = new Random();
        initializeObstacleTypes();
    }

    private void initializeObstacleTypes(){
        this.obstacleTypes = new ArrayList<>();
        obstacleTypes.add(EntityType.SMALL_BUILDING);
        obstacleTypes.add(EntityType.BIG_BUILDING);
        // omitted because these obstacles have not been implemented yet
        // obstacleTypes.add(EntityType.PROJECTILE);
        // obstacleTypes.add(EntityType.HOLE);
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

    public void jump() {
        if(!nestor.getJumpingStatus()) {
            // only play the jump sound if the player is currently mid-jump (isJumping)
            jumpPlayer.seek(jumpPlayer.getStartTime());
            jumpPlayer.play();
        }
        nestor.jump();
    }

    public boolean update(double deltaTime) {
        nestor.update(deltaTime);
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.update(deltaTime);
            if (obstacle.getX() < 110){
                if (obstacle.leftCollidesWith(nestor)){
                    // play death sound upon collision
                    deathPlayer.seek(deathPlayer.getStartTime());
                    deathPlayer.play();
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
            // obstacles.add(new Obstacle(canvasWidth, canvasHeight - OBSTACLE_HEIGHT));
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

     public Difficulty getDifficulty() {
        return difficulty;
     }
     public void setDifficulty(Difficulty newDifficulty) {
        difficulty = newDifficulty;
     }

     private Obstacle randObstacleGenerator(){
        int obstacleSelector = rand.nextInt(2); // set bound equal to # of available obstacles
        return new Obstacle(obstacleTypes.get(obstacleSelector));
     }
}
