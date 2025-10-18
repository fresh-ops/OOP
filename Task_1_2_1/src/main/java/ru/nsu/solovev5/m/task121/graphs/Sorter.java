package ru.nsu.solovev5.m.task121.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import ru.nsu.solovev5.m.task121.graphs.exceptions.CycleException;

/**
 * Util class for sorting graphs.
 */
public class Sorter {
    /**
     * Sorts vertices of the given graph.
     *
     * @param graph a graph to sort
     * @return a sorted array of vertices
     * @throws CycleException when this graph has cycles
     */
    public static Vertex[] topologicalSort(Graph graph) {
        var adjList = setUpAdjacencyList(graph);
        var l = new ArrayList<Vertex>();
        var s = new ArrayList<Vertex>();

        for (Vertex vertex : adjList.keySet()) {
            if (adjList.get(vertex).isEmpty()) {
                s.add(vertex);
            }
        }

        while (!s.isEmpty()) {
            var removed = s.remove(0);
            l.add(removed);

            for (var entry : adjList.entrySet()) {
                var neighbours = entry.getValue();
                if (neighbours.remove(removed) && neighbours.isEmpty()) {
                    s.add(entry.getKey());
                }
            }
        }

        for (var entry : adjList.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                throw new CycleException();
            }
        }

        l.addAll(s);

        return l.toArray(new Vertex[0]);
    }

    private static HashMap<Vertex, Set<Vertex>> setUpAdjacencyList(
        Graph graph) {
        var vertices = graph.getVertices();
        var list = new HashMap<Vertex, Set<Vertex>>();

        for (Vertex vertex : vertices) {
            var neighbours = graph.getNeighbours(vertex);
            list.putIfAbsent(vertex, new HashSet<>());
            for (Vertex neighbour : neighbours) {
                list.putIfAbsent(neighbour, new HashSet<>());
                list.get(neighbour).add(vertex);
            }
        }

        return list;
    }
}
