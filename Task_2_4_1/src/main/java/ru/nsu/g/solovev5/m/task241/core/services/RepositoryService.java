package ru.nsu.g.solovev5.m.task241.core.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import ru.nsu.g.solovev5.m.task241.core.models.Student;

/**
 * A service for repository management.
 */
public class RepositoryService {
    private final Path workingPath;

    /**
     * Creates a new repository service.
     *
     * @param workingPath the path containing students personal paths
     */
    public RepositoryService(Path workingPath) {
        this.workingPath = workingPath;
    }

    /**
     * Loads the student repository if needed.
     *
     * @param student the owner of repository
     * @throws IOException          if an I/O error occurred
     * @throws InterruptedException if current thread was interrupted
     * @throws RuntimeException     if failed to clone repository
     */
    public void loadRepositoryIfNotLoaded(
        Student student
    ) throws IOException, InterruptedException {
        if (isRepositoryLoaded(student)) {
            return;
        }

        var path = workingPath.resolve(student.personalPath());
        createIfNotExist(path);
        var processBuilder = new ProcessBuilder("git", "clone", "-v", student.git().toString())
            .redirectErrorStream(true)
            .directory(path.toFile());

        var process = processBuilder.start();

        try (var inputStream = process.getInputStream()) {
            inputStream.transferTo(System.out);
        }

        var exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Git clone failed with exit code " + exitCode);
        }
    }

    /**
     * Pulls updates from the remote repository.
     *
     * @param student the owner of repository
     * @throws IOException          if an I/O error occurred
     * @throws InterruptedException if current thread was interrupted
     * @throws RuntimeException     if repository is not loaded or failed to update
     */
    public void updateRepository(Student student) throws IOException, InterruptedException {
        if (!isRepositoryLoaded(student)) {
            throw new RuntimeException("Repository is not loaded. Nothing to update.");
        }

        var processBuilder = new ProcessBuilder("git", "pull", "-v")
            .redirectErrorStream(true)
            .directory(resolveRepository(student).toFile());

        var process = processBuilder.start();

        try (var inputStream = process.getInputStream()) {
            inputStream.transferTo(System.out);
        }

        var exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Git pull failed with exit code " + exitCode);
        }
    }

    /**
     * Deletes the student repository.
     *
     * @param student the repository owner
     * @throws IOException if an I/O error occurred
     */
    public void deleteRepository(Student student) throws IOException {
        if (isRepositoryLoaded(student)) {
            try (var stream = Files.walk(resolveRepository(student))) {
                stream.sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (IOException exception) {
                                System.err.println("Failed to delete file: " + p);
                            }
                        }
                    );
            }
        }
    }

    public boolean isRepositoryLoaded(Student student) {
        return Files.exists(resolveRepository(student));
    }

    public Path resolveRepository(Student student) {
        return workingPath.resolve(student.repositoryPath());
    }

    private void createIfNotExist(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
