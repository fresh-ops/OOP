package ru.nsu.g.solovev5.m.task241.dsl;

import groovy.lang.Binding;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.nsu.g.solovev5.m.task241.core.models.Config;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.models.Task;
import ru.nsu.g.solovev5.m.task241.dsl.builders.ConfigBuilder;
import ru.nsu.g.solovev5.m.task241.dsl.delegates.PartsAcceptor;
import ru.nsu.g.solovev5.m.task241.dsl.delegates.Statement;

/**
 * A config execution context.
 */
public class ExecutionContext implements PartsAcceptor {
    private final Set<Path> executingFiles;
    private final Binding binding;
    private final ConfigBuilder builder;

    /**
     * Creates a new execution context.
     *
     * @param statements the supported DSL statements
     */
    public ExecutionContext(List<Statement> statements) {
        executingFiles = new HashSet<>();
        binding = new Binding();
        for (var statement : statements) {
            statement.bind(binding);
            statement.acceptor(this);
        }
        builder = new ConfigBuilder();
    }

    @Override
    public <T> void accept(T part) {
        if (part.getClass() == StudyGroup.class) {
            builder.studyGroup((StudyGroup) part);
        } else if (part.getClass() == Task.class) {
            builder.task((Task) part);
        } else {
            throw new IllegalStateException("Unsupported type: " + part.getClass());
        }
    }

    /**
     * Returns the binding of this context.
     *
     * @return the binding
     */
    public Binding binding() {
        return binding;
    }

    /**
     * Returns a built config.
     *
     * @return a built config
     */
    public Config config() {
        return builder.build();
    }

    /**
     * Puts the path to the executing files.
     *
     * @param path a path to executable file
     */
    public void putExecuting(Path path) {
        executingFiles.add(path);
    }

    /**
     * Removes the path from the executing files.
     *
     * @param path a path to executable file
     */
    public void popExecuting(Path path) {
        executingFiles.remove(path);
    }

    /**
     * Checks if the path is executing now.
     *
     * @param path a path to executable file
     * @return {@code true} if the file is executing, {@code false} otherwise
     */
    public boolean isExecuting(Path path) {
        return executingFiles.contains(path);
    }
}
