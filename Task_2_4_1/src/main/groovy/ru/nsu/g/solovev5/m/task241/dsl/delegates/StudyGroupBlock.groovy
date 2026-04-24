package ru.nsu.g.solovev5.m.task241.dsl.delegates

import groovy.transform.CompileStatic
import ru.nsu.g.solovev5.m.task241.dsl.builders.StudyGroupBuilder

@CompileStatic
class StudyGroupBlock implements Statement {
    private PartsAcceptor acceptor

    void studyGroup(
            String id,
            @DelegatesTo(
                    strategy = Closure.DELEGATE_ONLY,
                    value = StudentDelegate
            ) Closure closure
    ) {
        var builder = new StudyGroupBuilder().id(id)
        var delegate = new StudentDelegate(builder.student())
        closure.rehydrate(delegate, this, this).call()
        acceptor.accept(builder.build())
    }

    @Override
    void bind(Binding binding) {
        binding.setVariable("studyGroup", this.&studyGroup)
    }

    @Override
    void acceptor(PartsAcceptor acceptor) {
        this.acceptor = acceptor
    }
}
