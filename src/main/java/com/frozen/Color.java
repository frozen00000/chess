package com.frozen;

/**
 * Enumeration of colors.
 */
enum Color {

    WHITE,
    BLACK,
    NONE; // This one is used for EmptyPiece.

    boolean isWhite() {
        return this == WHITE;
    }

    boolean isBlack() {
        return this == BLACK;
    }

    Color opposite() {
        if (isWhite()) {
            return BLACK;
        } else if (isBlack()) {
            return WHITE;
        }
        return NONE;
    }

}
