package ru.nsu.g.solovev5.m.task241.dsl.builders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

/**
 * A task builder.
 */
public class TaskBuilder {
    private String name;
    private String id;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Sets the name for a new task.
     *
     * @param name the new task name
     * @return this builder
     */
    public TaskBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the id for a new task.
     *
     * @param id the new task id
     * @return this builder
     */
    public TaskBuilder id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the soft deadline for a new task.
     *
     * @param softDeadline the new task soft deadline
     * @return this builder
     */
    public TaskBuilder softDeadline(String softDeadline) {
        this.softDeadline = LocalDate.parse(softDeadline, formatter);
        return this;
    }

    /**
     * Sets the hard deadline for a new task.
     *
     * @param hardDeadline the new task hard deadline
     * @return this builder
     */
    public TaskBuilder hardDeadline(String hardDeadline) {
        this.hardDeadline = LocalDate.parse(hardDeadline, formatter);
        return this;
    }

    /**
     * Builds a new task.
     *
     * @return a new task
     */
    public Task build() {
        return new Task(name, id, softDeadline, hardDeadline);
    }
}
