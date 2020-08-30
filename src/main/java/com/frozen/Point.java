package com.frozen;

import lombok.Value;

import java.util.Optional;

import static com.frozen.BoardImpl.SIZE;

/**
 * This class represents point on the board.
 * There are several static method to obtain an instance of Point.
 *
 */
@Value
class Point {

    // According to the size of board we know that there will be SIZE*SIZE points.
    // Point is immutable object and probably it would be better to reuse them instead of frequent instantiation.
    // Such cache should reduce load of GC.
    private static final Point[][] CACHE;

    static {
        CACHE = new Point[SIZE][SIZE];
        for (byte i = 0; i < SIZE; i++) {
            CACHE[i] = new Point[SIZE];
        }
        for (byte y = 0; y < SIZE; y++) {
            for (byte x = 0; x < SIZE; x++) {
                CACHE[y][x] = new Point(x, y);
            }
        }
    }

    private final byte x;
    private final byte y;

    /**
     * Retrieve instance of Point with specified coordinates.
     * Expected format is [a-h]{1}[1-8]{1} For example: "a1"
     * @param point coordinates.
     * @return instance of Point.
     */
    static Point of(String point) {
        if (point.length() != 2) {
            throw new IllegalArgumentException("Unexpected input format: " + point);
        }
        return Point.of(point.charAt(0), point.charAt(1));
    }

    /**
     * Retrieve instance of Point with specified coordinates.
     * @param x Coordinate a-h.
     * @param y Coordinate 1-8.
     * @return instance of Point.
     */
    static Point of(char x, char y) {
        if (x < 'a' || x > 'h') {
            throw new IllegalArgumentException("Unexpected x: " + x);
        }
        if (y < '1' || y > '8') {
            throw new IllegalArgumentException("Unexpected y: " + y);
        }
        return CACHE[7 + ('1' - y)][x - 'a'];
    }

    /**
     * Retrieve instance of Point with specified coordinates.
     * @param x Coordinate 0-7.
     * @param y Coordinate 0-7.
     * @return instance of Point.
     */
    static Point of(byte x, byte y) {
        if (isNotValid(x)) {
            throw new IllegalArgumentException("Unexpected x: " + x);
        }
        if (isNotValid(y)) {
            throw new IllegalArgumentException("Unexpected y: " + y);
        }
        return CACHE[y][x];
    }

    private Point(byte x, byte y) {
        this.x = x;
        this.y = y;
    }

    private static boolean isNotValid(int position) {
        return position < 0 || position >= SIZE;
    }

    private Optional<Point> getPoint(int x, int y) {
        if (isNotValid(x) || isNotValid(y)) {
            return Optional.empty();
        }
        return Optional.of(CACHE[y][x]);
    }


    // Following methods are used as directions in the moving logic.
    // Pieces declare directions that are valid by using this methods.
    // If next point according to direction is out of board, then empty optional will be returned.

    // Common directions

    Optional<Point> up() {
        return getPoint(x, y - 1);
    }

    Optional<Point> down() {
        return getPoint(x, y + 1);
    }

    Optional<Point> left() {
        return getPoint(x - 1, y);
    }

    Optional<Point> right() {
        return getPoint(x + 1, y);
    }

    Optional<Point> upAndLeft() {
        return getPoint(x - 1, y - 1);
    }

    Optional<Point> upAndRight() {
        return getPoint(x + 1, y - 1);
    }

    Optional<Point> downAndLeft() {
        return getPoint(x - 1, y + 1);
    }

    Optional<Point> downAndRight() {
        return getPoint(x + 1, y + 1);
    }

    // Directions for Knight

    Optional<Point> lUpAndRight() {
        return getPoint(x + 1, y - 2);
    }

    Optional<Point> lUpAndLeft() {
        return getPoint(x - 1, y - 2);
    }

    Optional<Point> lRightAndUp() {
        return getPoint(x + 2, y - 1);
    }

    Optional<Point> lRightAndDown() {
        return getPoint(x + 2, y + 1);
    }

    Optional<Point> lDownAndRight() {
        return getPoint(x + 1, y + 2);
    }

    Optional<Point> lDownAndLeft() {
        return getPoint(x - 1, y + 2);
    }

    Optional<Point> lLeftAndDown() {
        return getPoint(x - 2, y + 1);
    }

    Optional<Point> lLeftAndUp() {
        return getPoint(x - 2, y - 1);
    }

}

