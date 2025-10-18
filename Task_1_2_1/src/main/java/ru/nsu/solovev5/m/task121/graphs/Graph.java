package ru.nsu.solovev5.m.task121.graphs;

import java.util.Arrays;
import java.util.HashSet;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateVertexException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchVertexException;

/**
 * Base graph interface.
 */
public interface Graph {
    /**
     * Adds a vertex to this graph.
     *
     * @param vertex a vertex to add
     * @throws DuplicateVertexException when the given vertex is already
     *                                  in this graph
     */
    void add(Vertex vertex);

    /**
     * Adds an edge to this graph.
     * If this graph does not have vertices of this edge,
     * they will be added as a side effect.
     *
     * @param edge an edge to add
     * @throws DuplicateEdgeException when the given edge is already in
     *                                this graph
     */
    void add(Edge edge);

    /**
     * Removes a vertex from this graph.
     *
     * @param vertex a vertex to remove
     * @throws NoSuchVertexException when this graph does not have the
     *                               given vertex
     */
    void delete(Vertex vertex);

    /**
     * Removes an edge from this graph.
     *
     * @param edge an edge to remove
     * @throws NoSuchVertexException when this graph does not have any
     *                               vertex of this edge
     * @throws NoSuchEdgeException   when this graph does not have
     *                               the given edge
     */
    void delete(Edge edge);

    /**
     * Checks if this graph equals to an object.
     *
     * @param o an object to compare
     * @return {@code true} if both graphs are equal
     */
    default boolean equalsTo(Object o) {
        if (!(o instanceof Graph graph)) {
            return false;
        }

        var vertices = new HashSet<>(
            Arrays.asList(getVertices())
        );
        var otherVertices = new HashSet<>(
            Arrays.asList(graph.getVertices())
        );
        if (!vertices.equals(otherVertices)) {
            return false;
        }

        for (var vertex : vertices) {
            var neighboursA = new HashSet<>(
                Arrays.asList(getNeighbours(vertex))
            );
            var neighboursB = new HashSet<>(
                Arrays.asList(graph.getNeighbours(vertex))
            );
            if (!neighboursA.equals(neighboursB)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns neighbours of the vertex.
     * The returned array should be independent with the graph state.
     *
     * @param vertex a vertex to search neighbours of
     * @return an array of vertices
     * @throws NoSuchVertexException when this graph does not have
     *                               the given vertex
     */
    Vertex[] getNeighbours(Vertex vertex);

    /**
     * Returns vertices of this graph.
     * The returned array should be independent with the graph state.
     *
     * @return an array of vertices
     */
    Vertex[] getVertices();

    /**
     * Checks whether this graph has the given edge.
     *
     * @param edge an edge to check
     * @return {@code true} if an edge in this graph, {@code false} otherwise
     */
    boolean has(Edge edge);

    /**
     * Checks whether this graph has the given vertex.
     *
     * @param vertex a vertex to check
     * @return {@code true} if a vertex appears in this graph,
     * {@code false} otherwise
     */
    boolean has(Vertex vertex);
}
