package com.frozen;

import java.util.Collections;
import java.util.Set;

/**
 * This piece is used for empty cells.
 */
class EmptyPiece implements Piece {

    static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {}

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public Color getColor() {
        return Color.NONE;
    }

    @Override
    public char getSymbol() {
        return '　';
    }

    @Override
    public Set<Point> getAvailablePoints(Point currentPoint, BoardView board) {
        return Collections.emptySet();
    }

}
