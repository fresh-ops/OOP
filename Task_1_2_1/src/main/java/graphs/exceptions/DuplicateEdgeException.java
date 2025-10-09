package graphs.exceptions;

import graphs.Edge;

/**
 * An exception occurred when trying to add an edge that is already in graph.
 */
public class DuplicateEdgeException extends DuplicateElementException {
    /**
     * Constructs a new duplicate edge exception.
     *
     * @param edge a duplicate edge
     */
    public DuplicateEdgeException(Edge edge) {
        super(String.format(
                "The edge %s is already in graph",
                edge
        ));
    }}
