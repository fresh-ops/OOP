package ru.nsu.g.solovev5.m.task241.dsl.delegates

import groovy.transform.CompileStatic
import ru.nsu.g.solovev5.m.task241.dsl.builders.StudentBuilder

@CompileStatic
class StudentDelegate {
    private final StudentBuilder builder

    protected StudentDelegate(StudentBuilder builder) {
        this.builder = builder
    }

    StudentBuilder student(String name) {
        builder.name(name)
    }
}
