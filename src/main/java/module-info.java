module edu.wsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens edu.wsu to javafx.fxml;
    exports edu.wsu;

    opens edu.wsu.controller to javafx.fxml;
    exports edu.wsu.controller;

    opens edu.wsu.view to javafx.fxml;
    exports edu.wsu.view;
}
