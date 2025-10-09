package graphs.exceptions;

/**
 * A basic graph operation exception.
 */
abstract public class GraphException extends RuntimeException {
    /**
     * Constructs a new graph exception.
     *
     * @param message the detail message
     */
    public GraphException(String message) {
        super(message);
    }
}
