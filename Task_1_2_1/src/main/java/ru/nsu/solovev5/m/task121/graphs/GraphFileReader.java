package ru.nsu.solovev5.m.task121.graphs;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import ru.nsu.solovev5.m.task121.graphs.exceptions.MismatchFormatException;

/**
 * Provides utils for reading a graph from a file.
 */
public class GraphFileReader {
    private static final Pattern EDGE_PATTERN =
        Pattern.compile("^\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\s*\\)$");

    /**
     * Reads a graph from stream in format of edges list.
     * Accepts a stream in format: (a, b);(c, d);(a, d)
     *
     * @param stream      an input stream with an edges list
     * @param destination a graph to put edges
     * @throws MismatchFormatException when an input has a wrong format
     */
    public static void readEdge(InputStream stream, Graph destination) {
        try (var scanner = new Scanner(stream)) {

            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                for (var pair : line.split("\\s*;\\s*")) {
                    var matcher = EDGE_PATTERN.matcher(pair);
                    if (matcher.find()) {
                        var from = new Vertex(matcher.group(1));
                        var to = new Vertex(matcher.group(2));
                        var edge = new Edge(from, to);

                        destination.add(edge);
                    } else {
                        throw new MismatchFormatException(
                            pair, EDGE_PATTERN.toString()
                        );
                    }
                }
            }
        }
    }
}
