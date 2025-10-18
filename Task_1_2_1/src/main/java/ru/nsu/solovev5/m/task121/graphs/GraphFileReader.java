package ru.nsu.solovev5.m.task121.graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Provides utils for reading a graph from a file.
 */
public class GraphFileReader {
    private static final Pattern EDGE_PATTERN =
        Pattern.compile("^\\((\\w+)\\s*,(\\w+)\\s*\\)$");

    /**
     * Reads a graph from file in format of edges list.
     *
     * @param filename    a name of file with edges
     * @param destination a graph to put edges
     * @throws FileNotFoundException when a file with passed name was not found
     */
    public static void readEdge(String filename, Graph destination)
        throws FileNotFoundException {
        var scanner = new Scanner(new File(filename));

        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            for (var pair : line.split("\\s*;\\s*")) {
                var matcher = EDGE_PATTERN.matcher(pair);
                if (matcher.find()) {
                    var from = new Vertex(matcher.group(1));
                    var to = new Vertex(matcher.group(2));
                    var edge = new Edge(from, to);

                    destination.add(edge);
                }
            }
        }

        scanner.close();
    }
}
