package ru.nsu.g.solovev5.m.task112.game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextInputTest {
    ByteArrayInputStream inputBytes;
    ByteArrayOutputStream outputBytes;
    PrintStream stream;
    TextInput input;

    @BeforeEach
    void setUp() {
        outputBytes = new ByteArrayOutputStream();
        stream = new PrintStream(outputBytes, true, StandardCharsets.UTF_8);
    }

    @Test
    void nextLine() {
        var line = "Hello there".getBytes(StandardCharsets.UTF_8);
        inputBytes = new ByteArrayInputStream(line);
        input = new TextInput(inputBytes, stream);

        var inputted = input.nextLine("A line request");
        var expectedInput = "Hello there";
        assertEquals(expectedInput, inputted);

        var outputted = outputBytes.toString();
        var expectedOutput = "A line request >>> ";
        assertEquals(expectedOutput, outputted);
    }

    @Test
    void checkDecisionAccept() {
        var line = "1".getBytes(StandardCharsets.UTF_8);
        inputBytes = new ByteArrayInputStream(line);
        input = new TextInput(inputBytes, stream);

        var inputted = input.nextBooleanDecision("A decision request");
        assertTrue(inputted);

        var outputted = outputBytes.toString();
        var expectedOutput = "A decision request (1/0) >>> ";
        assertEquals(expectedOutput, outputted);
    }

    @Test
    void checkDecisionDecline() {
        var line = "0".getBytes(StandardCharsets.UTF_8);
        inputBytes = new ByteArrayInputStream(line);
        input = new TextInput(inputBytes, stream);

        var inputted = input.nextBooleanDecision("A decision request");
        assertFalse(inputted);

        var outputted = outputBytes.toString();
        var expectedOutput = "A decision request (1/0) >>> ";
        assertEquals(expectedOutput, outputted);
    }

    @Test
    void checkDecisionError() {
        var line = "Test 4 lol".getBytes(StandardCharsets.UTF_8);
        inputBytes = new ByteArrayInputStream(line);
        input = new TextInput(inputBytes, stream);

        var inputted = input.nextBooleanDecision("A decision request");
        assertFalse(inputted);

        var outputted = outputBytes.toString();
        var expectedOutput = """
                A decision request (1/0) >>> \
                Пожалуйста, введите одну цифру:
                \t1, чтобы подтвердить
                \t0, чтобы отказаться
                A decision request (1/0) >>> \
                Недоступная опция
                A decision request (1/0) >>> \
                Пожалуйста, введите одну цифру:
                \t1, чтобы подтвердить
                \t0, чтобы отказаться
                A decision request (1/0) >>>\s""";
        assertEquals(expectedOutput, outputted);
    }

    @Test
    void checkClosing() {
        var line = "".getBytes(StandardCharsets.UTF_8);
        inputBytes = new ByteArrayInputStream(line);
        input = new TextInput(inputBytes, stream);

        assertDoesNotThrow(() -> input.close());
    }
}