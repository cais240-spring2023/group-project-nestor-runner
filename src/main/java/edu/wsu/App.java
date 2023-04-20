package edu.wsu;

import edu.wsu.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
        stage.setScene(new Scene(menuLoader.load(), View.SCENE_WIDTH, View.SCENE_HEIGHT));
        stage.setTitle("Nestor Runner");
        stage.getIcons().add(
                new Image(Objects.requireNonNull(App.class.getResourceAsStream("/edu/wsu/sprites/Nestor.png")))
        );
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}