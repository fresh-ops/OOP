package ru.nsu.g.solovev5.m.task231;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.g.solovev5.m.task231.adapters.keyboard.KeyboardMovementStrategy;
import ru.nsu.g.solovev5.m.task231.application.CalculateNextStateUseCase;
import ru.nsu.g.solovev5.m.task231.application.CheckBorderCollisionsUseCase;
import ru.nsu.g.solovev5.m.task231.application.CheckDeadCollisionsUseCase;
import ru.nsu.g.solovev5.m.task231.application.CheckEnemiesCollisions;
import ru.nsu.g.solovev5.m.task231.application.CheckSelfCollisionsUseCase;
import ru.nsu.g.solovev5.m.task231.application.CreateGameStateFromConfigUseCase;
import ru.nsu.g.solovev5.m.task231.application.EatFoodUseCase;
import ru.nsu.g.solovev5.m.task231.application.GameWorker;
import ru.nsu.g.solovev5.m.task231.application.GenerateFoodUseCase;
import ru.nsu.g.solovev5.m.task231.application.GetCollidingFoodUseCase;
import ru.nsu.g.solovev5.m.task231.application.GetFreeCellsUseCase;
import ru.nsu.g.solovev5.m.task231.application.MoveSnakeUseCase;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.application.config.PlayerConfig;
import ru.nsu.g.solovev5.m.task231.application.strategies.cellpicking.RandomCellPickingStrategy;
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
    private GameWorker gameWorker;

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

        gameWorker = new GameWorker(
            new CreateGameStateFromConfigUseCase(),
            new CalculateNextStateUseCase(
                new GenerateFoodUseCase(
                    new GetFreeCellsUseCase(),
                    new RandomCellPickingStrategy(),
                    () -> FoodType.NORMAL
                ),
                new MoveSnakeUseCase(),
                new CheckDeadCollisionsUseCase(
                    new CheckBorderCollisionsUseCase(),
                    new CheckSelfCollisionsUseCase(),
                    new CheckEnemiesCollisions()
                ),
                new EatFoodUseCase(
                    new GetCollidingFoodUseCase()
                )
            ),
            CONFIG
        );
        gameLoopThread = new Thread(gameWorker);

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
                var state = gameWorker.getStateRecord();
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