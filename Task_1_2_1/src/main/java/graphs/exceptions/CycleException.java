package graphs.exceptions;

/**
 * An exception occurred when this graph has a cycle.
 */
public class CycleException extends TopologicalSortException {
    public CycleException() {
        super("A cycle found");
    }
}
