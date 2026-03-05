package ru.nsu.g.solovev5.m.task221;

import com.beust.jcommander.JCommander;

/**
 * The program starter.
 */
public class Main {
    /**
     * The program entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        var arguments = new CommandLineArguments();
        JCommander.newBuilder()
            .addObject(arguments)
            .build()
            .parse(args);

        System.out.println(arguments);
    }
}