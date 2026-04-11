package ru.nsu.g.solovev5.m.task231;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.g.solovev5.m.task231.adapters.keyboard.KeyboardMovementStrategy;
import ru.nsu.g.solovev5.m.task231.application.GameSession;
import ru.nsu.g.solovev5.m.task231.application.StartNewGameSessionUseCase;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.application.config.PlayerConfig;
import ru.nsu.g.solovev5.m.task231.application.strategies.cellpicking.RandomCellPickingStrategy;
import ru.nsu.g.solovev5.m.task231.domain.services.EatFoodService;
import ru.nsu.g.solovev5.m.task231.domain.services.FoodGenerator;
import ru.nsu.g.solovev5.m.task231.domain.services.GameTickService;
import ru.nsu.g.solovev5.m.task231.domain.services.MoveSnakeService;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;
import ru.nsu.g.solovev5.m.task231.presentation.drawer.GridDrawer;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.GameRenderer;

/**
 * The main program class.
 */
public class SnakeGame extends Application {
    private static final GameConfig CONFIG = new GameConfig(
        12, 15,
        5,
        List.of(
            new PlayerConfig(
                new Point2D(0, 0),
                new KeyboardMovementStrategy(3)
            )
        )
    );

    private AnimationTimer animationLoop;
    private Thread gameLoopThread;
    private GameSession gameSession;

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

        var startSessionUseCase = new StartNewGameSessionUseCase();
        gameSession = startSessionUseCase.invoke(
            CONFIG,
            new GameTickService(
                new FoodGenerator(
                    new RandomCellPickingStrategy(),
                    () -> FoodType.NORMAL
                ),
                new MoveSnakeService(),
                new EatFoodService()
            )
        );
        gameLoopThread = new Thread(gameSession);

        var drawer = new GridDrawer(CONFIG.rows(), CONFIG.columns());
        var renderer = new GameRenderer();
        animationLoop = new AnimationTimer() {
            long lastUpdated = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdated < 1_000) {
                    return;
                }

                drawer.clearCanvas();
                drawer.drawBoard();
                var state = gameSession.getStateRecord();
                var figures = renderer.renderAll(state.snakes(), state.foods());

                for (var figure : figures) {
                    drawer.drawFigure(figure);
                }
                lastUpdated = now;
            }
        };

        var pane = new BorderPane();
        pane.setCenter(drawer);

        var scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.show();

        var keyedMovement = CONFIG.players().stream()
            .map(PlayerConfig::strategy)
            .filter(s -> s instanceof KeyboardMovementStrategy)
            .map(KeyboardMovementStrategy.class::cast)
            .toList();

        scene.setOnKeyPressed(event -> {
            for (var strategy : keyedMovement) {
                strategy.onKeyPressed(event);
            }
        });

        animationLoop.start();
        gameLoopThread.start();
    }

    @Override
    public void stop() throws Exception {
        animationLoop.stop();
        gameLoopThread.interrupt();
        gameLoopThread.join();
    }
}