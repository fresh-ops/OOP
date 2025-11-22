package ru.nsu.g.solovev5.m.task131;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SearcherTest {
    @ParameterizedTest
    @MethodSource
    void checkFind(String message, String word, String fileContent, List<Long> occurrences,
                   @TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, fileContent);

        assertDoesNotThrow(
            () -> assertEquals(
                occurrences, Searcher.find(testFile.toString(), word),
                message
            )
        );
    }

    static Stream<Arguments> checkFind() {
        return Stream.of(
            Arguments.of(
                "Single occurrence", "Word",
                "This sentence contains a Word",
                List.of(25L)
            ),
            Arguments.of(
                "No occurrence", "Test",
                "No occurrences in this file",
                List.of()
            ),
            Arguments.of(
                "Multiple occurrences", "l",
                "Hello world",
                List.of(2L, 3L, 9L)
            ),
            Arguments.of(
                "Collisions", "ababa",
                "ababababa",
                List.of(0L, 2L, 4L)
            ),
            Arguments.of(
                "Repeated pattern", "lyalya",
                "lyalyalyalya",
                List.of(0L, 3L, 6L)
            ),
            Arguments.of(
                "Chinese character", "中",
                "Hello 中 world 中 test",
                List.of(6L, 14L)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkLargeFiles(String message, String word, int occurrenceNumber, long minLength,
                         @TempDir Path tempDir) throws IOException {
        var largeTestFile = tempDir.resolve("large-test.txt");
        var occ = generateLargeFile(largeTestFile, word, occurrenceNumber, minLength);

        assertDoesNotThrow(
            () -> assertEquals(
                occ, Searcher.find(largeTestFile.toString(), word),
                message
            )
        );
    }

    static Stream<Arguments> checkLargeFiles() {
        return Stream.of(
            Arguments.of(
                "Very much occurrences", "Match me", 1_000_000, 0
            ),
            Arguments.of(
                "Long search word", "pneumonoultramicroscopicsilicovolcanoconiosis ", 1_000, 5_000
            ),
            Arguments.of(
                "A very large file", "abacab", 10, 4_294_967_296L
            )
        );
    }

    static List<Long> generateLargeFile(Path file, String word, int occurrenceNumber,
                                        long minLength) throws IOException {
        var occurrences = new ArrayList<Long>();
        var random = new Random();
        var position = 0L;
        try (var writer = Files.newBufferedWriter(file)) {
            while (occurrenceNumber > 0) {
                if (random.nextDouble() > 0.9) {
                    occurrences.add(position);
                    writer.write(word);
                    position += word.length();
                    occurrenceNumber--;
                } else {
                    writer.write(random.nextInt(32, 126));
                    position++;
                }
            }

            while (position < minLength) {
                writer.write(random.nextInt(32, 126));
                position++;
            }
        }

        return occurrences;
    }
}