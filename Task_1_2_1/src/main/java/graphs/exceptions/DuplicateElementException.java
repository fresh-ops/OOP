package graphs.exceptions;

/**
 * An exception occurred when trying to add an entity that is already in graph.
 */
abstract public class DuplicateElementException extends GraphException {
    /**
     * Constructs a new duplicate element exception.
     *
     * @param message a message about duplicate element
     */
     public DuplicateElementException(String message) {
         super(String.format(
             "%s. Having duplicate elements in a graph is forbidden",
             message
         ));
     }
}
