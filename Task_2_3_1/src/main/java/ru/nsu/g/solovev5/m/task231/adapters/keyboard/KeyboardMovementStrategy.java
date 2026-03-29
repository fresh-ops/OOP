package ru.nsu.g.solovev5.m.task231.adapters.keyboard;

import javafx.scene.input.KeyEvent;

/**
 * A movement strategy based on the keyboard controlling.
 */
public class KeyboardMovementStrategy extends BufferedMovementStrategy {
    public KeyboardMovementStrategy(int bufferSize) {
        super(bufferSize);
    }

    /**
     * Handles the key pressed event and translates key codes into move directions.
     *
     * @param e the key pressed event
     */
    public void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W, UP -> put(MoveDirection.UP);
            case S, DOWN -> put(MoveDirection.DOWN);
            case A, LEFT -> put(MoveDirection.LEFT);
            case D, RIGHT -> put(MoveDirection.RIGHT);
            default -> {
            }
        }
    }
}
