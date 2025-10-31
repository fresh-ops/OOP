package ru.nsu.g.solovev5.m.task122;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HashTableTest {
    HashTable<String, Number> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @ParameterizedTest
    @MethodSource
    void checkPuttingElements(String message,
                              List<HashTable.Entry<String, Number>> entries) {
        assertEquals(
            0, table.size(),
            message + ". An empty table should have a zero size"
        );

        for (var entry : entries) {
            assertFalse(
                table.has(entry.getKey()),
                message + ". A table shouldn't contain an entry before adding"
            );
            assertNull(
                table.get(entry.getKey()),
                message + ". A not stated key should be associated with null"
            );
            assertDoesNotThrow(
                () -> table.put(entry.getKey(), entry.getValue()),
                message + ". Putting an entry shouldn't cause any exception"
            );
            assertTrue(
                table.has(entry.getKey()),
                message + ". A table should contain an entry after adding"
            );
        }

        assertEquals(
            entries.size(), table.size(),
            message + ". A table size should be same as an entries count"
        );

        for (var entry : entries) {
            assertTrue(
                table.has(entry.getKey()),
                message + ". A table should contain all entries after adding"
            );
            assertEquals(
                entry.getValue(), table.get(entry.getKey()),
                message + ". A containing value shouldn't change"
            );
        }
    }

    static Stream<Arguments> checkPuttingElements() {
        var one = new HashTable.Entry<String, Number>("One", 1);
        var two = new HashTable.Entry<String, Number>("Two", 2);
        var three = new HashTable.Entry<String, Number>("Three", 3);

        var nullKey = new HashTable.Entry<String, Number>(null, 0);
        var nullValue = new HashTable.Entry<String, Number>("Null Value", null);

        return Stream.of(
            Arguments.of(
                "Single item", List.of(one)
            ),
            Arguments.of(
                "Multiple items", List.of(one, two, three)
            ),
            Arguments.of(
                "Null fields", List.of(one, nullKey, two, nullValue)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkUpdatingElements(String message,
                               List<HashTable.Entry<String, Number>> entries,
                               List<HashTable.Entry<String, Number>> replacements) {
        for (var entry : entries) {
            table.put(entry.getKey(), entry.getValue());
        }

        for (var replacement : replacements) {
            assertDoesNotThrow(
                () -> table.put(replacement.getKey(), replacement.getValue()),
                message + ". Replacing an element shouldn't cause any exception"
            );
            assertEquals(
                replacement.getValue(), table.get(replacement.getKey()),
                message + ". After replacing a new value should be presented"
            );
        }

        assertEquals(
            entries.size(), table.size(),
            message + ". A table size should be same as before replacing"
        );
    }

    static Stream<Arguments> checkUpdatingElements() {
        var a1 = new HashTable.Entry<String, Number>("a", 1);
        var a2 = new HashTable.Entry<String, Number>("a", 100);

        var b1 = new HashTable.Entry<String, Number>("b", 2);
        var b2 = new HashTable.Entry<String, Number>("b", 200);
        var b3 = new HashTable.Entry<String, Number>("b", 20_000);

        var c1 = new HashTable.Entry<String, Number>("c", 3);
        var c2 = new HashTable.Entry<String, Number>("c", 34);
        var c3 = new HashTable.Entry<String, Number>("c", 345);
        var c4 = new HashTable.Entry<String, Number>("c", 3_456);

        return Stream.of(
            Arguments.of(
                "A single replacement",
                List.of(a1, b1, c1),
                List.of(a1)
            ),
            Arguments.of(
                "Multiple replacements of one element",
                List.of(a1, b1, c1),
                List.of(c2, c3, c4)
            ),
            Arguments.of(
                "Multiple shuffled replacements",
                List.of(a1, b1, c1),
                List.of(b2, c2, a2, c3, c4, b3)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkRemovingElements(String message,
                               List<HashTable.Entry<String, Number>> entries,
                               List<HashTable.Entry<String, Number>> removings,
                               int correctlyRemoved) {
        for (var entry : entries) {
            table.put(entry.getKey(), entry.getValue());
        }

        for (var removing : removings) {
            assertDoesNotThrow(
                () -> table.remove(removing.getKey()),
                message + ". Removing an element shouldn't cause any exception"
            );
            assertFalse(
                table.has(removing.getKey()),
                message + ". After removing an element shouldn't be in the table"
            );
            assertNull(
                table.get(removing.getKey()),
                message + ". A removed element should null"
            );
        }

        assertEquals(
            entries.size() - correctlyRemoved, table.size(),
            message + ". A removing an element should decrease the table size"
        );
    }

    static Stream<Arguments> checkRemovingElements() {
        var one = new HashTable.Entry<String, Number>("One", 1);
        var two = new HashTable.Entry<String, Number>("Two", 2);
        var three = new HashTable.Entry<String, Number>("Three", 3);

        return Stream.of(
            Arguments.of(
                "Single removing", List.of(one, two, three),
                List.of(one), 1
            ),
            Arguments.of(
                "Removing all", List.of(one, two, three),
                List.of(one, two, three), 3

            ),
            Arguments.of(
                "Removing non-present element", List.of(one, three),
                List.of(two), 0
            )
        );
    }

    @Test
    void checkEquals() {
        var a = new HashTable.Entry<String, Number>("a", 1);
        table.put(a.getKey(), a.getValue());
        var b = new HashTable.Entry<String, Number>("b", 2);
        table.put(b.getKey(), b.getValue());
        var c = new HashTable.Entry<String, Number>("c", 3);
        table.put(c.getKey(), c.getValue());
        var d = new HashTable.Entry<String, Number>("d", 4);
        table.put(d.getKey(), d.getValue());
        var e = new HashTable.Entry<String, Number>("e", 5);
        table.put(e.getKey(), e.getValue());

        var other = new HashTable<String, Number>();
        other.put(a.getKey(), 0);
        other.put(b.getKey(), b.getValue());
        other.put(c.getKey(), c.getValue());
        other.put(d.getKey(), d.getValue());
        other.put(e.getKey(), e.getValue());

        assertNotEquals(
            table, other,
            "Tables with different values at the same key are not equal"
        );

        table.remove(b.getKey());
        table.remove(c.getKey());
        other.remove(a.getKey());

        assertNotEquals(
            table, other,
            "Tables with different keys are not equal"
        );

        table.remove(a.getKey());
        other.remove(b.getKey());
        other.remove(c.getKey());

        assertEquals(
            table, other,
            "Tables with the same keys and mapped values are equal"
        );
    }
}