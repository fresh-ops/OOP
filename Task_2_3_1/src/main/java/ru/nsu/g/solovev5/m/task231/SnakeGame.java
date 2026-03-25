package ru.nsu.g.solovev5.m.task231;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        var label = new Label("Hello, World!");

        var scene = new Scene(new StackPane(label), 640, 480);

        stage.setScene(scene);
        stage.show();
    }
}