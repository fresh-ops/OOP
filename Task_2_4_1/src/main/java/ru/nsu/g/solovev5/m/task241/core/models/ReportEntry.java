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

    /**
     * Returns a grade for the task.
     *
     * @return {@code 1} if all checks are passed, {@code 0} otherwise
     */
    public int grade() {
        return buildPassed && stylePassed && coveragePassed ? 1 : 0;
    }

    /**
     * A flexible chain builder.
     */
    public static class Builder {
        private String taskId;
        private String studentName;
        private boolean buildPassed;
        private boolean stylePassed;
        private boolean coveragePassed;

        /**
         * Sets all fields from the passed entry.
         *
         * @param entry the fields owner
         * @return this builder
         */
        public Builder from(ReportEntry entry) {
            this.taskId = entry.taskId();
            this.studentName = entry.studentName();
            this.buildPassed = entry.buildPassed();
            this.stylePassed = entry.stylePassed();
            this.coveragePassed = entry.coveragePassed();

            return this;
        }

        /**
         * Set the task id.
         *
         * @param taskId the new entry task id
         * @return this builder
         */
        public Builder taskId(String taskId) {
            this.taskId = taskId;

            return this;
        }

        /**
         * Set the student name.
         *
         * @param studentName the new entry student name
         * @return this builder
         */
        public Builder studentName(String studentName) {
            this.studentName = studentName;

            return this;
        }

        /**
         * Set the build flag.
         *
         * @param buildPassed the new entry build flag
         * @return this builder
         */
        public Builder buildPassed(boolean buildPassed) {
            this.buildPassed = buildPassed;

            return this;
        }

        /**
         * Set the style flag.
         *
         * @param stylePassed the new entry style flag
         * @return this builder
         */
        public Builder stylePassed(boolean stylePassed) {
            this.stylePassed = stylePassed;

            return this;
        }

        /**
         * Set the coverage flag.
         *
         * @param coveragePassed the new entry coverage flag
         * @return this builder
         */
        public Builder coveragePassed(boolean coveragePassed) {
            this.coveragePassed = coveragePassed;

            return this;
        }

        /**
         * Build a new report entry with passed parameters.
         *
         * @return a new report entry
         */
        public ReportEntry build() {
            return new ReportEntry(taskId, studentName, buildPassed, stylePassed, coveragePassed);
        }
    }
}
