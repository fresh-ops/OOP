package graphs.exceptions;

import graphs.Vertex;

/**
 * An exception occurred when trying to add a vertex that is already in graph.
 */
public class DuplicateVertexException extends DuplicateElementException {
    /**
     * Constructs a new duplicate vertex exception.
     *
     * @param vertex a duplicate vertex
     */
    public DuplicateVertexException(Vertex vertex) {
        super(String.format(
            "The vertex %s is already in graph",
            vertex
        ));
    }
}
