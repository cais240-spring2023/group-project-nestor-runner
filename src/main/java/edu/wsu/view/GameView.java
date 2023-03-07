package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.Entity;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameView {
    private StackPane endScreen;
    private Canvas canvas;
    private GraphicsContext gc;

    StackPane gameRoot;
    BorderPane hud;
    StackPane playSpace;
    Pane ground;

    public GameView(StackPane gameRoot, BorderPane hud, StackPane playSpace, Pane ground){
        this.gameRoot = gameRoot;
        this.hud = hud;
        this.playSpace = playSpace;
        this.ground = ground;
        initialize();
    }

    public void initialize() {
        endScreen = getEndScreen();
        gameRoot.getChildren().remove(endScreen);
        canvas = new Canvas(playSpace.getPrefWidth(), playSpace.getPrefHeight());
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        playSpace.getChildren().add(canvas);
    }

    public int getWidth() {
        return (int) canvas.getWidth();
    }

    public int getHeight() {
        return (int) canvas.getHeight();
    }


    private void draw(Entity ent) {
        gc.setFill(ent.getColor());
        gc.fillRect(ent.getX(), ent.getY(), ent.getWidth(), ent.getHeight());
    }

    public void drawEntities(ArrayList<Entity> entities){
        for (Entity entity : entities) {
            draw(entity);
        }
    }


    public void mainMenu(ActionEvent event) {
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
        //playAgain.setOnAction(event -> start());

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
}
