package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.*;
import edu.wsu.model.enums.Difficulty;

import java.io.File;

import edu.wsu.view.FreezePane;
import edu.wsu.view.View;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameController {

    private NestorRunner gameInstance;

    @FXML
    StackPane gameRoot;
    @FXML
    Label scoreField;
    @FXML
    StackPane playSpace;
    @FXML
    Pane ground;

    private FreezePane endPane;
    private FreezePane pausePane;
    private Canvas canvas;
    private GraphicsContext gc;

    // Patch notes: added Doom OST to improve player concentration.
    private MediaPlayer backgroundPlayer;


    @FXML
    public void initialize() {
        setBackgroundTrack("edu/wsu/sound/Fluffing-a-Duck.mp3");
        endPane = new FreezePane("Game Over!",
                "Play Again", "Main Menu",
                event -> start(), this::swapToMainMenu
        );
        pausePane = new FreezePane("Game Paused.",
                "Resume", "Main Menu",
                event -> unPause(), this::swapToMainMenu
        );
    }

    public int getWidth() {
        return (int) canvas.getWidth();
    }

    public int getHeight() {
        return (int) canvas.getHeight();
    }

    public void setBackgroundTrack(String newBackgroundTrack) {
        String backgroundTrack = "src/main/resources/" + newBackgroundTrack;
        Media backgroundMedia = new Media(new File(backgroundTrack).toURI().toString());
        backgroundPlayer = new MediaPlayer(backgroundMedia);
    }

    public void draw(Entity ent) {
        String imgURL = "";
        switch (ent.getEntityType()) {
            case NESTOR:
                imgURL = "/edu/wsu/sprites/Nestor.png";
                break;
            case BIG_BUILDING:
                imgURL = "/edu/wsu/sprites/BigBuilding.png";
                break;
            case SMALL_BUILDING:
                imgURL = "/edu/wsu/sprites/SmallBuilding.png";
                break;
        }
        Image img = new Image(Objects.requireNonNull(getClass().getResource(imgURL)).toString());
        gc.drawImage(img, ent.getX(), ent.getY(), ent.getWidth(), ent.getHeight());
    }

    public void drawEntities(ArrayList<Entity> entities){
        for (Entity entity : entities) {
            draw(entity);
        }
    }

    /**
     * This belongs in GameView (just like draw entities etc.), but it's here for now just so score updates.
     * The logic is going to be the same regardless of where it is.
     * @author Jacob York
     */
    private void drawScore(int newScore) {
        scoreField.setText(formatScore(newScore));
    }

    private static String formatScore(int score) {
        int numDigits = ("" + score).length();
        return "0".repeat(4 - numDigits) + score;
    }

    private void swapToMainMenu(ActionEvent event) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
            Parent menuRoot = menuLoader.load();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Scene menuScene = new Scene(menuRoot, 640, 480);
            stage.setScene(menuScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // temp
    private void pause() {}
    private void unPause() {}

    public void updateGC() {
        // Clear the canvas,
        gc.setFill(Color.rgb(0, 0, 0, 0));
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.fillRect(0, 0, getWidth(), getHeight());
        // and redraw everything.
        drawEntities(gameInstance.getEntities());
        drawScore(gameInstance.getScore());
    }

    public void start() {
        this.gameInstance = new NestorRunner(Difficulty.EASY, (int) playSpace.getPrefHeight());

        // Patch notes: added Doom OST to improve player concentration during hard difficulty.
        if (gameInstance.getDifficulty() == Difficulty.EASY)
            setBackgroundTrack("Copyright/Rip-and-Tear-Doom-OST.mp3");

        // start background music
        backgroundPlayer.seek(backgroundPlayer.getStartTime());
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setVolume(0.2);
        backgroundPlayer.play();

        // view constructor takes care of this
        gameRoot.getChildren().remove(endPane);
        canvas = new Canvas(View.SCENE_WIDTH, gameInstance.ground);

        // wipe old gc before getting new one.
        if (gc != null) updateGC();
        gc = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);
        playSpace.getChildren().add(canvas);

        AnimationTimer timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                // 1e9 = 10^-9
                double deltaTime = (now - lastTime) / 1e9;
                lastTime = now;
                updateGC();
                // update the game, update returns true if there is a collision event
                boolean collision = gameInstance.update(deltaTime);
                if (collision) {
                    stop();
                    // stop background music (on player death)
                    backgroundPlayer.stop();
                    endPane.setTitle("Game Over!\nScore: " + formatScore(gameInstance.getScore()));
                    gameRoot.getChildren().add(endPane);
                }

            }
        };

        // javafx keyboard event set focus at the end
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameInstance.jump();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                // pause
            }
        });

        timer.start();
        canvas.requestFocus();
    }
}