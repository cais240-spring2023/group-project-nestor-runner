package edu.wsu.view;

import edu.wsu.controller.NestorRunnerController;
import edu.wsu.model.Entities.Entity;
import edu.wsu.model.Nestor;
import edu.wsu.model.NestorRunner;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class NestorRunnerView extends Pane implements Observer, View {

    Image largeObstacleImg, coinImg, nestorImg, projectileImg, shieldImg, smallObstacleImg;
    ImageView nestorView;

    FreezePane gameOverPane;
    FreezePane pausePane;

    NestorRunnerController controller;

    public static final String LARGE_OBSTACLE_PNG = "/edu/wsu/sprites/LargeObstacle.png";
    public static final String COIN_PNG = "/edu/wsu/sprites/Coin.png";
    public static final String NESTOR_PNG = "/edu/wsu/sprites/Nestor.png";
    public static final String PROJECTILE_PNG = "/edu/wsu/sprites/Projectile.png";
    public static final String SHIELD_PNG = "/edu/wsu/sprites/Shield.png";
    public static final String SMALL_OBSTACLE_PNG = "/edu/wsu/sprites/SmallObstacle.png";

    public NestorRunnerView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(NestorRunnerView.class.getResource("/edu/wsu/fxml/game2.fxml"));
        fxmlLoader.setRoot(this);
        controller = new NestorRunnerController();
        fxmlLoader.setController(controller);
        fxmlLoader.load();
        largeObstacleImg = new Image(Objects.requireNonNull(getClass().getResource(LARGE_OBSTACLE_PNG)).toString());
        coinImg = new Image(Objects.requireNonNull(getClass().getResource(COIN_PNG)).toString());
        nestorImg = new Image(Objects.requireNonNull(getClass().getResource(NESTOR_PNG)).toString());
        projectileImg = new Image(Objects.requireNonNull(getClass().getResource(PROJECTILE_PNG)).toString());
        shieldImg = new Image(Objects.requireNonNull(getClass().getResource(SHIELD_PNG)).toString());
        smallObstacleImg = new Image(Objects.requireNonNull(getClass().getResource(SMALL_OBSTACLE_PNG)).toString());

        initializeGame();
    }

    public Label getScoreLabel() {
         return (Label) this.getChildren().get(2);
    }

    public void initializeGame() {
        nestorView = new ImageView(nestorImg);
        nestorView.setLayoutX(Nestor.X);
        nestorView.setLayoutY(Nestor.GROUND_Y - Nestor.HEIGHT);
        nestorView.setFitWidth(Nestor.WIDTH);
        nestorView.setFitHeight(Nestor.HEIGHT);
        this.getChildren().add(nestorView);
        controller.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof NestorRunner;
        assert arg instanceof NestorRunner;

        NestorRunner gameInstance = (NestorRunner) arg;
        Label score = getScoreLabel();
        score.setText(gameInstance.getScore().toString());

        nestorView.setLayoutY(gameInstance.getNestorY());

        for (Entity entity : gameInstance.getEntities()) {
            ImageView entityView = null;
            switch (entity.type()) {
                case "Coin":
                    entityView = new ImageView(coinImg);
                    break;
                case "Hole":
                    Rectangle rectangle = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
                    rectangle.setFill(Color.LIGHTSTEELBLUE);
                    break;
                case "LargeObstacle":
                    entityView = new ImageView(largeObstacleImg);
                    break;
                case "Projectile":
                    entityView = new ImageView(projectileImg);
                    break;
                case "Shield":
                    entityView = new ImageView(shieldImg);
                    break;
                case "SmallObstacle":
                    entityView = new ImageView(smallObstacleImg);
                    break;
            }
            if (entityView != null) {
                entityView.setLayoutX(entity.getX());
                entityView.setLayoutY(entity.getY());
                entityView.setFitWidth(entity.getWidth());
                entityView.setFitHeight(entity.getHeight());
            }
            this.getChildren().add(entityView);
        }

        if (gameInstance.isDead()) {
            gameOverPane = new FreezePane("Game Over", "Restart", "Main Menu");
            this.getChildren().add(gameOverPane);
        }

        if (gameInstance.isPaused()) {
            pausePane = new FreezePane("Paused", "Resume", "Main Menu");
            this.getChildren().add(pausePane);
        }
    }
}
