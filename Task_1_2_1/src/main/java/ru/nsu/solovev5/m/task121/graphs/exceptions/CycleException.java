package ru.nsu.solovev5.m.task121.graphs.exceptions;

/**
 * An exception occurred when this graph has a cycle.
 */
public class CycleException extends TopologicalSortException {
    /**
     * Constructs a new cycle exception.
     */
    public CycleException() {
        super("A cycle found");
    }
}
