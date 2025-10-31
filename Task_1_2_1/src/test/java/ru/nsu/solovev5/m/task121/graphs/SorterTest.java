package ru.nsu.solovev5.m.task121.graphs;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.solovev5.m.task121.AdjacencyList;
import ru.nsu.solovev5.m.task121.AdjacencyMatrix;
import ru.nsu.solovev5.m.task121.IncidenceMatrix;
import ru.nsu.solovev5.m.task121.graphs.exceptions.CycleException;

class SorterTest {
    @ParameterizedTest
    @MethodSource
    void checkNormalTopologicalSort(String message, Graph graph,
                                    Vertex[] vertices, Edge[] edges,
                                    Vertex[] order) {
        for (var vertex : vertices) {
            graph.add(vertex);
        }
        for (var edge : edges) {
            graph.add(edge);
        }

        assertDoesNotThrow(
            () -> Sorter.topologicalSort(graph),
            message + ". Non-cyclic graph shouldn't cause any exception"
        );
        assertArrayEquals(
            order, Sorter.topologicalSort(graph),
            message + ". The outputted order differs from expected"
        );
    }

    static Stream<Arguments> checkNormalTopologicalSort() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var d = new Vertex("d");

        var ab = new Edge(a, b);
        var bc = new Edge(b, c);
        var ad = new Edge(a, d);

        return Stream.of(
            Arguments.of(
                "An empty graph", new AdjacencyList(),
                new Vertex[]{}, new Edge[]{},
                new Vertex[]{}
            ),
            Arguments.of(
                "A graph with a single vertex", new AdjacencyList(),
                new Vertex[]{a}, new Edge[]{},
                new Vertex[]{a}
            ),
            Arguments.of(
                "Trivial case", new AdjacencyMatrix(),
                new Vertex[]{a, b, c}, new Edge[]{ab, bc},
                new Vertex[]{a, b, c}
            ),
            Arguments.of(
                "Shuffled vertices", new AdjacencyMatrix(),
                new Vertex[]{c, a, b}, new Edge[]{bc, ab},
                new Vertex[]{a, b, c}
            ),
            Arguments.of(
                "Unconnected graph", new IncidenceMatrix(),
                new Vertex[]{a, b, c, d}, new Edge[]{bc, ad},
                new Vertex[]{a, b, d, c}
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkCyclicTopologicalSort(String message, Graph graph,
                                    Vertex[] vertices, Edge[] edges) {
        for (var vertex : vertices) {
            graph.add(vertex);
        }
        for (var edge : edges) {
            graph.add(edge);
        }

        assertThrows(
            CycleException.class,
            () -> Sorter.topologicalSort(graph),
            message + ". A cyclic graph should cause an exception"
        );
    }

    static Stream<Arguments> checkCyclicTopologicalSort() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var d = new Vertex("d");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var bc = new Edge(b, c);
        var cd = new Edge(c, d);
        var da = new Edge(d, a);
        var aa = new Edge(a, a);

        return Stream.of(
            Arguments.of(
                "Loop", new AdjacencyList(),
                new Vertex[]{a}, new Edge[]{aa}
            ),
            Arguments.of(
                "Simple cycle", new AdjacencyMatrix(),
                new Vertex[]{a, b}, new Edge[]{ab, ba}
            ),
            Arguments.of(
                "Loong cycle", new IncidenceMatrix(),
                new Vertex[]{a, b, c, d}, new Edge[]{ab, bc, cd, da}
            )
        );
    }
}