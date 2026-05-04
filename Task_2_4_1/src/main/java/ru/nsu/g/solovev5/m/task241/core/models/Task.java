package ru.nsu.g.solovev5.m.task241.core.models;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A task model.
 *
 * @param name         the task name
 * @param id           the task identifier
 * @param softDeadline the deadline by which the task must be submitted for review
 * @param hardDeadline the deadline by which the task must be accepted
 */
public record Task(String name, String id, LocalDate softDeadline, LocalDate hardDeadline) {
    /**
     * Creates a new task.
     *
     * @param name         the task name
     * @param id           the task identifier
     * @param softDeadline the deadline by which the task must be submitted for review
     * @param hardDeadline the deadline by which the task must be accepted
     */
    public Task {
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(id, "id is null");
        Objects.requireNonNull(softDeadline, "softDeadline is null");
        Objects.requireNonNull(hardDeadline, "hardDeadline is null");
        assert hardDeadline.isAfter(softDeadline);
    }

    /**
     * Returns the task path.
     *
     * @return the task path
     */
    public Path path() {
        return Path.of(id.replace(" ", "_"));
    }
}
