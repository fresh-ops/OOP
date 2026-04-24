package ru.nsu.g.solovev5.m.task241.dsl.builders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;

class ConfigBuilderTest {
    @Test
    void studyGroup_should_addStudyGroup() {
        var builder = new ConfigBuilder();

        var config = builder.build();
        assertTrue(config.studyGroups().isEmpty());

        var group = new StudyGroup("12345", Set.of(
            new Student("Ivan Ivanov", "Ivan", URI.create("https://github.com/Ivan"))
        ));
        builder.studyGroup(group);

        config = builder.build();
        assertFalse(config.studyGroups().isEmpty());
        assertTrue(config.studyGroups().contains(group));
    }
}