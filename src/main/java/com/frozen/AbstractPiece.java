package com.frozen;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Abstract implementation of piece.
 * All pieces are immutable.
 */
@EqualsAndHashCode
abstract class AbstractPiece implements Piece {

    private final Color color;
    private final PieceType pieceType;

    AbstractPiece(PieceType pieceType, Color color) {
        this.pieceType = pieceType;
        this.color = color;
    }

    abstract char getWhiteSymbol();

    abstract char getBlackSymbol();

    abstract Set<Point> getPoints(Point currentPoint, BoardView board);

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public char getSymbol() {
        return getColor().isWhite() ? getWhiteSymbol() : getBlackSymbol();
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }

    @Override
    public Set<Point> getAvailablePoints(Point currentPoint, BoardView board) {
        if (!this.equals(board.getPiece(currentPoint))) {
            throw new IllegalStateException("currentPoint does not point to this piece.");
        }
        return getPoints(currentPoint, board);
    }

    Set<Point> commonAvailable(Point currentPoint, BoardView board,
                               List<Function<Point, Optional<Point>>> directions) {
        return commonAvailable(currentPoint, board, directions, Short.MAX_VALUE, null);
    }

    Set<Point> commonAvailableWithLimit1(Point currentPoint, BoardView board,
                               List<Function<Point, Optional<Point>>> directions) {
        return commonAvailable(currentPoint, board, directions, (short) 1, null);
    }

    /**
     * Retrieves set of available points for this piece at particular point/board.
     * The algorithm is similar to breadth-first search.
     * One of the most tricky methods in the application.
     * @param currentPoint initial point.
     * @param board board with other pieces.
     * @param directions list of supported directions for piece.
     * @param limit limit of loops.
     * @param predicate function with piece-specific check.
     * @return set of points that are reachable from current point according to specified restrictions.
     */
    Set<Point> commonAvailable(Point currentPoint, BoardView board,
                                List<Function<Point, Optional<Point>>> directions,
                                short limit, Predicate<Piece> predicate) {
        Set<Point> result = new HashSet<>();
        List<Point> currentPoints = new ArrayList<>(directions.size()); // There should always be a front of expansion.
        for (int i = 0; i < directions.size(); i++) {
            currentPoints.add(currentPoint); // initially this is current point.
        }
        // By several reasons the direction can not be used any more.
        // Following array stores such information about each direction.
        boolean[] disabledDirections = new boolean[directions.size()];
        short countOfDisabled = 0;
        // Some pieces can make only one step from their current point (like King and Pawn). In this case limit
        // has value 1 and count should be greater that limit (we should perform only one iteration).
        short count = 0;
        while (countOfDisabled < disabledDirections.length && count++ < limit) {
            for (var i = 0; i < directions.size(); i++) { // In each iteration we handle all directions.
                if (disabledDirections[i]) { // If direction if disabled then we should not use it.
                    continue;
                }
                var point = currentPoints.get(i); // Each direction has own 'current' point.
                var newPoint = directions.get(i).apply(point); // Get next point according to directions.
                if (newPoint.isPresent()) { // We cat get out of board size. In this case option would be empty.
                    var p = newPoint.get();
                    var piece = board.getPiece(p);
                    if (piece.getColor() != getColor() // Destination cell can be not empty. It is ok if colors are different.
                            && (predicate == null || predicate.test(piece))) { // Calling piece can provide additional condition.
                        result.add(p);
                        currentPoints.set(i, p);
                        if (!piece.isEmpty()) {
                            disabledDirections[i] = true; // in this case we attack an enemy and can not continue direction.
                            countOfDisabled++;
                        }
                    } else {
                        disabledDirections[i] = true; // Colors are same or another special condition.
                        countOfDisabled++;
                    }
                } else {
                    disabledDirections[i] = true; // Out of board, disable direction.
                    countOfDisabled++;
                }
            }
        }
        return result;
    }

}
