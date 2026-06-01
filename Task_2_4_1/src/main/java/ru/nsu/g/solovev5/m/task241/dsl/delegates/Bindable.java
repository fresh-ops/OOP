package ru.nsu.g.solovev5.m.task241.dsl.delegates;

import groovy.lang.Binding;

/**
 * An object that can be bind.
 */
public interface Bindable {
    /**
     * Adds this object to the given binding.
     *
     * @param binding a binding to adjust
     */
    void bind(Binding binding);
}
