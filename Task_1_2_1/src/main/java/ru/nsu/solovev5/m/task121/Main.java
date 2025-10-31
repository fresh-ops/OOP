package ru.nsu.solovev5.m.task121;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            try (var stream = new FileInputStream(filename)) {
                GraphFileReader.readEdge(stream, graph);
                break;
            } catch (FileNotFoundException e) {
                System.out.print("File not found. Try another filename >>> ");
                filename = scanner.nextLine().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(graph);
        scanner.close();
    }
}
