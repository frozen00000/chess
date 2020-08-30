package com.frozen;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardImplTest {

    private BoardImpl board;

    @Test
    public void testSimpleCheck() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('e', '8'), new King(Color.BLACK));
        board.set(Point.of('d', '7'), new Rook(Color.WHITE));
        board.set(Point.of('a', '1'), new King(Color.WHITE));
        assertEquals(MoveResult.CHECK, board.move(new Move(Point.of('d', '7'), Point.of('e', '7'))));
    }

    @Test
    public void testSimpleMate() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('e', '8'), new King(Color.BLACK));
        board.set(Point.of('a', '7'), new Rook(Color.WHITE));
        board.set(Point.of('b', '7'), new Rook(Color.WHITE));
        board.set(Point.of('a', '1'), new King(Color.WHITE));
        assertEquals(MoveResult.CHECKMATE, board.move(new Move("a7 a8")));
    }

    @Test
    public void testIncorrectMovesUnderCheck() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('e', '8'), new King(Color.BLACK));
        board.set(Point.of('e', '7'), new Rook(Color.BLACK));
        board.set(Point.of('e', '6'), new Rook(Color.WHITE));
        assertEquals(MoveResult.FAILED, board.move(new Move("e7 a7")));
    }

    @Test
    public void testRookShouldDefence() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('e', '8'), new King(Color.BLACK));
        board.set(Point.of('c', '7'), new Rook(Color.BLACK));
        board.set(Point.of('e', '6'), new Rook(Color.WHITE));
        board.set(Point.of('a', '1'), new King(Color.WHITE));
        assertEquals(MoveResult.FAILED, board.move(new Move("c7 b7")));
        assertEquals(MoveResult.OK, board.move(new Move("c7 e7")));
    }

    @Test
    public void testStalemate() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
        board.set(Point.of('f', '8'), new King(Color.BLACK));
        board.set(Point.of('f', '7'), new Bishop(Color.WHITE));
        board.set(Point.of('f', '5'), new King(Color.WHITE));
        assertEquals(MoveResult.STALEMATE, board.move(new Move("f5 f6")));
    }

}