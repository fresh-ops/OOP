package ru.nsu.g.solovev5.m.task241.dsl.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StudentBuilderTest {
    @Test
    void build_should_applyParameters() {
        var name = "Ivan Ivanov";
        var nickname = "Ivan";
        var repository = "https://github.com/Ivan";

        var student = new StudentBuilder().name(name)
            .aka(nickname)
            .submitsAt(repository)
            .build();

        assertEquals(name, student.name());
        assertEquals(nickname, student.nickname());
        assertEquals(repository, student.repository().toString());
    }

    @Test
    void name_should_overwriteLastName() {
        var oldName = "Ivan Ivanov";
        var newName = "Ivan Petrov";
        var nickname = "Ivan";
        var repository = "https://github.com/Ivan";

        var student = new StudentBuilder().name(oldName)
            .aka(nickname)
            .submitsAt(repository)
            .name(newName)
            .build();

        assertEquals(newName, student.name());
    }

    @Test
    void nickname_should_overwriteLastNickname() {
        var name = "Ivan Ivanov";
        var oldNickname = "Ivan";
        var newNickname = "Vanya";
        var repository = "https://github.com/Ivan";

        var student = new StudentBuilder().name(name)
            .aka(oldNickname)
            .submitsAt(repository)
            .aka(newNickname)
            .build();

        assertEquals(newNickname, student.nickname());
    }

    @Test
    void repository_should_overwriteLastRepository() {
        var name = "Ivan Ivanov";
        var nickname = "Ivan";
        var oldRepository = "https://github.com/Ivan";
        var newRepository = "https://gitlab.com/Ivan";

        var student = new StudentBuilder().name(name)
            .aka(nickname)
            .submitsAt(oldRepository)
            .submitsAt(newRepository)
            .build();

        assertEquals(newRepository, student.repository().toString());
    }
}