package ru.nsu.g.solovev5.m.task241.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReportEntryBuilderTest {
    @Test
    void build_should_applyPassedParameters() {
        var taskId = "Task 1 1 1";
        var studentName = "Ivan Ivanov";
        var buildPassed = true;
        var stylePassed = true;
        var coveragePassed = true;
        var grade = 1;

        var entry = new ReportEntry.Builder()
            .taskId(taskId)
            .studentName(studentName)
            .buildPassed(buildPassed)
            .stylePassed(stylePassed)
            .coveragePassed(coveragePassed)
            .grade(grade)
            .build();

        assertEquals(taskId, entry.taskId());
        assertEquals(studentName, entry.studentName());
        assertEquals(buildPassed, entry.buildPassed());
        assertEquals(stylePassed, entry.stylePassed());
        assertEquals(coveragePassed, entry.coveragePassed());
        assertEquals(grade, entry.grade());
    }

    @Test
    void from_should_copyEntryFields() {
        var entry = new ReportEntry("Task 1 1 1", "Ivan Ivanov", true, true, false, 0);
        var builtEntry = new ReportEntry.Builder()
            .from(entry)
            .build();

        assertEquals(entry, builtEntry);
    }
}