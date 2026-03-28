package ru.nsu.g.solovev5.m.task231.domain.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CollisionServiceTest {
    @Test
    void collides_should_returnTrue_when_passedSamePoint() {
        var point = new Point2D(0, 0);

        assertTrue(CollisionService.collides(point, point));
    }

    @ParameterizedTest
    @MethodSource("generateEqualPoints")
    void collides_should_returnTrue_when_passedEqualPoints(Point2D a, Point2D b) {
        assertTrue(CollisionService.collides(a, b));
    }

    @ParameterizedTest
    @MethodSource("generateDifferentPoints")
    void collides_should_returnFalse_when_passedDifferentPoints(Point2D a, Point2D b) {
        assertFalse(CollisionService.collides(a, b));
    }

    @ParameterizedTest
    @MethodSource("generateSetAndPointFromIt")
    void collides_should_returnTrue_when_pointIsInSet(List<Point2D> points, Point2D point) {
        assertTrue(CollisionService.collides(points, point));
    }

    @ParameterizedTest
    @MethodSource("generateSetAndPointNotIn")
    void collides_should_returnFalse_when_pointIsNotInSet(List<Point2D> points, Point2D point) {
        assertFalse(CollisionService.collides(points, point));
    }

    @ParameterizedTest
    @MethodSource("generateCollidingSets")
    void collides_should_returnTrue_when_setsColliding(List<Point2D> a, List<Point2D> b) {
        assertTrue(CollisionService.collides(a, b));
    }

    @ParameterizedTest
    @MethodSource("generateNonCollidingSets")
    void collides_should_returnFalse_when_setsNotColliding(List<Point2D> a, List<Point2D> b) {
        assertFalse(CollisionService.collides(a, b));
    }

    @Test
    void collides_should_returnFalse_when_setIsEmpty() {
        assertFalse(CollisionService.collides(List.of(), new Point2D(0, 0)));
    }

    @Test
    void collides_should_returnFalse_when_firstSetIsEmpty() {
        assertFalse(CollisionService.collides(List.of(), List.of(new Point2D(7, 6))));
    }

    @Test
    void collides_should_returnFalse_when_secondSetIsEmpty() {
        assertFalse(CollisionService.collides(List.of(new Point2D(2, 1)), List.of()));
    }

    @Test
    void collides_should_returnFalse_when_bothSetsAreEmpty() {
        assertFalse(CollisionService.collides(List.of(), List.of()));
    }

    @Test
    void collides_should_throwException_when_firstPointIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides((Point2D) null, new Point2D(0, 0))
        );
    }

    @Test
    void collides_should_throwException_when_secondPointIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides(new Point2D(0, 0), (Point2D) null)
        );
    }

    @Test
    void collides_should_throwException_when_bothPointsAreNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides((Point2D) null, null)
        );
    }

    @Test
    void collides_should_throwException_when_SetIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides((List<Point2D>) null, new Point2D(0, 0))
        );
    }

    @Test
    void collides_should_throwException_when_singlePointIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides(List.of(new Point2D(0, 0)), (Point2D) null)
        );
    }

    @Test
    void collides_should_throwException_when_setAndPointAreNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides((List<Point2D>) null, (Point2D) null)
        );
    }

    @Test
    void collides_should_throwException_when_firstSetIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides((List<Point2D>) null, List.of(new Point2D(0, 0)))
        );
    }

    @Test
    void collides_should_throwException_when_secondSetIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides(List.of(new Point2D(0, 0)), (List<Point2D>) null)
        );
    }

    @Test
    void collides_should_throwException_when_bothSetsAreNull() {
        assertThrows(
            NullPointerException.class,
            () -> CollisionService.collides((List<Point2D>) null, (List<Point2D>) null)
        );
    }

    static Stream<Arguments> generateEqualPoints() {
        return Stream.of(
            Arguments.of(new Point2D(0, 0), new Point2D(0, 0)),
            Arguments.of(new Point2D(5, 3), new Point2D(5, 3)),
            Arguments.of(new Point2D(2, 9), new Point2D(2, 9)),
            Arguments.of(new Point2D(1, 8), new Point2D(1, 8))
        );
    }

    static Stream<Arguments> generateDifferentPoints() {
        return Stream.of(
            Arguments.of(new Point2D(0, 1), new Point2D(0, 0)),
            Arguments.of(new Point2D(4, 4), new Point2D(5, 3)),
            Arguments.of(new Point2D(1, 9), new Point2D(2, 9)),
            Arguments.of(new Point2D(0, 0), new Point2D(1, 8))
        );
    }

    static Stream<Arguments> generateSetAndPointFromIt() {
        var a = new Point2D(0, 0);
        var b = new Point2D(1, 3);
        var c = new Point2D(4, 9);
        var d = new Point2D(6, 9);

        return Stream.of(
            Arguments.of(List.of(a), a),
            Arguments.of(List.of(a, b), a),
            Arguments.of(List.of(a, b, c), c),
            Arguments.of(List.of(a, b, c, d), b)
        );
    }

    static Stream<Arguments> generateSetAndPointNotIn() {
        var a = new Point2D(0, 0);
        var b = new Point2D(1, 3);
        var c = new Point2D(4, 9);
        var d = new Point2D(6, 9);

        var out = new Point2D(4, 4);

        return Stream.of(
            Arguments.of(List.of(a), out),
            Arguments.of(List.of(a, b), out),
            Arguments.of(List.of(a, b, c), out),
            Arguments.of(List.of(a, b, c, d), out)
        );
    }

    static Stream<Arguments> generateCollidingSets() {
        return Stream.of(
            Arguments.of(
                List.of(new Point2D(1, 1), new Point2D(2, 2)),
                List.of(new Point2D(2, 2), new Point2D(3, 3))
            ),
            Arguments.of(
                List.of(new Point2D(1, 1), new Point2D(2, 2), new Point2D(3, 3)),
                List.of(new Point2D(2, 2), new Point2D(3, 3), new Point2D(4, 4))
            ),
            Arguments.of(
                List.of(new Point2D(1, 1), new Point2D(2, 2), new Point2D(3, 3)),
                List.of(new Point2D(2, 2))
            ),
            Arguments.of(
                List.of(new Point2D(2, 2)),
                List.of(new Point2D(1, 1), new Point2D(2, 2), new Point2D(3, 3))
            ),
            Arguments.of(
                List.of(new Point2D(5, 5), new Point2D(6, 6)),
                List.of(new Point2D(5, 5), new Point2D(6, 6))
            ),
            Arguments.of(
                List.of(new Point2D(-1, -1), new Point2D(0, 0)),
                List.of(new Point2D(0, 0), new Point2D(1, 1))
            ),
            Arguments.of(
                List.of(new Point2D(1000, 1000), new Point2D(2000, 2000)),
                List.of(new Point2D(2000, 2000), new Point2D(3000, 3000))
            )
        );
    }

    static Stream<Arguments> generateNonCollidingSets() {
        return Stream.of(
            Arguments.of(
                List.of(new Point2D(1, 1), new Point2D(2, 2)),
                List.of(new Point2D(3, 3), new Point2D(4, 4))
            ),
            Arguments.of(
                List.of(new Point2D(1, 1)),
                List.of(new Point2D(2, 2))
            ),
            Arguments.of(
                List.of(new Point2D(1, 1), new Point2D(2, 2), new Point2D(3, 3)),
                List.of(new Point2D(4, 4), new Point2D(5, 5))
            ),
            Arguments.of(
                List.of(new Point2D(-5, -5), new Point2D(-4, -4)),
                List.of(new Point2D(4, 4), new Point2D(5, 5))
            ),
            Arguments.of(
                List.of(new Point2D(1000, 1000), new Point2D(2000, 2000)),
                List.of(new Point2D(3000, 3000), new Point2D(4000, 4000))
            ),
            Arguments.of(
                List.of(new Point2D(5, 1), new Point2D(5, 2)),
                List.of(new Point2D(5, 3), new Point2D(5, 4))
            ),
            Arguments.of(
                List.of(new Point2D(1, 5), new Point2D(2, 5)),
                List.of(new Point2D(3, 5), new Point2D(4, 5))
            )
        );
    }
}