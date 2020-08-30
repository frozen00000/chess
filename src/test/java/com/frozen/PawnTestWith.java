package com.frozen;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PawnTestWith extends TestWithBoard {

    private final Pawn whitePawn = new Pawn(Color.WHITE);
    private final Pawn blackPawn = new Pawn(Color.BLACK);

    @Test
    public void testPawnAttack() {
        board.set(Point.of('a', '3'), blackPawn);
        board.set(Point.of('c', '3'), blackPawn);
        board.set(Point.of('b', '2'), whitePawn);
        board.set(Point.of('b', '4'), whitePawn);
        assertEquals(Set.of(
                Point.of('b', '3'),
                Point.of('c', '3'),
                Point.of('a', '3')
        ), whitePawn.getAvailablePoints(Point.of('b', '2'), board));
    }

    @Test
    public void testNoAvailableMoves() {
        board.set(Point.of('a', '3'), whitePawn);
        board.set(Point.of('a', '2'), whitePawn);
        assertEquals(Set.of(), whitePawn.getAvailablePoints(Point.of('a', '2'), board));
    }

    @Test
    public void testNotFirstStepOfPawn() {
        board = new BoardImpl(new PieceFactoryImpl(), true);
        board.move(new Move("a2 a3"));
//        board.move(new Move("d7 d6"));
        assertEquals(Set.of(Point.of('a', '4')), whitePawn.getAvailablePoints(Point.of('a', '3'), board));
        assertEquals(Set.of(Point.of('e', '6'), Point.of('e', '5')),
                blackPawn.getAvailablePoints(Point.of('e', '7'), board));

//        assertTrue(board.isFirstStep(Point.of('e', '7')));
    }

}