package ru.nsu.solovev5.m.task121.graphs.exceptions;

/**
 * An exception occurred when this graph has a cycle.
 */
public class CycleException extends TopologicalSortException {
    public CycleException() {
        super("A cycle found");
    }
}
