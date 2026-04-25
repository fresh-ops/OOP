package ru.nsu.g.solovev5.m.task241.core.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.solovev5.m.task241.core.models.Student;

class RepositoryServiceTest {
    @Test
    void resolveRepository_should_appendPathToWorkingDirectory(@TempDir Path workingDirectory) {
        var student = new Student("Ivanov Ivan", "Ivan", URI.create("https://github.com/Ivan/OOP"));
        var service = new RepositoryService(workingDirectory);

        var resolved = service.resolveRepository(student);
        assertEquals(workingDirectory.resolve(student.repositoryPath()), resolved);
    }

    @Test
    void repositoryManagementMethods_should_workWithRepository(
        @TempDir Path workingDirectory
    ) throws IOException, InterruptedException {
        var student = new Student(
            "Ivanov Ivan",
            "Ivan",
            URI.create("https://github.com/sarcasticadmin/empty-repo")
        );
        var service = new RepositoryService(workingDirectory);

        assertFalse(Files.exists(workingDirectory.resolve(student.repositoryPath())));
        assertFalse(
            service.isRepositoryLoaded(student),
            "isRepositoryLoaded should return false if there is no repository"
        );

        service.loadRepositoryIfNotLoaded(student);
        assertTrue(
            Files.exists(workingDirectory.resolve(student.repositoryPath())),
            "loadRepositoryIfNotLoaded should create a new directory"
        );
        assertTrue(
            service.isRepositoryLoaded(student),
            "isRepositoryLoaded should return true if there is a repository"
        );

        service.deleteRepository(student);
        assertFalse(
            Files.exists(workingDirectory.resolve(student.repositoryPath())),
            "deleteRepository should delete the directory"
        );
        assertFalse(
            service.isRepositoryLoaded(student),
            "isRepositoryLoaded should return false if there is no repository"
        );
    }
}