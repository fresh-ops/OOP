package ru.nsu.g.solovev5.m.task241.dsl.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class TaskBuilderTest {
    @Test
    void build_should_applyParameters() {
        var name = "Task 1 1 1";
        var softDeadline = LocalDate.now();
        var hardDeadline = LocalDate.now().plusDays(1);
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        var builder = new TaskBuilder();
        builder.name(name)
            .softDeadline(softDeadline.format(formatter))
            .hardDeadline(hardDeadline.format(formatter));

        var task = builder.build();
        assertEquals(name, task.name());
        assertEquals(softDeadline, task.softDeadline());
        assertEquals(hardDeadline, task.hardDeadline());
    }

}