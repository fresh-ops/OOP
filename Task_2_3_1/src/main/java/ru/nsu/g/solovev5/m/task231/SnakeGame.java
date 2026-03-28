package ru.nsu.g.solovev5.m.task231;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.g.solovev5.m.task231.presentation.board.BoardController;

public class SnakeGame extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var boardResource = getClass().getResource("presentation/board/BoardView.fxml");
        Objects.requireNonNull(boardResource);
        var boardLoader = new FXMLLoader(boardResource);

        Parent board = boardLoader.load();
        BoardController controller = boardLoader.getController();

        controller.setColumns(2);
        controller.setRows(3);

        var pane = new BorderPane();
        pane.setCenter(board);

        pane.setStyle("-fx-background-color: linen; -fx-padding: 16;");
        var scene = new Scene(pane, 640, 480);

        stage.setScene(scene);
        stage.show();
    }
}