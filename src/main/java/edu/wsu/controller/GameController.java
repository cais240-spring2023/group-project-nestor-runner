package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Entity;
import edu.wsu.model.Nestor;
import edu.wsu.model.Obstacle;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class GameController {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int OBSTACLE_HEIGHT = 50;
    private static final int OBSTACLE_SPEED = 100;

    @FXML
    StackPane gameRoot;
    @FXML
    BorderPane hud;
    @FXML
    StackPane playSpace;
    @FXML
    Pane ground;

    private StackPane endScreen;
    private Canvas canvas;
    private GraphicsContext gc;

    private Nestor nestor;
    private Obstacle[] obstacles;
    private int numObstacles;
    private int obstacleSpacing;
    private int obstacleCounter;
    private Random rand;

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
        gc.setFill(ent.getColor());
        gc.fillRect(ent.getX(), ent.getY(), ent.getWidth(), ent.getHeight());
    }

    private void mainMenu(ActionEvent event) {
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

    private StackPane getPauseScreen() {
        ////////////////////////////////////////////////
        VBox gameEndMenu = new VBox();
        gameEndMenu.setAlignment(Pos.CENTER);

        Label resultsTitle = new Label("Paused");
        resultsTitle.setFont(Font.font("Blackadder ITC", 100));
        resultsTitle.setTextFill(Color.WHITE);
        VBox.setMargin(resultsTitle, new Insets(5, 5, 5, 5));

        Button resume = new Button("Resume");
        resume.setCursor(Cursor.HAND);
        resume.setFont(Font.font("Franklin Gothic Medium", 20));
        resume.setTextFill(Color.WHITE);
        resume.setStyle("-fx-background-color: #000000;");
        VBox.setMargin(resume, new Insets(5, 5, 5, 5));
        // resume.setOnAction(event -> start());

        Button mainMenu = new Button("Main Menu");
        mainMenu.setCursor(Cursor.HAND);
        mainMenu.setFont(Font.font("Franklin Gothic Medium", 20));
        mainMenu.setTextFill(Color.WHITE);
        mainMenu.setStyle("-fx-background-color: #000000;");
        VBox.setMargin(mainMenu, new Insets(5, 5, 5, 5));
        mainMenu.setOnAction(event -> mainMenu(event));

        gameEndMenu.getChildren().addAll(resultsTitle, resume, mainMenu);
        ////////////////////////////////////////////////

        Pane grayFilter = new Pane();
        grayFilter.setOpacity(.75);
        grayFilter.setStyle("-fx-background-color: #808080;");

        StackPane endScreen = new StackPane();
        endScreen.setAlignment(Pos.CENTER);
        endScreen.getChildren().addAll(grayFilter, gameEndMenu);

        return endScreen;
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
        mainMenu.setOnAction(event -> mainMenu(event));

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
        gameRoot.getChildren().remove(endScreen);

        canvas = new Canvas(playSpace.getPrefWidth(), playSpace.getPrefHeight());
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        playSpace.getChildren().add(canvas);

        nestor = new Nestor(200, 200);
        obstacles = new Obstacle[10];
        numObstacles = 0;
        obstacleSpacing = 300;
        obstacleCounter = obstacleSpacing;
        rand = new Random();

        // javafx keyboard event set focus at the end
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                nestor.jump();
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

                // Clear the canvas
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, getWidth(), getHeight());

                // Update and draw the nestor
                nestor.update(deltaTime);
                draw(nestor);

                // Update and draw the obstacles
                for (int i = 0; i < numObstacles; i++) {
                    Obstacle obstacle = obstacles[i];
                    obstacle.update(deltaTime);
                    draw(obstacle);

                    if (obstacle.leftCollidesWith(nestor)) {
                        stop();
                        gameRoot.getChildren().add(endScreen);
                    }
                }

                // Spawn a new obstacle if needed
                //
                obstacleCounter -= OBSTACLE_SPEED * deltaTime;
                if (obstacleCounter <= 0) {
                    obstacleCounter = obstacleSpacing;
                    if (numObstacles < obstacles.length) {
                        obstacles[numObstacles] = new Obstacle(getWidth(), getHeight() - OBSTACLE_HEIGHT);
                        numObstacles++;
                    }
                }
            }
        };
        timer.start();
        canvas.requestFocus();
    }
}
