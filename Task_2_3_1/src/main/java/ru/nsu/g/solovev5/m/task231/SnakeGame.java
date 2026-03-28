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
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;
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

        var loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.render(new GameStateRecord(8, 8, List.of()));
            }
        };
        loop.start();

        stage.setScene(scene);
        stage.show();
    }
}