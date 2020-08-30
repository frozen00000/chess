package com.frozen;

import java.util.List;
import java.util.Set;

class Queen extends AbstractPiece {

    private static final char WHITE_SYMBOL = '♕';
    private static final char BLACK_SYMBOL = '♛';

    Queen(Color color) {
        super(PieceType.QUEEN, color);
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
                List.of(Point::down, Point::up, Point::left, Point::right,
                        Point::downAndLeft, Point::downAndRight, Point::upAndLeft, Point::upAndRight));
    }

}
