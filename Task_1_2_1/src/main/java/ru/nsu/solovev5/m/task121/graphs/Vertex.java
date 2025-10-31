package ru.nsu.solovev5.m.task121.graphs;

/**
 * Represents a graph vertex.
 *
 * @param name a vertex name
 */
public record Vertex(String name) {
    @Override
    public String toString() {
        return name;
    }
}
