package ru.nsu.solovev5.m.task121.graphs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EdgeTest {
    @Test
    void checkHasFrom() {
        var edge = new Edge(
            new Vertex("a"),
            new Vertex("b")
        );

        assertTrue(edge.has(new Vertex("a")));
    }

    @Test
    void checkHasTo() {
        var edge = new Edge(
            new Vertex("a"),
            new Vertex("b")
        );

        assertTrue(edge.has(new Vertex("b")));
    }

    @Test
    void checkDoesNotHave() {
        var edge = new Edge(
            new Vertex("a"),
            new Vertex("b")
        );

        assertFalse(edge.has(new Vertex("test")));
    }
}