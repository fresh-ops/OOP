package ru.nsu.g.solovev5.m.task241.core.models;

import java.util.List;

/**
 * An application config object.
 *
 * @param studyGroups the list of study groups
 */
public record Config(List<StudyGroup> studyGroups) {
}
