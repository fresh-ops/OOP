package ru.nsu.g.solovev5.m.task241.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StudentTest {
    @ParameterizedTest
    @MethodSource("invalidParameters")
    void constructor_should_throwException_when_anyArgumentIsNull(
        String name, String nickname, URI repository
    ) {
        assertThrows(
            NullPointerException.class,
            () -> new Student(name, nickname, repository)
        );
    }

    static Stream<Arguments> invalidParameters() {
        String validName = "Ivan Ivanov";
        String validNickname = "Ivan";
        URI validRepository = URI.create("https://github.com/Ivan");

        return Stream.of(
            Arguments.of(null, validNickname, validRepository),
            Arguments.of(validName, null, validRepository),
            Arguments.of(validName, validNickname, null)
        );
    }

    @ParameterizedTest
    @MethodSource("validParameters")
    void personalPath_should_useNickname(String name, String nickname, URI repository) {
        var student = new Student(name, nickname, repository);
        var personalPath = student.personalPath();
        var expectedPersonalPath = Path.of(nickname.replaceAll(" ", "_"));

        assertEquals(expectedPersonalPath, personalPath);
    }

    @ParameterizedTest
    @MethodSource("validParameters")
    void repositoryPath_should_extendPersonalPath(String name, String nickname, URI repository) {
        var student = new Student(name, nickname, repository);
        var personalPath = student.personalPath();
        var repositoryPath = student.repositoryPath();
        assertTrue(repositoryPath.startsWith(personalPath));
    }

    @ParameterizedTest
    @MethodSource("validParameters")
    void git_should_haveGitExtension(String name, String nickname, URI repository) {
        var student = new Student(name, nickname, repository);
        assertTrue(student.git().toString().endsWith(".git"));
    }

    static Stream<Arguments> validParameters() {
        return Stream.of(
            Arguments.of("Ivan Ivanov", "Ivan",  URI.create("https://github.com/Ivan/OOP")),
            Arguments.of("Petr Petrov", "Petr I",  URI.create("https://github.com/Petr_I/OOP"))
        );
    }
}