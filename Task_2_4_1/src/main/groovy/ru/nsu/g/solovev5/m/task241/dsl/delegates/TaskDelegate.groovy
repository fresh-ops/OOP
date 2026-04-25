package ru.nsu.g.solovev5.m.task241.dsl.delegates

import groovy.transform.CompileStatic
import ru.nsu.g.solovev5.m.task241.dsl.builders.TaskBuilder

import java.util.function.Supplier

@CompileStatic
class TaskDelegate {
    private final Supplier<TaskBuilder> supplier

    protected TaskDelegate(Supplier<TaskBuilder> supplier) {
        this.supplier = supplier
    }

    TaskBuilder task(String name) {
        supplier.get().name(name)
    }
}
