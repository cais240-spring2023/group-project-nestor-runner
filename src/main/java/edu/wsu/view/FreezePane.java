/**
 * A pane with two buttons for displaying Messages.
 * Sits on top of the game.
 */
package edu.wsu.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FreezePane extends StackPane {

    Label titleLabel;
    Button button1;
    Button button2;

    public FreezePane(String title, String button1Title, String button2Title) {
        super();
        this.setAlignment(Pos.CENTER);

        Pane grayFilter = new Pane(); {
            grayFilter.setOpacity(.75);
            grayFilter.setStyle("-fx-background-color: #808080;");
            this.getChildren().add(grayFilter);
        }

        VBox vBox = new VBox(); {
            vBox.setAlignment(Pos.CENTER);
            titleLabel = new Label(title); {
                titleLabel.setFont(Font.font("Blackadder ITC", 100));
                titleLabel.setTextFill(Color.WHITE);
                VBox.setMargin(titleLabel, new Insets(5, 5, 5, 5));
                vBox.getChildren().add(titleLabel);
            }
            button1 = new Button(button1Title); {
                button1.setCursor(Cursor.HAND);
                button1.setFont(Font.font("Franklin Gothic Medium", 20));
                button1.setTextFill(Color.WHITE);
                button1.setStyle("-fx-background-color: #000000;");
                VBox.setMargin(button1, new Insets(5, 5, 5, 5));
                vBox.getChildren().add(button1);
            }
            button2 = new Button(button2Title); {
                button2.setCursor(Cursor.HAND);
                button2.setFont(Font.font("Franklin Gothic Medium", 20));
                button2.setTextFill(Color.WHITE);
                button2.setStyle("-fx-background-color: #000000;");
                VBox.setMargin(button2, new Insets(5, 5, 5, 5));
                vBox.getChildren().add(button2);
            }
            this.getChildren().add(vBox);
        }
    }

    public FreezePane(String title, String button1Title, String button2Title,
                      EventHandler<ActionEvent> button1Action, EventHandler<ActionEvent> button2Action) {

        this(title, button1Title, button2Title);
        setButton1Action(button1Action);
        setButton2Action(button2Action);
    }

    public String getTitle() {
        return titleLabel.getText();
    }
    public void setTitle(String newTitle) {
        titleLabel.setText(newTitle);
    }

    public String getButton1Title() {
        return button1.getText();
    }
    public void setButton1Title(String newButton1Title) {
        button1.setText(newButton1Title);
    }

    public String getButton2Title() {
        return button2.getText();
    }
    public void setButton2Title(String newButton2Title) {
        button2.setText(newButton2Title);
    }

    public EventHandler<ActionEvent> getButton1Action() {
        return button1.getOnAction();
    }
    public void setButton1Action(EventHandler<ActionEvent> newButton1Action) {
        button1.setOnAction(newButton1Action);
    }

    public EventHandler<ActionEvent> getButton2Action() {
        return button1.getOnAction();
    }
    public void setButton2Action(EventHandler<ActionEvent> newButton2Action) {
        button2.setOnAction(newButton2Action);
    }
}
