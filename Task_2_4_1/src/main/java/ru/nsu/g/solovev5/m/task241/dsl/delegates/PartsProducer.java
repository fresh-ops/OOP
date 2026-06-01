package ru.nsu.g.solovev5.m.task241.dsl.delegates;

/**
 * An object that produces parts of something.
 */
public interface PartsProducer {
    /**
     * Sets the parts acceptor.
     *
     * @param acceptor the parts acceptor
     */
    void acceptor(PartsAcceptor acceptor);
}
