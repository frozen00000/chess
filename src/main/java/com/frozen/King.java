package com.frozen;

import java.util.List;
import java.util.Set;

class King extends AbstractPiece {

    private static final char WHITE_SYMBOL = '♔';
    private static final char BLACK_SYMBOL = '♚';

    King(Color color) {
        super(PieceType.KING, color);
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
        return commonAvailableWithLimit1(currentPoint, board,
                List.of(Point::down, Point::up, Point::left, Point::right,
                        Point::downAndLeft, Point::downAndRight, Point::upAndLeft, Point::upAndRight));
        //TODO add castling
    }
}
