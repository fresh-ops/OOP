package ru.nsu.g.solovev5.m.task212.actors;

/**
 * Defines an actor contract.
 */
public interface Actor extends Runnable {
    /**
     * Returns this actor role.
     *
     * @return this actor role
     */
    Role getRole();

    /**
     * Tests if this actor is alive.
     *
     * @return {@code true} if this actor is alive, {@code false} otherwise
     */
    boolean isAlive();

    /**
     * Stops the actor work.
     */
    void stop();
}
