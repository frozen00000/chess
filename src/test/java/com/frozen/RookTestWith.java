package com.frozen;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RookTestWith extends TestWithBoard {

    private final Rook rook = new Rook(Color.WHITE);

    @Test
    public void testCommonMoves() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('b', '2'), rook);
        assertEquals(Set.of(
                Point.of('b', '1'),
                Point.of('b', '3'),
                Point.of('b', '4'),
                Point.of('b', '5'),
                Point.of('b', '6'),
                Point.of('b', '7'),
                Point.of('b', '8'),
                Point.of('a', '2'),
                Point.of('c', '2'),
                Point.of('d', '2'),
                Point.of('e', '2'),
                Point.of('f', '2'),
                Point.of('g', '2'),
                Point.of('h', '2')
        ), rook.getPoints(Point.of('b', '2'), board));
    }

    @Test
    public void testBlockedDirection() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('b', '4'), new Rook(Color.BLACK));
        board.set(Point.of('b', '2'), rook);
        assertEquals(Set.of(
                Point.of('b', '1'),
                Point.of('b', '3'),
                Point.of('b', '4'),
                Point.of('a', '2'),
                Point.of('c', '2'),
                Point.of('d', '2'),
                Point.of('e', '2'),
                Point.of('f', '2'),
                Point.of('g', '2'),
                Point.of('h', '2')
        ), rook.getPoints(Point.of('b', '2'), board));
    }
}