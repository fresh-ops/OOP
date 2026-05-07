package ru.nsu.g.solovev5.m.task241.core.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

class ReportsRepositoryTest {
    @Test
    void mapUnmap_should_keepEntrySame(@TempDir Path workingPath) throws JsonProcessingException {
        var entry = new ReportEntry(
            "Task 1 1 1",
            "Ivan Ivanov",
            true,
            true,
            false,
            0
        );
        var repository = new ReportsRepository(
            new PathResolver(workingPath)
        );

        var mapped = repository.map(entry);
        var unmapped = repository.unmap(mapped);

        assertEquals(entry, unmapped);
    }

    @Test
    void store_should_createNewFile(@TempDir Path workingPath) throws IOException {
        var entry = new ReportEntry(
            "Task 1 1 1",
            "Ivan Ivanov",
            true,
            true,
            false,
            0
        );
        var resolver = new PathResolver(workingPath);
        var repository = new ReportsRepository(resolver);

        repository.store(entry);
        assertTrue(Files.exists(resolver.resolveReport(entry)));
    }

    @Test
    void load_should_returnEmptyEntry_when_noEntryStored(
        @TempDir Path workingPath
    ) throws IOException {
        var student = new Student(
            "Ivan Ivanov",
            "Ivan",
            URI.create("path.to.github.repo")
        );
        var task = new Task(
            "My Task",
            "Task 1 1 1",
            LocalDate.now(),
            LocalDate.now().plusDays(1)
        );
        var repository = new ReportsRepository(new PathResolver(workingPath));

        var entry = repository.load(student, task);
        assertNotNull(entry);
        assertEquals(student.name(), entry.studentName());
        assertEquals(task.id(), entry.taskId());
        assertFalse(entry.buildPassed());
        assertFalse(entry.stylePassed());
        assertFalse(entry.coveragePassed());
        assertEquals(0, entry.grade());
    }

    @Test
    void storeLoad_should_keepEntrySame(@TempDir Path workingPath) throws IOException {
        var student = new Student(
            "Ivan Ivanov",
            "Ivan",
            URI.create("path.to.github.repo")
        );
        var task = new Task(
            "My Task",
            "Task 1 1 1",
            LocalDate.now(),
            LocalDate.now().plusDays(1)
        );
        var entry = new ReportEntry(
            task.id(),
            student.name(),
            true,
            true,
            false,
            0
        );
        var repository = new ReportsRepository(new PathResolver(workingPath));

        repository.store(entry);
        var loaded = repository.load(student, task);
        assertNotNull(loaded);
        assertEquals(entry, loaded);
    }

    @Test
    void store_should_updateEntry(@TempDir Path workingPath) throws IOException {
        var student = new Student(
            "Ivan Ivanov",
            "Ivan",
            URI.create("path.to.github.repo")
        );
        var task = new Task(
            "My Task",
            "Task 1 1 1",
            LocalDate.now(),
            LocalDate.now().plusDays(1)
        );
        var entry = new ReportEntry(
            task.id(),
            student.name(),
            true,
            true,
            false,
            0
        );
        var updatedEntry = new ReportEntry(
            entry.taskId(),
            entry.studentName(),
            entry.buildPassed(),
            entry.stylePassed(),
            !entry.coveragePassed(),
            entry.grade()
        );
        var repository = new ReportsRepository(new PathResolver(workingPath));

        repository.store(entry);
        repository.store(updatedEntry);
        var loaded = repository.load(student, task);
        assertNotNull(loaded);
        assertEquals(updatedEntry, loaded);
    }

    @Test
    void loadAll_should_returnAllStoredEntries(@TempDir Path workingPath) throws IOException {
        var entries = List.of(
            new ReportEntry("Task 1 1 1", "Ivan Ivanov", true, true, true, 1),
            new ReportEntry("Task 1 1 1", "Petr Petrov", true, false, true, 0),
            new ReportEntry("Task 1 1 2", "Ivan Ivanov", true, true, false, 0)
        );

        var repository = new ReportsRepository(new PathResolver(workingPath));
        for (var entry : entries) {
            repository.store(entry);
        }

        var loaded = repository.loadAll();
        assertNotNull(loaded);
        assertTrue(entries.containsAll(loaded));
        assertTrue(loaded.containsAll(entries));
    }

    @Test
    void loadAll_should_returnEmptyList_when_reportsDoesNotExist(
        @TempDir Path workingPath
    ) throws IOException {
        var resolver = new PathResolver(workingPath);
        var repository = new ReportsRepository(resolver);
        var loaded = repository.loadAll();
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
        assertTrue(Files.notExists(resolver.reportsPath()));
    }
}