package com.frozen;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BishopTestWith extends TestWithBoard {

    private final Bishop bishop = new Bishop(Color.WHITE);

    @Test
    public void getMoves() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('c', '3'), bishop);
        assertEquals(Set.of(
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
        ), bishop.getAvailablePoints(Point.of('c', '3'), board));
    }
}