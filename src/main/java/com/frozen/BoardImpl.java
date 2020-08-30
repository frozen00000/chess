package com.frozen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Encapsulates logic that is related to chess Board.
 * Contains positions of all pieces, provides API for moving and retrieving current positions.
 */
class BoardImpl implements Board {

    static final byte SIZE = 8;
    private final PieceFactory pieceFactory;
    private final Piece[][] cells;
    private final Set<Point> whitePieces = new HashSet<>(); // Set of points of white pieces
    private final Set<Point> blackPieces = new HashSet<>(); // Set of points of black pieces
    private Point whiteKingPoint; // Point of white king
    private Point blackKingPoint; // Point of black king

    // Stores flags that means whether this is initial move of piece or not.
    // True - this is initial position, False - otherwise.
    // For example Pawn can make step forward 2 cells from initial position.
    private boolean[][] initialPositions;

    /**
     * Constructor for Board implementation.
     * Board can be initialized with default set of pieces.
     * @param pieceFactory factory for piece creation.
     * @param defaultInit flag for specifying whether default initialization is required.
     */
    BoardImpl(PieceFactory pieceFactory, boolean defaultInit) {
        Objects.requireNonNull(pieceFactory);
        this.pieceFactory = pieceFactory;
        cells = new Piece[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            cells[x] = new Piece[SIZE];
            // Instead of using null, let's use EmptyPiece. It allows us to get rid of multiple null-checks
            // in business logic and as a result simplify it.
            Arrays.fill(cells[x], EmptyPiece.INSTANCE);
        }
        if (defaultInit) {
            defaultInit();
        }
    }

    private void defaultInit() {
        for (byte y = 2; y < SIZE - 2; y++) {
            for (byte x = 0; x < SIZE; x++) {
                set(Point.of(x, y), EmptyPiece.INSTANCE);
            }
        }
        for (byte x = 0; x < SIZE; x++) {
            set(Point.of(x, (byte) 1), pieceFactory.create(PieceType.PAWN, Color.BLACK));
            set(Point.of(x, (byte) 6), pieceFactory.create(PieceType.PAWN, Color.WHITE));
        }
        initPiecesRow((byte) 0, Color.BLACK);
        initPiecesRow((byte) 7, Color.WHITE);
        initialPositions = new boolean[4][SIZE];
        for (int y = 0; y < initialPositions.length; y++) {
            initialPositions[y] = new boolean[SIZE];
            Arrays.fill(initialPositions[y], true);
        }
    }

    private void initPiecesRow(byte y, Color color) {
        set(Point.of((byte) 0, y), pieceFactory.create(PieceType.ROOK, color));
        set(Point.of((byte) 7, y), pieceFactory.create(PieceType.ROOK, color));
        set(Point.of((byte) 1, y), pieceFactory.create(PieceType.KNIGHT, color));
        set(Point.of((byte) 6, y), pieceFactory.create(PieceType.KNIGHT, color));
        set(Point.of((byte) 5, y), pieceFactory.create(PieceType.BISHOP, color));
        set(Point.of((byte) 2, y), pieceFactory.create(PieceType.BISHOP, color));
        set(Point.of((byte) 3, y), pieceFactory.create(PieceType.QUEEN, color));
        set(Point.of((byte) 4, y), pieceFactory.create(PieceType.KING, color));
    }

    /**
     * Sets piece to point.
     * All changes of cells should be done via this method.
     * It maintains several helper collections and references.
     * @param point point for setting.
     * @param piece piece for setting.
     */
    void set(Point point, Piece piece) {
        cells[point.getY()][point.getX()] = piece;
        if (!piece.isEmpty()) {
            Set<Point> setToAdd = piece.isWhite() ? whitePieces : blackPieces;
            Set<Point> setToRemove = piece.isWhite() ? blackPieces: whitePieces;
            setToAdd.add(point);
            setToRemove.remove(point);
            if (piece.getType() == PieceType.KING) {
                if (piece.isWhite()) {
                    whiteKingPoint = point;
                } else {
                    blackKingPoint = point;
                }
            }
        } else {
            whitePieces.remove(point);
            blackPieces.remove(point);
            if (whiteKingPoint != null && whiteKingPoint.equals(point)) {
                whiteKingPoint = null;
            }
            if (blackKingPoint != null && blackKingPoint.equals(point)) {
                blackKingPoint = null;
            }
        }
    }

    /**
     * Performs simple move of piece.
     * @param move move to perform.
     */
    private void move0(Move move) {
        Piece piece = getPiece(move.getFrom());
        set(move.getTo(), piece);
        set(move.getFrom(), EmptyPiece.INSTANCE);
    }

    @Override
    public MoveResult move(Move move) {
        Color movingColor = getPiece(move.getFrom()).getColor();
        // First of all let's validate this move.
        if (!getAvailablePoints(move.getFrom()).contains(move.getTo()) // Is specified destination reachable from source?
                || tryMove(move, () -> isCheck(movingColor))) { // Does this move lead to check?
            return MoveResult.FAILED;
        }
        move0(move); // Ok, it looks valid.
        unmarkInitialPosition(move.getFrom()); // This position is not longer initial.
        unmarkInitialPosition(move.getTo()); // As well as this one.
        Color enemy = movingColor.opposite();
        if (isCheck(enemy)) {
            if (noAnySafeMove(enemy)) {
                return MoveResult.CHECKMATE;
            }
            return MoveResult.CHECK;
        } else if (noAnySafeMove(enemy)) {
            return MoveResult.STALEMATE;
        }
        return MoveResult.OK;
    }

    /**
     * Returns point of king for specified color.
     * @param color color of king.
     * @return point of king.
     */
    private Point getKingPoint(Color color) {
        Point kingPoint = color.isWhite() ? whiteKingPoint : blackKingPoint;
        if (kingPoint == null) {
            throw new IllegalStateException(String.format("There is no %s King on the board.", color.toString().toLowerCase()));
        }
        return kingPoint;
    }

    private Set<Point> getPiecePoints(Color color) {
        return color.isWhite() ? whitePieces : blackPieces;
    }

    private Set<Point> getAvailablePoints(Point point) {
        return getPiece(point).getAvailablePoints(point, this);
    }

    private Stream<Move> getPossibleMovesStream(Color color) {
        return getPiecePoints(color).parallelStream()
                .flatMap(from -> getAvailablePoints(from).stream().map(to -> new Move(from, to)));
    }

    /**
     * Determines check.
     * If any move's target is king - it is check.
     * @param color color of player against whom verification should be done.
     * @return true if check, false otherwise.
     */
    private boolean isCheck(Color color) {
        Point kingPoint = getKingPoint(color);
        return getPossibleMovesStream(color.opposite())
                .anyMatch(move -> move.getTo().equals(kingPoint));
    }

    /**
     * Verifies that there is no any safe move.
     * Move is unsafe if after that kind is under attack.
     * @param color color of player.
     * @return true if there is no any safe move, false otherwise.
     */
    private boolean noAnySafeMove(Color color) {
        return getPossibleMovesStream(color).collect(Collectors.toList()).stream()
                .noneMatch(move -> tryMove(move, () -> !isCheck(color)));
    }

    /**
     * Template method to make move, invoke checker and rollback move.
     * This method temporary changes the state of board.
     * @param move checking move.
     * @param checker checker function.
     * @return result of checker function.
     */
    private boolean tryMove(Move move, Supplier<Boolean> checker) {
        Piece toPiece = getPiece(move.getTo());
        Piece fromPiece = getPiece(move.getFrom());
        move0(move);
        boolean result = checker.get();
        // rollback last move
        set(move.getTo(), toPiece);
        set(move.getFrom(), fromPiece);
        return result;
    }

    @Override
    public Piece getPiece(Point point) {
        return cells[point.getY()][point.getX()];
    }

    @Override
    public boolean isFirstStep(Point point) {
        int py = point.getY();
        if (initialPositions == null || py > 1 && py < 6) {
            return false;
        }
        int y = point.getY() > 5 ? point.getY() - 4 : point.getY();
        return initialPositions[y][point.getX()];
    }

    private void unmarkInitialPosition(Point point) {
        int py = point.getY();
        if (initialPositions != null && py < 2 || py > 5) {
            int y = point.getY() > 5 ? point.getY() - 4 : point.getY();
            initialPositions[y][point.getX()] = false;
        }
    }

}
