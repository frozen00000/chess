package com.frozen;

public class PieceFactoryImpl implements PieceFactory {

    @Override
    public Piece create(PieceType type, Color color) {
        switch (type) {
            case EMPTY: return EmptyPiece.INSTANCE;
            case KING: return new King(color);
            case QUEEN: return new Queen(color);
            case BISHOP: return new Bishop(color);
            case KNIGHT: return new Knight(color);
            case ROOK: return new Rook(color);
            case PAWN: return new Pawn(color);
            default: throw new UnsupportedOperationException(String.format("Type '%s' is not supported.", type.toString()));
        }
    }

}
