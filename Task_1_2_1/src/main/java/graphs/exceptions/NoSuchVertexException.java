package graphs.exceptions;

import graphs.Vertex;

/**
 * An exception occurred when this graph does not have the specified vertex.
 */
public class NoSuchVertexException extends NoSuchElementException {
    /**
     * Constructs a new no such vertex exception.
     *
     * @param vertex a missing vertex
     */
    public NoSuchVertexException(Vertex vertex) {
        super(String.format(
            "No such vertex %s",
            vertex
        ));
    }
}
