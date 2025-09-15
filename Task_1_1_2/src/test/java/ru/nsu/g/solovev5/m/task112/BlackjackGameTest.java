package ru.nsu.g.solovev5.m.task112;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    @Test
    void checkGame() {
        for (var i = 0; i < 1_000; i++) {
            var inputTest = "test 1 1 0 0 1 wrong 1 0 0 4 3 1 1 0 0 0 0 0 0"
                    .getBytes(StandardCharsets.UTF_8);
            var simulatedInput = new ByteArrayInputStream(inputTest);

            System.setIn(simulatedInput);
            BlackjackGame.main(new String[0]);
        }
    }
}