package ru.nsu.g.solovev5.m.task241.core.models;

import java.util.Objects;

/**
 * Represents a checker report entry.
 *
 * @param taskId         the checking task report
 * @param studentName    the name of task submitter
 * @param buildPassed    the successful build flag
 * @param stylePassed    the successful style check flag
 * @param coveragePassed the enough test coverage flag
 */
public record ReportEntry(
    String taskId,
    String studentName,
    boolean buildPassed,
    boolean stylePassed,
    boolean coveragePassed
) {
    /**
     * Creates a new checker report entry.
     *
     * @param taskId         the checking task report
     * @param studentName    the name of task submitter
     * @param buildPassed    the successful build flag
     * @param stylePassed    the successful style check flag
     * @param coveragePassed the enough test coverage flag
     */
    public ReportEntry {
        Objects.requireNonNull(taskId);
        Objects.requireNonNull(studentName);
    }
}
