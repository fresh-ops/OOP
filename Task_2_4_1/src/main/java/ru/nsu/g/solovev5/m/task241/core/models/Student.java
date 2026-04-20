package ru.nsu.g.solovev5.m.task241.core.models;

import java.net.URI;
import java.util.Objects;

/**
 * A student model.
 *
 * @param name       the student's name
 * @param nickname   the student's GitHub nickname
 * @param repository the url to student's repository
 */
public record Student(String name, String nickname, URI repository) {
    /**
     * Creates a new student.
     *
     * @param name       the student's name
     * @param nickname   the student's GitHub nickname
     * @param repository the url to student's repository
     * @throws NullPointerException if either of the arguments is null
     */
    public Student {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(nickname, "Nickname must not be null");
        Objects.requireNonNull(repository, "RepoUrl must not be null");
    }
}
