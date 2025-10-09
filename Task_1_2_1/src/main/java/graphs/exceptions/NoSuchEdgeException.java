package graphs.exceptions;

import graphs.Edge;

/**
 * An exception occurred when this graph does not have the specified edge.
 */
public class NoSuchEdgeException extends NoSuchElementException {
    /**
     * Constructs a new no such edge exception.
     *
     * @param edge a missing edge
     */
    public NoSuchEdgeException(Edge edge) {
        super(String.format(
            "No such edge: %s",
            edge
        ));
    }
}
