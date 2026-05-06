package ru.nsu.g.solovev5.m.task241.core.services;

import java.nio.file.Path;
import org.gradle.api.GradleException;
import org.gradle.tooling.GradleConnector;

public class BuildService {
    public boolean successfulBuild(Path project) {
        var connector = GradleConnector.newConnector();
        connector.forProjectDirectory(project.toFile());

        try (var connection = connector.connect()) {
            var launcher = connection.newBuild();

            launcher.forTasks("test", "jacocoTestReport");
            launcher.run();
            return true;
        } catch (GradleException e) {
            return false;
        }
    }
}
