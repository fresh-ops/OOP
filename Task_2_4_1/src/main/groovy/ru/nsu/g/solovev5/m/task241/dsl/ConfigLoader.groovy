package ru.nsu.g.solovev5.m.task241.dsl

import ru.nsu.g.solovev5.m.task241.core.models.Config
import ru.nsu.g.solovev5.m.task241.dsl.delegates.CourseBlock
import ru.nsu.g.solovev5.m.task241.dsl.delegates.Statement
import ru.nsu.g.solovev5.m.task241.dsl.delegates.StudyGroupBlock

import java.nio.file.Path

class ConfigLoader {
    private final List<Statement> SUPPORTED_STATEMENTS = [
            new StudyGroupBlock(),
            new CourseBlock(),
    ]

    Config loadFrom(Path path) {
        var context = new ExecutionContext(SUPPORTED_STATEMENTS)
        context.putExecuting(path)
        context.binding().setVariable("include", {
            String file -> this.include(Path.of(file), context)
        })
        var shell = new GroovyShell(context.binding())
        shell.evaluate(path.toFile())
        context.popExecuting(path)

        context.config()
    }

    private void include(Path path, ExecutionContext context) {
        if (context.isExecuting(path)) {
            throw new RuntimeException("Circular includes on $path")
        }
        context.putExecuting(path)
        var shell = new GroovyShell(context.binding())
        shell.evaluate(path.toFile())
        context.popExecuting(path)
    }
}
