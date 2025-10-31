package ru.nsu.solovev5.m.task121.graphs;

/**
 * Represents a graph edge.
 *
 * @param from a starting vertex
 * @param to   an ending vertex
 */
public record Edge(Vertex from, Vertex to) {
    /**
     * Checks whether this edge contains the given vertex.
     *
     * @param vertex a vertex to check
     * @return {@code true} if the given vertex is a part of this edge, {@code false} otherwise
     */
    public boolean has(Vertex vertex) {
        return vertex.equals(from) || vertex.equals(to);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", from, to);
    }
}
