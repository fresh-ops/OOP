package ru.nsu.g.solovev5.m.task241;

import java.io.IOException;
import java.nio.file.Path;
import ru.nsu.g.solovev5.m.task241.dsl.ConfigLoader;

public class Main {
    public static void main(String[] args) throws IOException {
        var loader = new ConfigLoader();

        var config = loader.loadFrom(Path.of("checker.groovy"));
        System.out.println(config);
    }
}
