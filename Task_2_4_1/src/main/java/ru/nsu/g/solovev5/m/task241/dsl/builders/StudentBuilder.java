package ru.nsu.g.solovev5.m.task241.dsl.builders;

import java.net.URI;
import ru.nsu.g.solovev5.m.task241.core.models.Student;

/**
 * A chain student builder.
 */
public class StudentBuilder {
    private String name;
    private String nickname;
    private URI repository;

    /**
     * Sets the name for a new student.
     *
     * @param name the new student's name
     * @return this builder
     */
    public StudentBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the nickname for a new student.
     *
     * @param nickname the new student's nickname
     * @return this builder
     */
    public StudentBuilder aka(String nickname) {
        this.nickname = nickname;
        return this;
    }

    /**
     * Sets the repository url for a new student.
     *
     * @param repository the new student's repository
     * @return this builder
     */
    public StudentBuilder submitsAt(String repository) {
        this.repository = URI.create(repository);
        return this;
    }

    /**
     * Builds a new student.
     *
     * @return a new student
     */
    Student build() {
        return new Student(name, nickname, repository);
    }
}
