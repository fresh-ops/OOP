package graphs;

public interface Graph {
    /**
     * Adds a vertex to this graph.
     *
     * @param vertex a vertex to add
     * @throws graphs.exceptions.DuplicateVertexException
     * when the given vertex is already in this graph
     */
    void add(Vertex vertex);

    /**
     * Removes a vertex from this graph.
     *
     * @param vertex a vertex to remove
     * @throws graphs.exceptions.NoSuchVertexException
     * when this graph does not have the given vertex
     */
    void delete(Vertex vertex);

    /**
     * Adds an edge to this graph.
     * If this graph does not have vertices of this edge,
     * they will be added as a side effect.
     *
     * @param edge an edge to add
     * @throws graphs.exceptions.DuplicateEdgeException
     * when the given edge is already in this graph
     */
    void add(Edge edge);

    /**
     * Removes an edge from this graph.
     *
     * @param edge an edge to remove
     * @throws graphs.exceptions.NoSuchEdgeException
     * when this graph does not have the given edge
     */
    void delete(Edge edge);

    /**
     * Sorts vertices in this graph.
     *
     * @return a sorted array of vertices
     * @throws graphs.exceptions.CycleException
     * when this graph has cycles
     */
    Vertex[] topologicalSort();
}
