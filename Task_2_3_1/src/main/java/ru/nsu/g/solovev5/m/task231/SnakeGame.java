package ru.nsu.g.solovev5.m.task231;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.presentation.drawer.GridDrawer;

/**
 * The main program class.
 */
public class SnakeGame extends Application {
    private static final GameConfig CONFIG = new GameConfig(
        12, 15,
        5,
        List.of()
    );

    private AnimationTimer animationLoop;

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

        var drawer = new GridDrawer(CONFIG.rows(), CONFIG.columns());
        animationLoop = new AnimationTimer() {
            long lastUpdated = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdated < 1_000) {
                    return;
                }

                drawer.clearCanvas();
                drawer.drawBoard();
                lastUpdated = now;
            }
        };

        var pane = new BorderPane();
        pane.setCenter(drawer);

        var scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.show();

        animationLoop.start();
    }

    @Override
    public void stop() throws Exception {
        animationLoop.stop();
    }
}