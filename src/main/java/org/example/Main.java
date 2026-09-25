package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Label message = new Label("Welcome, Frank Simukonda!");

        Button startButton = new Button("Start");
        Button resetButton = new Button("Reset");

        startButton.setOnAction(e -> {
            message.setText("Great! You clicked the button.");
        });
        resetButton.setOnAction(e -> {
            message.setText("Welcome, Frank Simukonda!");
        });

        VBox layout = new VBox(15);
        layout.getChildren().addAll(message, startButton, resetButton);

        Scene scene = new Scene(layout, 400, 300);

        stage.setTitle("JavaFX Lab -202508816 - frank simukonda");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}