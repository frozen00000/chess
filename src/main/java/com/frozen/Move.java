package com.frozen;

import lombok.Value;

@Value
class Move {

    private final Point from;
    private final Point to;

    Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    Move(String userInput) {
        String[] parts = userInput.trim().split("\\s");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Unexpected input format: " + userInput);
        }
        from = Point.of(parts[0]);
        to = Point.of(parts[1]);
    }

}
