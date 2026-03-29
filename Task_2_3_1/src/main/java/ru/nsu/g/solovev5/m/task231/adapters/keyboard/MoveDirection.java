package ru.nsu.g.solovev5.m.task231.adapters.keyboard;

/**
 * A movement direction in the Cartesian coordinates.
 */
public enum MoveDirection {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Returns the opposite direction.
     *
     * @return the opposite direction
     */
    public MoveDirection opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
