package ru.nsu.g.solovev5.m.task241.core.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task241.core.models.ReportEntry;

class TaskEstimatorTest {
    @Test
    void estimate_should_returnOne_when_allCriteriaPassed() {
        var entry = new ReportEntry("My Task", "Ivan Ivanov", true, true, true, 0);
        var estimator = new TaskEstimator();

        assertEquals(1, estimator.estimate(entry));
    }

    @Test
    void estimate_should_returnZero_when_notAllCriteriaPassed() {
        var entry = new ReportEntry("My Task", "Ivan Ivanov", true, false, true, 0);
        var estimator = new TaskEstimator();

        assertEquals(0, estimator.estimate(entry));
    }

}