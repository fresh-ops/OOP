package ru.nsu.g.solovev5.m.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommandLineArgumentsTest {
    @Test
    void parse_should_correctlyInitializeArguments() {
        var args = new String[]{"-c", "config.json"};
        var parsed = CommandLineArguments.parse(args);

        assertEquals(args[1], parsed.getConfigPath());
    }
}