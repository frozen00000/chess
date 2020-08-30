package com.frozen;

import java.util.List;
import java.util.Set;

class Knight extends AbstractPiece {

    private static final char WHITE_SYMBOL = '♘';
    private static final char BLACK_SYMBOL = '♞';

    Knight(Color color) {
        super(PieceType.KNIGHT, color);
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
                List.of(Point::lDownAndLeft, Point::lDownAndRight, Point::lLeftAndDown, Point::lLeftAndUp,
                        Point::lRightAndDown, Point::lRightAndUp, Point::lUpAndLeft, Point::lUpAndRight));
    }
}
