package com.frozen;

import java.util.List;
import java.util.Set;

class Bishop extends AbstractPiece {

    private static final char WHITE_SYMBOL = '♗';
    private static final char BLACK_SYMBOL = '♝';

    Bishop(Color color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    char getWhiteSymbol() {
        return WHITE_SYMBOL;
    }

    @Override
    char getBlackSymbol() {
        return BLACK_SYMBOL;
    }

    @Override
    Set<Point> getPoints(Point currentPoint, BoardView board) {
        return commonAvailable(currentPoint, board,
                List.of(Point::downAndLeft, Point::upAndLeft, Point::downAndRight, Point::upAndRight));
    }

}
