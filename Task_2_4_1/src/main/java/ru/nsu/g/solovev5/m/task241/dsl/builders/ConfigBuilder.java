package ru.nsu.g.solovev5.m.task241.dsl.builders;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task241.core.models.Config;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;

/**
 * An application config builder.
 */
public class ConfigBuilder {
    private final List<StudyGroup> studyGroups = new ArrayList<>();

    /**
     * Adds a new study group to a new config.
     *
     * @param studyGroup a new config study group
     * @return this config builder
     */
    public ConfigBuilder studyGroup(StudyGroup studyGroup) {
        studyGroups.add(studyGroup);
        return this;
    }

    /**
     * Builds a new config.
     *
     * @return a new config
     */
    public Config build() {
        return new Config(List.copyOf(studyGroups));
    }
}
