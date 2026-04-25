package ru.nsu.g.solovev5.m.task241.dsl.delegates


import ru.nsu.g.solovev5.m.task241.dsl.builders.TaskBuilder

class CourseBlock implements Statement {
    private PartsAcceptor acceptor
    private List<TaskBuilder> builders = []

    void course(
            @DelegatesTo(
                    strategy = Closure.DELEGATE_ONLY,
                    value = TaskDelegate
            ) Closure closure
    ) {
        var delegate = new TaskDelegate(() -> createBuilder())
        closure.rehydrate(delegate, this, this).call()
        produceTasks()
    }

    private TaskBuilder createBuilder() {
        var builder = new TaskBuilder()
        builders << builder

        builder
    }

    private void produceTasks() {
        builders.forEach { builder -> acceptor.accept(builder.build()) }
    }

    @Override
    void bind(Binding binding) {
        binding.setVariable("course", this.&course)
    }

    @Override
    void acceptor(PartsAcceptor acceptor) {
        this.acceptor = acceptor
    }
}
