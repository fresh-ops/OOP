package ru.nsu.g.solovev5.m.task241.dsl.delegates

import groovy.transform.CompileStatic
import ru.nsu.g.solovev5.m.task241.dsl.builders.StudentBuilder

import java.util.function.Supplier

@CompileStatic
class StudentDelegate {
    private final Supplier<StudentBuilder> supplier

    protected StudentDelegate(Supplier<StudentBuilder> supplier) {
        this.supplier = supplier
    }

    StudentBuilder student(String name) {
        supplier.get().name(name)
    }
}
