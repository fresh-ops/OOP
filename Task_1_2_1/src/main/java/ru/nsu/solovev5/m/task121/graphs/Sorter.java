package ru.nsu.solovev5.m.task121.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import ru.nsu.solovev5.m.task121.graphs.exceptions.CycleException;

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
        var L = new ArrayList<Vertex>();
        var S = new ArrayList<Vertex>();

        for (Vertex vertex : adjList.keySet()) {
            if (adjList.get(vertex).isEmpty()) {
                S.add(vertex);
            }
        }

        while (!S.isEmpty()) {
            var removed = S.remove(0);
            L.add(removed);

            for (var entry : adjList.entrySet()) {
                var neighbours = entry.getValue();
                if (neighbours.remove(removed) && neighbours.isEmpty()) {
                    S.add(entry.getKey());
                }
            }
        }

        for (var entry : adjList.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                throw new CycleException();
            }
        }

        return L.toArray(new Vertex[0]);
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
