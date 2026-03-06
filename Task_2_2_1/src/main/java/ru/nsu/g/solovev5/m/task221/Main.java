package ru.nsu.g.solovev5.m.task221;

import com.beust.jcommander.JCommander;
import ru.nsu.g.solovev5.m.task221.pizzeria.Pizzeria;

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

        var pizzeria = new Pizzeria();
        pizzeria.work();
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            System.out.println("External interruption. Stopping...");
        } finally {
            while (!pizzeria.isStopped()) {
                try {
                    pizzeria.stop();
                } catch (InterruptedException e) {
                    System.out.println("External interruption. Stopping...");
                }
            }
        }
    }
}