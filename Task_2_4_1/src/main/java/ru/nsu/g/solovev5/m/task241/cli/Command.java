package ru.nsu.g.solovev5.m.task241.cli;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import ru.nsu.g.solovev5.m.task241.core.models.Config;
import ru.nsu.g.solovev5.m.task241.core.services.PathResolver;
import ru.nsu.g.solovev5.m.task241.dsl.ConfigLoader;

/**
 * A base command class with predefined environment properties.
 */
public abstract class Command implements Callable<Integer> {
    protected final Config config;
    protected final PathResolver resolver;

    /**
     * Creates a new command and initializes environment.
     */
    public Command() {
        this.config = loadConfig();
        this.resolver = new PathResolver(Path.of(".checker"));
    }

    /**
     * Loads configuration file.
     *
     * @return a loaded config
     */
    private static Config loadConfig() {
        var loader = new ConfigLoader();

        return loader.loadFrom(Path.of("checker.groovy"));
    }
}
