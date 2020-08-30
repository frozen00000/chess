package com.frozen;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestWithInitialBoard {

    private BoardImpl board;

    @Before
    public void before() {
        board = new BoardImpl(new PieceFactoryImpl(), true);
    }

    @Test
    public void testInitialAvailablePointsForWhitePawn() {
        Pawn whitePawn = new Pawn(Color.WHITE);
        assertEquals(Set.of(Point.of('a', '3'), Point.of('a', '4')),
                whitePawn.getAvailablePoints(Point.of('a', '2'), board));
    }

    @Test
    public void testInitialAvailablePointsForBlackPawn() {
        Pawn blackPawn = new Pawn(Color.BLACK);
        assertEquals(Set.of(Point.of('a', '5'), Point.of('a', '6')),
                blackPawn.getAvailablePoints(Point.of('a', '7'), board));
    }

    @Test
    public void testInitialAvailablePointsForRook() {
        assertEquals(Collections.emptySet(), new Rook(Color.WHITE).getPoints(Point.of('a', '1'), board));
    }

    @Test
    public void testInitialAvailablePointsForQueen() {
        assertEquals(Collections.emptySet(), new Queen(Color.WHITE).getPoints(Point.of('d', '1'), board));
    }

    @Test
    public void testInitialAvailablePointsForBishop() {
        assertEquals(Collections.emptySet(), new Bishop(Color.WHITE).getPoints(Point.of('c', '1'), board));
    }

    @Test
    public void testInitialAvailablePointsForKing() {
        assertEquals(Collections.emptySet(), new King(Color.WHITE).getPoints(Point.of('e', '1'), board));
    }

    @Test
    public void testInitialAvailablePointsForKnight() {
        assertEquals(Set.of(Point.of('a', '3'), Point.of('c', '3')),
                new Knight(Color.WHITE).getPoints(Point.of('b', '1'), board));
    }
}
