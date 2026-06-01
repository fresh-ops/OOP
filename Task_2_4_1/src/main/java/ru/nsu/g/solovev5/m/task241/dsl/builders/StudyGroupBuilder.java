package ru.nsu.g.solovev5.m.task241.dsl.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;

/**
 * A study group builder.
 */
public class StudyGroupBuilder {
    private String id;
    private final List<StudentBuilder> builders;

    /**
     * Creates a new study group builder.
     */
    public StudyGroupBuilder() {
        this.builders = new ArrayList<>();
    }

    /**
     * Sets the id for a new group.
     *
     * @param id the new group id
     * @return this study group builder
     */
    public StudyGroupBuilder id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Registers a new student in this group builder.
     *
     * @return a student builder to customize a new student
     */
    public StudentBuilder student() {
        var builder = new StudentBuilder();
        builders.add(builder);
        return builder;
    }

    /**
     * Builds a new study group.
     *
     * @return a new study group
     */
    public StudyGroup build() {
        var students = builders.stream()
            .map(StudentBuilder::build)
            .collect(Collectors.toUnmodifiableSet());

        return new StudyGroup(id, students);
    }
}
