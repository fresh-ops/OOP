package ru.nsu.g.solovev5.m.task241.core.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.gradle.tooling.GradleConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * A service for checking projects' test coverage.
 */
public class TestCoverageService {
    /**
     * Checks a project for test coverage.
     *
     * @param project a path to project
     * @return percent of code covered with tests
     * @throws InterruptedException         if the task was interrupted
     * @throws ParserConfigurationException if failed apply a configuration
     * @throws IOException                  if an I/O error occurred
     * @throws SAXException                 if a parsing error occurred
     */
    public double check(
        Path project
    ) throws IOException, ParserConfigurationException, SAXException {
        runTests(project);
        return readCoverage(project);
    }

    /**
     * Run projects tests.
     *
     * @param project a path to the testing project
     */
    private void runTests(Path project) {
        var connector = GradleConnector.newConnector();
        connector.forProjectDirectory(project.toFile());

        try (var connection = connector.connect()) {
            var launcher = connection.newBuild();

            launcher.forTasks("test", "jacocoTestReport");
            launcher.run();
        }
    }

    /**
     * Reads the test coverage percent for the project.
     *
     * @param project a path to the project
     * @return percent of code covered with tests
     * @throws ParserConfigurationException if failed apply a configuration
     * @throws IOException                  if an I/O error occurred
     * @throws SAXException                 if a parsing error occurred
     */
    private double readCoverage(
        Path project
    ) throws IOException, ParserConfigurationException, SAXException {
        var reportPath = project.resolve("build/reports/jacoco/test/jacocoTestReport.xml");
        if (!Files.exists(reportPath)) {
            throw new FileNotFoundException("Report file not found");
        }

        var report = loadReport(reportPath);
        report.getDocumentElement().normalize();

        var root = report.getDocumentElement();
        var children = root.getChildNodes();
        double covered = 0;
        double missed = 0;

        for (var i = 0; i < children.getLength(); i++) {
            var child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("counter")) {
                var type = child.getAttributes().getNamedItem("type").getNodeValue();
                if (type.equals("INSTRUCTION")) {
                    covered = Double.parseDouble(
                        child.getAttributes().getNamedItem("covered").getNodeValue()
                    );
                    missed = Double.parseDouble(
                        child.getAttributes().getNamedItem("missed").getNodeValue()
                    );
                    break;
                }
            }
        }

        var total = missed + covered;
        return total == 0 ? 0.0 : (covered * 100.0) / total;
    }

    /**
     * Loads the JaCoCo report document.
     *
     * @param report a path to the report document
     * @return a JaCoCo report
     * @throws ParserConfigurationException if failed apply a configuration
     * @throws IOException                  if an I/O error occurred
     * @throws SAXException                 if a parsing error occurred
     */
    private Document loadReport(
        Path report
    ) throws ParserConfigurationException, IOException, SAXException {
        var factory = DocumentBuilderFactory.newInstance();

        factory.setFeature(
            "http://xml.org/sax/features/external-general-entities",
            false
        );
        factory.setFeature(
            "http://xml.org/sax/features/external-parameter-entities",
            false
        );
        factory.setFeature(
            "http://apache.org/xml/features/nonvalidating/load-external-dtd",
            false
        );
        factory.setXIncludeAware(false);

        var builder = factory.newDocumentBuilder();
        builder.setEntityResolver((publicId, systemId) ->
            new org.xml.sax.InputSource(new java.io.StringReader(""))
        );

        return builder.parse(report.toFile());
    }
}
