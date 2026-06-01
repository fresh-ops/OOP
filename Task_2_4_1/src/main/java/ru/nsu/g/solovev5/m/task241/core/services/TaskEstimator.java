package ru.nsu.g.solovev5.m.task241.core.services;

import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;

/**
 * A service that assigns grades for tasks.
 */
public class TaskEstimator {
    /**
     * Checks the task status and estimates it.
     *
     * @param entry the entry about the task status
     * @return a grade
     */
    public int estimate(ReportEntry entry) {
        var accepted = entry.buildPassed() && entry.stylePassed() && entry.coveragePassed();
        return accepted ? 1 : 0;
    }
}
