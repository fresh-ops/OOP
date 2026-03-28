package ru.nsu.g.solovev5.m.task231.domain.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * Util class to check collisions between points.
 */
public class CollisionService {
    private CollisionService() {
    }

    /**
     * Checks if points collide.
     *
     * @param a the first point to check
     * @param b the second point to check
     * @return {@code true} if points collide, {@code false} otherwise
     */
    public static boolean collides(Point2D a, Point2D b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return a.equals(b);
    }

    /**
     * Checks if points collide.
     *
     * @param points the set of points
     * @param point  the point to check
     * @return {@code true} if the point collides the set, {@code false} otherwise
     */
    public static boolean collides(List<Point2D> points, Point2D point) {
        Objects.requireNonNull(points);
        Objects.requireNonNull(point);
        return points.contains(point);
    }

    /**
     * Checks if points collide.
     *
     * @param a the set of points to check
     * @param b the set of points to check
     * @return {@code true} if sets collide each other, {@code false} otherwise
     */
    public static boolean collides(List<Point2D> a, List<Point2D> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return !Collections.disjoint(a, b);
    }
}
