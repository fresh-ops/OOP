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
    void resolveRepository_should_appendPathToStudentsDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var resolver = new PathResolver(workingDirectory);

        var resolved = resolver.resolveRepository(student);
        assertEquals(resolver.studentsPath().resolve(student.repositoryPath()), resolved);
    }

    @Test
    void resolvePersonalPath_should_appendToStudentsDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var resolver = new PathResolver(workingDirectory);

        var resolved = resolver.resolvePersonalPath(student);
        assertEquals(resolver.studentsPath().resolve(student.personalPath()), resolved);
    }

    @Test
    void resolveTask_should_appendPathToStudentsDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var task = new Task("My Task", "Task 1 1 1", LocalDate.now(), LocalDate.now().plusDays(1));
        var resolver = new PathResolver(workingDirectory);

        var resolved = resolver.resolveTask(student, task);
        assertEquals(
            resolver.studentsPath().resolve(student.repositoryPath()).resolve(task.path()),
            resolved
        );
    }

    @Test
    void studentsPath_should_beInWorkingDirectory(@TempDir Path workingDirectory) {
        var resolver = new PathResolver(workingDirectory);

        assertEquals(
            workingDirectory.resolve("students"),
            resolver.studentsPath()
        );
    }
}