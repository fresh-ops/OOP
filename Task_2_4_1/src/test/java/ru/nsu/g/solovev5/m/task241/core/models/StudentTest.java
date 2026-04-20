package ru.nsu.g.solovev5.m.task241.core.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
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
}