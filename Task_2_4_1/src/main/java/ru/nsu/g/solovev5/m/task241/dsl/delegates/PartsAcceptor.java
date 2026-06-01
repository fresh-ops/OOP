package ru.nsu.g.solovev5.m.task241.dsl.delegates;

/**
 * An object that accepts parts of something.
 */
public interface PartsAcceptor {
    /**
     * Accepts the part.
     *
     * @param part the providing part
     * @param <T>  the part type
     */
    <T> void accept(T part);
}
