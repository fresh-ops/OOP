package ru.nsu.g.solovev5.m.task241.core.services;

import java.nio.file.Path;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

/**
 * A working path resolver.
 */
public class PathResolver {
    private final Path workingPath;

    /**
     * Creates a new path resolver.
     *
     * @param workingPath a path to the root of working directory
     */
    public PathResolver(Path workingPath) {
        this.workingPath = workingPath;
    }

    /**
     * Resolves the path to the student repository.
     *
     * @param student the repository owner
     * @return a path to the repository
     */
    public Path resolveRepository(Student student) {
        return workingPath.resolve(student.repositoryPath());
    }

    /**
     * Resolves the path to the student personal directory.
     *
     * @param student the directory owner
     * @return a path to the personal directory
     */
    public Path resolvePersonalPath(Student student) {
        return workingPath.resolve(student.personalPath());
    }

    /**
     * Resolves the path to the task solution.
     *
     * @param student the repository owner
     * @param task    the desired task
     * @return a path to the solution directory
     */
    public Path resolveTask(Student student, Task task) {
        return resolveRepository(student).resolve(task.path());
    }
}
