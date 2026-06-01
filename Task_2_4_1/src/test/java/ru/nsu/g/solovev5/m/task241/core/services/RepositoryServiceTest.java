package ru.nsu.g.solovev5.m.task241.core.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void repositoryManagementMethods_should_workWithRepository(
        @TempDir Path workingDirectory
    ) throws IOException, InterruptedException {
        var student = new Student(
            "Ivanov Ivan",
            "Ivan",
            URI.create("https://github.com/sarcasticadmin/empty-repo")
        );
        var resolver = new PathResolver(workingDirectory);
        var service = new RepositoryService(resolver);

        assertFalse(Files.exists(resolver.resolveRepository(student)));
        assertFalse(
            service.isRepositoryLoaded(student),
            "isRepositoryLoaded should return false if there is no repository"
        );

        service.loadRepositoryIfNotLoaded(student);
        assertTrue(
            Files.exists(resolver.resolveRepository(student)),
            "loadRepositoryIfNotLoaded should create a new directory"
        );
        assertTrue(
            service.isRepositoryLoaded(student),
            "isRepositoryLoaded should return true if there is a repository"
        );
        assertDoesNotThrow(
            () -> service.updateRepository(student)
        );

        service.deleteRepository(student);
        assertFalse(
            Files.exists(resolver.resolveRepository(student)),
            "deleteRepository should delete the directory"
        );
        assertFalse(
            service.isRepositoryLoaded(student),
            "isRepositoryLoaded should return false if there is no repository"
        );
    }
}