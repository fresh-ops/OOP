package ru.nsu.g.solovev5.m.task241.cli;

import java.io.IOException;
import picocli.CommandLine;
import ru.nsu.g.solovev5.m.task241.core.models.Student;
import ru.nsu.g.solovev5.m.task241.core.models.StudyGroup;
import ru.nsu.g.solovev5.m.task241.core.services.RepositoryService;

@CommandLine.Command(
    name = "fetch",
    description = "fetches all repositories"
)
public class FetchCommand extends Command {
    @Override
    public Integer call() {
        try {
            for (var group : config.studyGroups()) {
                fetchGroup(group);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
            return 1;
        }
        return 0;
    }

    private void fetchGroup(StudyGroup group) throws InterruptedException {
        System.out.println("Fetching group " + group.id());
        for (var student : group.students()) {
            fetchStudent(student);
        }
    }

    private void fetchStudent(Student student) throws InterruptedException {
        var service = new RepositoryService(resolver);

        try {
            service.loadRepositoryIfNotLoaded(student);
            service.updateRepository(student);
        } catch (IOException | RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
