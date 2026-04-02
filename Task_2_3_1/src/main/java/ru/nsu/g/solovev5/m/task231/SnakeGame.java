package ru.nsu.g.solovev5.m.task231;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.g.solovev5.m.task231.presentation.drawer.GridDrawer;

/**
 * The main program class.
 */
public class SnakeGame extends Application {
    /**
     * The application entry point.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Snake Game");

        var drawer = new GridDrawer(12, 15);
        var pane = new BorderPane();
        pane.setCenter(drawer);

        var scene = new Scene(pane, 640, 480);


        stage.setScene(scene);
        stage.show();
    }
}