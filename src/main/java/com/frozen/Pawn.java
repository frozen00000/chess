package com.frozen;

import java.util.List;
import java.util.Set;

class Pawn extends AbstractPiece {

    private static final char WHITE_SYMBOL = '♙';
    private static final char BLACK_SYMBOL = '♟';

    Pawn(Color color) {
        super(PieceType.PAWN, color);
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
        Set<Point> result = commonAvailable(currentPoint, board,
                isWhite() ? List.of(Point::up) : List.of(Point::down),
                board.isFirstStep(currentPoint) ? (short) 2 : (short) 1, Piece::isEmpty);
        result.addAll(commonAvailable(currentPoint, board,
                isWhite() ? List.of(Point::upAndLeft, Point::upAndRight) : List.of(Point::downAndLeft, Point::downAndRight),
                (short) 1, p -> p.isEnemy(this)));
        return result;
    }

}
