package edu.wsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("menu.fxml"));
        stage.setScene(new Scene(menuLoader.load(), 640, 480));

        stage.setTitle("Nestor Runner");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}