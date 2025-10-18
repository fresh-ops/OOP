package ru.nsu.solovev5.m.task121;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.solovev5.m.task121.graphs.Edge;
import ru.nsu.solovev5.m.task121.graphs.Vertex;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateVertexException;

class IncidenceMatrixTest {
    IncidenceMatrix matrix;

    @BeforeEach
    void setUp() {
        matrix = new IncidenceMatrix();
    }

    @ParameterizedTest
    @MethodSource
    void checkNormalVertexAdding(String message, Vertex[] vertices) {
        for (var vertex : vertices) {
            assertFalse(
                matrix.has(vertex),
                message + ". A vertex shouldn't appear before adding"
            );
            matrix.add(vertex);
            assertTrue(
                matrix.has(vertex),
                message + ". A vertex should appear after adding"
            );
        }

        for (var vertex : vertices) {
            assertTrue(
                matrix.has(vertex),
                message + ". Every vertex should stay in graph"
            );
        }
    }

    static Stream<Arguments> checkNormalVertexAdding() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var capitalA = new Vertex("A");
        var longName = new Vertex("VertexWith_longName");

        return Stream.of(
            Arguments.of("A single vertex", new Vertex[]{a}),
            Arguments.of("Multiple vertex", new Vertex[]{a, b, c}),
            Arguments.of("Different cases", new Vertex[]{a, capitalA}),
            Arguments.of("Long name", new Vertex[]{longName})
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkDuplicateVertexAdding(String message,
                                    List<Integer> duplicatePositions,
                                    Vertex[] vertices) {
        for (var i = 0; i < vertices.length; i++) {
            var vertex = vertices[i];
            if (duplicatePositions.contains(i)) {
                assertTrue(
                    matrix.has(vertices[i]),
                    message + ". A graph should notify if it has the vertex"
                );
                assertThrows(
                    DuplicateVertexException.class,
                    () -> matrix.add(vertex),
                    message + ". A graph should throw an exception on adding"
                        + " a duplicate vertex"
                );
            } else {
                assertFalse(
                    matrix.has(vertex),
                    message + ". A vertex shouldn't appear before adding"
                );
                assertDoesNotThrow(
                    () -> matrix.add(vertex),
                    message + ". A graph shouldn't throw an exception on"
                        + " adding non-duplicate vertex"
                );
                assertTrue(
                    matrix.has(vertex),
                    message + ". A vertex should appear after adding"
                );
            }
        }
    }

    static Stream<Arguments> checkDuplicateVertexAdding() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        return Stream.of(
            Arguments.of(
                "Instant duplicate",
                List.of(1),
                new Vertex[]{a, a}
            ),
            Arguments.of(
                "Long distance between duplicates",
                List.of(3),
                new Vertex[]{a, b, c, a}
            ),
            Arguments.of(
                "Several duplicates",
                Arrays.asList(2, 3),
                new Vertex[]{a, b, a, a}
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkNormalEdgeAdding(String message, Edge[] edges) {
        for (var edge : edges) {
            assertFalse(
                matrix.has(edge),
                message + ". A edge shouldn't appear before adding"
            );
            matrix.add(edge);
            assertTrue(
                matrix.has(edge),
                message + ". A edge should appear after adding"
            );
            assertTrue(
                matrix.has(edge.from()),
                message + ". A graph should contain starting a vertex of"
                    + " inner edge"
            );
            assertTrue(
                matrix.has(edge.to()),
                message + ". A graph should contain ending a vertex of"
                    + " inner edge"
            );
        }

        for (var edge : edges) {
            assertTrue(
                matrix.has(edge),
                message + ". Every edge should stay in graph"
            );
        }
    }

    static Stream<Arguments> checkNormalEdgeAdding() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var ac = new Edge(a, c);
        var cb = new Edge(c, b);

        return Stream.of(
            Arguments.of("A single vertex", new Edge[]{ab}),
            Arguments.of("Reverse edge", new Edge[]{ab, ba}),
            Arguments.of("Multiple edges", new Edge[]{ab, ba, ac, cb})
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkDuplicateEdgeAdding(String message,
                                  List<Integer> duplicatePositions,
                                  Edge[] edges) {
        for (var i = 0; i < edges.length; i++) {
            var edge = edges[i];
            if (duplicatePositions.contains(i)) {
                assertTrue(
                    matrix.has(edges[i]),
                    message + ". A graph should notify if it has the edge"
                );
                assertThrows(
                    DuplicateEdgeException.class,
                    () -> matrix.add(edge),
                    message + ". A graph should throw an exception on adding"
                        + " a duplicate edge"
                );
            } else {
                assertFalse(
                    matrix.has(edge),
                    message + ". A edge shouldn't appear before adding"
                );
                assertDoesNotThrow(
                    () -> matrix.add(edge),
                    message + ". A graph shouldn't throw an exception on"
                        + " adding non-duplicate edge"
                );
                assertTrue(
                    matrix.has(edge),
                    message + ". A edge should appear after adding"
                );
                assertTrue(
                    matrix.has(edge.from()),
                    message + ". A graph should contain starting a vertex of"
                        + " inner edge"
                );
                assertTrue(
                    matrix.has(edge.to()),
                    message + ". A graph should contain ending a vertex of"
                        + " inner edge"
                );
            }
        }
    }

    static Stream<Arguments> checkDuplicateEdgeAdding() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var ac = new Edge(a, c);
        var cb = new Edge(c, b);

        return Stream.of(
            Arguments.of(
                "Instant duplicate",
                List.of(1),
                new Edge[]{ab, ab}
            ),
            Arguments.of(
                "Long distance between duplicates",
                List.of(3),
                new Edge[]{ab, ba, cb, ab}
            ),
            Arguments.of(
                "Several duplicates",
                Arrays.asList(2, 3),
                new Edge[]{ab, ba, ab, ab}
            )
        );
    }
}