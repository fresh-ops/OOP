package ru.nsu.solovev5.m.task121.graphs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.solovev5.m.task121.AdjacencyList;
import ru.nsu.solovev5.m.task121.AdjacencyMatrix;
import ru.nsu.solovev5.m.task121.IncidenceMatrix;

class GraphTest {
    @ParameterizedTest
    @MethodSource
    void checkEquals(String message, Graph a, Graph b, Vertex[] vertices, Edge[] edges) {
        for (var vertex : vertices) {
            a.add(vertex);
            b.add(vertex);
        }

        for (var edge : edges) {
            a.add(edge);
            b.add(edge);
        }

        assertTrue(
            a.equalsTo(a),
            message + ". Graph A should be equal to itself"
        );
        assertTrue(
            b.equalsTo(b),
            message + ". Graph B should be equal to itself"
        );
        assertTrue(
            a.equalsTo(b),
            message + ". Graph A should be equal to graph B"
        );
        assertTrue(
            b.equalsTo(a),
            message + ". Graph B should be equal to graph A"
        );
    }

    static Stream<Arguments> checkEquals() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var cb = new Edge(c, b);

        return Stream.of(
            Arguments.of(
                "AdjacencyMatrix and IncidenceMatrix",
                new AdjacencyMatrix(),
                new IncidenceMatrix(),
                new Vertex[]{a, c},
                new Edge[]{ab, ba, cb}
            ),
            Arguments.of(
                "AdjacencyList and IncidenceMatrix",
                new AdjacencyList(),
                new IncidenceMatrix(),
                new Vertex[]{a, c},
                new Edge[]{ab, ba, cb}
            ),
            Arguments.of(
                "AdjacencyMatrix and AdjacencyList",
                new AdjacencyMatrix(),
                new AdjacencyList(),
                new Vertex[]{a, c},
                new Edge[]{ab, ba, cb}
            )
        );
    }


    @ParameterizedTest
    @MethodSource
    void checkDifferent(String message, Graph a, Graph b, Edge[] edgesForA, Edge[] edgesForB) {
        for (var edge : edgesForA) {
            a.add(edge);
        }

        for (var edge : edgesForB) {
            b.add(edge);
        }

        assertFalse(
            a.equalsTo(b),
            message + ". Graph A should NOT be equal to graph B"
        );
        assertFalse(
            b.equalsTo(a),
            message + ". Graph B should NOT be equal to graph A"
        );
    }

    static Stream<Arguments> checkDifferent() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var d = new Vertex("d");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var ac = new Edge(a, c);
        var bc = new Edge(b, c);
        var cd = new Edge(c, d);

        return Stream.of(
            Arguments.of(
                "AdjacencyMatrix and IncidenceMatrix - different edges",
                new AdjacencyMatrix(),
                new IncidenceMatrix(),
                new Edge[]{ab, ac},
                new Edge[]{ab, bc}
            ),
            Arguments.of(
                "AdjacencyList and IncidenceMatrix - different structure",
                new AdjacencyList(),
                new IncidenceMatrix(),
                new Edge[]{ab, ba, ac},
                new Edge[]{ab, ac, cd}
            ),
            Arguments.of(
                "AdjacencyMatrix and AdjacencyList - missing vertex",
                new AdjacencyMatrix(),
                new AdjacencyList(),
                new Edge[]{ab, ac, cd},
                new Edge[]{ab, ac}
            ),
            Arguments.of(
                "Same graph types but different edges - AdjacencyList",
                new AdjacencyList(),
                new AdjacencyList(),
                new Edge[]{ab, bc},
                new Edge[]{ab, ac}
            ),
            Arguments.of(
                "Same graph types but different edges - AdjacencyMatrix",
                new AdjacencyMatrix(),
                new AdjacencyMatrix(),
                new Edge[]{ab, ba},
                new Edge[]{ab}
            )
        );
    }
}