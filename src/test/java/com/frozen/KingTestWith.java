package com.frozen;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class KingTestWith extends TestWithBoard {

    private final King king = new King(Color.WHITE);

    @Test
    public void testCommonMoves() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('c', '3'), king);
        assertEquals(Set.of(
                // vertical line
                Point.of('c', '2'),
                Point.of('c', '4'),

                // horizontal line
                Point.of('b', '3'),
                Point.of('d', '3'),

                // diagonal
                Point.of('b', '2'),
                Point.of('d', '4'),

                // diagonal
                Point.of('b', '4'),
                Point.of('d', '2')
        ), king.getPoints(Point.of('c', '3'), board));
    }
}