package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.entities.CannonBall;
import edu.wsu.model.entities.Entity;
import edu.wsu.model.entities.FloorPiece;
import edu.wsu.model.entities.Nestor;
import edu.wsu.model.NestorRunner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.io.IOException;
import java.util.Objects;

public class GameView extends StackPane implements View {

    private final Label scoreField;
    private final Sound sound;
    private final FreezePane endPane;
    private final FreezePane pausePane;
    private final GraphicsContext gc;
    private final Canvas canvas;
    private boolean paused;

    public GameView(double musicVolume, double sfxVolume) {
        super();

        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #add8e6;");

        endPane = new FreezePane("Game Over!", "Play Again", "Main Menu");
        endPane.setButton2Action(this::swapToMainMenu);
        pausePane = new FreezePane("Game Paused.", "Resume", "Main Menu");
        sound = new Sound(musicVolume, sfxVolume);
        canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        StackPane playSpace = new StackPane();
        playSpace.getChildren().add(canvas);
        this.getChildren().add(playSpace);

        BorderPane hud = new BorderPane();
        GridPane scoreBox = new GridPane();
        Label scoreLabel = new Label("Score:");
        scoreLabel.setFont(Font.font("Franklin Gothic Medium", 20));
        scoreBox.add(scoreLabel,0, 0);
        scoreField = new Label("0000");
        scoreField.setFont(Font.font("Franklin Gothic Medium", FontPosture.ITALIC, 30));
        scoreBox.add(scoreField,0, 1);
        scoreBox.setPadding(new Insets(15, 15, 15, 15));
        hud.setTop(scoreBox);
        this.getChildren().add(hud);
    }

    public void setEventHandler(EventHandler<KeyEvent> e) {
        canvas.setOnKeyPressed(e);
        canvas.requestFocus();
    }

    public void swapToMainMenu(ActionEvent event) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
            Parent menuRoot = menuLoader.load();
            View.getStage(event).setScene(new Scene(menuRoot, 640, 480));

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Object obj) {
        assert obj instanceof NestorRunner;
        NestorRunner nestorRunner = (NestorRunner) obj;

        wipeGC();

        for (Entity entity : nestorRunner.getScrollingEntities()) {
            draw(
                    entity.type().toString(),
                    entity.getX(), entity.getY(),
                    entity.getWidth(), entity.getHeight()
            );
        }
        for (FloorPiece floorPiece : nestorRunner.getFloorPieces()) {
            draw(
                    floorPiece.type().toString(),
                    floorPiece.getX(), floorPiece.getY(),
                    floorPiece.getWidth(), floorPiece.getHeight()
            );
        }
        for (CannonBall cannonBall : nestorRunner.getCannonBalls()) {
            draw(
                    cannonBall.type().toString(),
                    cannonBall.getX(), cannonBall.getY(),
                    cannonBall.getWidth(), cannonBall.getHeight()
            );
        }

        if (nestorRunner.getCannonTimer() > 0) {
            draw("Cannon", nestorRunner.getNestorX(), nestorRunner.getNestorY(),
                    73, 55);
            if (sound.backGroundTrackIsPlaying()) {
                sound.pauseBackGroundTrack();
                sound.startDoomSoundTrack();
                sound.doomIsActive = true;
            }
        }
        else if (sound.doomSoundTrackIsPlaying()) {
            sound.pauseDoomSoundTrack();
            sound.resumeBackGroundTrack();
            sound.doomIsActive = false;
        }

        if (nestorRunner.getShieldTimer() > 0) {
            draw("Bubble", nestorRunner.getNestorX() - (nestorRunner.bubbleRadius - Nestor.WIDTH/2),
                    nestorRunner.getNestorY() - (nestorRunner.bubbleRadius - 10),
                    2 * nestorRunner.bubbleRadius, 2 * nestorRunner.bubbleRadius);
        }

        draw("Nestor",
                nestorRunner.getNestorX(), nestorRunner.getNestorY(),
                Nestor.WIDTH, Nestor.HEIGHT);

        drawScore(nestorRunner.getScore());
    }

    public void wipeGC() {
        gc.setFill(Color.rgb(0, 0, 0, 0));
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    public void drawScore(int score) {
        scoreField.setText(String.format("%04d", score));
    }

    /**
     * @param spriteName the name of the Sprite in resources.
     */
    public void draw(String spriteName, int xPos, int yPos, int width, int height) {
        if (spriteName.equals("FloorPiece"))  {
            gc.setFill(Color.BLACK);
            gc.fillRect(xPos, yPos, width, height);
        }
        else {
            String imgURL = "/edu/wsu/sprites/" + spriteName + ".png";
            Image img = new Image(Objects.requireNonNull(getClass().getResource(imgURL)).toString());
            gc.drawImage(img, xPos, yPos, width, height);
        }
    }

    public Sound getSound() {
        return sound;
    }

    public void end() {
        getChildren().add(endPane);
        sound.stopBackGroundTrack();
        sound.stopDoomSoundTrack();
        sound.playDeathSound();
    }

    public void restart() {
        getChildren().remove(endPane);
        sound.startBackGroundTrack();
    }

    public void pause() {
        if (paused) return;

        getChildren().add(pausePane);
        if (sound.backGroundTrackIsPlaying()) sound.pauseBackGroundTrack();
        else if (sound.doomSoundTrackIsPlaying()) sound.pauseDoomSoundTrack();

        paused = true;
    }

    public void unPause() {
        if (!paused) return;

        getChildren().remove(pausePane);
        if (sound.doomIsActive) sound.resumeDoomSoundTrack();
        else sound.resumeBackGroundTrack();

        paused = false;
    }

    public FreezePane getPausePane() {
        return pausePane;
    }
    public FreezePane getEndPane() {
        return endPane;
    }
}
