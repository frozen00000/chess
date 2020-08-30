package com.frozen;

import java.util.Set;

/**
 * General interface for Piece entity.
 * It contains some common methods that should be supported by each instance of piece.
 */
interface Piece {

    /**
     * Each piece has one of 7 types.
     * @return type of
     */
    PieceType getType();

    /**
     * Each piece has color.
     * @return color of piece.
     */
    Color getColor();

    /**
     * Each piece has unicode symbol. It depends on type and color.
     * @return unicode symbol.
     */
    char getSymbol();

    /**
     * Retrieves set of points that a piece can reach from current point/board by 1 move.
     * @param currentPoint initial location of piece.
     * @param board board with other pieces.
     * @return set of available points.
     */
    Set<Point> getAvailablePoints(Point currentPoint, BoardView board);

    /**
     * Checks whether the type of piece is EMPTY.
     * @return true if type is EMPTY, false otherwise.
     */
    default boolean isEmpty() {
        return getType() == PieceType.EMPTY;
    }

    /**
     * Checks whether the color of piece is white.
     * @return true if color is white, false otherwise.
     */
    default boolean isWhite() {
        return getColor().isWhite();
    }

    /**
     * Checks whether the specified piece is an enemy for this one.
     * @param piece piece for checking.
     * @return true if specified piece is an enemy, false otherwise.
     */
    default boolean isEnemy(Piece piece) {
        if (getType() == PieceType.EMPTY || piece.getType() == PieceType.EMPTY) {
            return false;
        }
        return getColor() != piece.getColor();
    }
}
