package ru.nsu.solovev5.m.task121.graphs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.solovev5.m.task121.AdjacencyList;
import ru.nsu.solovev5.m.task121.AdjacencyMatrix;
import ru.nsu.solovev5.m.task121.IncidenceMatrix;
import ru.nsu.solovev5.m.task121.graphs.exceptions.MismatchFormatException;

class GraphFileReaderTest {
    @ParameterizedTest
    @MethodSource
    void checkReadEdgeValid(String message, List<Edge> edges, Graph destination) {
        var streamData = edges.stream().map(Edge::toString).toList();
        var stream = new ByteArrayInputStream(
            String.join(";", streamData).getBytes(StandardCharsets.UTF_8)
        );

        assertDoesNotThrow(
            () -> GraphFileReader.readEdge(stream, destination),
            message + ". An input in correct format should not cause any"
                + " exception"
        );

        for (var edge : edges) {
            assertTrue(
                destination.has(edge),
                message + ". All edges should be in the graph"
            );
        }
    }

    static Stream<Arguments> checkReadEdgeValid() {
        var a = new Vertex("a");
        var b = new Vertex("b");
        var c = new Vertex("c");
        var d = new Vertex("d");
        var numbered = new Vertex("a123");
        var longVertexName = new Vertex("longVertexName");

        var ab = new Edge(a, b);
        var aa = new Edge(a, a);
        var bb = new Edge(b, b);
        var bd = new Edge(b, d);
        var ca = new Edge(c, a);
        var cc = new Edge(c, c);
        var cd = new Edge(c, d);
        var blongVertexName = new Edge(b, longVertexName);
        var anumbered = new Edge(a, numbered);

        return Stream.of(
            Arguments.of(
                "Regular graph", List.of(ab, bd, ca, cd),
                new AdjacencyMatrix()
            ),
            Arguments.of(
                "Long name", List.of(blongVertexName, ab, bd),
                new IncidenceMatrix()
            ),
            Arguments.of(
                "Name with numbers", List.of(anumbered, cd, ca),
                new AdjacencyMatrix()
            ),
            Arguments.of(
                "Loop", List.of(aa, cc, bb),
                new AdjacencyList()
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkReadEdgeInvalid(String message, String input, Graph destination) {
        var stream = new ByteArrayInputStream(
            input.getBytes(StandardCharsets.UTF_8)
        );

        assertThrows(
            MismatchFormatException.class,
            () -> GraphFileReader.readEdge(stream, destination),
            message + ". An input in wrong format should cause an exception"
        );
    }

    static Stream<Arguments> checkReadEdgeInvalid() {
        return Stream.of(
            Arguments.of(
                "Empty braces", "(a, b);()",
                new AdjacencyList()
            ),
            Arguments.of(
                "Wrong separator", "(a, b),(b,c)",
                new IncidenceMatrix()
            ),
            Arguments.of(
                "Unclosed edge", "(a,b",
                new AdjacencyMatrix()
            ),
            Arguments.of(
                "Missed name", "(a, )",
                new AdjacencyMatrix()
            )
        );
    }
}