package ru.nsu.g.solovev5.m.task241.core.models;

import java.net.URI;
import java.nio.file.Path;
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

    /**
     * Returns the suffix of personal path.
     *
     * @return the suffix of personal path
     */
    public Path personalPath() {
        return Path.of(nickname.replaceAll(" ", "_"));
    }

    /**
     * Returns the suffix of repository path.
     *
     * @return the suffix of repository path
     */
    public Path repositoryPath() {
        var path = repository.getPath();
        var repositoryName = path.substring(path.lastIndexOf('/') + 1);

        return personalPath().resolve(repositoryName);
    }

    /**
     * Returns the URI of clonable git repository.
     *
     * @return the URI of clonable git repository
     */
    public URI git() {
        return URI.create(repository + ".git");
    }
}
