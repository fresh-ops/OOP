package ru.nsu.solovev5.m.task121.graphs;

import ru.nsu.solovev5.m.task121.graphs.exceptions.CycleException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateVertexException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchVertexException;

public interface Graph {
    /**
     * Adds a vertex to this graph.
     *
     * @param vertex a vertex to add
     * @throws DuplicateVertexException
     * when the given vertex is already in this graph
     */
    void add(Vertex vertex);

    /**
     * Removes a vertex from this graph.
     *
     * @param vertex a vertex to remove
     * @throws NoSuchVertexException
     * when this graph does not have the given vertex
     */
    void delete(Vertex vertex);

    /**
     * Adds an edge to this graph.
     * If this graph does not have vertices of this edge,
     * they will be added as a side effect.
     *
     * @param edge an edge to add
     * @throws DuplicateEdgeException
     * when the given edge is already in this graph
     */
    void add(Edge edge);

    /**
     * Removes an edge from this graph.
     *
     * @param edge an edge to remove
     * @throws NoSuchVertexException
     * when this graph does not have any vertex of this edge
     * @throws NoSuchEdgeException
     * when this graph does not have the given edge
     */
    void delete(Edge edge);

    /**
     * Sorts vertices in this graph.
     *
     * @return a sorted array of vertices
     * @throws CycleException
     * when this graph has cycles
     */
    Vertex[] topologicalSort();
}
