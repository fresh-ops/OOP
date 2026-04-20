package ru.nsu.g.solovev5.m.task241.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StudyGroupTest {
    @ParameterizedTest
    @MethodSource("nullishArguments")
    void constructor_should_throwNullPointerException_when_anyArgumentIsNull(
        String id, Set<Student> students
    ) {
        assertThrows(
            NullPointerException.class,
            () -> new StudyGroup(id, students)
        );
    }

    static Stream<Arguments> nullishArguments() throws URISyntaxException {
        return Stream.of(
            Arguments.of("12345", null),
            Arguments.of(null, Set.of(
                new Student("Ivanov Ivan", "Ivan", new URI("https://github.com/Ivan"))
            ))
        );
    }

    @Test
    void constructor_should_throwIllegalArgumentException_when_noStudentsProvided() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new StudyGroup("12345", Set.of())
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void size_should_returnNumberOfStudents(String id, Set<Student> students) {
        var group = new StudyGroup(id, students);

        assertEquals(students.size(), group.size());
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void contains_should_returnTrue_when_studentIsInGroup(String id, Set<Student> students) {
        var group = new StudyGroup(id, students);

        for (var student : students) {
            assertTrue(group.contains(student));
        }
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void contains_should_returnFalse_when_studentIsNotInGroup(String id, Set<Student> students) throws URISyntaxException {
        var group = new StudyGroup(id, students);
        var absentStudent = new Student(
            "Mitrphan Prostakov",
            "Minor",
            new URI("https://github.com/what")
        );

        assertFalse(
            students.contains(absentStudent),
            "Test data error"
        );
        assertFalse(group.contains(absentStudent));
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void iterator_should_iterateOverAllStudents(String id, Set<Student> students) {
        var group = new StudyGroup(id, students);
        var iterated = new HashSet<Student>();

        for (var student : students) {
            assertFalse(iterated.contains(student));
            iterated.add(student);
        }

        assertEquals(iterated.size(), group.size());
        assertEquals(iterated, students);
    }

    static Stream<Arguments> validArguments() throws URISyntaxException {
        return Stream.of(
            Arguments.of("12345", Set.of(
                new Student("Ivanov Ivan", "Ivan", new URI("https://github.com/Ivan")),
                new Student("Petrov Petr", "Petr", new URI("https://github.com/Petr")),
                new Student("Vasiliy Pupkin", "Vasya", new URI("https://github.com/Vasya"))
            )),
            Arguments.of("abcde", Set.of(
                new Student("John Smith", "user-a1", new URI("https://github.com/John"))
            ))
        );
    }
}