package ru.nsu.g.solovev5.m.task241.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TaskTest {
    @Test
    void constructor_shouldNot_acceptSoftDeadlineBeforeHardDeadline() {
        assertThrows(
            AssertionError.class,
            () -> new Task("name", LocalDate.now(), LocalDate.now())
        );
    }

    @ParameterizedTest
    @MethodSource("nullishParameters")
    void constructor_shouldNot_acceptNullishParameters(
        String name, LocalDate softDeadline, LocalDate hardDeadline
    ) {
        assertThrows(
            NullPointerException.class,
            () -> new Task(name, softDeadline, hardDeadline)
        );
    }

    static Stream<Arguments> nullishParameters() {
        return Stream.of(
            Arguments.of(null, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2)),
            Arguments.of("Task 1 1 1", null, LocalDate.of(2025, 1, 2)),
            Arguments.of("Task 1 1 1", LocalDate.of(2025, 1, 1), null)
        );
    }

    @Test
    void path_should_returnTaskNameSuffix() {
        var task = new Task("Task 1 1 1", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2));
        var path = task.path();

        assertEquals(Path.of("Task_1_1_1"), path);
    }
}