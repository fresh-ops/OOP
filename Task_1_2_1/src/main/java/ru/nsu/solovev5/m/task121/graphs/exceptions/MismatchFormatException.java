package ru.nsu.solovev5.m.task121.graphs.exceptions;

/**
 * An exception occurred when a GraphFileReader founds a wrong format in input.
 */
public class MismatchFormatException extends RuntimeException {
    /**
     * Constructs a new mismatch format exception.
     *
     * @param mismatched a string with wrong format
     * @param expected   expected format
     */
    public MismatchFormatException(String mismatched, String expected) {
        super(String.format(
            "Mismatched the input format in \"%s\", expected %s",
            mismatched, expected
        ));
    }
}
