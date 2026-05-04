package ru.nsu.g.solovev5.m.task241.core.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

class PathResolverTest {
    @Test
    void resolveRepository_should_appendPathToWorkingDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var resolver = new PathResolver(workingDirectory);

        var resolved = resolver.resolveRepository(student);
        assertEquals(workingDirectory.resolve(student.repositoryPath()), resolved);
    }

    @Test
    void resolvePersonalPath_should_appendToWorkingDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var resolver = new PathResolver(workingDirectory);

        var resolved = resolver.resolvePersonalPath(student);
        assertEquals(workingDirectory.resolve(student.personalPath()), resolved);
    }

    @Test
    void resolveTask_should_appendPathToWorkingDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var task = new Task("My Task", "Task 1 1 1", LocalDate.now(), LocalDate.now().plusDays(1));
        var resolver = new PathResolver(workingDirectory);

        var resolved = resolver.resolveTask(student, task);
        assertEquals(
            workingDirectory.resolve(student.repositoryPath()).resolve(task.path()),
            resolved
        );
    }
}