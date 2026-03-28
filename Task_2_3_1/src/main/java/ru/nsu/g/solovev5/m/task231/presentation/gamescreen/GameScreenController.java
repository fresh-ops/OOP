package ru.nsu.g.solovev5.m.task231.presentation.gamescreen;

import java.util.Objects;
import javafx.fxml.FXML;
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;
import ru.nsu.g.solovev5.m.task231.presentation.board.BoardController;

/**
 * A controller for the game screen.
 */
public class GameScreenController {
    @FXML
    private BoardController boardController;

    /**
     * Initializes this controller.
     */
    @FXML
    private void initialize() {
        Objects.requireNonNull(boardController);
    }

    /**
     * Renders the game state.
     *
     * @param state the state to render
     */
    public void render(GameStateRecord state) {
        boardController.render(state);
    }
}
