package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.*;
import edu.wsu.model.enums.Difficulty;
import edu.wsu.model.enums.EntityType;
import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameController {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int OBSTACLE_HEIGHT = 50;
    private static final int OBSTACLE_SPEED = 100;

    private NestorRunner gameInstance;

    @FXML
    StackPane gameRoot;
    @FXML
    Label scoreField;
    @FXML
    StackPane playSpace;
    @FXML
    Pane ground;

    private StackPane endScreen;
    private Canvas canvas;
    private GraphicsContext gc;

    private final String backgroundTrack = "src/main/resources/edu/wsu/Fluffing-a-Duck.mp3";
    private final Media backgroundMedia = new Media(new File(backgroundTrack).toURI().toString());
    private final MediaPlayer backgroundPlayer = new MediaPlayer(backgroundMedia);


    @FXML
    public void initialize() {
        endScreen = getEndScreen();
    }

    public int getWidth() {
        return (int) canvas.getWidth();
    }

    public int getHeight() {
        return (int) canvas.getHeight();
    }

    public void draw(Entity ent) {
        String imgURL = "";
        switch (ent.getEntityType()) {
            case NESTOR:
                imgURL = "/edu/wsu/Nestor.png";
                break;
            case BIG_BUILDING:
                imgURL = "/edu/wsu/BigBuilding.png";
                break;
            case SMALL_BUILDING:
                imgURL = "/edu/wsu/SmallBuilding.png";
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
        int numDigits = ("" + newScore).length();
        String scoreText = "0".repeat(4 - numDigits) + newScore;
        scoreField.setText(scoreText);
    }

    private void swapToMainMenu(ActionEvent event) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("menu.fxml"));
            Parent menuRoot = menuLoader.load();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Scene menuScene = new Scene(menuRoot, 640, 480);
            stage.setScene(menuScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StackPane getEndScreen() {
        ////////////////////////////////////////////////
        VBox gameEndMenu = new VBox();
        gameEndMenu.setAlignment(Pos.CENTER);

        Label resultsTitle = new Label("Game Over!");
        resultsTitle.setFont(Font.font("Blackadder ITC", 100));
        resultsTitle.setTextFill(Color.WHITE);
        VBox.setMargin(resultsTitle, new Insets(5, 5, 5, 5));

        Button playAgain = new Button("Play Again");
        playAgain.setCursor(Cursor.HAND);
        playAgain.setFont(Font.font("Franklin Gothic Medium", 20));
        playAgain.setTextFill(Color.WHITE);
        playAgain.setStyle("-fx-background-color: #000000;");
        VBox.setMargin(playAgain, new Insets(5, 5, 5, 5));
        playAgain.setOnAction(event -> start());

        Button mainMenu = new Button("Main Menu");
        mainMenu.setCursor(Cursor.HAND);
        mainMenu.setFont(Font.font("Franklin Gothic Medium", 20));
        mainMenu.setTextFill(Color.WHITE);
        mainMenu.setStyle("-fx-background-color: #000000;");
        VBox.setMargin(mainMenu, new Insets(5, 5, 5, 5));
        mainMenu.setOnAction(event -> swapToMainMenu(event));

        gameEndMenu.getChildren().addAll(resultsTitle, playAgain, mainMenu);
        ////////////////////////////////////////////////

        Pane grayFilter = new Pane();
        grayFilter.setOpacity(.75);
        grayFilter.setStyle("-fx-background-color: #808080;");

        StackPane endScreen = new StackPane();
        endScreen.setAlignment(Pos.CENTER);
        endScreen.getChildren().addAll(grayFilter, gameEndMenu);

        return endScreen;
    }

        public void start() {
        this.gameInstance = new NestorRunner(Difficulty.EASY);

        //start background music
        backgroundPlayer.seek(backgroundPlayer.getStartTime());
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setVolume(0.2);
        backgroundPlayer.play();

        //view constructor takes care of this
        gameRoot.getChildren().remove(endScreen);
        canvas = new Canvas(playSpace.getPrefWidth(), playSpace.getPrefHeight());
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        playSpace.getChildren().add(canvas);


        // javafx keyboard event set focus at the end
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameInstance.jump();
            }
        });

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

                // Clear the canvas (this should be in view)
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(0, 0, getWidth(), getHeight());

                // update the game, update returns true if there is a collision event
                boolean collision = gameInstance.update(deltaTime);
                drawEntities(gameInstance.getEntities());
                drawScore(gameInstance.getScore());
                if (collision){
                    stop();
                    //stop background music (on player death)
                    backgroundPlayer.stop();
                    gameRoot.getChildren().add(endScreen);
                }

            }
        };
        timer.start();
        canvas.requestFocus();
    }
}
