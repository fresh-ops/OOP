package ru.nsu.g.solovev5.m.task241.core.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TestCoverageService {
    public double check(
        Path project
    ) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        var process = processBuilder(project).start();
        try (var inputStream = process.getInputStream()) {
            inputStream.transferTo(System.out);
        }

        process.waitFor();
        return readCoverage(project);
    }

    private ProcessBuilder processBuilder(Path project) {
        var isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        var commands = new ArrayList<String>();
        if (isWindows) {
            commands.addAll(List.of("cmd", "/c", "gradlew.bat"));
        } else {
            commands.add("./gradlew");
        }

        commands.addAll(List.of("test", "jacocoTestReport", "-Djacoco.xml=true"));
        return new ProcessBuilder(commands)
            .directory(project.toFile())
            .redirectErrorStream(true);
    }

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
