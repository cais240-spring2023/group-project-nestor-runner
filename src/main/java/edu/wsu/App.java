package edu.wsu;

import edu.wsu.model.NestorRunnerSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("menu.fxml"));
    private static FXMLLoader gameLoader = new FXMLLoader(App.class.getResource("game.fxml"));
    private static Scene scene;
    private static StackPane gamePane;
    private static StackPane menuPane;

    static {
        try {
            menuPane = menuLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            gamePane = gameLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        NestorRunnerSingleton.getInstance().setMenuView(menuLoader.getController());
        NestorRunnerSingleton.getInstance().setGameView(gameLoader.getController());
        scene = new Scene(menuPane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if(fxml.equals("game")) {
            scene.setRoot(gamePane);
        } else if(fxml.equals("menu")) {
            scene.setRoot(menuPane);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}