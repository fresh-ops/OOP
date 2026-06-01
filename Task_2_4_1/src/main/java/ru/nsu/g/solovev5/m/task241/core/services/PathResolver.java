package ru.nsu.g.solovev5.m.task241.core.services;

import com.google.common.base.Objects;
import java.nio.file.Path;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.Task;

/**
 * A working path resolver.
 */
public class PathResolver {
    private static final Path STUDENTS_PATH = Path.of("students");
    private static final Path REPORTS_PATH = Path.of("reports");
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
        return studentsPath().resolve(student.repositoryPath());
    }

    /**
     * Resolves the path to the student personal directory.
     *
     * @param student the directory owner
     * @return a path to the personal directory
     */
    public Path resolvePersonalPath(Student student) {
        return studentsPath().resolve(student.personalPath());
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

    /**
     * Resolves the path to the task checking report.
     *
     * @param student the task submitter
     * @param task    the submitting task
     * @return a path to the report entry
     */
    public Path resolveReport(Student student, Task task) {
        var name = Integer.toString(Objects.hashCode(student.name(), task.id()));
        return reportsPath().resolve(name);
    }

    /**
     * Resolves the path to the task checking report.
     *
     * @param entry the report entry
     * @return a path to the report entry
     */
    public Path resolveReport(ReportEntry entry) {
        var name = Integer.toString(Objects.hashCode(entry.studentName(), entry.taskId()));
        return reportsPath().resolve(name);
    }

    /**
     * Returns the working directory.
     *
     * @return the working directory
     */
    public Path workingDirectory() {
        return this.workingPath;
    }

    /**
     * Returns the location of students personal paths.
     *
     * @return the location of students personal paths
     */
    public Path studentsPath() {
        return workingPath.resolve(STUDENTS_PATH);
    }

    /**
     * Returns the location of reports.
     *
     * @return path to reports
     */
    public Path reportsPath() {
        return workingPath.resolve(REPORTS_PATH);
    }
}
