package ru.nsu.solovev5.m.task121.graphs.exceptions;

/**
 * A basic exception occurred during the topological sorting of the graph.
 */
public abstract class TopologicalSortException extends GraphException {
    /**
     * Constructs a new topological sort exception.
     *
     * @param message a detail message
     */
    public TopologicalSortException(String message) {
        super(String.format(
            "Can not sort the graph: %s",
            message
        ));
    }
}
