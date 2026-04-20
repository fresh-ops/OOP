package ru.nsu.g.solovev5.m.task241.dsl.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;

/**
 * A study group builder.
 */
public class StudyGroupBuilder {
    private final String id;
    private final List<StudentBuilder> builders;

    /**
     * Creates a new study group builder.
     *
     * @param id the id of a new study group
     */
    public StudyGroupBuilder(String id) {
        this.id = id;
        this.builders = new ArrayList<>();
    }

    /**
     * Registers a new student in this group builder.
     *
     * @param name the name of a new student
     * @return a student builder to customize a new student
     */
    public StudentBuilder student(String name) {
        var builder = new StudentBuilder().name(name);
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
