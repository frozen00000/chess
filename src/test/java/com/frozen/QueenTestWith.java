package com.frozen;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class QueenTestWith extends TestWithBoard {

    private final Queen queen = new Queen(Color.WHITE);

    @Test
    public void testCommonMoves() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('c', '3'), queen);
        assertEquals(Set.of(
                // vertical line
                Point.of('c', '1'),
                Point.of('c', '2'),
                Point.of('c', '4'),
                Point.of('c', '5'),
                Point.of('c', '6'),
                Point.of('c', '7'),
                Point.of('c', '8'),

                // horizontal line
                Point.of('a', '3'),
                Point.of('b', '3'),
                Point.of('d', '3'),
                Point.of('e', '3'),
                Point.of('f', '3'),
                Point.of('g', '3'),
                Point.of('h', '3'),

                // diagonal
                Point.of('a', '1'),
                Point.of('b', '2'),
                Point.of('d', '4'),
                Point.of('e', '5'),
                Point.of('f', '6'),
                Point.of('g', '7'),
                Point.of('h', '8'),

                // diagonal
                Point.of('b', '4'),
                Point.of('a', '5'),
                Point.of('d', '2'),
                Point.of('e', '1')
        ), queen.getPoints(Point.of('c', '3'), board));
    }
}