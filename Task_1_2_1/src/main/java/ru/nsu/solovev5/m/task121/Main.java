package ru.nsu.solovev5.m.task121;

import java.io.FileNotFoundException;
import java.util.Scanner;
import ru.nsu.solovev5.m.task121.graphs.GraphFileReader;

/**
 * Main class.
 */
public class Main {
    /**
     * Program entry point.
     *
     * @param args program args
     */
    public static void main(String[] args) {
        var graph = new AdjacencyMatrix();
        var scanner = new Scanner(System.in);
        System.out.print("Enter filename >>> ");
        var filename = scanner.nextLine().trim();
        while (true) {
            try {
                GraphFileReader.readEdge(filename, graph);
                break;
            } catch (FileNotFoundException e) {
                System.out.print("File not found. Try another filename >>> ");
                filename = scanner.nextLine().trim();
            }
        }
        System.out.println(graph);
        scanner.close();
    }
}
