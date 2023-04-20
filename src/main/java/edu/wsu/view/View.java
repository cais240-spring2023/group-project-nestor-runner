package edu.wsu.view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public interface View {

    int SCENE_WIDTH = 640;
    int SCENE_HEIGHT = 480;
    String TITLE_FONT_WIN = "Blackadder ITC";
    String BASE_FONT = "Franklin Gothic Medium";

    static Stage getStage(ActionEvent actionEvent) {
        return (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }
}
