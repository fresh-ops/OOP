package ru.nsu.g.solovev5.m.task212;

/**
 * A main class that controls services communication and lifecycle.
 */
public class NodeRunner implements Runnable {
    public static void main(String[] args) {
        var runner = new NodeRunner();
        runner.run();
    }

    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}
