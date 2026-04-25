package ru.nsu.g.solovev5.m.task241.core.services;

import java.nio.file.Path;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

public class PathResolver {
    private final Path workingPath;

    public PathResolver(Path workingPath) {
        this.workingPath = workingPath;
    }

    public Path resolveRepository(Student student) {
        return workingPath.resolve(student.repositoryPath());
    }

    public Path resolvePersonalPath(Student student) {
        return workingPath.resolve(student.personalPath());
    }

    public Path resolveTask(Student student, Task task) {
        return resolveRepository(student).resolve(task.path());
    }
}
