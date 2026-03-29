package ru.nsu.g.solovev5.m.task231;

import java.util.List;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.g.solovev5.m.task231.adapters.keyboard.KeyboardMovementStrategy;
import ru.nsu.g.solovev5.m.task231.application.GameSession;
import ru.nsu.g.solovev5.m.task231.application.config.GameSessionConfig;
import ru.nsu.g.solovev5.m.task231.application.config.PlayerConfig;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;
import ru.nsu.g.solovev5.m.task231.presentation.gamescreen.GameScreenController;

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

        var gameScreenResource = getClass().getResource("presentation/gamescreen/GameScreen.fxml");
        Objects.requireNonNull(gameScreenResource);
        var gameScreenLoader = new FXMLLoader(gameScreenResource);
        Node gameScreen = gameScreenLoader.load();
        GameScreenController controller = gameScreenLoader.getController();


        var pane = new BorderPane();
        pane.setCenter(gameScreen);
        var scene = new Scene(pane, 640, 480);

        var movement = new KeyboardMovementStrategy(5);
        scene.setOnKeyPressed(movement::onKeyPressed);

        var session = new GameSession(
            new GameSessionConfig(
                8, 8,
                List.of(
                    new PlayerConfig(new Point2D(0, 0), movement)
                )
            )
        );

        var gameLoop = new AnimationTimer() {
            private long lastUpdate;

            @Override
            public void handle(long now) {
                if (now - lastUpdate <= 25_0000_000) {
                    return;
                }
                session.tick();
                lastUpdate = now;
            }
        };
        var animationLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.render(session.getFrozenState());
            }
        };

        gameLoop.start();
        animationLoop.start();

        stage.setScene(scene);
        stage.show();
    }
}