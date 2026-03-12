package ru.nsu.g.solovev5.m.task221;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * A program command-line arguments storage.
 */
public class CommandLineArguments {
    /**
     * Parses string args into a new object.
     *
     * @param args a string args
     * @return a new CommandLineArguments
     */
    public static CommandLineArguments parse(String[] args) {
        var arguments = new CommandLineArguments();
        JCommander.newBuilder()
            .addObject(arguments)
            .build()
            .parse(args);

        return arguments;
    }

    private CommandLineArguments() {}

    @Parameter(names = {"--config", "-c"}, description = "Path to configuration file")
    private String configPath;

    public String getConfigPath() {
        return configPath;
    }

    @Override
    public String toString() {
        return "CommandLineArguments{"
            + "configPath='" + configPath + '\''
            + '}';
    }
}
