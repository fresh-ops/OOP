package ru.nsu.g.solovev5.m.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SearcherTest {
    @ParameterizedTest
    @MethodSource
    void checkFind(String message, String word, String fileContent, List<Long> occurrences) {
        try (var writer = new BufferedWriter(new FileWriter("test.txt"))) {
            writer.write(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals(
                occurrences, Searcher.find("test.txt", word),
                message
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkLargeFiles(String message, String word, int occurrenceNumber, long minLength) {
        var occ = generateLargeFile("test.txt", word, occurrenceNumber, minLength);

        try {
            assertEquals(
                occ, Searcher.find("test.txt", word),
                message
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    static List<Long> generateLargeFile(String filename, String word, int occurrenceNumber,
                                        long minLength) {
        var occurrences = new ArrayList<Long>();
        var random = new Random();
        var position = 0L;
        try (var writer = new BufferedWriter(new FileWriter(filename))) {
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
        } catch (IOException e) {
            System.out.println("An exception occurred while generating a test file: "
                + e.getMessage());
            return null;
        }

        return occurrences;
    }
}