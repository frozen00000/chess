package com.frozen;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class KnightTestWith extends TestWithBoard {

    private final Knight knight = new Knight(Color.WHITE);

    @Test
    public void testCommonMovies() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('d', '4'), knight);
        assertEquals(Set.of(
                Point.of('e', '6'),
                Point.of('f', '5'),
                Point.of('f', '3'),
                Point.of('e', '2'),
                Point.of('c', '2'),
                Point.of('b', '3'),
                Point.of('b', '5'),
                Point.of('c', '6')
        ), knight.getPoints(Point.of('d', '4'), board));
    }
}