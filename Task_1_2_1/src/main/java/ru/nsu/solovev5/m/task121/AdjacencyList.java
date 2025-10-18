package ru.nsu.solovev5.m.task121;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ru.nsu.solovev5.m.task121.graphs.Edge;
import ru.nsu.solovev5.m.task121.graphs.Graph;
import ru.nsu.solovev5.m.task121.graphs.Vertex;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateVertexException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchVertexException;

/**
 * Represents a graph using an adjacency list.
 */
public class AdjacencyList implements Graph {
    private final HashMap<Vertex, Set<Vertex>> list;

    /**
     * Constructs a new adjacency list.
     */
    public AdjacencyList() {
        this.list = new HashMap<>();
    }

    @Override
    public void add(Vertex vertex) {
        if (list.get(vertex) != null) {
            throw new DuplicateVertexException(vertex);
        }

        list.put(vertex, new HashSet<>());
    }
    @Override
    public void add(Edge edge) {
        var from = edge.from();
        var fromAdjacency = list.get(from);
        var to = edge.to();

        if (fromAdjacency != null && fromAdjacency.contains(to)) {
            throw new DuplicateEdgeException(edge);
        } else if (fromAdjacency == null) {
            fromAdjacency = new HashSet<>();
            list.put(from, fromAdjacency);
        }
        fromAdjacency.add(to);

        if (list.get(to) == null) {
            add(to);
        }
    }

    @Override
    public void delete(Vertex vertex) {
        if (list.get(vertex) == null) {
            throw new NoSuchVertexException(vertex);
        }

        list.remove(vertex);
        list.keySet().forEach((key) -> {
            list.get(key).remove(vertex);
        });
    }
    @Override
    public void delete(Edge edge) {
        var from = edge.from();
        var fromAdjacency = list.get(from);

        if (fromAdjacency == null) {
            throw new NoSuchVertexException(from);
        }

        var to = edge.to();
        if (list.get(to) == null) {
            throw new NoSuchVertexException(to);
        }

        if (!fromAdjacency.remove(to)) {
            throw new NoSuchEdgeException(edge);
        }
    }

        @Override
    public Vertex[] getVertices() {
        return list.keySet().toArray(new Vertex[0]);
    }
    @Override
    public Vertex[] getNeighbours(Vertex vertex) {
        if (!list.containsKey(vertex)) {
            throw new NoSuchVertexException(vertex);
        }

        return list.get(vertex).toArray(new Vertex[0]);
    }

    @Override
    public boolean has(Edge edge) {
        var neighbours = list.get(edge.from());
        return neighbours != null && neighbours.contains(edge.to());
    }

@Override
    public boolean has(Vertex vertex) {
        return list.containsKey(vertex);
    }

@Override
    public int hashCode() {
        return Objects.hashCode(list);
    }

@Override
    public boolean equals(Object o) {
        return equalsTo(o);
    }

@Override
    public String toString() {
        var strings = list.entrySet().stream().map(entry -> String.format(
            "%s -> %s;",
            entry.getKey(),
            entry.getValue()
        )).toList();

        return String.join("\n", strings);
    }






}
