package graphs.exceptions;

/**
 * An exception occurred when this does not have the specified element.
 */
abstract public class NoSuchElementException extends GraphException {
    /**
     * Constructs a new no such element exception.
     *
     * @param message a detail message
     */
    public NoSuchElementException(String message) {
        super(message);
    }
}
