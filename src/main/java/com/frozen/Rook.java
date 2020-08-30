package com.frozen;

import java.util.List;
import java.util.Set;

class Rook extends AbstractPiece {

    private static final char WHITE_SYMBOL = '♖';
    private static final char BLACK_SYMBOL = '♜';

    Rook(Color color) {
        super(PieceType.ROOK, color);
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
        return commonAvailable(currentPoint, board, List.of(Point::down, Point::up, Point::left, Point::right));
    }

}
