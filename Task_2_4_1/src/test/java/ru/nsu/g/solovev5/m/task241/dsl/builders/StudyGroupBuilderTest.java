package ru.nsu.g.solovev5.m.task241.dsl.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task241.core.models.Student;

class StudyGroupBuilderTest {
    @Test
    void build_should_applyParameters() {
        var id = "12345";
        var students = Set.of(
            new Student("Ivan Ivanov", "Ivan", URI.create("https://github.com/Ivan")),
            new Student("Petr Petrov", "Petr", URI.create("https://github.com/Petr")),
            new Student("John Smith", "John", URI.create("https://github.com/John"))
        );

        var builder = new StudyGroupBuilder(id);
        for (var student : students) {
            builder.student(student.name())
                .aka(student.nickname())
                .submitsAt(student.repository().toString());
        }

        var group = builder.build();
        assertEquals(id, group.id());
        assertEquals(students, group.students());
    }
}