package ru.nsu.g.solovev5.m.task221;

import com.beust.jcommander.Parameter;

/**
 * A program command-line arguments storage.
 */
public class CommandLineArguments {
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
