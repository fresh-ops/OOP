package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import java.nio.file.Files;
import picocli.CommandLine;

@CommandLine.Command(
    name = "clean",
    description = "cleans the working directory"
)
public class CleanCommand extends Command {
    @Override
    public Integer call() throws Exception {
        try (var paths = Files.walk(resolver.workingDirectory())) {
            paths.forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    System.err.println("Failed to delete " + p);
                }
            });
        }
        return 0;
    }
}
