package com.frozen;

import org.junit.Before;

class TestWithBoard {

    BoardImpl board;

    @Before
    public void before() {
        board = new BoardImpl(new PieceFactoryImpl(), false);
    }

}
