package ru.nsu.solovev5.m.task121;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchVertexException;

class AdjacencyListTest {
    AdjacencyList list;

    @BeforeEach
    void setUp() {
        list = new AdjacencyList();
    }

    @ParameterizedTest
    @MethodSource
    void checkNormalVertexAdding(String message, Vertex[] vertices) {
        for (var vertex : vertices) {
            assertFalse(
                list.has(vertex),
                message + ". A vertex shouldn't appear before adding"
            );
            list.add(vertex);
            assertTrue(
                list.has(vertex),
                message + ". A vertex should appear after adding"
            );
        }

        for (var vertex : vertices) {
            assertTrue(
                list.has(vertex),
                message + ". Every vertex should stay in graph"
            );
        }

        assertArrayEquals(
            vertices, list.getVertices(),
            message + ". Inner vertices array should be the same as in"
                + " the test data"
        );
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
                    list.has(vertices[i]),
                    message + ". A graph should notify if it has the vertex"
                );
                assertThrows(
                    DuplicateVertexException.class,
                    () -> list.add(vertex),
                    message + ". A graph should throw an exception on adding"
                        + " a duplicate vertex"
                );
            } else {
                assertFalse(
                    list.has(vertex),
                    message + ". A vertex shouldn't appear before adding"
                );
                assertDoesNotThrow(
                    () -> list.add(vertex),
                    message + ". A graph shouldn't throw an exception on adding"
                        + " non-duplicate vertex"
                );
                assertTrue(
                    list.has(vertex),
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
                list.has(edge),
                message + ". A edge shouldn't appear before adding"
            );
            list.add(edge);
            assertTrue(
                list.has(edge),
                message + ". A edge should appear after adding"
            );
            assertTrue(
                list.has(edge.from()),
                message + ". A graph should contain starting a vertex of"
                    + " inner edge"
            );
            assertTrue(
                list.has(edge.to()),
                message + ". A graph should contain ending a vertex of"
                    + " inner edge"
            );
        }

        for (var edge : edges) {
            assertTrue(
                list.has(edge),
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
                    list.has(edges[i]),
                    message + ". A graph should notify if it has the edge"
                );
                assertThrows(
                    DuplicateEdgeException.class,
                    () -> list.add(edge),
                    message + ". A graph should throw an exception on adding"
                        + " a duplicate edge"
                );
            } else {
                assertFalse(
                    list.has(edge),
                    message + ". A edge shouldn't appear before adding"
                );
                assertDoesNotThrow(
                    () -> list.add(edge),
                    message + ". A graph shouldn't throw an exception on adding"
                        + " non-duplicate edge"
                );
                assertTrue(
                    list.has(edge),
                    message
                        + ". A edge should appear after adding"
                );
                assertTrue(
                    list.has(edge.from()),
                    message + ". A graph should contain starting a vertex of"
                        + " inner edge"
                );
                assertTrue(
                    list.has(edge.to()),
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

    @ParameterizedTest
    @MethodSource
    void checkDeletingValidVertex(String message, Vertex[] vertices,
                                  Vertex removable) {
        for (var vertex : vertices) {
            list.add(vertex);
        }

        assertDoesNotThrow(
            () -> list.delete(removable),
            message + ". Removing a valid vertex shouldn't cause any exception"
        );
        assertFalse(
            list.has(removable),
            message + ". After removing a vertex shouldn't appear in the graph"
        );
    }

    static Stream<Arguments> checkDeletingValidVertex() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        return Stream.of(
            Arguments.of("A single vertex", new Vertex[]{a}, a),
            Arguments.of("The last vertex", new Vertex[]{a, b, c}, c),
            Arguments.of("The first vertex", new Vertex[]{a, b, c}, a),
            Arguments.of("A vertex in the middle", new Vertex[]{a, b, c}, b)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkDeletingInvalidVertex(String message, Vertex[] vertices,
                                    Vertex removable) {
        for (var vertex : vertices) {
            list.add(vertex);
        }

        var before = list.getVertices();
        assertThrows(
            NoSuchVertexException.class,
            () -> list.delete(removable),
            message + ". Removing an invalid vertex should cause an exception"
        );
        assertArrayEquals(
            before, list.getVertices(),
            message + ". Failed removing shouldn't change vertices set"
        );
    }

    static Stream<Arguments> checkDeletingInvalidVertex() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var d = new Vertex("d");

        return Stream.of(
            Arguments.of("An empty graph", new Vertex[]{}, a),
            Arguments.of("A single vertex", new Vertex[]{a}, c),
            Arguments.of("Multiple vertices", new Vertex[]{a, b, c}, d)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkDeletingValidEdge(String message, Edge[] edges, Edge removable) {
        for (var edge : edges) {
            list.add(edge);
        }

        assertDoesNotThrow(
            () -> list.delete(removable),
            message + ". Removing a valid edge shouldn't cause any exception"
        );
        assertFalse(
            list.has(removable),
            message + ". After removing a edge shouldn't appear in the graph"
        );
    }

    static Stream<Arguments> checkDeletingValidEdge() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var ac = new Edge(a, c);
        var cb = new Edge(c, b);

        return Stream.of(
            Arguments.of("A single edge", new Edge[]{ab}, ab),
            Arguments.of("The first edge", new Edge[]{ab, ba, ac}, ab),
            Arguments.of("The last edge", new Edge[]{ab, ba, ac, cb}, cb),
            Arguments.of("An edge in the middle", new Edge[]{ab, ba, ac, cb}, ba)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkDeletingInvalidEdge(String message, Edge[] edges,
                                  Edge removable) {
        for (var edge : edges) {
            list.add(edge);
        }

        if (list.has(removable.from()) && list.has(removable.to())) {
            assertThrows(
                NoSuchEdgeException.class,
                () -> list.delete(removable),
                message + ". Removing an invalid edge should cause an exception"
            );
        } else {
            assertThrows(
                NoSuchVertexException.class,
                () -> list.delete(removable),
                message + ". Removing an edge in graph without any any vertex"
                    + " of the edge should cause an exception"
            );
        }
    }

    static Stream<Arguments> checkDeletingInvalidEdge() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");

        var ab = new Edge(a, b);
        var ba = new Edge(b, a);
        var ac = new Edge(a, c);
        var cb = new Edge(c, b);

        return Stream.of(
            Arguments.of("An empty graph", new Edge[]{}, ab),
            Arguments.of("A single edge", new Edge[]{ab}, ac),
            Arguments.of("Multiple edges", new Edge[]{ab, ac, cb}, ba)
        );
    }
}