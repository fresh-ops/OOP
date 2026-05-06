package ru.nsu.g.solovev5.m.task241.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

/**
 * A repository of report entries.
 */
public class ReportsRepository {
    private final PathResolver resolver;

    /**
     * Creates a new reports' repository.
     *
     * @param resolver the path resolver
     */
    public ReportsRepository(PathResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Store an entry in this repository.
     *
     * @param entry the entry to store
     * @throws IOException if any I/O error occurred
     */
    public void store(ReportEntry entry) throws IOException {
        var path = resolver.resolveReport(entry);
        var content = map(entry);
        if  (Files.notExists(path)) {
            Files.createDirectories(path.getParent());
        }
        Files.writeString(
            path, content,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    /**
     * Loads an entry for the given student and task.
     *
     * @param student the task submitter
     * @param task    the submitting task
     * @return an entry about the task status
     * @throws IOException if any I/O error occurred
     */
    public ReportEntry load(Student student, Task task) throws IOException {
        var path = resolver.resolveReport(student, task);
        if  (Files.exists(path)) {
            var content = Files.readString(path, Charset.defaultCharset());
            return unmap(content);
        }
        return emptyEntry(student, task);
    }

    /**
     * Loads all previously stored entries.
     *
     * @return previously stored entries
     * @throws IOException if an I/O error occurred
     */
    public List<ReportEntry> loadAll() throws IOException {
        var path = resolver.reportsPath();
        if (Files.notExists(path)) {
            return List.of();
        }

        try (var reportPaths = Files.list(path)) {
            return reportPaths
                .map(p -> {
                    try {
                        return Files.readString(p, Charset.defaultCharset());
                    } catch (IOException e) {
                        System.err.println(p + ": " + e.getMessage());
                        return "";
                    }
                })
                .filter(r -> !r.isBlank())
                .map(r -> {
                    try {
                        return unmap(r);
                    } catch (JsonProcessingException e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        }
    }

    /**
     * Maps the given entry to a JSON string.
     *
     * @param entry the entry to map
     * @return the serialized entry
     * @throws JsonProcessingException if an error occurred
     */
    public String map(ReportEntry entry) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString(entry);
    }

    /**
     * Unmaps the serialized entry.
     *
     * @param json the string containing serialized entry
     * @return the deserialized entry
     * @throws JsonProcessingException if an error occurred
     */
    public ReportEntry unmap(String json) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, ReportEntry.class);
    }

    private ReportEntry emptyEntry(Student student, Task task) {
        return new ReportEntry(
            task.id(),
            student.name(),
            false,
            false,
            false
        );
    }
}
