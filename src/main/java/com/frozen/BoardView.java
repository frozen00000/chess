package com.frozen;

/**
 * Read-only interface for chess Board.
 * It contains API only for reading.
 * It should not contains any modification methods.
 */
interface BoardView {

    /**
     * Returns piece for specified point.
     * @param point point of required piece.
     * @return piece for specified point.
     */
    Piece getPiece(Point point);

    /**
     * Verifies whether position at specified point is initial. It means that piece
     * stays here from the start of the game.
     * @param point point for verification.
     * @return true if position at specified point is initial, false otherwise.
     */
    boolean isFirstStep(Point point);

}
